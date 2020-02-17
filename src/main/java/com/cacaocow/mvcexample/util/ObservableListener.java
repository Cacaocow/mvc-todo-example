package com.cacaocow.mvcexample.util;

@FunctionalInterface
public interface ObservableListener {
    void propertyChangedEvent(ObservableEvent event);
}
