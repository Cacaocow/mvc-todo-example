package com.cacaocow.mvcexample.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoViewEvent {
    private Object source;
    private TodoEventType type;
}