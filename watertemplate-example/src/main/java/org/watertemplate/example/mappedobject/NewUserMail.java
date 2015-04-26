package org.watertemplate.example.mappedobject;

import org.apache.commons.lang3.text.WordUtils;
import org.watertemplate.Template;

class NewUserMail extends Template {
    public NewUserMail(final User user) {
        addMappedObject("user", user, (userMap) -> {
            userMap.add("email", user.email);
            userMap.add("username", user.username);
            userMap.addLocaleSensitiveObject("date_of_birth", user.dateOfBirth, (date, locale) -> date.toString());

            userMap.addMappedObject("name", user.name, (name, nameMap) -> {
                nameMap.add("capitalized", WordUtils.capitalizeFully(name));
            });
        });
    }

    @Override
    protected String getFilePath() {
        return "example/mappedobject/new_user.mail";
    }
}
