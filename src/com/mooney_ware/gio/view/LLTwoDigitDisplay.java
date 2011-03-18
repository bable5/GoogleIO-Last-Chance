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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Sean Mooney
 *
 */
public class LLTwoDigitDisplay extends LinearLayout implements DigitDisplay{

    BallDigitDrawable[] mDigits;
    int mNumPlaces;
    final int BASE = 10;
    int[] DIGIT_MASK;
    private ArrayList<DigitDisplay.SegmentLightListener> segListeners = new ArrayList<DigitDisplay.SegmentLightListener>();


    /**
     * @param context
     * @param attrs
     */
    public LLTwoDigitDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * @param context
     */
    public LLTwoDigitDisplay(Context context) {
        super(context);
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
        setValue(0);

        ImageView ivTens = new ImageView(context);
        ivTens.setImageDrawable(digits[1]);
        addView(ivTens);
        
        AdjustingSegListener tensListener = new AdjustingSegListener(ivTens);
        digits[1].registerSegmentListener(tensListener);
        
        ImageView ivOnes = new ImageView(context);
        ivOnes.setImageDrawable(digits[0]);
        addView(ivOnes);
        AdjustingSegListener onesListener = new AdjustingSegListener(ivOnes);
        digits[0].registerSegmentListener(onesListener);
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


    /* (non-Javadoc)
     * @see com.mooney_ware.gio.view.DigitDisplay#registerSegmentListenet(com.mooney_ware.gio.view.DigitDisplay.SegmentLightListener)
     */
    @Override
    public void registerSegmentListener(SegmentLightListener listener) {
        synchronized (segListeners) {
            segListeners.add(listener);
        }
    }

    private class AdjustingSegListener implements DigitDisplay.SegmentLightListener{

        View mParent;
        public AdjustingSegListener(View parent){
            mParent = parent;
        }

        /* (non-Javadoc)
         * @see com.mooney_ware.gio.view.DigitDisplay.SegmentLightListener#onSegmentLight(java.util.List)
         */
        @Override
        public void onSegmentLight(List<RectF> segmentBounds) {
            synchronized(segListeners){

            }
        }

        /* (non-Javadoc)
         * @see com.mooney_ware.gio.view.DigitDisplay.SegmentLightListener#onSegmentDark(java.util.List)
         */
        @Override
        public void onSegmentDark(List<RectF> segmentBounds) {
            synchronized(segListeners){  
                
                if(segListeners.isEmpty()) return;
                
                float xOffset = getmXOffset();
                float yOffset = getmYOffset();
                for(RectF r : segmentBounds){
                    r.left = r.left + xOffset;
                    r.top = r.top + yOffset;
                    r.right = r.right + xOffset;
                    r.bottom = r.bottom + yOffset;
                }
                for(SegmentLightListener sll : segListeners){
                    sll.onSegmentDark(segmentBounds);
                }
            }
        }

        /**
         * @return the mXOffset
         */
        public synchronized float getmXOffset() {
            return mParent.getLeft();
        }

//        /**
//         * @param mXOffset the mXOffset to set
//         */
//        public synchronized void setmXOffset(float mXOffset) {
//            this.mXOffset = mXOffset;
//        }

        /**
         * @return the mYOffset
         */
        public synchronized float getmYOffset() {
            return mParent.getTop();
        }

//        /**
//         * @param mYOffset the mYOffset to set
//         */
//        public synchronized void setmYOffset(float mYOffset) {
//            this.mYOffset = mYOffset;
//        }

    }

    /* (non-Javadoc)
     * @see com.mooney_ware.gio.view.DigitDisplay#getIntrinsicWidth()
     */
    @Override
    public int getIntrinsicWidth() {
        int n = mNumPlaces;
        BallDigitDrawable[] digits = mDigits;
        int acc = 0;
        for(int i = 0; i<n; i++){
            acc+= digits[i].getIntrinsicWidth();
        }
        return acc;
    }

    /* (non-Javadoc)
     * @see com.mooney_ware.gio.view.DigitDisplay#getIntrinsicHeight()
     */
    @Override
    public int getIntrinsicHeight() {
        int n = mNumPlaces;
        BallDigitDrawable[] digits = mDigits;
        int acc = 0;
        for(int i = 0; i<n; i++){
            acc+= digits[i].getIntrinsicHeight();
        }
        return acc;
    }
}
