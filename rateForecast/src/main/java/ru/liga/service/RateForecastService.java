package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Course;
import ru.liga.model.ReadMapperCourse;
import ru.liga.types.Commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Класс RateForecastService в который формирует расчет курса валюты.
 * <p>
 * на вход подается Command command
 */


public class RateForecastService {
    private final List<Course> courseList;
    private final Command command;

    public RateForecastService(Command command) {
        this.command = command;
        this.courseList = new ReadMapperCourse(command.getCurrency()).getCourseListFromFile();
    }

    /**
     * Метод отображает курс валют
     */
    public void rateForecast() {
        if (command.getCommand().equals(Commands.TOMORROW.getLowerCommand())) {
            System.out.println(rateForecastTomorrow());

        } else if (command.getCommand().equals(Commands.WEEK.getLowerCommand())) {
            for (Course cr : rateForecastToWeek()) {
                System.out.println(cr);
            }

        } else {
            System.out.println("мая не панимать какой прогноз твая хатеть");
        }
    }

    /*
     * расчет курса валюты на завтра
     */
    private Course rateForecastTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        while (tomorrow.isAfter(courseList.get(0).getDay())) {
            addNewCourse();
        }
        return courseList.get(0);
    }

    /*
     * Добавление нового курса в courseList
     * <p>
     * 1) получаем  новую стоимость валюты
     * 2) добавляем в начало листа новый курс
     * 3) убираем лишний курс(самый старый из списка)
     */
    private void addNewCourse() {

        BigDecimal resRate = getNewRate(this.courseList);
        this.courseList.add(0, new Course(1, this.courseList.get(0).getDay().plusDays(1), resRate));
        this.courseList.remove(7);
    }

    /*
     * расчет курса валюты на следующий день
     */
    private BigDecimal getNewRate(List<Course> courseList) {
        BigDecimal resRate;
        BigDecimal sum = new BigDecimal(0);
        for (Course cr : courseList) {
            sum = sum.add(cr.getOneCoinCourse());
        }
        resRate = sum.divide(BigDecimal.valueOf(7), RoundingMode.HALF_UP);
        return resRate;
    }

    /*
     * расчет курса валюты на неделю
     */
    private List<Course> rateForecastToWeek() {

        LocalDate toWeek = LocalDate.now().plusDays(7);

        while (toWeek.isAfter(courseList.get(0).getDay())) {
            addNewCourse();
        }
        courseList.sort(Comparator.comparing(Course::getDay));
        return courseList;
    }
}
