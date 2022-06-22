package ru.liga.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * класс Course
 */
public class Course {
    private double nominal;
    private LocalDate day;
    private double cours;

    public Course(double nominal, LocalDate nextDay, double cours) {
        this.nominal = nominal;
        this.day = nextDay;
        this.cours = cours;
    }

    public Course() {

    }

    public double getNominal() {
        return nominal;
    }

    public LocalDate getDay() {
        return day;
    }

    public double getCours() {
        return cours;
    }


    /**
     * Метод getWeekDay возвращает день недели в формате Пн,Вт,Ср,...
     */
    public String getWeekDay(LocalDate day) {
        String tempDayWeek = day.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru", "RU"));
        return tempDayWeek.substring(0, 1).toUpperCase() + tempDayWeek.substring(1);
    }

    @Override
    public String toString() {

        return getWeekDay(day) + " "
                + day.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + " - " + String.format("%.2f", cours / nominal);
    }
}
