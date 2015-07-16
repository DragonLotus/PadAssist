package com.example.anthony.damagecalculator.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by DragonLotus on 7/14/2015.
 */
public class TextStroke extends TextView {

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(String.format(text.toString()), type);
    }

    public TextStroke(Context context) {
        super(context);
    }

    public TextStroke(Context context, AttributeSet attrs){
        super(context, attrs);

        setDrawingCacheEnabled(false);
    }

    public TextStroke(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

//      Works but side strokes are cut off.
    @Override
    protected void onDraw(Canvas canvas) {
        int textColor = getTextColors().getDefaultColor();
        setTextColor(0xFF000000);
        getPaint().setStrokeWidth(10);
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setAntiAlias(true);
        getPaint().setStrokeJoin(Paint.Join.ROUND);
        super.onDraw(canvas);
        setTextColor(textColor);
        getPaint().setStrokeWidth(0);
        getPaint().setStyle((Paint.Style.FILL));
        super.onDraw(canvas);
    }
}
