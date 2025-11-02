package com.tidal.pawpal.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class UserDeletionEvent extends ApplicationEvent {

    private final Long userId;

    public UserDeletionEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

}
