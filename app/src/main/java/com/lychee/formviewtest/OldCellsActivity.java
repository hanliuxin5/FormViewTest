package com.lychee.formviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.lychee.formviewtest.lychee.UiHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OldCellsActivity extends AppCompatActivity {

    FrameLayout fl;
//    Button b1;
//    Button b2;
//    Button b3;
//    Button b4;

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
    int supplementY;

    //初始显示人员viewGroup
    int w_width;
    int w_height;
    int fontSize = 12;
    float avatarSize = 1;
    //比赛类型
    int raceType;
    //补全完整的比分表
    ArrayList<OldCellBean.ResultEntity.ScoreEntity> scoreList = new ArrayList<>();
    HashMap<String, OldCellBean.ResultEntity.ScoreEntity> scoreHM = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout) findViewById(R.id.fl);
        hideSystemUI();
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSystemUI();
            }
        });
        //获取屏幕数据
        getScreenInfo();
        //获取数据
        //fa315fce76c61a319601014f1d601a79 简短
        //9db5fe0082936fa914e5d41cd14e7ddc 简单
        //6ba4a4debe744bcc695559472196c259 复杂
        //ea97edfec1a47f1d27cfc8a68280a423 很复杂混双
        //2150326876238429a7f45263a2252c11 待开赛状态

        loadUI("2150326876238429a7f45263a2252c11");

    }


    private void loadUI(String id) {
        fl.removeAllViews();
        APIContext.findKnockOutScore4OldCells(id, new MyOkHttpCallback(this) {
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

    private void getScreenInfo() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        LogUtil.i("屏幕尺寸：宽度 = " + width + "px, 高度 = " + height + "px, 密度 = " + dm.densityDpi + "dpi");
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
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

    private void complementScore(OldCellBean bean) {
        List<OldCellBean.ResultEntity.LotbookEntity> lotBoos = bean.getResult().getLotbook();
        if (lotBoos == null || lotBoos.size() <= 0) {
            return;
        }

        //修正初始lotbook
        for (int i = 0; i < lotBoos.size() / 2; i++) {
            OldCellBean.ResultEntity.LotbookEntity lot1 = lotBoos.get(i * 2);
            OldCellBean.ResultEntity.LotbookEntity lot2 = lotBoos.get(i * 2 + 1);
            int seatType1 = Integer.parseInt(lot1.getSeatType());
            int seatType2 = Integer.parseInt(lot2.getSeatType());

            if (seatType1 > 0 || seatType2 > 0) {
                for (int j = i; j < lotBoos.size() / 2; j++) {
                    if (scoreList.get(j).getFightGroupTurnNo().equals("1")) {
                        OldCellBean.ResultEntity.ScoreEntity normal = scoreList.get(j);
                        int rounder = Integer.parseInt(normal.getRoundOrder());
                        normal.setRoundOrder((rounder + 1) + "");
                    }
                }
                OldCellBean.ResultEntity.ScoreEntity fix = new OldCellBean.ResultEntity.ScoreEntity();
                fix.setFightGroupTurnNo("1");
                fix.setFightGroupTurGroupNo("1");
                fix.setRoundOrder((i + 1) + "");

                fix.setPlayer1Id(lot1.getPlayerId());
                fix.setPlayer1(lot1.getName());

                fix.setPlayer2Id(lot2.getPlayerId());
                fix.setPlayer2(lot2.getName());

                fix.setScore1(seatType1 > 0 ? "0" : "3");
                fix.setScore2(seatType2 > 0 ? "0" : "3");

                fix.setIcon1(lot1.getIcon());
                fix.setIcon2(lot2.getIcon());
                fix.setStatus("-1");
                scoreList.add(i, fix);
            }
        }

        for (int i = 0; i < scoreList.size(); i++) {
            OldCellBean.ResultEntity.ScoreEntity score = scoreList.get(i);
            String id = score.getFightGroupTurnNo() + "-" + score.getFightGroupTurGroupNo() + "-" + score.getRoundOrder();
            scoreHM.put(id, score);
        }
    }

    private void analyzeDate(OldCellBean bean) {

        seekMaxX(bean);
        seekMaxY(bean);
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity x0 = bean.getResult().getSeats().getXline().get(0);
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity x1 = bean.getResult().getSeats().getXline().get(1);
        raceType = Integer.parseInt(bean.getResult().getRaceType());
        scoreList.addAll(bean.getResult().getScore());
        complementScore(bean);
//        supplementX = Integer.parseInt(x0.getX1());
//        float k1 = (maxX - supplementX) / width;
        float k1 = maxX / width;
//        float k1 = 1f;

        float k2 = maxY / height;


        scalyX = k1;
        scalyY = k2;
//        if (k1 > 12) {
////            scalyY = k1;
////        scalyY = (float) (k1 / 1.5);
//            fontSize = (int) (fontSize / scalyY * 8 + 0.5f);
//        }
//        if (raceType == 2) {
//            avatarSize = 0.6f;
//        }
//        if(raceType)
//        scalyY = (float) (k1 / 1.5);
        LogUtil.d("scalyX=" + scalyX + ",scalyY=" + scalyY);
        LogUtil.d("k1=" + k1 + ",k2=" + k2);
        LogUtil.d("fontSize=" + fontSize);

        w_width = (int) (Integer.parseInt(x0.getX1()) / scalyX + 0.5f);
        w_height = (int) ((Integer.parseInt(x1.getY1()) - Integer.parseInt(x0.getY1())) / 2 / scalyY + 0.5f);

        LogUtil.d("修正前：w_width=" + w_width + ",w_height=" + w_height);

        if (w_width < 150) {
            supplementX = w_width - 200;
            w_width = 200;
            if (raceType == 2) {
                supplementX = w_width - 300;
                w_width = 300;
            }
        }
//        else if (w_width > 400) {
//            supplementX = w_width - 300;
//            w_width = 300;
//            if (raceType == 2) {
//                supplementX = w_width - 600;
//                w_width = 600;
//            }
//            supplementX = w_width - 400;
//            w_width = 400;
//            fontSize = fontSize * w_width / 400;
//        }


        fontSize = (int) (fontSize * (w_height / 46.0f / 1.7f));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        fl.setBackgroundColor(Color.parseColor("#ffffff"));
        fl.setLayoutParams(params);
        supplementY = (int) (Integer.parseInt(x0.getY1()) / scalyY - w_height / 2);

        drawX(bean.getResult().getSeats().getXline());
        drawY(bean.getResult().getSeats().getYline());


        drawLotBooks(bean.getResult().getLotbook(), bean.getResult().getSeats().getXline());
        LogUtil.d("修正后：w_width=" + w_width + ",w_height=" + w_height +
                ",supplementX=" + supplementX + ",supplementY=" + supplementY +
                ",fontSize=" + fontSize);


    }

    private void drawX(List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> list) {
        ImageView X;
        FrameLayout.LayoutParams params;
        OldCellBean.ResultEntity.SeatsEntity.XlineEntity item;

        for (int i = 0; i < list.size(); i++) {

            item = list.get(i);
            int width = Math.abs((int) ((Integer.parseInt(item.getX2()) - Integer.parseInt(item.getX1())) / scalyX + 0.5f));
            int height = ScreenUtils.pxToDpCeilInt(this, 2);
            int left = 0;
            if (item.getSortNo().split("-").length == 3) {
                left = (int) (Integer.parseInt(item.getX1()) / scalyX + 0.5f);
            } else if (item.getSortNo().split("-").length == 4) {
                left = (int) (Integer.parseInt(item.getX2()) / scalyX + 0.5f);

            }
            int top = (int) (Integer.parseInt(item.getY1()) / scalyY);
//            LogUtil.d("X轴线长度" + left);

            X = new ImageView(this);
            params = new FrameLayout.LayoutParams(width, height);
            params.leftMargin = left - supplementX;
//            params.leftMargin = left;
            params.topMargin = top - supplementY;

            if ("1".equals(item.getExtra())) {
                X.setBackgroundColor(Color.BLUE);
//                X.setBackgroundDrawable(getResources().getDrawable(R.drawable.dash_line));
//                X.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            } else {
                X.setBackgroundColor(Color.GREEN);
            }
            if (item.getSortNo().split("-").length == 3 && !item.getSortNo().startsWith("0")) {
                drawResults(left, top, item.getSortNo());
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
            params.topMargin = top - supplementY;
            Y.setBackgroundColor(Color.RED);
            Y.setLayoutParams(params);
            fl.addView(Y);

        }
    }

    private void drawLotBooks(List<OldCellBean.ResultEntity.LotbookEntity> list, List<OldCellBean.ResultEntity.SeatsEntity.XlineEntity> xList) {
        FrameLayout.LayoutParams fParams;
        LinearLayout whole;

        LinearLayout.LayoutParams tParams;
        TextView tv_order;
        TextView tv_number;


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
            fParams.topMargin = p_y - supplementY;

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
            tv_order.setTextSize(fontSize);
            tv_number.setTextSize(fontSize);

            //种序
            tv_order.setText(lot.getSortNo());
            //签号
            tv_number.setText(lot.getCodeLetter());

            whole.addView(tv_order);
            whole.addView(tv_number);
            whole.addView(drawLotBook(lot));

            fl.addView(whole);
        }
    }

    private void drawResults(int left, int top, String id) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height * 2);
        params.leftMargin = left - supplementX;
        params.topMargin = top - w_height - supplementY;
        layout.setLayoutParams(params);

//        LogUtil.d("id===" + id);
//        layout.setBackgroundColor(Color.parseColor("#66ccff"));
        layout.addView(drawResultTop(scoreHM.get(id)));
        layout.addView(drawResultBottom(scoreHM.get(id)));

        fl.addView(layout);

    }

    private LinearLayout drawLotBook(OldCellBean.ResultEntity.LotbookEntity lot) {
        LinearLayout.LayoutParams lParams;
        LinearLayout people;

        LinearLayout.LayoutParams aParams;
        ImageView avatar;
        LinearLayout.LayoutParams nParams;
        TextView name;

        lParams = new LinearLayout.LayoutParams(w_width / 22 * 15, w_height);
        people = new LinearLayout(this);
        people.setOrientation(LinearLayout.HORIZONTAL);
        people.setLayoutParams(lParams);

        people.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_frame_red));

        switch (raceType) {
            case 1:
            case 3:
                avatar = new ImageView(this);
                aParams = new LinearLayout.LayoutParams((int) (w_height * avatarSize) - 4, (int) (w_height * avatarSize) - 4);
                aParams.leftMargin = 4;
                aParams.topMargin = 2;
                aParams.bottomMargin = 2;
                aParams.gravity = Gravity.CENTER_VERTICAL;
                avatar.setLayoutParams(aParams);
                ImageLoader.getInstance().displayImage(UiHelper.getImageUrl(lot.getIcon()), avatar, UiHelper.r360Options());

                name = new TextView(this);
                nParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
                name.setLayoutParams(nParams);
                name.setGravity(Gravity.CENTER);
//                name.setBackgroundColor(Color.parseColor("#aabbff"));
                name.setText(lot.getName().equals("") ? "bye" : lot.getName());
                name.setTextSize(fontSize);

                people.addView(avatar);
                people.addView(name);
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    avatar = new ImageView(this);
                    aParams = new LinearLayout.LayoutParams((int) (w_height * avatarSize) - 4, (int) (w_height * avatarSize) - 4);
                    aParams.leftMargin = 4;
                    aParams.topMargin = 2;
                    aParams.bottomMargin = 2;
                    aParams.gravity = Gravity.CENTER_VERTICAL;
                    avatar.setLayoutParams(aParams);
                    ImageLoader.getInstance().displayImage(UiHelper.getImageUrl(lot.getIcon().contains(";") ? lot.getIcon().split(";")[i] : ""), avatar, UiHelper.r360Options());

                    people.addView(avatar);
                }

                name = new TextView(this);
                nParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
                name.setLayoutParams(nParams);
                name.setGravity(Gravity.CENTER);
                name.setText(lot.getName().equals("") ? "bye" : lot.getName());
                name.setTextSize(fontSize);

                people.addView(name);
                break;
        }

        return people;
    }

    private LinearLayout drawResultTop(OldCellBean.ResultEntity.ScoreEntity score) {
        LinearLayout.LayoutParams lParams;
        LinearLayout result;

        LinearLayout.LayoutParams aParams;
        ImageView avatar;
        LinearLayout.LayoutParams nParams;
        TextView name;

        lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
//        lParams = new LinearLayout.LayoutParams(w_width / 22 * 15, w_height);
        result = new LinearLayout(this);
        result.setOrientation(LinearLayout.HORIZONTAL);
        result.setLayoutParams(lParams);


        String icon;
        String player;
        int score1 = 0;
        int score2 = 0;
        if (!TextUtils.isEmpty(score.getScore1()))
            score1 = Integer.parseInt(score.getScore1());
        if (!TextUtils.isEmpty(score.getScore2()))
            score2 = Integer.parseInt(score.getScore2());
        if (score1 > score2) {
            icon = score.getIcon1();
            player = score.getPlayer1();
        } else if (score1 < score2) {
            icon = score.getIcon2();
            player = score.getPlayer2();
        } else {
            icon = "";
            player = "";
        }


        switch (raceType) {
            case 1:
            case 3:
                avatar = new ImageView(this);
                aParams = new LinearLayout.LayoutParams((int) (w_height * avatarSize), (int) (w_height * avatarSize));
                aParams.gravity = Gravity.CENTER_VERTICAL;
                avatar.setLayoutParams(aParams);
                ImageLoader.getInstance().displayImage(UiHelper.getImageUrl(icon), avatar, UiHelper.r360Options());

                name = new TextView(this);
                nParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (w_height * avatarSize));
                name.setLayoutParams(nParams);
                name.setText(player);
                name.setGravity(Gravity.CENTER);

                name.setTextSize(fontSize);

                result.addView(avatar);
                result.addView(name);
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    avatar = new ImageView(this);
                    aParams = new LinearLayout.LayoutParams((int) (w_height * avatarSize), w_height);
                    aParams.gravity = Gravity.CENTER_VERTICAL;
                    avatar.setLayoutParams(aParams);

                    ImageLoader.getInstance().displayImage(UiHelper.getImageUrl(icon.contains(";") ? icon.split(";")[i] : ""), avatar, UiHelper.r360Options());

                    result.addView(avatar);
                }

                name = new TextView(this);
                nParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
                name.setLayoutParams(nParams);
                name.setGravity(Gravity.CENTER);
                name.setText(player.equals("") ? "" : player);
                name.setTextSize(fontSize);

                result.addView(name);
                break;
        }

        return result;
    }

    private TextView drawResultBottom(OldCellBean.ResultEntity.ScoreEntity score) {
        LinearLayout.LayoutParams tParams;
        TextView tv;

        tParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, w_height);
//        tParams.gravity = Gravity.CENTER_VERTICAL;
        tv = new TextView(this);
        tv.setTextSize(fontSize);
        tv.setLayoutParams(tParams);

//        LogUtil.d("status==" + score.getStatus());
        //状态
        switch (Integer.parseInt(score.getStatus())) {
            case -1:
                //轮空
                tv.setText("轮空");
                break;
            case 0:
                //待开赛
                tv.setText("待开赛");
                break;
            case 1:
                //双方弃权
                tv.setText("双弃");
                break;
            case 2:
                //已结束
                tv.setText(score.getScore1() + ":" + score.getScore2());
                break;
            case 3:
                //比赛进行中
                tv.setText("进行中");
                break;
            case 4:
                //选手1弃权
                tv.setText("P1弃权");
                break;
            case 5:
                //选手2弃权
                tv.setText("P2弃权");
                break;
            case 6:
                //选手1退赛
                tv.setText("P1退赛");
                break;
            case 7:
                //选手2退赛
                tv.setText("P2退赛");
                break;
        }
        return tv;
    }
}
