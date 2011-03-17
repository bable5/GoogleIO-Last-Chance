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
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

import com.mooney_ware.gio.model.CountDownDriver;
import com.mooney_ware.gio.view.DigitDisplay;

public class GoogleIOCountdown extends Activity {

    public static final String TAG = "GIO";

    private CountDownDriver mCountDownDriver = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //AbstractCountdownView cdview = (AbstractCountdownView) findViewById(R.id.countdown_view);

        DigitDisplay tdd = (DigitDisplay)findViewById(R.id.countdown_view);
        tdd.setValue(12);
        
        DigitDisplay hours = (DigitDisplay)findViewById(R.id.hours_counter);
        hours.setValue(34);

        DigitDisplay minutes = (DigitDisplay)findViewById(R.id.minutes_counter);
        minutes.setValue(45);

        DigitDisplay seconds = (DigitDisplay)findViewById(R.id.seconds_counter);
        seconds.setValue(67);
        
        
//        if (mCountDownDriver == null) {
//            Time targetTime = getCountdownTargetDate();
//            mCountDownDriver = new CountDownDriver(targetTime);
//            mCountDownDriver.registerListener(cdview);
//            mCountDownDriver.start();
//        }
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