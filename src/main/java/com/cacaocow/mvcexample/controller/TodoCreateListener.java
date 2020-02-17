package com.cacaocow.mvcexample.controller;

@FunctionalInterface
public interface TodoCreateListener {
    void create(TodoCreateEvent e);
}
