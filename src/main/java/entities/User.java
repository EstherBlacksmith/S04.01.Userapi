package entities;

import java.util.UUID;

public class User {
    private UUID id;
    private Name name ;
    private Email email ;


    public User(Name name, Email email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public UUID getId() {
        return id;
    }
}
