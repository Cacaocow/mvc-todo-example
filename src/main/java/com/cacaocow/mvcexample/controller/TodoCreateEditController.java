package com.cacaocow.mvcexample.controller;

import com.cacaocow.mvcexample.model.Todo;
import com.cacaocow.mvcexample.view.TodoCreateEditView;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TodoCreateEditController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoCreateEditController.class);

    private TodoCreateEditView view;
    private Set<TodoCreateListener> listeners = new HashSet<>();
    private Todo model;

    public void init(Todo todo) {
        LOG.debug("Initializing TodoCreateEditController");
        model = todo;
        view = new TodoCreateEditView();
        view.init(todo != null);
        if (todo != null) {
            view.prefillForm(todo.getName(), todo.getDescription(), todo.getExpire().toString());
        }
        view.setSize(200, 400);
        view.setLocationRelativeTo(null);
        view.addTodoCreateEventListener(e -> {
            switch (e.getType()) {
                case CREATED:
                    create();
                case SAVE:
                    save();
            }
        });
    }

    public void showView(boolean visible) {
        view.setVisible(visible);
    }

    public void addTodoCreateListener(TodoCreateListener listener) {
        LOG.debug("Adding new TodoCreateListener");
        this.listeners.add(listener);
    }

    private void save() {
        setModelFromView();
        view.dispose();
        LOG.debug("Saved todo {}", model.getName());
    }

    private void create() {
        model = new Todo();
        setModelFromView();
        view.dispose();
        LOG.debug("Created todo {}", model.getName());
        raiseTodoCreateEvent(new TodoCreateEvent(this, model));
    }

    private void setModelFromView() {
        model.setName(view.getTodoName());
        model.setDescription(view.getTodoDescription());
        model.setExpire(LocalDateTime.parse(view.getTodoExpire()));
    }

    private void raiseTodoCreateEvent(TodoCreateEvent e) {
        LOG.debug("Raising TodoCreateEvent: {}", e.getTodo().getName());
        for (var listener : listeners) {
            listener.create(e);
        }
    }
}