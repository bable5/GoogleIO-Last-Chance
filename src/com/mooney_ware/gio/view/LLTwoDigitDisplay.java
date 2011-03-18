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
import android.graphics.Color;
import android.util.AttributeSet;
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
        
        ImageView ivOnes = new ImageView(context);
        ivOnes.setImageDrawable(digits[0]);
        addView(ivOnes);
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
        for(BallDigitDrawable bdd : mDigits){
            bdd.registerSegmentListener(listener);
        }
    }
}
