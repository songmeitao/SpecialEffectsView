package com.wingsofts.simplelinechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songmeitao on 2016/6/27.
 */
public class SpecialEffectsButton extends View {

    private Context context;
    private Paint textPaint;
    private boolean isfollow = false;
    private Paint bgPaint;
    private int radius = -1;
    private int height;
    private int width;
    private int time = 1;
    private float centerX;
    private float centerY;

    Handler timerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            radius += 10;
            if (radius <= width * 2) {
                timerHandler.sendEmptyMessageDelayed(time, time);
                invalidate();
            } else {
                radius = -1;
            }
        }
    };


    public SpecialEffectsButton(Context context) {
        super(context);
        init(context);
    }


    public SpecialEffectsButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpecialEffectsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        this.context = context;

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.parseColor("#ECFAF2"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#B7B7B7"));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        //计算文字baseline
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawColor(isfollow == true ? Color.parseColor("#00CE7E") : Color.parseColor("#B7B7B7"));
        bgPaint.setColor(isfollow == true ? Color.parseColor("#B7B7B7") : Color.parseColor("#00CE7E"));
        canvas.drawCircle(centerX,centerY, radius, bgPaint);
        canvas.drawText(isfollow == true ? "取消啊" : "关注", width / 2, textBaseY, textPaint);
    }


    private boolean isValidClick(float x, float y) {
        if (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()) {
            return true;
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                centerX = event.getX();
                centerY = event.getY();

                isfollow = !isfollow;
                timerHandler.sendEmptyMessageDelayed(time, time);

                return true;
        }
        return false;
    }


}