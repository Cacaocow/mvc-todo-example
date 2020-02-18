package com.cacaocow.mvcexample.view;

import com.cacaocow.mvcexample.util.Observable;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TodoListView extends JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListView.class);

    private JPanel todos;
    private Set<TodoViewEventListener> listeners = new HashSet<>();

    public TodoListView() {
        this.setLayout(new BorderLayout());
    }

    public void init() {
        LOG.debug("Initializing TodoListView...");
        if (todos != null) {
            LOG.debug("Removing previous todo list view");
            this.remove(todos);
        } else {
            var createButton = new JButton("Create");
            createButton.addActionListener(e -> raiseTodoViewEvent(TodoEventType.CREATE));
            this.add(createButton, BorderLayout.PAGE_END);
        }

        todos = new JPanel();
        todos.setLayout(new BoxLayout(todos, BoxLayout.Y_AXIS));
        todos.setFocusable(true);
        this.add(todos);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.revalidate();
        LOG.debug("Finished initializing TodoListView");
    }

    public void addTodoItem(String todoName, String todoDescription, String todoExpire, Observable model) {
        this.todos.add(new TodoItem(listeners, todoName, todoDescription, todoExpire, model));
        this.todos.revalidate();
    }

    protected void raiseTodoViewEvent(TodoEventType type) {
        for (var listener : listeners) {
            listener.listen(new TodoViewEvent(this, type));
        }
    }

    public void addTodoViewEventListener(TodoViewEventListener listener) {
        this.listeners.add(listener);
    }
}

class TodoItem extends JPanel {
    private static final Logger LOG = LoggerFactory.getLogger(TodoItem.class);
    private Set<TodoViewEventListener> listeners;

    TodoItem(Set<TodoViewEventListener> listeners, String name, String description, String expire, Observable todo) {
        this.listeners = listeners;
        this.setLayout(new FlowLayout());
        var todoName = new JLabel(name);

        var todoDescription = new JLabel(description);

        var todoDate = new JLabel(expire);

        var editButton = new JButton("Edit");
        editButton.setEnabled(true);
        editButton.addActionListener(e -> raiseEvent(TodoEventType.EDIT, todo));

        var deleteButton = new JButton("Delete");
        deleteButton.setEnabled(true);
        deleteButton.addActionListener(e -> raiseEvent(TodoEventType.DELETE, todo));

        this.add(todoName);
        this.add(todoDescription);
        this.add(todoDate);
        this.add(editButton);
        this.add(deleteButton);

        todo.registerEventListener(e -> {
            switch (e.getPropertyName()) {
                case "name":
                    todoName.setText(e.getNewValue().toString());
                    break;
                case "description":
                    todoDescription.setText(e.getNewValue().toString());
                    break;
                case "expire":
                    todoDate.setText(e.getNewValue().toString());
                    break;
                default:
                    // Ignore
                    break;
            }
            this.revalidate();
            this.repaint();
        });
    }

    protected void raiseEvent(TodoEventType type, Observable item) {
        LOG.debug("Raise TodoViewEvent type={} from item '{}'", type, item);
        for (var listener : listeners) {
            listener.listen(new TodoViewEvent(item, type));
        }
    }
}