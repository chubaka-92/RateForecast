package ru.liga.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * класс Course
 * содержит в себе информаци о стоимости монет:
 * количество единиц - nominal
 * день - day
 * стоимостью монет - cours
 */
public class Course {
    private final int nominal;
    private final LocalDate day;
    private final BigDecimal cours;

    public Course(int nominal, LocalDate nextDay, BigDecimal cours) {
        this.nominal = nominal;
        this.day = nextDay;
        this.cours = cours;
    }

    /**
     * getDay возвращает день курса
     * */
    public LocalDate getDay() {
        return day;
    }

    /**
     * getOneCoinCourse возвращает стоисмость валюты за еденицу
     * */
    public BigDecimal getOneCoinCourse(){
        return cours.divide(BigDecimal.valueOf(nominal));
    }


    /*
     * Метод getWeekDay возвращает день недели в формате Пн,Вт,Ср,...
     */
    private String getWeekDay(LocalDate day) {
        String tempDayWeek = day.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru", "RU"));
        return tempDayWeek.substring(0, 1).toUpperCase() + tempDayWeek.substring(1);
    }

    @Override
    public String toString() {

        return getWeekDay(day) + " "
                + day.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + " - " + String.format("%.2f", getOneCoinCourse());
    }
}
