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
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.mooney_ware.gio.GoogleIOCountdown;

/**
 * @author Sean Mooney
 *
 */
public class BallDisplay extends Drawable{

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
    
    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {
        
        Log.i(GoogleIOCountdown.TAG, "Drawing " + value);
        
       final int WIDTH = 4;
       final int HEIGHT = 7;
       
       int curX = 50, curY = 100;
       int xPad = 30, yPad = 30;
       int radius = 5;
       
       Paint p = new Paint();
       
       int consummableMask = SHAPE_DESC[value];
       
        for(int h = 0; h < HEIGHT; h++){
            curY = 100;
            for(int w = 0; w < WIDTH; w++){
                int doDraw = consummableMask & 0x00000001;
                
                int color;
                
                
                if(doDraw == 1){
                    color = Color.BLACK;
                }else{
                    color = Color.GRAY;
                }
                
                p.setColor(color);
                canvas.drawCircle(curX, curY, radius, p);
                
                consummableMask = consummableMask >>> 1;
                curY -= yPad;
            }
            curX += xPad;
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
    { 
        0x0F9999F,  //0
        0x01111111, //1
        0x0F11F88F, //2
        0x0F11F11F, //3
        0x0999F111, //4
        0x0F88F11F, //5
        0x0F88F99F, //6
        0x0F111111, //7
        0x0F99F99F, //8
        0x0F99F111 }; //9
    
    static final byte BYTEMASK_1000 = (byte)0x1000;
    static final byte BYTEMASK_0100 = (byte)0x0100;
    static final byte BYTEMASK_0010 = (byte)0x0010;
    static final byte BYTEMASK_0001 = (byte)0x0001;
    static final byte BYTEMASK_0000 = (byte)0x0000;
    
}
