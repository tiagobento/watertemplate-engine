package org.watertemplate.example.main.menu;

import org.watertemplate.Template;

import java.util.ArrayList;
import java.util.List;

public class MenuPartial extends Template {

    private final List<MenuItem> menuItems = new ArrayList<>();

    {
        menuItems.add(new MenuItem("Home", "/"));
        menuItems.add(new MenuItem("About", "/about"));
        menuItems.add(new MenuItem("Contact", "/contact"));
    }

    public MenuPartial() {
        add("mustDisplayMenu", true);

        addCollection("items", menuItems, (item, itemMap) -> {
            itemMap.add("label", item.getLabel());
            itemMap.add("url", item.getUrl());
        });
    }

    @Override
    protected String getFilePath() {
        return "example/menu.html.partial";
    }
}


