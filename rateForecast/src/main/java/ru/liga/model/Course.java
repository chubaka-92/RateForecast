package ru.liga.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * класс Course
 * содержит в себе информаци о стоимости монет
 */
public class Course {

    //private static final Logger logger = LoggerFactory.getLogger(Course.class); если настроить логирование на этом классе,то будет много спама.пока оставлю так
    private final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final int nominal;
    private final LocalDate day;
    private final BigDecimal cours;


    public Course(int nominal, LocalDate day, BigDecimal cours) {
        this.nominal = nominal;
        this.day = day;
        this.cours = cours;
    }

    /**
     * getDay возвращает день курса
     *
     * @return LocalDate day
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * getOneCoinCourse возвращает стоисмость валюты за еденицу
     *
     * @return BigDecimal результат деления
     */
    public BigDecimal getOneCoinCourse() {
        return cours.divide(BigDecimal.valueOf(nominal));
    }

    /**
     * getNominal количество монет
     *
     * @return int nominal
     */
    public int getNominal() {
        return nominal;
    }

    /**
     * getCours возвращает стоимость монет(курс)
     *
     * @return BigDecimal cours
     */
    public BigDecimal getCours() {
        return cours;
    }

    @Override
    public String toString() {

        return getWeekDay(day) + " "
                + day.format(FORMAT_DATE)
                + " - " + String.format("%.2f", getOneCoinCourse());
    }

    /*
     * Метод getWeekDay возвращает день недели в формате Пн,Вт,Ср,...
     */
    private String getWeekDay(LocalDate day) {
        String tempDayWeek = day.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, new Locale("ru", "RU"));
        return tempDayWeek.substring(0, 1).toUpperCase() + tempDayWeek.substring(1);
    }
}
