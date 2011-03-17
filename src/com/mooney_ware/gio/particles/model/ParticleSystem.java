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
package com.mooney_ware.gio.particles.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.mooney_ware.gio.particles.model.ParticleSystem.Particle;

/**
 * @author Sean Mooney
 *
 */
public class ParticleSystem implements Iterable<Particle> {

    RectF mSystemBounds;
    BoundsStrategy mLeftBoundStrat = PARTICLE_KILL;
    BoundsStrategy mRightBoundStrat = PARTICLE_KILL;
    BoundsStrategy mBoundsTopStrat = PARTICLE_KILL;
    BoundsStrategy mBoundBottomStrat = PARTICLE_BOUNCE;

    List<Particle> mParticles = new ArrayList<ParticleSystem.Particle>();
 
    /**
     * Add a new particle to the system.
     * @param size
     * @param loc
     * @param velocity
     */
    public void addParticle(int size, PointF loc, PointF vel){
        Particle p = new Particle(size, loc, vel);
        mParticles.add(p);
    }

    public void setSystemBounds(RectF systemBounds){
        mSystemBounds = systemBounds;
    }
    
    /**
     * Step all the particles by 1.
     */
    public void stepAllParticles(){
        List<Particle> particles = mParticles;
        ArrayList<Particle> removeList = new ArrayList<Particle>();
        RectF sysBounds = mSystemBounds;
        
        if(sysBounds == null){
            Log.w("ParticleSystems", "NO SYSTEM BOUNDS");
        }
        
        for(Particle p : particles){
            p.step(1);
            if(sysBounds.left < p.getLeftBound()){
                onIntersection(removeList, p, mLeftBoundStrat);
            }else if(sysBounds.right > p.getRightBound()){
                onIntersection(removeList, p, mRightBoundStrat);
            }else if(sysBounds.top < p.getTopBound()){
                onIntersection(removeList, p, mBoundsTopStrat);
            }else if(sysBounds.bottom > p.getBottomBound()){
                onIntersection(removeList, p, mBoundBottomStrat);
            }
        }
        mParticles.removeAll(removeList);
    }

    private void onIntersection(List<Particle> removeList, Particle p, BoundsStrategy strat){
        switch(strat.atBoundery()){
            case DIE:
            {
                removeList.add(p);
            }
                break;
            case REFLECT:
            {
                PointF vel = p.mVelocity;
                vel.x = -vel.x;
                vel.y = -vel.y;
            }
                break;
        }
    }
    
    /**
     * @author Sean Mooney
     *
     */
    public class Particle {

        /**
         * Radius of the particle
         */
        private float mSize;
        /**
         * Center of the point.
         */
        private PointF mLocation;
        private PointF mVelocity;

        public Particle(float size, PointF location, PointF velocity){
            mSize = size;
            mLocation = location;
            mVelocity = velocity;
        }

        public PointF getLocation(){
            return mLocation;
        }
        
        public float getLeftBound(){
            return mLocation.x - mSize;
        }

        public float getRightBound(){
            return mLocation.x + mSize;
        }

        public float getTopBound(){
            return mLocation.y + mSize;
        }

        public float getBottomBound(){
            return mLocation.y - mSize;
        }

        public void step(int dT){

        }
    }

    public static final BoundsStrategy PARTICLE_KILL = new BoundsStrategy() {
        @Override
        public ParticleResult atBoundery() {
            return ParticleResult.DIE;
        }
    };

    public static final BoundsStrategy PARTICLE_BOUNCE = new BoundsStrategy() {
        @Override
        public ParticleResult atBoundery() {
            return ParticleResult.REFLECT;
        }
    };

    public static interface BoundsStrategy{
        public enum ParticleResult{
            DIE,
            REFLECT
        }
        public ParticleResult atBoundery();
    }

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Particle> iterator() {
        return mParticles.listIterator();
    }
}
