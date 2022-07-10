package ru.liga.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.mapper.ReadMapperCourse;
import ru.liga.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class SevenDaysAlgTest {

    private List<Course> courseList;

    private SevenDaysAlg sevenDaysAlg;

    @BeforeEach
    public void setup() {
        courseList = new ReadMapperCourse("USD").getCourseListFromFile(7);
    }


    @Test
    void checkRateForecastToDate() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(0, LocalDate.parse("2022-07-12"));

        sevenDaysAlg = new SevenDaysAlg(dates, courseList);

        assertThat(sevenDaysAlg.rateForecast().get(0).getCours().toString())
                .isEqualTo("52.8087");

    }

    @Test
    void checkRateForecastToManyDays() {
        List<LocalDate> dates = new ArrayList<>();
        //dates.add(0,LocalDate.parse("2022-07-11"));
        dates.add(0, LocalDate.parse("2022-07-12"));
        dates.add(1, LocalDate.parse("2022-07-13"));
        dates.add(2, LocalDate.parse("2022-07-14"));
        dates.add(3, LocalDate.parse("2022-07-15"));
        dates.add(4, LocalDate.parse("2022-07-16"));
        dates.add(5, LocalDate.parse("2022-07-17"));
        dates.add(6, LocalDate.parse("2022-07-18"));

        // String[] expCur = new String[]{"52.8151","52.8087", "52.8109", "52.8251", "52.8465", "52.8401", "52.8250"};
        String[] expCur = new String[]{"52.8087", "52.8109", "52.8251", "52.8465", "52.8401", "52.8250", "52.8245"};

        SevenDaysAlg sevenDaysAlg = new SevenDaysAlg(dates, courseList);

        for (int i = 0; i < 7; i++) {
            assertThat(sevenDaysAlg.rateForecast().get(i).getCours().toString())
                    .isEqualTo(expCur[i]);
        }
    }
}