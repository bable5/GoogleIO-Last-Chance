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
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * @author Sean Mooney
 *
 */
public class BouncingBallsCountdown extends AbstractCountdownView {

    static final int BG_COLOR = Color.WHITE;
    
    NPlaceNumber mDaysDisplay;
    NPlaceNumber mHoursDisplay;
    NPlaceNumber mMinutesDisplay;
    NPlaceNumber mSecondsDisplay;
    
    /**
     * @param context
     */
    public BouncingBallsCountdown(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attributes
     */
    public BouncingBallsCountdown(Context context, AttributeSet attributes) {
        super(context, attributes);
        init();
    }

    // Common setup functions for constructors.
    private void init() {
        mDaysDisplay = new NPlaceNumber(3);
        mHoursDisplay = new NPlaceNumber(2);
        mMinutesDisplay = new NPlaceNumber(2);
        mSecondsDisplay = new NPlaceNumber(2);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.drawColor(BG_COLOR);
        
        mSecondsDisplay.setValue(mSeconds);
        mSecondsDisplay.draw(canvas);
    }

    static class NPlaceNumber extends Drawable {

        //TODO could make this more generic by using an abstract type and a factory.
        final BallDisplay[] mDigits;
        final int mNumPlaces;
        final int BASE = 10;
        final int[] DIGIT_MASK;
        
        public NPlaceNumber(int places){
            if(places < 0 )throw new IllegalArgumentException("May not have fewer than 0 places");
            
            mNumPlaces = places;
            
            BallDisplay[] digits = new BallDisplay[places];
            mDigits = digits;
            
            int[] dMask = new int[places]; 
            DIGIT_MASK = dMask;
            
            
            for(int i = 0; i<places; i++){
                digits[i] = new BallDisplay();
                dMask[i] = (int)Math.pow(BASE, i);
            }
        }
        
        public void setValue(int v){
            int value = v;
            

            BallDisplay[] digitDisp = mDigits;
            
        
            for(int i = mNumPlaces - 1; i>=0; i--){
                int placeValue = value / DIGIT_MASK[i];
                value -= placeValue * DIGIT_MASK[i];
                
                digitDisp[i].setValue(placeValue);
            }
            
            this.invalidateSelf();
        }
        
        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
         */
        @Override
        public void draw(Canvas canvas) {
            
            BallDisplay[] digitDisp = mDigits;
            for(int i = 0; i<digitDisp.length; i++){
                digitDisp[i].draw(canvas);
            }
        
        }

        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#getOpacity()
         */
        @Override
        public int getOpacity() {
            // TODO Auto-generated method stub
            return 0;
        }

        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#setAlpha(int)
         */
        @Override
        public void setAlpha(int alpha) {
            // TODO Auto-generated method stub
            
        }

        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
         */
        @Override
        public void setColorFilter(ColorFilter cf) {
            BallDisplay[] digitDisp = mDigits;
            for(int i = 0; i<digitDisp.length; i++){
                digitDisp[i].setColorFilter(cf);
            }
        }
        
    }
}
