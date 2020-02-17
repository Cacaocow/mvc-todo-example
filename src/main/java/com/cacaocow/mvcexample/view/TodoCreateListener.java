package com.cacaocow.mvcexample.view;

@FunctionalInterface
public interface TodoCreateListener {
    void create(TodoCreateEvent e);
}
