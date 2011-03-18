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
package com.mooney_ware.gio;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;

import com.mooney_ware.gio.particles.model.ParticleSystem;
import com.mooney_ware.gio.particles.view.SimpleParticleView;

/**
 * @author Sean Mooney
 *
 */
public class ParticleFountain extends Activity{

    SimpleParticleView mParticleView;
    ParticleSystem mParticleSystem;
    
    final Runnable runfountain = new Runnable() {
        
        @Override
        public final void run() {
            mParticleSystem.addParticle(10, new PointF(300, 300), new PointF(5, 5));
            handler.postDelayed(this, 1000);
        }
        
    };
    final Handler handler = new Handler();
    
    @Override
    public void onResume(){
        super.onResume();
        handler.post(runfountain);
        mParticleSystem.start(mParticleView);
    }
    
    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runfountain);
    }
    
    @Override
    public void onCreate(Bundle savedStateInformation){
        super.onCreate(savedStateInformation);
        setContentView(R.layout.particle_fountain);
        mParticleView = (SimpleParticleView)findViewById(R.id.particle_fountain_view);
        
        
        mParticleSystem = new ParticleSystem();
        ParticleSystem particleSystem = mParticleSystem;
    
        particleSystem.setSystemBounds(new RectF(0, 0, 800, 480));
        particleSystem.addParticle(5, new PointF(100, 100), new PointF(10, 10));
        particleSystem.addParticle(5, new PointF(50, 50), new PointF(15f, 10));
    
        
        mParticleView.setSystem(particleSystem);
    }
}
