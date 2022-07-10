package ru.liga.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.mapper.ReadMapperCourse;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MysticalAlgTest {

    private MysticalAlg mysticalAlg;
    private List<Course> courseList;
    private List<String> actualCarrency;


    @BeforeEach
    public void setup() {
        courseList = new ReadMapperCourse("EUR").getCourseListFromFile(30);
        actualCarrency = new ArrayList<>();
        for (Course cr : courseList) {
            actualCarrency.add(cr.getCours().toString());
        }
    }

    @Test
    void checkRateForecastToDate() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-07-15"));

        mysticalAlg = new MysticalAlg(dates, courseList);

        assertThat(actualCarrency).contains(mysticalAlg.rateForecast().get(0).getCours().toString());

    }

    @Test
    void checkRateForecastToManyDays() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-07-12"));
        dates.add(1, LocalDate.parse("2022-07-13"));
        dates.add(2, LocalDate.parse("2022-07-14"));
        dates.add(3, LocalDate.parse("2022-07-15"));
        dates.add(4, LocalDate.parse("2022-07-16"));
        dates.add(5, LocalDate.parse("2022-07-17"));
        dates.add(6, LocalDate.parse("2022-07-18"));

        mysticalAlg = new MysticalAlg(dates, courseList);

        for (Course cr : mysticalAlg.rateForecast()) {
            assertThat(actualCarrency).contains(cr.getCours().toString());
        }
    }

}