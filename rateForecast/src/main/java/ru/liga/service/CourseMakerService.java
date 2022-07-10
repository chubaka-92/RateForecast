package ru.liga.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.graphic.Graphic;
import ru.liga.logic.LastYearsAlg;
import ru.liga.logic.LinearRegressionAlg;
import ru.liga.logic.MysticalAlg;
import ru.liga.logic.SevenDaysAlg;
import ru.liga.mapper.ReadMapperCourse;
import ru.liga.model.Command;
import ru.liga.model.Course;
import ru.liga.types.Commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseMakerService {

    private static final Logger logger = LoggerFactory.getLogger(CourseMakerService.class);
    private final Command command;

    public CourseMakerService(Command command) {
        this.command = command;
    }

    public Map<String, List<Course>> forecastingСourses() {
        logger.debug("Начало процесса вычесления прогноза курса");
        logger.info("Начало процесса вычесления прогноза курса");
        Map<String, List<Course>> coursesAll = new HashMap<>();
        List<Course> courses = new ArrayList<>();
        String[] currencies = command.getCurrencies();
        List<LocalDate> dates = command.getPeriod();
        String alg = command.getAlg();

        for (int cur = 0; cur < currencies.length; cur++) {
            ReadMapperCourse courseSours = new ReadMapperCourse(currencies[cur]);
            List<Course> coursesDB;

            if (alg.equals(Commands.SEVENDAYS.getLowerCommand())) {
                coursesDB = courseSours.getCourseListFromFile(7);
                courses = new SevenDaysAlg(dates, coursesDB).rateForecast();
            } else if (alg.equals(Commands.LASTYEARS.getLowerCommand())) {
                coursesDB = courseSours.getCourseListFromFile(365);
                courses = new LastYearsAlg(dates, coursesDB).rateForecast();
            } else if (alg.equals(Commands.MYST.getLowerCommand())) {
                coursesDB = courseSours.getCourseListFromFile(30);
                courses = new MysticalAlg(dates, coursesDB).rateForecast();
            } else if (alg.equals(Commands.LINEREG.getLowerCommand())) {
                coursesDB = courseSours.getCourseListFromFile(30);
                courses = new LinearRegressionAlg(dates, coursesDB).rateForecast();
            }

            coursesAll.put(currencies[cur], courses);
        }

        if (command.getOutPut() != null && command.getOutPut().equals(Commands.GRAPHIC.getLowerCommand())) {
            createGraphic(coursesAll);
        }
        logger.debug("Конец процесса вычесления прогноза курса");
        logger.info("Конец процесса вычесления прогноза курса");
        return coursesAll;
    }

    // createGraphic создает картинку графика
    private void createGraphic(Map<String, List<Course>> coursesAll) {
        Graphic graphic = new Graphic();
        graphic.setDataset(coursesAll);
        graphic.getCurrencyRatesAsGraph();
    }
}
