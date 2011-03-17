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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mooney_ware.gio.GoogleIOCountdown;

/**
 * @author Sean Mooney
 *
 */
public class BallDigitDrawable extends Drawable{

    private int mAlpha;

    private int value;

    /**
     * Set the value this display should display.
     * @param v
     */
    public void setValue(int v){

        if(value < 0 || value > 9){
            Log.w(GoogleIOCountdown.TAG, "Invalid value in BallDisplay " + v);
        }

        this.value = v;
    }

    public void setBounds(Rect newBounds){
        super.setBounds(newBounds);
        this.bounds = newBounds;
    }
    
    Rect bounds;
    @Override
    protected void onBoundsChange(Rect newBounds){
        super.onBoundsChange(newBounds);
        Log.i("BallDisplay", "Bounds " + bounds);
    }
    
    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {

        final int WIDTH = 4;
        final int HEIGHT = 7;
        Rect bounds = this.bounds;
        if(bounds == null) bounds = getBounds();

        //bounds = new Rect(25, 25, 300, 300);
        
        //TODO: Elim redudancy
        int curX = bounds.left, curY = bounds.top;
        int radius = 3; //bounds.width() / WIDTH;
        if(radius < 2){
            radius = 2;
        }
        
        int diameter = 2 * radius;
        int xPad =  diameter + 2; //bounds.width() / ( WIDTH * ( diameter ) );
        int yPad = diameter + 2; //bounds.height() / ( WIDTH * ( diameter ) );
        int xStart = curX;
        
        Paint p = new Paint();

        int consummableMask = SHAPE_DESC[value];
        Log.i(GoogleIOCountdown.TAG, "Drawing " + value + " at " + bounds);

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
