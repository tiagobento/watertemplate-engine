package org.watertemplate.example.example.mappedobject;

import java.util.Date;

public class MappedObjectMain {
    public static void main(String[] args) {
        User user = new User("tiago bento fernandes", "tiagobento", "tiagobento@example.com", new Date());

        NewUserMail newUserMail = new NewUserMail(user);
        System.out.println(newUserMail.render());
    }
}