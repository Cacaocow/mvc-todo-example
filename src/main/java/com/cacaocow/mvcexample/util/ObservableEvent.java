package com.cacaocow.mvcexample.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ObservableEvent {
    private Object source;
    private String propertyName;
    private Object oldValue;
    private Object newValue;
}
