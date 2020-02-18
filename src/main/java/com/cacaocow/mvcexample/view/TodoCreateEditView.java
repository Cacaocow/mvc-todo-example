package com.cacaocow.mvcexample.view;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TodoCreateEditView extends JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(TodoCreateEditView.class);

    private JTextField todoName;
    private JTextArea todoDescription;
    private JTextField todoDate;

    private Set<TodoViewEventListener> listeners = new HashSet<>();

    public void init(boolean save) {
        LOG.debug("Initializing TodoCreateEditView");
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

        if (save) {
            saveButton.addActionListener(e -> raiseEvent(TodoEventType.SAVE));
        } else {
            saveButton.addActionListener(e -> raiseEvent(TodoEventType.CREATED));
        }

        form.add(saveButton);
        this.add(form);
    }

    public void prefillForm(String todoName, String todoDescription, String todoExpire) {
        this.todoName.setText(todoName);
        this.todoDescription.setText(todoDescription);
        this.todoDescription.setText(todoExpire);
    }

    public void addTodoCreateEventListener(TodoViewEventListener listener) {
        LOG.debug("Adding TodoCreateEventListener");
        this.listeners.add(listener);
    }

    public String getTodoName() {
        return this.todoName.getText();
    }

    public String getTodoDescription() {
        return this.todoDescription.getText();
    }

    public String getTodoExpire() {
        return this.todoDate.getText();
    }

    private void raiseEvent(TodoEventType type) {
        switch (type) {
            case CREATED:
                LOG.debug("Raising create new Todo={}", todoName.getText());
                break;
            case SAVE:
                LOG.debug("Raising save Todo={}", todoName.getText());
                break;
            default:
                LOG.error("Unexpected event type={}", type);
        }
        for (var listener : listeners) {
            listener.listen(new TodoViewEvent(this, type));
        }
        super.dispose();
    }
}
