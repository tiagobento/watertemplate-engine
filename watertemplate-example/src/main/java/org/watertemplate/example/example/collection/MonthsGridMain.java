package org.watertemplate.example.example.collection;

import java.time.Year;

public class MonthsGridMain {

    public static void main(String[] args) {
        MonthsGrid monthsGrid = new MonthsGrid(Year.of(2015));
        System.out.println(monthsGrid.render());
    }
}
