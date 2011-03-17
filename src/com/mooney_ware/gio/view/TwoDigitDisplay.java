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
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Sean Mooney
 *
 */
public class TwoDigitDisplay extends View implements DigitDisplay{

    BallDigitDrawable[] mDigits;
    int mNumPlaces;
    final int BASE = 10;
    int[] DIGIT_MASK;

    /**
     * @param context
     */
    public TwoDigitDisplay(Context context) {
        super(context);
        init(context);
    }
    
    public TwoDigitDisplay(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context){
        int places = 2;
        if(places < 0 )throw new IllegalArgumentException("May not have fewer than 0 places");
        
        setBackgroundColor(Color.WHITE);
        
        mNumPlaces = places;
        
        BallDigitDrawable[] digits = new BallDigitDrawable[places];
        mDigits = digits;
        
        int[] dMask = new int[places]; 
        DIGIT_MASK = dMask;
        
        
        for(int i = 0; i<places; i++){
            digits[i] = new BallDigitDrawable();
            dMask[i] = (int)Math.pow(BASE, i);
        }
//        
//        digits[0].setBounds(new Rect(60, 10, 50, 200));
//        digits[1].setBounds(new Rect(10, 10, 50, 200));
//        
        setValue(0);
    }
    
    
    
    
    /* (non-Javadoc)
     * @see android.view.View#onSizeChanged(int, int, int, int)
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        BallDigitDrawable[] digits = mDigits;


        int n = digits.length - 1;
        int x = 10;
        int topY = getPaddingTop() + 10;
        int newHeight = h - (getPaddingTop() + getPaddingBottom());
        for(int i = n; i>=0; i--){
            int cellWidth = digits[i].getIntrinsicWidth();
            Rect newBounds  = new Rect(x, topY, cellWidth, newHeight);
            digits[i].setBounds(newBounds);
            x += cellWidth;
        }
    }

    @Override 
    protected void onDraw(Canvas canvas){
        BallDigitDrawable[] digits = mDigits;
        super.onDraw(canvas);
        
        Paint p = new Paint();
        p.setColor(0x00FF0000);
        canvas.drawRect(
                new Rect(getLeft(),
                        getTop(),
                        getRight(),
                        getBottom())
                , p);

        for(int i = 0; i<digits.length; i++){
            digits[i].draw(canvas);
        }
        
    }
    
    public void setValue(int v){
        int value = v;
        BallDigitDrawable[] digitDisp = mDigits;

        for(int i = mNumPlaces - 1; i>=0; i--){
            int placeValue = value / DIGIT_MASK[i];
            value -= placeValue * DIGIT_MASK[i];

            digitDisp[i].setValue(placeValue);
        }
        invalidate();
    }
}
