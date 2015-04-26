package org.watertemplate.example.mappedobject;

import java.util.Date;

class User {
    final String name;
    final String username;
    final String email;
    final Date dateOfBirth;

    User(final String name, final String username, final String email, final Date dateOfBirth) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
