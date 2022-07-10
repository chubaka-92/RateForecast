package ru.liga.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.mapper.ReadMapperCourse;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LastYearsAlgTest {

    private List<Course> courseList;
    private LastYearsAlg lastYearsAlg;

    @BeforeEach
    public void setup() {
        courseList = new ReadMapperCourse("EUR").getCourseListFromFile(365);
    }

    @Test
    void checkRateForecastToDate() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-08-25"));

        lastYearsAlg = new LastYearsAlg(dates, courseList);

        assertThat(lastYearsAlg.rateForecast().get(0).getCours().toString())
                .isEqualTo("86.8058");

    }

    @Test
    void checkRateForecastToManyDays() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-08-24"));
        dates.add(1, LocalDate.parse("2022-08-25"));
        dates.add(2, LocalDate.parse("2022-08-26"));
        dates.add(3, LocalDate.parse("2022-08-27"));
        dates.add(4, LocalDate.parse("2022-08-28"));
        dates.add(5, LocalDate.parse("2022-08-29"));
        dates.add(6, LocalDate.parse("2022-08-30"));

        String[] expCur = new String[]{"86.7838", "86.8058", "86.5814", "87.0576", "87.0304", "87.0304", "87.0304"};

        lastYearsAlg = new LastYearsAlg(dates, courseList);

        for (int i = 0; i < 7; i++) {
            assertThat(lastYearsAlg.rateForecast().get(i).getCours().toString())
                    .isEqualTo(expCur[i]);
        }
    }

}