package com.lychee.formviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.lychee.formviewtest.lychee.APIContext;
import com.lychee.formviewtest.lychee.FuckJson;
import com.lychee.formviewtest.lychee.LogUtil;
import com.lychee.formviewtest.lychee.MyOkHttpCallback;
import com.lychee.formviewtest.lychee.OldCellBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout fl;

    float k1;
    float k2;
    //理论范围
    float maxX;
    float maxY;
    //屏幕宽高
    float width;
    float height;
    //缩放系数
    float scalyX;
    float scalyY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.fl);
        //获取屏幕数据
        getScreenInfo();
        //获取数据
        //9db5fe0082936fa914e5d41cd14e7ddc 简单
        //6ba4a4debe744bcc695559472196c259 复杂
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

        k1 = maxX / width;
        k2 = maxY / height;


        scalyX = k1;
        scalyY = k1;
        LogUtil.d("scalyX=" + scalyX + ",scalyY" + scalyY);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) width, (int) (maxY / scalyY) + 40);
        params.gravity = Gravity.CENTER;
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fl.getLayoutParams();
//        params.width = (int) width;
//        params.height = params.height + 100;
        fl.setBackgroundColor(Color.WHITE);
        fl.setLayoutParams(params);
//        fl.requestLayout();

        drawX(bean.getResult().getSeats().getXline());
//        drawY(bean.getResult().getSeats().getYline());


    }

    private void drawX(List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> list) {
        View X;
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity item;
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            int width = Math.abs((int) ((Integer.parseInt(item.getX2()) - Integer.parseInt(item.getX1())) / scalyX));
            int height = 2;
            int left = 0;
            if (item.getSortNo().split("-").length == 3) {
                left = (int) (Integer.parseInt(item.getX1()) / scalyX);
            } else if (item.getSortNo().split("-").length == 4) {
                left = (int) (Integer.parseInt(item.getX2()) / scalyX);
            }
            int top = (int) (Integer.parseInt(item.getY1()) / scalyX);

            X = new View(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.leftMargin = left;
            params.topMargin = top;

            if ("1".equals(item.getExtra())) {
//                if (item.getSortNo().split("-").length == 3) {
//                    X.setBackgroundColor(getResources().getColor(R.color.blue));
//                } else if (item.getSortNo().split("-").length == 4) {
//                    X.setBackgroundColor(getResources().getColor(R.color.black));
//                }
            } else {
                X.setBackgroundColor(getResources().getColor(R.color.green));
                X.setLayoutParams(params);
                fl.addView(X);
            }
//            X.setLayoutParams(params);
//            fl.addView(X);
        }


    }

    private void drawY(List<OldCellBean.ResultEntity.SeatsEntity.YlineEntity> list) {
        View Y;
        OldCellBean.ResultEntity.SeatsEntity.YlineEntity item;
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            int width = 2;
            int height = (int) ((Integer.parseInt(item.getY2()) - Integer.parseInt(item.getY1())) / scalyX);
            int left = (int) (Integer.parseInt(item.getX1()) / scalyX);
            int top = (int) (Integer.parseInt(item.getY1()) / scalyX);

            Y = new View(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.leftMargin = left;
            params.topMargin = top;
            Y.setBackgroundColor(Color.RED);
            Y.setLayoutParams(params);
            fl.addView(Y);
        }
    }

}
