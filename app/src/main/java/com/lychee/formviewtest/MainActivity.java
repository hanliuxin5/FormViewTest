package com.lychee.formviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lychee.formviewtest.lychee.APIContext;
import com.lychee.formviewtest.lychee.FuckJson;
import com.lychee.formviewtest.lychee.LogUtil;
import com.lychee.formviewtest.lychee.MyOkHttpCallback;
import com.lychee.formviewtest.lychee.OldCellBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout fl;

    //理论布局范围
    float maxX;
    float maxY;
    //屏幕宽高
    float width;
    float height;
    //缩放系数
    float scalyX;
    float scalyY;
    int supplementX;
    //初始显示人员viewGroup
    int w_width;
    int w_height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.fl);
        //获取屏幕数据
        getScreenInfo();
        //获取数据
        //fa315fce76c61a319601014f1d601a79 简短
        //9db5fe0082936fa914e5d41cd14e7ddc 简单
        //6ba4a4debe744bcc695559472196c259 复杂
        //ea97edfec1a47f1d27cfc8a68280a423 很复杂混双
        APIContext.findKnockOutScore4OldCells("6ba4a4debe744bcc695559472196c259", new MyOkHttpCallback(this) {
            @Override
            public void onSuccess(String json) {
                json = FuckJson.cao(json, "lotbook", "score");
//                json = FuckJson.gan(json, "seats");
                OldCellBean bean = gson.fromJson(json, OldCellBean.class);
//                LogUtil.d(bean.getResult().getBye() + "," + bean.getResult().getLotbook().size() + "," +
//                        bean.getResult().getSeats().getXline().size());
                analyzeDate(bean);
            }
        });

    }


    public void getScreenInfo() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        LogUtil.i("屏幕尺寸：宽度 = " + width + "px, 高度 = " + height + "px, 密度 = " + dm.densityDpi + "dpi");
    }

    private void seekMaxX(OldCellBean bean) {
        List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> xline = bean.getResult().getSeats().getXline();
        if (xline == null || xline.size() <= 0) {
            return;
        }
        int max = Integer.parseInt(xline.get(0).getX2());
        for (int i = 0; i < xline.size(); i++) {
            int v = Integer.parseInt(xline.get(i).getX2());
            if (v > max) {
                max = v;
            }
        }
        maxX = max;
        LogUtil.d("maxX: " + max);
    }

    private void seekMaxY(OldCellBean bean) {
        List<OldCellBean.ResultEntity.SeatsEntity.YlineEntity> yline = bean.getResult().getSeats().getYline();
        if (yline == null || yline.size() <= 0) {
            return;
        }
        int max = Integer.parseInt(yline.get(0).getY2());
        for (int i = 0; i < yline.size(); i++) {
            int v = Integer.parseInt(yline.get(i).getY2());
            if (v > max) {
                max = v;
            }
        }
        maxY = max;
        LogUtil.d("maxY: " + max);
    }

    private void analyzeDate(OldCellBean bean) {

        seekMaxX(bean);
        seekMaxY(bean);
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity x0 = bean.getResult().getSeats().getXline().get(0);
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity x1 = bean.getResult().getSeats().getXline().get(1);

//        supplementX = Integer.parseInt(x0.getX1());
//        float k1 = (maxX - supplementX) / width;
        float k1 = maxX / width;
        float k2 = maxY / height;


        scalyX = k1;
        scalyY = k1;
//        scalyY = (float) (k1 / 1.5);

        LogUtil.d("scalyX=" + scalyX + ",scalyY" + scalyY);


        w_width = (int) (Integer.parseInt(x0.getX1()) / scalyX);
        w_height = (int) ((Integer.parseInt(x1.getY1()) - Integer.parseInt(x0.getY1())) / 2 / scalyY);

        LogUtil.d("修正前：w_width=" + w_width + ",w_height=" + w_height);

        if (w_width > 400 || w_width < 300) {
            supplementX = w_width - 400;
            w_width = 400;

//            (int) (maxY / scalyY) + 29
//            (int) (width + Math.abs(supplementX))
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fl.setLayoutParams(params);

        }

        drawX(bean.getResult().getSeats().getXline());
        drawY(bean.getResult().getSeats().getYline());


        drawLootBooks(bean.getResult().getLotbook(), bean.getResult().getSeats().getXline());
        LogUtil.d("修正后：w_width=" + w_width + ",w_height=" + w_height + ",supplementX=" + supplementX);


    }

    private void drawX(List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> list) {
        ImageView X;
        FrameLayout.LayoutParams params;
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity item;

        for (int i = 0; i < list.size(); i++) {

            item = list.get(i);
            int width = Math.abs((int) ((Integer.parseInt(item.getX2()) - Integer.parseInt(item.getX1())) / scalyX));
            int height = ScreenUtils.pxToDpCeilInt(this, 2);
            int left = 0;
            if (item.getSortNo().split("-").length == 3) {
                left = (int) (Integer.parseInt(item.getX1()) / scalyX);
            } else if (item.getSortNo().split("-").length == 4) {
                left = (int) (Integer.parseInt(item.getX2()) / scalyX);
            }
            int top = (int) (Integer.parseInt(item.getY1()) / scalyY);

            X = new ImageView(this);
            params = new FrameLayout.LayoutParams(width, height);
            params.leftMargin = left - supplementX;
//            params.leftMargin = left;
            params.topMargin = top;

            if ("1".equals(item.getExtra())) {

                X.setBackgroundDrawable(getResources().getDrawable(R.drawable.dash_line));
                X.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            } else {

                X.setBackgroundColor(Color.GREEN);

            }
            X.setLayoutParams(params);
            fl.addView(X);
        }


    }

    private void drawY(List<OldCellBean.ResultEntity.SeatsEntity.YlineEntity> list) {
        View Y;
        OldCellBean.ResultEntity.SeatsEntity.YlineEntity item;
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            int width = ScreenUtils.pxToDpCeilInt(this, 2);
            int height = (int) ((Integer.parseInt(item.getY2()) - Integer.parseInt(item.getY1())) / scalyY);
            int left = (int) (Integer.parseInt(item.getX1()) / scalyX);
            int top = (int) (Integer.parseInt(item.getY1()) / scalyY);

            Y = new View(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.leftMargin = left - supplementX;
//            params.leftMargin = left;
            params.topMargin = top;
            Y.setBackgroundColor(Color.RED);
            Y.setLayoutParams(params);
            fl.addView(Y);
        }
    }

    private void drawLootBooks(List<OldCellBean.ResultEntity.LotbookEntity> list, List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> xList) {
        FrameLayout.LayoutParams fParams;
        LinearLayout whole;

        LinearLayout.LayoutParams tParams;
        TextView tv_order;
        TextView tv_number;


        LinearLayout.LayoutParams lParams;
        LinearLayout people;

        LinearLayout.LayoutParams aParams;
        ImageView avatar;
        LinearLayout.LayoutParams nParams;
        TextView name;

        OldCellBean.ResultEntity.SeatsEntity.XlineEntity x0;
        OldCellBean.ResultEntity.LotbookEntity lot;
        int p_x = 0;
        int p_y;

        for (int i = 0; i < list.size(); i++) {
            x0 = xList.get(i);
            lot = list.get(i);

            fParams = new FrameLayout.LayoutParams(w_width, w_height);
            fParams.leftMargin = p_x;
            p_y = (int) (Integer.parseInt(x0.getY1()) / scalyY - w_height / 2);
            fParams.topMargin = p_y;


            whole = new LinearLayout(this);
            whole.setLayoutParams(fParams);
            whole.setOrientation(LinearLayout.HORIZONTAL);

            tParams = new LinearLayout.LayoutParams(0, w_height, 1.0f);
            tv_order = new TextView(this);
            tv_number = new TextView(this);
            tv_order.setLayoutParams(tParams);
            tv_number.setLayoutParams(tParams);
            tv_order.setGravity(Gravity.CENTER);
            tv_number.setGravity(Gravity.CENTER);
            tv_order.setTextSize(8);
            tv_number.setTextSize(8);
            tv_order.setText(lot.getSortNo());
            tv_number.setText(lot.getCodeLetter());

            lParams = new LinearLayout.LayoutParams(w_width / 22 * 15, w_height);
            people = new LinearLayout(this);
            people.setOrientation(LinearLayout.HORIZONTAL);
            people.setLayoutParams(lParams);
            people.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_frame_red));


//            if (!lot.getIcon().contains(";")) {
//                //单团
//                avatar = new ImageView(this);
//                aParams = new LinearLayout.LayoutParams(w_height, w_height);
//                avatar.setLayoutParams(aParams);
//                ImageLoader.getInstance().displayImage(UiHelper.getImageUrl(lot.getIcon()), avatar, UiHelper.r360Options());
//
//                name = new TextView(this);
//                nParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
//                name.setLayoutParams(nParams);
//                name.setText(lot.getName());
//            } else if (lot.getIcon().split(";").length == 2) {
//                //双
//
//            }


            whole.addView(tv_order);
            whole.addView(tv_number);
            whole.addView(people);

            fl.addView(whole);
        }
    }


}
