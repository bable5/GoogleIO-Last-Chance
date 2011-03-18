/**
 *   Copyright 2010 Sean L. Mooney
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

package com.mooney_ware.gio;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;

import com.mooney_ware.gio.model.CountDownDriver;
import com.mooney_ware.gio.model.CountdownListener;
import com.mooney_ware.gio.particles.model.ParticleSystem;
import com.mooney_ware.gio.particles.view.ParticleSystemDrawable;
import com.mooney_ware.gio.view.DigitDisplay;

public class GoogleIOCountdown extends Activity  {

    public static final String TAG = "GIO";

    CountDownDriver mCountDownDriver = null;
    ParticleSystem mParticleSystem = null;
    Runnable particleRunner;
    final Handler particleHandler = new Handler();
    
    DigitDisplay mDaysDisplay;
    DigitDisplay mHoursDisplay;
    DigitDisplay mMinutesDisplay;
    DigitDisplay mSecondsDisplay;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCountDown();
        setupParticles();
    }
    
    @Override
    public void onResume(){
        super.onResume();
        particleHandler.post(particleRunner);
        mCountDownDriver.start();
        registerDigitChangeListeners();
    }
    
    @Override
    public void onPause(){
        super.onPause();
        particleHandler.removeCallbacks(particleRunner);
        mCountDownDriver.stop();
    }
    
    protected void  registerDigitChangeListeners(){
       class Listener implements DigitDisplay.SegmentLightListener{
           Random ayn = new Random();
           
            
            float xAdj, yAdj;
            
            public Listener(float xAdj, float yAdj){
                this.xAdj = xAdj;
                this.yAdj = yAdj;
            }
            
            @Override
            public final void onSegmentLight(List<RectF> segmentBounds) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public final void onSegmentDark(List<RectF> segmentBounds) {
                ParticleSystem ps = mParticleSystem;
                for(RectF bound : segmentBounds){
                    int numToSpawn = 1;
                    for(int i = 0; i< numToSpawn; i++){
                        float dx = ayn.nextInt(40) - 20; 
                        float dy = 20 - ayn.nextInt(20);
                        
                        int radius = (int)(bound.width())/2;
                        if(radius < 2)
                            radius = 2;


                        ps.addParticle(radius,
                                new PointF(bound.centerX() + xAdj, bound.centerY() + yAdj), 
                                new PointF(dx, dy)
                        );
                    }
                }
            }
            
        };
        
//        View allDigits = findViewById(R.layout.main);
        
        //TODO: Fix yoffset and xoffset.
        float yOffset = 150; //allDigits.getTop();
        
        float xOffset = 10; //allDigits.getLeft();
        
        mDaysDisplay.registerSegmentListener(new Listener(xOffset, yOffset));
        mHoursDisplay.registerSegmentListener(new Listener(xOffset += mDaysDisplay.getIntrinsicWidth(), yOffset));
        mMinutesDisplay.registerSegmentListener(new Listener(xOffset += mDaysDisplay.getIntrinsicWidth(), yOffset));
        mSecondsDisplay.registerSegmentListener(new Listener(xOffset += mDaysDisplay.getIntrinsicWidth(), yOffset));
    }
    
    protected void setupParticles(){
        final ParticleSystem ps = new ParticleSystem();
        mParticleSystem = ps;
        
        ParticleSystemDrawable psd = new ParticleSystemDrawable();
        psd.setParticleSystem(ps);
        
        final ImageView particleView = (ImageView)findViewById(R.id.particle_imageview);
        Drawable d = getResources().getDrawable(R.drawable.simpleparticle);
        psd.setParticleDrawable(d);
        particleView.setImageDrawable(psd);
        
        //RectF particleBounds = new RectF(particleView.getLeft(), particleView.getTop(), particleView.getRight(), particleView.getBottom());
        //ps.setSystemBounds(particleBounds);
        
        
        particleRunner = new Runnable() {
            @Override
            public final void run() {
                ps.stepAllParticles();
                particleView.postInvalidate();
                particleHandler.postDelayed(this, 35);
            }
        };
        
    }
    
    
    
    protected void setupCountDown(){
        setContentView(R.layout.main);

        final DigitDisplay tdd = (DigitDisplay)findViewById(R.id.days_counter);
        final DigitDisplay hoursView = (DigitDisplay)findViewById(R.id.hours_counter);
        final DigitDisplay minutesView = (DigitDisplay)findViewById(R.id.minutes_counter);
        final DigitDisplay secondsView = (DigitDisplay)findViewById(R.id.seconds_counter);
        
        mDaysDisplay = tdd;
        mHoursDisplay = hoursView;
        mMinutesDisplay = minutesView;
        mSecondsDisplay = secondsView;
        
        Drawable daysImage = getResources().getDrawable(R.drawable.daysegment);
        Drawable hoursImage = getResources().getDrawable(R.drawable.hoursegment);
        Drawable minuteImage = getResources().getDrawable(R.drawable.minsegment);
        Drawable secondImage = getResources().getDrawable(R.drawable.secsegment);
        Drawable offImage = getResources().getDrawable(R.drawable.offsegment);
        
        mDaysDisplay.setOnDrawable(daysImage);
        mDaysDisplay.setOffDrawable(offImage);
        
        mHoursDisplay.setOnDrawable(hoursImage);
        mHoursDisplay.setOffDrawable(offImage);
        
        mMinutesDisplay.setOnDrawable(minuteImage);
        mMinutesDisplay.setOffDrawable(offImage);
        
        mSecondsDisplay.setOnDrawable(secondImage);
        mSecondsDisplay.setOffDrawable(offImage);
        
        if (mCountDownDriver == null) {
            Time targetTime = getCountdownTargetDate();
            mCountDownDriver = new CountDownDriver(targetTime);
            mCountDownDriver.registerListener(
                    new CountdownListener() {
                        @Override
                        public final void onTick(final int days, final int hours, final int minutes, final int seconds) {
                               tdd.setValue(days);
                               hoursView.setValue(hours);
                               minutesView.setValue(minutes);
                               secondsView.setValue(seconds);
                        }
                    }
            );
            mCountDownDriver.start();
        }
    }

    /**
     * Returns the date to countdown towards.
     * 
     * @return
     */
    private Time getCountdownTargetDate() {

        Time time = new Time("PST");

        int year = 2011;
        int month = Calendar.MAY;
        int dayOfMonth = 9;

        int hourOfDay = 9;
        int minute = 0;
        int second = 0;

        time.set(second, minute, hourOfDay, dayOfMonth, month, year);
        time.normalize(false);

        Log.i(TAG, "Target time: " + time.toString());
        return time;
    }
}