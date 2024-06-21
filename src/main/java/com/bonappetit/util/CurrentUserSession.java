package com.bonappetit.util;

import com.bonappetit.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@Component
@SessionScope
public class CurrentUserSession {

    private long id;

    private String username;

    public boolean isLogged() {
        return id > 0;
    }

    public void logUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public void logout() {
        this.id = -1;
    }
}
