package org.watertemplate.example.main.menu;

class MenuItem {
    private final String label;
    private final String url;

    MenuItem(final String label, final String url) {
        this.label = label;
        this.url = url;
    }

    String getLabel() {
        return label;
    }

    String getUrl() {
        return url;
    }
}
