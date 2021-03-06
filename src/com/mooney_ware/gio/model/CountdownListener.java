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
package com.mooney_ware.gio.model;

/**
 * Defines a listener interface for anything that wants to be notified when the
 * Countdown objec associated with it ticks.
 * 
 * @author Sean Mooney
 * 
 */
public interface CountdownListener {

    /**
     * Update to the given tick.
     * 
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     */
    public void onTick(int days, int hours, int minutes, int seconds);
}
