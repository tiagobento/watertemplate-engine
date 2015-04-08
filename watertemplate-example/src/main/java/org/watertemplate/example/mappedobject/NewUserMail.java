package org.watertemplate.example.mappedobject;

import org.apache.commons.lang3.text.WordUtils;
import org.watertemplate.Template;

class NewUserMail extends Template {
    public NewUserMail(final User user) {
        addMappedObject("user", user, (userMap) -> {
            userMap.add("email", user.email);
            userMap.add("username", user.username);
            userMap.add("date_of_birth", user.dateOfBirth);

            userMap.addMappedObject("name", user.name, (name, nameMap) -> {
                nameMap.add("capitalized", WordUtils.capitalizeFully(name));
            });
        });
    }

    @Override
    protected String getFilePath() {
        return "new_user.mail";
    }
}
