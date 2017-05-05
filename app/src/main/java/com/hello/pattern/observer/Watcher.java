package com.hello.pattern.observer;

//The same as Observer in AndroidOs
public interface Watcher {
    
    /**
     *This method is called if the specified {@code Watchable} object's
     * {@code notifyObservers} method is called (because the {@code Watchable}
     * object has been updated.
     * @param watchable
     * @param data
     */
    public void update(Watchable watchable, Object data);
}
