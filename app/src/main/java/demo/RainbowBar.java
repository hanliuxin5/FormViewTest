package demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lychee.formviewtest.R;
import com.lychee.formviewtest.ScreenUtils;

/**
 * Created by lychee on 2016/8/30.
 */
public class RainbowBar extends View {

    int barColor1 = Color.parseColor("#1E88E5");
    int barColor2 = Color.parseColor("#000000");

    int rWeight = ScreenUtils.dpToPxInt(getContext(), 80);

    int rHeight = ScreenUtils.dpToPxInt(getContext(), 30);

    int space = ScreenUtils.dpToPxInt(getContext(), 10);


    float startX = 0;
    float delta = 10f;
    Paint mPaint1;
    Paint mPaint2;

    public RainbowBar(Context context) {
        super(context);
    }

    public RainbowBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RainbowBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.rainbowbar, 0, 0);
        rWeight = t.getDimensionPixelOffset(R.styleable.rainbowbar_rainbowbar_hspace, rWeight);
        rHeight = t.getDimensionPixelOffset(R.styleable.rainbowbar_rainbowbar_vspace, rHeight);
        barColor1 = t.getColor(R.styleable.rainbowbar_rainbowbar_color, barColor1);
        t.recycle();


    }


//    //draw be invoke numbers.
//    int index = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(barColor1);
        mPaint1.setStrokeWidth(space);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(barColor2);
        mPaint2.setStrokeWidth(space);

        float sw = this.getMeasuredWidth();

        if (startX >= sw + (rWeight + space) - (sw % (rWeight + space))) {
            startX = 0;
        } else {
            startX += delta;
        }

        float start = startX;
        // draw latter parse
        while (start < sw) {
            canvas.drawLine(start, 5, start + rWeight, 5, mPaint1);
            start += (rWeight + space);
        }

        start = startX - space - rWeight;

        // draw front parse
        while (start >= -rWeight) {
            canvas.drawLine(start, 5, start + rWeight, 5, mPaint2);
            start -= (rWeight + space);
        }

//        if (index >= 700000) {
//            index = 0;
//        }

        invalidate();
    }
}
