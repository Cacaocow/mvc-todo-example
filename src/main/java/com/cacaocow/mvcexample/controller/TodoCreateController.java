package com.cacaocow.mvcexample.controller;

import com.cacaocow.mvcexample.model.Todo;
import com.cacaocow.mvcexample.view.TodoCreateEditView;
import com.cacaocow.mvcexample.view.TodoCreateEvent;
import com.cacaocow.mvcexample.view.TodoCreateListener;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class TodoCreateController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoCreateController.class);

    private TodoCreateEditView view;
    private Set<TodoCreateListener> listeners = new HashSet<>();

    public void init(Todo todo) {
        LOG.debug("Initializing TodoCreateController");
        view = new TodoCreateEditView();
        view.init(todo);
        view.setSize(200, 400);
        view.setLocationRelativeTo(null);
        view.addTodoCreateEventListener(this::raiseTodoCreateEvent);
    }

    public void showView(boolean visible) {
        view.setVisible(visible);
    }

    public void addTodoCreateListener(TodoCreateListener listener) {
        LOG.debug("Adding new TodoCreateListener");
        this.listeners.add(listener);
    }

    private void raiseTodoCreateEvent(TodoCreateEvent e) {
        LOG.debug("Passing on TodoCreateEvent: {}", e.getTodo().getName());
        for (var listener : listeners) {
            listener.create(e);
        }
    }
}