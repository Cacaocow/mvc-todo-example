package com.cacaocow.mvcexample.controller;

import com.cacaocow.mvcexample.model.Todo;
import com.cacaocow.mvcexample.view.TodoListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoListController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListController.class);

    private List<Todo> model = new ArrayList<>();
    private TodoListView view = new TodoListView();

    public TodoListController() {
        model.add(new Todo("Essen", "Mache Essen", LocalDateTime.now()));
        model.add(new Todo("Programmieren", "Mache Essen", LocalDateTime.now()));
        model.add(new Todo("Testen", "Mache Essen", LocalDateTime.now()));
        model.add(new Todo("Herbst", "Mache Essen", LocalDateTime.now()));
    }

    public void init() {
        LOG.debug("Initialize TodoListController");
        view.setLocationRelativeTo(null);
        view.setSize(500,300);
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.addTodoViewEventListener(e -> {
            LOG.debug("Received TodoViewEvent type={}", e.getType());
            switch (e.getType()) {
                case CREATE:
                    break;
                case EDIT:
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
}
