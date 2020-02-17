package com.cacaocow.mvcexample.view;

@FunctionalInterface
public interface TodoViewEventListener {
    void listen(TodoViewEvent e);
}
