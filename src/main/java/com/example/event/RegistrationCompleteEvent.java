package com.example.event;

import com.example.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AppUser user;
    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
