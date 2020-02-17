package com.cacaocow.mvcexample.view;

import com.cacaocow.mvcexample.model.Todo;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TodoListView extends JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListView.class);

    private JPanel todos;
    private Set<TodoViewEventListener> listeners = new HashSet<>();

    public TodoListView() {
        this.setLayout(new BorderLayout());
    }

    public void init(List<Todo> todoList) {
        LOG.debug("Initializing TodoListView...");
        if (todos != null) {
            LOG.debug("Removing previous todo list view");
            this.remove(todos);
        } else {
            var createButton = new JButton("Create");
            createButton.addActionListener(e -> raiseTodoViewEvent(TodoViewEvent.Type.CREATE));
            this.add(createButton, BorderLayout.PAGE_END);
        }

        todos = new JPanel();
        todos.setLayout(new BoxLayout(todos, BoxLayout.Y_AXIS));
        todoList.stream().map(t -> new TodoItem(listeners, t)).forEach(t -> todos.add(t));
        todos.setFocusable(true);
        this.add(todos);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.revalidate();
        LOG.debug("Finished initializing TodoListView");
    }

    protected void raiseTodoViewEvent(TodoViewEvent.Type type) {
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
    TodoItem(Set<TodoViewEventListener> listeners, Todo todo) {
        this.listeners = listeners;
        this.setLayout(new FlowLayout());
        var todoName = new JLabel(todo.getName());

        var todoDescription = new JLabel(todo.getDescription());

        var todoDate = new JLabel(todo.getExpire().toString());

        var editButton = new JButton("Edit");
        editButton.setEnabled(true);
        editButton.addActionListener(e -> raiseEvent(TodoViewEvent.Type.EDIT, todo));

        var deleteButton = new JButton("Delete");
        deleteButton.setEnabled(true);
        deleteButton.addActionListener(e -> raiseEvent(TodoViewEvent.Type.DELETE, todo));

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

    protected void raiseEvent(TodoViewEvent.Type type, Todo item) {
        LOG.debug("Raise TodoViewEvent type={} from item '{}'", type, item.getName());
        for (var listener : listeners) {
            listener.listen(new TodoViewEvent(item, type));
        }
    }
}