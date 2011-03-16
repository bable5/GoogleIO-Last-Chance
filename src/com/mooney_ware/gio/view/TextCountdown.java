/**
 * Copyright 2010 Sean L. Mooney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package com.mooney_ware.gio.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * A simple text based countdown display.
 * 
 * @author Sean Mooney
 * 
 */
public class TextCountdown extends AbstractCountdownView {

    private Paint mPaint;
    private static final int BG_COLOR = Color.WHITE;

    /**
     * @param context
     */
    public TextCountdown(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTypeface(Typeface.SERIF);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.drawColor(BG_COLOR);
        
        int x = 100, y = 100;
        
        canvas.drawText(formatTime(), x, y, mPaint);
    }

    /**
     * Create a string for the amount of time left.
     * 
     * @return
     */
    private String formatTime() {
        StringBuilder sb = new StringBuilder();

        final String SEP = ":";

        sb.append(mDays);
        sb.append(SEP);
        sb.append(mHours);
        sb.append(SEP);
        sb.append(mMinutes);
        sb.append(SEP);
        sb.append(mSeconds);
        sb.append(SEP);

        return sb.toString();
    }

}
