package com.cacaocow.mvcexample.util;

public interface Observable {
    void registerEventListener(ObservableListener listener);
}
