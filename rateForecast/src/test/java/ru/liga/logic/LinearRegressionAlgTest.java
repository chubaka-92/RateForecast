package ru.liga.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.mapper.ReadMapperCourse;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinearRegressionAlgTest {
    private List<Course> courseList;

    private LinearRegressionAlg linearRegressionAlg;

    @BeforeEach
    public void setup() {
        courseList = new ReadMapperCourse("EUR").getCourseListFromFile(30);
    }

    @Test
    void checkRateForecastToDate() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-07-15"));

        linearRegressionAlg = new LinearRegressionAlg(dates, courseList);

        assertThat(linearRegressionAlg.rateForecast().get(0).toString())
                .isEqualTo("Пт 15.07.2022 - 46,71");
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

        String[] expCourses = new String[]{"Пн 11.07.2022 - 48,98", "Вт 12.07.2022 - 48,27", "Ср 13.07.2022 - 47,68",
                "Чт 14.07.2022 - 47,21", "Пт 15.07.2022 - 46,71", "Сб 16.07.2022 - 46,26",
                "Вс 17.07.2022 - 45,83", "Пн 18.07.2022 - 48,98"};

        linearRegressionAlg = new LinearRegressionAlg(dates, courseList);

        for (int i = 0; i < 7; i++) {
            assertThat(linearRegressionAlg.rateForecast().get(i).toString())
                    .isEqualTo(expCourses[i]);
        }
    }
}