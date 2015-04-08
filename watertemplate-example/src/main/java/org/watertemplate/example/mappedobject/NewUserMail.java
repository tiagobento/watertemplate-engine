package org.watertemplate.example.mappedobject;

import org.apache.commons.lang3.text.WordUtils;
import org.watertemplate.Template;

class NewUserMail extends Template {
    public NewUserMail(final User user) {
        addMappedObject("user", user, (u, userMap) -> {
            userMap.add("email", u.email);
            userMap.add("username", u.username);
            userMap.add("date_of_birth", u.dateOfBirth);

            userMap.addMappedObject("name", u.name, (name, nameMap) -> {
                nameMap.add("capitalized", WordUtils.capitalizeFully(name));
            });
        });
    }

    @Override
    protected String getFilePath() {
        return "new_user.mail";
    }
}
