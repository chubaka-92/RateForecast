package ru.liga.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CourseTest {


    private Course course = new Course(2, LocalDate.now(), new BigDecimal("130.7024"));

    @Test
    void checkGetDay() {
        assertThat(course.getDay())
                .isEqualTo(LocalDate.now());
    }

    @Test
    void checkGetOneCoinCourse() {
        assertThat(course.getOneCoinCourse().toString())
                .isEqualTo("65.3512");
    }

    @Test
    void checkGetNominal() {
        assertThat(course.getNominal())
                .isEqualTo(2);
    }

    @Test
    void checkGetCours() {
        assertThat(course.getCours().toString())
                .isEqualTo("130.7024");
    }
}