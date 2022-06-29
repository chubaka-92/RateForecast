package ru.liga.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReadMapperCourse {

    private final String RESOURCES = "src\\main\\resources\\";
    private final String currency;

    public ReadMapperCourse(String currency) {
        this.currency = currency;
    }

    /**
     * Метод getCourseListFromFile читает 7 запис из файла с информацией о курсах
     * И возвращает лист из 7 последних записей курса
     *
     * @return List<Course> список курсов
     */
    public List<Course> getCourseListFromFile(){
        //BufferedReader csvReader = null;
        try (BufferedReader csvReader = new BufferedReader(new FileReader(getFilePath()))){
            List<Course> list = new ArrayList<>();
            String row = "";
            int countStr = 0;
            //csvReader = new BufferedReader(new FileReader(getFilePath()));
            csvReader.readLine(); // пропускаем первую строку файла с описанием колонок
            while ((row = csvReader.readLine()) != null && countStr < 7) {

                String[] data = row.split(";");

                list.add(new Course(Integer.parseInt(data[0].replace(" ", "")),
                        LocalDate.parse(data[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        BigDecimal.valueOf(Double.parseDouble((data[2].substring(2, data[2].length() - 1).replace(",", "."))))));

                countStr++;
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Метод getPathFile выбирает путь до фала в зависимости от валюты
     */
    private String getFilePath() {
        if (currency.equals("USD")) {
            return RESOURCES + "RC_F01_06_2002_T18_06_2022_USD.csv";
        } else if (currency.equals("EUR")) {
            return RESOURCES + "RC_F01_06_2002_T18_06_2022_EUR.csv";
        } else if (currency.equals("TRY")) {
            return RESOURCES + "RC_F01_06_2002_T18_06_2022_TRY.csv";
        } else {
            throw new RuntimeException("Файл для валюты " + currency + " не определена");
        }
    }

}
