package com.cacaocow.mvcexample.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoViewEvent {

    public enum Type {
        CREATE, EDIT, DELETE;
    }
    private Object source;
    private Type type;
}