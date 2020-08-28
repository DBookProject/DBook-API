package dbook.api.json;

import dbook.api.Domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

public class UserResponse extends Response {

    Long id;

    String name;

    public UserResponse(int status, String message, User user) {
        super(status, message);

        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}