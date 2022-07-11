package ru.liga.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MysticalAlg {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Course> courseList;
    private final List<LocalDate> dates;

    public MysticalAlg(List<LocalDate> dates, List<Course> courseSourse) {
        this.dates = dates;
        this.courseList = courseSourse;
    }

    public List<Course> rateForecast() {
        logger.info("Расчет прогноза курса по алгоритму Mystical");

        List<Course> result = new ArrayList<>();
        if (dates.size() == 1) {
            while (dates.get(0).isAfter(courseList.get(0).getDay())) {
                addRandomCourse();
            }
            result.add(courseList.get(0));
        } else {
            while (dates.get(dates.size() - 1).isAfter(courseList.get(0).getDay())) {
                Course tempCourse = addRandomCourse();
                if (LocalDate.now().isBefore(tempCourse.getDay())) {
                    result.add(tempCourse);
                }
            }
            result.sort(Comparator.comparing(Course::getDay));
        }
        return result;
    }

    private Course addRandomCourse() {
        logger.debug("Добавление рандомного нового курса");
        this.courseList.add(0, new Course(1,
                this.courseList.get(0).getDay().plusDays(1),
                courseList.get(getRandomNumb()).getOneCoinCourse()));
        this.courseList.remove(30);
        return courseList.get(0);
    }

    /*
     * получаем рандомный индекс
     */
    private int getRandomNumb() {
        return new Random().nextInt(courseList.size());
    }
}
