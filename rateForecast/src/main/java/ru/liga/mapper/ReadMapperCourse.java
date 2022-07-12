package ru.liga.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReadMapperCourse {

    private static final Logger logger = LoggerFactory.getLogger(ReadMapperCourse.class);
    private final String RESOURCES = "src\\main\\resources\\db\\";
    private final String currency;

    public ReadMapperCourse(String currency) {
        this.currency = currency;
    }

    /**
     * Метод getCourseListFromFile читает заданное количество записей из файла с информацией о курсах
     * И возвращает лист из них
     *
     * @param countRows количество записей с курсом
     * @return List<Course> список курсов
     */
    public List<Course> getCourseListFromFile(int countRows) {
        logger.info("Начало подготовки исторических данных по руксам валют");
        try (BufferedReader csvReader = new BufferedReader(new FileReader(getFilePath()))) {
            List<Course> list = new ArrayList<>();
            String row = "";
            int countStr = 0;
            csvReader.readLine(); // пропускаем первую строку файла с описанием колонок
            while ((row = csvReader.readLine()) != null && countStr < countRows) {

                String[] data = row.split(";");
                list.add(new Course(Integer.parseInt(data[0].replace(" ", "")),
                        LocalDate.parse(data[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        BigDecimal.valueOf(Double.parseDouble((data[2]).replace(",", ".")))));
                countStr++;
            }
            logger.info("Подготовка исторических данных по руксам валют завершена");
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Метод getPathFile выбирает путь до фала в зависимости от валюты
     */
    private String getFilePath() {
        logger.debug("Определение пути до файла с историей курсов валют");  // можно через switch -> case
        if (currency.equals("USD")) {
            return RESOURCES + "USD_RC_F01_02_2005_T02_07_2022.csv";
        } else if (currency.equals("EUR")) {
            return RESOURCES + "EUR_RC_F01_02_2005_T02_07_2022.csv";
        } else if (currency.equals("TRY")) {
            return RESOURCES + "TRY_RC_F01_02_2005_T02_07_2022.csv";
        } else if (currency.equals("BGN")) {
            return RESOURCES + "BGN_RC_F01_02_2005_T02_07_2022.csv";
        } else if (currency.equals("AMD")) {
            return RESOURCES + "AMD_RC_F01_02_2005_T02_07_2022.csv";
        } else {
            throw new RuntimeException("Файл для валюты " + currency + " не определена");
        }
    }

}
