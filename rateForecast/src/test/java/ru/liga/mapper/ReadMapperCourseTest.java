package ru.liga.mapper;

import org.junit.jupiter.api.Test;
import ru.liga.model.Course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReadMapperCourseTest {
    private ReadMapperCourse readMapperCourse;

    @Test
    void checkGetCourseListFromFile() {
        readMapperCourse = new ReadMapperCourse("EUR");
        assertThat(readMapperCourse.getCourseListFromFile(15).size())
                .isEqualTo(15);
    }

    @Test
    void checkNegativeGetCourseListFromFileNoValidCurrency() {
        readMapperCourse = new ReadMapperCourse("Lol");
        assertThatThrownBy(() -> readMapperCourse.getCourseListFromFile(7))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void checkGetCourseListFromFileUSD() {
        readMapperCourse = new ReadMapperCourse("USD");
        assertThat(readMapperCourse.getCourseListFromFile(15).get(0))
                .isExactlyInstanceOf(Course.class);
    }

}