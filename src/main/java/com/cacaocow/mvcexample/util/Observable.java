package com.cacaocow.mvcexample.util;

public interface Observable {
    void registerEventListener(ObservableListener listener);
    void unregisterEventListener(ObservableListener listener);
}
