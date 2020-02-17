package com.cacaocow.mvcexample.model;

import com.cacaocow.mvcexample.util.Observable;
import com.cacaocow.mvcexample.util.ObservableEvent;
import com.cacaocow.mvcexample.util.ObservableListener;
import lombok.Getter;
import lombok.var;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Todo implements Observable {
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private LocalDateTime expire;

    private Set<ObservableListener> listeners = new HashSet<>();

    @Override
    public void registerEventListener(ObservableListener listener) {
        listeners.add(listener);
    }


    private void raiseEvent(String property, Object oldVal, Object newVal) {
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
