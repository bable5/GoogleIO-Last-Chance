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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mooney_ware.gio.R;
import com.mooney_ware.gio.particles.model.ParticleSystem;
import com.mooney_ware.gio.particles.model.ParticleSystem.Particle;

/**
 * @author Sean Mooney
 *
 */
public class SimpleParticleView extends View{

    ParticleSystem mSystem;
    
    
    /**
     * @param context
     */
    public SimpleParticleView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public SimpleParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setSystem(ParticleSystem system){
        this.mSystem = system;
        setSystemBounds();
    }
    
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(mSystem == null) return;
        
        Drawable d = getResources().getDrawable(R.drawable.simpleparticle);
        
        for(Particle p : mSystem){
            PointF loc = p.getLocation();
            Log.i("ParticleView", "Drawing a particle at " + loc.x + "," + loc.y);
            d.setBounds((int)loc.x, (int)loc.y, (int)loc.x + 5, (int)loc.y + 5);
            d.draw(canvas);
        }
    }
    
    protected void setSystemBounds(){
        if(mSystem!=null){
            mSystem.setSystemBounds(new RectF(getLeft(), getTop(), getRight(), getBottom()));
        }
    }
    
    
    
    @Override
    public void onSizeChanged(int w, int h, int ow, int oh){
        super.onSizeChanged(w, h, ow, oh);
        setSystemBounds();
    }
}
