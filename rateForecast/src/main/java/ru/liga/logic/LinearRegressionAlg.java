package ru.liga.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinearRegressionAlg {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Course> courseList;
    private final List<LocalDate> dates;

    private static final int PERIOD_COUNT_DAYS = 7;
    public LinearRegressionAlg(List<LocalDate> dates, List<Course> courseSourse) {
        this.dates = dates;
        this.courseList = courseSourse;
    }

    public List<Course> rateForecast() {
        logger.info("Расчет прогноза курса по алгоритму Mystical");
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

    private Course addNewCourse() {
        logger.debug("Добавление нового курса");
        this.courseList.add(0, new Course(1, this.courseList.get(0).getDay().plusDays(1), getNewRate(courseList)));
        this.courseList.remove(PERIOD_COUNT_DAYS);
        return courseList.get(0);
    }


    /*
     * расчет курса валюты на следующий день
     */
    private BigDecimal getNewRate(List<Course> courseList) {
        logger.debug("Расчет стоимости валюты");
        Double[] x = new Double[courseList.size()];
        Double[] y = new Double[courseList.size()];

        for (int i = 0; i < courseList.size(); i++) {
            x[i] = (double) i;
        }

        for (int i = courseList.size() - 1, j = 0; i >= 0; i--, j++) {
            y[j] = Double.parseDouble(courseList.get(i).getCours().toString());
        }

        LinearRegression linearRegression = new LinearRegression(x, y);

        BigDecimal resRate = BigDecimal.valueOf(linearRegression.predict(31));
        return resRate;
    }
}
