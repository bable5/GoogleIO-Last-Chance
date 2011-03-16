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

import android.content.Context;
import android.view.View;

import com.mooney_ware.gio.model.CountdownListener;

/**
 * A an abstract countdown view, for the common pieces of the countdown - namely
 * storing the current countdown status.
 * 
 * @author Sean Mooney
 * 
 */
public abstract class AbstractCountdownView extends View implements
        CountdownListener {

    protected int mDays, mHours, mMinutes, mSeconds;

    public AbstractCountdownView(Context context) {
        super(context);
    }

    /**
     * Set the internal tick state and invalidate, to force a redraw.
     */
    public void onTick(int days, int hours, int minutes, int seconds) {
        this.mDays = days;
        this.mHours = hours;
        this.mMinutes = minutes;
        this.mSeconds = seconds;

        invalidate();
    }

}
