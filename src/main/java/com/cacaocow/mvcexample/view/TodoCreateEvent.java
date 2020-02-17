package com.cacaocow.mvcexample.view;

import com.cacaocow.mvcexample.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoCreateEvent {
    private Object source;
    private Todo todo;
}
