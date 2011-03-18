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

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;

import com.mooney_ware.gio.model.CountDownDriver;
import com.mooney_ware.gio.model.CountdownListener;
import com.mooney_ware.gio.particles.model.ParticleSystem;
import com.mooney_ware.gio.particles.view.SimpleParticleView;
import com.mooney_ware.gio.view.DigitDisplay;

public class GoogleIOCountdown extends Activity {

    public static final String TAG = "GIO";

    private CountDownDriver mCountDownDriver = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCountDown();
        setupParticles();
    }
    
    Runnable particleRunner;
    final Handler particleHandler = new Handler();
    public void setupParticles(){
        final ParticleSystem ps = new ParticleSystem();
        ps.setSystemBounds(new RectF(0, 0, 800, 480));
        
        ps.addParticle(5, new PointF(100, 100), new PointF(1, 1));
        ps.addParticle(5, new PointF(101, 101), new PointF(1, -11));
        
        final SimpleParticleView spv = new SimpleParticleView(this);
        setContentView(spv);
        spv.setSystem(ps);
        
        particleRunner = new Runnable() {
            @Override
            public final void run() {
                Log.i(TAG, "Stepping particles");
                ps.stepAllParticles();
                spv.postInvalidate();
                particleHandler.postDelayed(this, 100);
            }
        };
        
        particleHandler.postDelayed(particleRunner, 100);
    }
    
    
    
    public void setupCountDown(){
        setContentView(R.layout.main);

        final DigitDisplay tdd = (DigitDisplay)findViewById(R.id.days_counter);
        final DigitDisplay hoursView = (DigitDisplay)findViewById(R.id.hours_counter);
        final DigitDisplay minutesView = (DigitDisplay)findViewById(R.id.minutes_counter);
        final DigitDisplay secondsView = (DigitDisplay)findViewById(R.id.seconds_counter);
        
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