package com.cacaocow.mvcexample.view;

import com.cacaocow.mvcexample.model.Todo;
import lombok.var;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TodoCreateEditView extends JFrame {

    private JTextField todoName;
    private JTextArea todoDescription;
    private JTextField todoDate;

    private Set<TodoCreateListener> listeners = new HashSet<>();

    public void init(Todo todo) {
        var form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        form.add(new JLabel("Name"));
        todoName = new JTextField();
        form.add(todoName);

        form.add(new JLabel("Description"));
        todoDescription = new JTextArea();
        form.add(todoDescription);

        form.add(new JLabel("Datum"));
        todoDate = new JTextField(LocalDateTime.now().plusHours(1).toString());
        form.add(todoDate);

        var saveButton = new JButton("Save");

        if (todo != null) {
            todoName.setText(todo.getName());
            todoDescription.setText(todo.getDescription());
            todoDate.setText(todo.getExpire().toString());
            saveButton.addActionListener(e -> save(todo));
        } else {
            saveButton.addActionListener(e -> create());
        }

        form.add(saveButton);
        this.add(form);
    }

    public void addTodoCreateEventListener(TodoCreateListener listener) {
        this.listeners.add(listener);
    }

    private void save(Todo item) {
        item.setName(todoName.getText());
        item.setDescription(todoDescription.getText());
        item.setExpire(LocalDateTime.parse(todoDate.getText()));
        super.dispose();
    }

    private void create() {
        Todo todo = new Todo(todoName.getText(), todoDescription.getText(), LocalDateTime.parse(todoDate.getText()));
        for (var listener : listeners) {
            listener.create(new TodoCreateEvent(this, todo));
        }
        super.dispose();
    }
}
