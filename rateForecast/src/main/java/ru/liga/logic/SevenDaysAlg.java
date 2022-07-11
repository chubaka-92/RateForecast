package ru.liga.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс RateForecastService в который формирует расчет курса валюты.
 * <p>
 * на вход подается Command command
 */


public class SevenDaysAlg {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Course> courseList;
    private final List<LocalDate> dates;

    public SevenDaysAlg(List<LocalDate> dates, List<Course> courseSourse) {
        this.dates = dates;
        this.courseList = courseSourse;
    }

    /**
     * Метод отображает курс валют
     */
    public List<Course> rateForecast() {
        logger.info("Расчет прогноза курса по алгоритму SevenDays");
        List<Course> result = new ArrayList<>();
        if (dates.size() == 1) {
            while (dates.get(0).isAfter(courseList.get(0).getDay())) {
                addNewCourse();
            }
            result.add(courseList.get(0));
        } else {
            while (dates.get(dates.size() - 1).isAfter(courseList.get(0).getDay())) {
                Course tempCourse = addNewCourse();
                if (LocalDate.now().isBefore(tempCourse.getDay())) {
                    result.add(tempCourse);
                }
            }
            result.sort(Comparator.comparing(Course::getDay));
        }
        return result;
    }

    /*
     * Добавление нового курса в courseList
     *
     * 1) получаем  новую стоимость валюты
     * 2) добавляем в начало листа новый курс
     * 3) убираем лишний курс(самый старый из списка)
     */
    private Course addNewCourse() {
        logger.debug("Добавление нового курса");
        BigDecimal resRate = getNewRate(this.courseList);
        this.courseList.add(0, new Course(1, this.courseList.get(0).getDay().plusDays(1), resRate));
        this.courseList.remove(7);
        return courseList.get(0);
    }

    /*
     * расчет курса валюты на следующий день
     */
    private BigDecimal getNewRate(List<Course> courseList) {
        logger.debug("Расчет стоимости валюты");
        BigDecimal resRate;
        BigDecimal sum = new BigDecimal(0);
        for (Course cr : courseList) {
            sum = sum.add(cr.getOneCoinCourse());
        }
        resRate = sum.divide(BigDecimal.valueOf(7), RoundingMode.HALF_UP);
        return resRate;
    }
}
