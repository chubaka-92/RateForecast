package ru.liga.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LastYearsAlg {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Course> courseList;
    private final List<LocalDate> dates;

    public LastYearsAlg(List<LocalDate> dates, List<Course> courseSourse) {
        this.dates = dates;
        this.courseList = courseSourse;
    }

    public List<Course> rateForecast() {
        logger.info("Расчет прогноза курса по алгоритму LastYears");
        List<Course> result = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            LocalDate tempDate = dates.get(i).minusYears(1);

            for (int j = 0; j < courseList.size(); j++) {
                if (courseList.get(j).getDay().equals(tempDate)) {
                    result.add(courseList.get(j));
                }
                if (result.size() < (i + 1) && j > 0 && courseList.get(j - 1).getDay().equals(tempDate.minusDays(1))) {
                    result.add(new Course(courseList.get(j - 1).getNominal(), tempDate, courseList.get(j - 1).getCours()));
                }
                if (result.size() < (i + 1) && j > 1 && courseList.get(j - 2).getDay().equals(tempDate.minusDays(2))) {
                    result.add(new Course(courseList.get(j - 2).getNominal(), tempDate, courseList.get(j - 2).getCours()));
                }
            }
        }
        return result;
    }
}
