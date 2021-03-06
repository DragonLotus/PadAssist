package com.padassist.Graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by DragonLotus on 7/14/2015.
 */
public class ThinStroke extends TextView {

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(String.format(text.toString()), type);
    }

    public ThinStroke(Context context) {
        super(context);
    }

    public ThinStroke(Context context, AttributeSet attrs){
        super(context, attrs);

        setDrawingCacheEnabled(false);
    }

    public ThinStroke(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    //      Works but side strokes are cut off.
    @Override
    protected void onDraw(Canvas canvas) {
        int textColor = getTextColors().getDefaultColor();
        setTextColor(0xFF555555);
        getPaint().setStrokeWidth(6);
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
