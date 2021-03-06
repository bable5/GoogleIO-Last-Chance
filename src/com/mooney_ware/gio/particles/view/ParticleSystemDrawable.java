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

import java.util.List;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

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
        List<Particle> ps = mParticles.copyParticles();
        if(ps == null) return;
        
        for(Particle p : ps){
            PointF loc = p.getLocation();
            Drawable d = p.getDrawable();
            int r = (int)p.getSize();
            d.setBounds((int)loc.x - r, (int)loc.y -r, (int)loc.x + r, (int)loc.y + r);
            d.draw(canvas);
        }
        
    }

    
    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#onBoundsChange(android.graphics.Rect)
     */
    @Override
    protected void onBoundsChange(Rect bounds) {

        Log.i("PSDrawable", "Bound Change");
        super.onBoundsChange(bounds);
        if(mParticles != null){
            Log.i("PSDrawable", "Changing system bounds");
            mParticles.setSystemBounds(new RectF(bounds));
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
