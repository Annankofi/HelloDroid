package com.hello.pattern.observer;

import java.util.ArrayList;

//The same as Observable

/**
 * Provides methods for registering or unregistering arbitrary observers in an {@link ArrayList}.
 *
 * This abstract class is intended to be subclassed and specialized to maintain
 * a registry of observers of specific types and dispatch notifications to them.
 *
 * @param T The observer type.
 */
public abstract class Watchable<T> {

    //protect mean that it will used by childClass
    /**
     * The list of observers.  An observer can be in the list at most
     * once and will never be null.
     */
    protected final ArrayList<T> mObservers = new ArrayList<T>();

    /**
     * Adds an observer to the list. The observer cannot be null and it must not already
     * be registered.
     * @param observer the observer to register
     * @throws IllegalArgumentException the observer is null
     * @throws IllegalStateException the observer is already registered
     */
    public void registerObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer is null.");
        }
        synchronized (this) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("observer is already registered.");
            }
            mObservers.add(observer);
        }
    }
    
    /**
     * Removes a previously registered observer. The observer must not be null and it
     * must already have been registered.
     * @param observer the observer to unregister
     * @throws IllegalArgumentException the observer is null
     * @throws IllegalStateException the observer is not yet registered
     */
    public void unregisterObserver(T observer){
        if (observer == null) {
            throw new IllegalStateException("observer is null");
        }
//        synchronized (this) {
//            if (!mObservers.contains(observer)) {
//                throw new IllegalStateException("observer already unregisted.");
//            }
//            mObservers.remove(observer);
//        }
        synchronized(mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                throw new IllegalStateException("Observer " + observer + " was not registered.");
            }
            mObservers.remove(index);
        }
    }
    
    /**
     * unregister all observers
     */
    public void unregisterAll(){
        synchronized (this) {
            mObservers.clear();
        }
    }
}
