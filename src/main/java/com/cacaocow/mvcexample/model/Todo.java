package com.cacaocow.mvcexample.model;

import com.cacaocow.mvcexample.util.Observable;
import com.cacaocow.mvcexample.util.ObservableEvent;
import com.cacaocow.mvcexample.util.ObservableListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class Todo implements Observable {

    private static final Logger LOG = LoggerFactory.getLogger(Todo.class);

    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private LocalDateTime expire;

    private Set<ObservableListener> listeners = new HashSet<>();

    public Todo(String name, String description, LocalDateTime expire) {
        this.name = name;
        this.description = description;
        this.expire = expire;
    }

    @Override
    public void registerEventListener(ObservableListener listener) {
        LOG.debug("Register new listener to Todo={}", this);
        listeners.add(listener);
    }

    @Override
    public void unregisterEventListener(ObservableListener listener) {
        LOG.debug("Unregister listener from Todo={}", this);
        listeners.remove(listener);
    }


    private void raiseEvent(String property, Object oldVal, Object newVal) {
        LOG.debug("Raise event for property={} to {} listener(s)", property, listeners.size());
        for (var listener : listeners) {
            listener.propertyChangedEvent(new ObservableEvent(this, property, oldVal, newVal));
        }
    }

    public void setName(String name) {
        var tmp = this.name;
        this.name = name;
        raiseEvent("name", tmp, this.name);
    }

    public void setDescription(String description) {
        var tmp = this.description;
        this.description = description;
        raiseEvent("description", tmp, this.description);
    }

    public void setExpire(LocalDateTime expire) {
        var tmp = this.expire;
        this.expire = expire;
        raiseEvent("expire", tmp, this.expire);
    }
}
