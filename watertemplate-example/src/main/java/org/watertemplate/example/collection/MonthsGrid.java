package org.watertemplate.example.collection;

import org.watertemplate.Template;
import org.watertemplate.example.nestedtemplates.MasterTemplate;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;

public class MonthsGrid extends Template {

    private static final Collection<Month> months = Arrays.asList(Month.values());

    private final Year year;

    public MonthsGrid(final Year year) {
        this.year = year;
        add("year", year.toString());
        addCollection("months", months, (month, map) -> {
            map.add("lowerName", month.name().toLowerCase());
            map.add("daysCount", month.length(year.isLeap()) + "");
        });
    }

    @Override
    protected Template getMasterTemplate() {
        return new MasterTemplate("Months grid for " + year);
    }

    @Override
    protected String getFilePath() {
        return "example/collection/months_grid.html";
    }
}
