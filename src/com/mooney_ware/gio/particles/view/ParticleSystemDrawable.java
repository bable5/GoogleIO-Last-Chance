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
package com.mooney_ware.gio.particles.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import com.mooney_ware.gio.particles.model.ParticleSystem;
import com.mooney_ware.gio.particles.model.ParticleSystem.Particle;

/**
 * 
 * @author Sean Mooney
 *
 */
public class ParticleSystemDrawable extends Drawable{

    private ParticleSystem mParticles;
    private Drawable mParticleImage;
    private int mAlpha = PixelFormat.OPAQUE;
    
    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {
        ParticleSystem ps = mParticles;
        if(ps == null) return;
        
        Drawable d = getParticleDrawable();
        
        for(Particle p : ps){
            PointF loc = p.getLocation();
            int r = (int)p.getSize();
            d.setBounds((int)loc.x - r, (int)loc.y -r, (int)loc.x + r, (int)loc.y + r);
            d.draw(canvas);
        }
        
    }

    /**
     * Get the drawable for the particle.
     * If the drawable is not instaniated, create a 
     * basic shape drawable and return it.
     * @return
     */
    public Drawable getParticleDrawable(){
        if(mParticleImage == null){
            mParticleImage = new ShapeDrawable(new OvalShape());
        }
        
        return mParticleImage;
        
    }
    
    public void setParticleDrawable(Drawable d){
        mParticleImage = d;
    }
    
    public void setParticleSystem(ParticleSystem ps){
        mParticles = ps;
    }
    
    public ParticleSystem getParticleSystem(){
        return mParticles;
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
        mAlpha = alpha;
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        // TODO Auto-generated method stub
        
    }

}
