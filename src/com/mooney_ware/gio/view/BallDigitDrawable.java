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
import java.util.Arrays;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mooney_ware.gio.GoogleIOCountdown;

/**
 * @author Sean Mooney
 *
 */
public class BallDigitDrawable extends Drawable implements DigitDisplay{

    private int mAlpha;

    private int value;

    private static final int CELLS_ACCROSS = 4;
    private static final int CELLS_DOWN = 7;
    
    private int mRadius = 5;
    private int dotPadding = 2* mRadius + 2;
    private final Paint mPaint = getPaint();;
    
    private ArrayList<DigitDisplay.SegmentLightListener> segListener = new ArrayList<DigitDisplay.SegmentLightListener>();
    
    
    public Paint getPaint(){
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        return p;
    }
    
    /**
     * Set the value this display should display.
     * @param v
     */
    @Override
    public void setValue(int v){

        if(value < 0 || value > 9){
            Log.w(GoogleIOCountdown.TAG, "Invalid value in BallDisplay " + v);
        }

        this.value = v;
        this.invalidateSelf();
    }

    public void setBounds(Rect newBounds){
        super.setBounds(newBounds);        
    }
    
    Rect bounds;
    @Override
    protected void onBoundsChange(Rect newBounds){
        super.onBoundsChange(newBounds);
        Log.v("BallDisplay", "Bounds " + bounds);
    }
    
    
    
    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#getIntrinsicHeight()
     */
    @Override
    public int getIntrinsicHeight() {
        return (CELLS_DOWN * 2 * mRadius) + ((CELLS_DOWN -1)  * dotPadding);
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#getIntrinsicWidth()
     */
    @Override
    public int getIntrinsicWidth() {
        return (CELLS_ACCROSS * 2 * mRadius) + ( (CELLS_ACCROSS -1)  * dotPadding);
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {

        final int WIDTH = CELLS_ACCROSS;
        final int HEIGHT = CELLS_DOWN;
        Rect bounds = getBounds();
        Paint p = mPaint; 
        //bounds = new Rect(25, 25, 300, 300);
        
        int radius = mRadius;
        if(radius <= 1){
            radius = 1;
        }
        
        int xPad = dotPadding ;
        int yPad = dotPadding;
        int xStart = bounds.left + radius;
        int curX;
        int curY = bounds.top + radius;
        
        
        int consummableMask = SHAPE_DESC[value];

        for(int h = 0; h < HEIGHT; h++){
            curX = xStart;
            for(int w = WIDTH - 1; w >= 0; w--){
                int doDraw = consummableMask & 0x00000001;
                int color;

                if(doDraw == 1){
                    color = Color.BLACK;
                }else{
                    color = Color.GRAY;
                }

                p.setColor(color);
                canvas.drawCircle(curX, curY, radius, p);

                consummableMask = (consummableMask >>> 1);
                curX += xPad;
            }
            curY += yPad;
        }

        
        notifyDarkSements(Arrays.asList(new RectF( 
                bounds.left, 
                bounds.top, 
                bounds.left +radius, 
                bounds.top + radius ) 
        ));
        
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#getOpacity()
     */
    @Override
    public int getOpacity() {
        return mAlpha;
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#setAlpha(int)
     */
    @Override
    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        // TODO Auto-generated method stub

    }

    public void notifyLitSegments(List<RectF> segmentBounds) {
        synchronized(segListener){
            for(SegmentLightListener sll : segListener){
                //copy the list to prevent aliasing problems if 
                //the reciever changes the list. Changes to the rectangles will be seen.
                sll.onSegmentLight(new ArrayList<RectF>(segmentBounds));
            }
        }
    }

    
    public void notifyDarkSements(List<RectF> segmentBounds) {
        synchronized(segListener){
            for(SegmentLightListener sll : segListener){
                //copy the list to prevent aliasing problems if 
                //the reciever changes the list. Changes to the rectangles will be seen.
                sll.onSegmentDark(new ArrayList<RectF>(segmentBounds));
            }
        }
    }
    

    /* (non-Javadoc)
     * @see com.mooney_ware.gio.view.DigitDisplay#registerSegmentListenet(com.mooney_ware.gio.view.DigitDisplay.SegmentLightListener)
     */
    @Override
    public void registerSegmentListener(SegmentLightListener listener) {
        synchronized (segListener) {
            segListener.add(listener);
        }
    }


    // Ints to describe how the number layout.
    // Each dot to draw has a corresponding bit mask that means draw (1) or
    // don't (0)
    static final int[] SHAPE_DESC = 
    {   0x0F99999F,  //0
        0x08888888, //1
        0x0F11F88F, //2
        0x0F88F88F, //3
        0x0888F999, //4
        0x0F88F11F, //5
        0x0F99F111, //6
        0x0888888F, //7
        0x0F99F99F, //8
        0x0888F99F }; //9
}
