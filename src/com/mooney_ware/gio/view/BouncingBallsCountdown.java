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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;

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
    
    ListView numbers;
    
    /**
     * @param context
     */
    public BouncingBallsCountdown(Context context) {
        super(context);
        numbers = new ListView(context);
        init();
    }

    /**
     * @param context
     * @param attributes
     */
    public BouncingBallsCountdown(Context context, AttributeSet attributes) {
        super(context, attributes);
        numbers = new ListView(context);
        init();
    }

    // Common setup functions for constructors.
    private void init() {
        mDaysDisplay = new NPlaceNumber(3);
        mHoursDisplay = new NPlaceNumber(2);
        mMinutesDisplay = new NPlaceNumber(2);
        mSecondsDisplay = new NPlaceNumber(2);
    }

    
    
    /* (non-Javadoc)
     * @see com.mooney_ware.gio.view.AbstractCountdownView#onTick(int, int, int, int)
     */
    @Override
    public void onTick(int days, int hours, int minutes, int seconds) {
        // TODO Auto-generated method stub
        super.onTick(days, hours, minutes, seconds);
        mDaysDisplay.setValue(mDays);
        mHoursDisplay.setValue(mHours);
        mMinutesDisplay.setValue(mMinutes);
        mSecondsDisplay.setValue(mSeconds);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        placeDigits();
        
        canvas.drawColor(BG_COLOR);

        placeDigits();
        
//        mDaysDisplay.draw(canvas);
//        mHoursDisplay.draw(canvas);
//        mMinutesDisplay.draw(canvas);
        mSecondsDisplay.draw(canvas);
    }
    
    protected void placeDigits(){
        int width = getWidth();
        int height = getHeight();
        
        int left = getLeft();
        int top = getTop();
        
        int totalDigits = 7;
        int cellWidth = width / totalDigits;
        //place day;
        int counterWidth = 3 * cellWidth;
        Rect bounds = new Rect(left, top, left + counterWidth, top + height);
        mDaysDisplay.setBounds(bounds);
        
        //place hour;
        counterWidth = 2 * cellWidth;
        left += counterWidth;
        bounds = new Rect(left, top, left + counterWidth, top + height);
        mHoursDisplay.setBounds(bounds);
        
        //place minute
        left += counterWidth;
        bounds = new Rect(left, top, left + counterWidth, top + height);
        mMinutesDisplay.setBounds(bounds);
        
        //place second
        left += counterWidth;
        bounds = new Rect(left, top, left + counterWidth, top + height);
        mMinutesDisplay.setBounds(bounds);
    }

    static class NPlaceNumber extends Drawable {

        //TODO could make this more generic by using an abstract type and a factory.
        final BallDigitDrawable[] mDigits;
        final int mNumPlaces;
        final int BASE = 10;
        final int[] DIGIT_MASK;
        
        public NPlaceNumber(int places){
            if(places < 0 )throw new IllegalArgumentException("May not have fewer than 0 places");
            
            mNumPlaces = places;
            
            BallDigitDrawable[] digits = new BallDigitDrawable[places];
            mDigits = digits;
            
            int[] dMask = new int[places]; 
            DIGIT_MASK = dMask;
            
            
            for(int i = 0; i<places; i++){
                digits[i] = new BallDigitDrawable();
                dMask[i] = (int)Math.pow(BASE, i);
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

            this.invalidateSelf();
        }
        
        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
         */
        @Override
        public void draw(Canvas canvas) {
            
            BallDigitDrawable[] digitDisp = mDigits;
//            for(int i = 0; i<digitDisp.length; i++){
//                digitDisp[i].draw(canvas);
//            }
            digitDisp[0].draw(canvas);
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
            BallDigitDrawable[] digitDisp = mDigits;
            for(int i = 0; i<digitDisp.length; i++){
                digitDisp[i].setColorFilter(cf);
            }
        }

        /* (non-Javadoc)
         * @see android.graphics.drawable.Drawable#onBoundsChange(android.graphics.Rect)
         */
        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
        
            int cellWidth = bounds.width() / mNumPlaces;
            int left = bounds.left;
            int top = bounds.top;
            
            int h = bounds.height();
            int n = mNumPlaces;
            
            BallDigitDrawable[] digits = mDigits;
            
            for(int i = 0; i<n; i++){
                Rect b = new Rect(left, top, left+cellWidth, h);
                digits[i].setBounds(bounds);
                left += cellWidth;
            }
        }   
    }
}
