package com.cacaocow.mvcexample.controller;

import com.cacaocow.mvcexample.model.Todo;
import com.cacaocow.mvcexample.view.TodoListView;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TodoListController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListController.class);

    private List<Todo> model = new ArrayList<>();
    private TodoListView view = new TodoListView();

    public void init() {
        LOG.debug("Initialize TodoListController");
        view.setLocationRelativeTo(null);
        view.setSize(500,300);
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.addTodoViewEventListener(e -> {
            LOG.debug("Received TodoViewEvent type={}", e.getType());
            switch (e.getType()) {
                case CREATE:
                    initCreateView(null);
                    break;
                case EDIT:
                    initCreateView((Todo) e.getSource());
                    break;
                case DELETE:
                    model.remove(e.getSource());
                    refreshViewModel();
                    break;
            }
        });
        refreshViewModel();
    }

    public void showView(boolean visible) {
        view.setVisible(visible);
    }

    private void refreshViewModel() {
        view.init(model);
    }

    private void initCreateView(Todo todo) {
        var controller = new TodoCreateController();
        controller.init(todo);
        controller.addTodoCreateListener(e -> addTodo(e.getTodo()));
        controller.showView(true);
        LOG.debug("Opened new create/edit view: {}", todo != null ? todo.getName() : "Create Todo");
    }

    private void addTodo(Todo todo) {
        model.add(todo);
        refreshViewModel();
        LOG.debug("Added new todo: {}", todo.getName());
    }
}
