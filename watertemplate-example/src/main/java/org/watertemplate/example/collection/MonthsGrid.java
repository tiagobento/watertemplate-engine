package org.watertemplate.example.collection;

import org.watertemplate.Template;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;

class MonthsGrid extends Template {

    private static final Collection<Month> months = Arrays.asList(Month.values());

    MonthsGrid(final Year year) {
        add("year", year);
        addCollection("months", months, (month, map) -> {
            map.add("lowerName", month.name().toLowerCase());
            map.add("daysCount", month.length(year.isLeap()));
        });
    }

    @Override
    protected String getFilePath() {
        return "months_grid.html";
    }
}
