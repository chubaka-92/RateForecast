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

    private final String currency;
    private final String pathFile;

    private List<Course> courseList;

    public ReadMapperCourse(String currency) throws IOException {
        this.currency = currency;
        this.pathFile = getPathFile();
        this.courseList = mapCourseListFromFile();
    }

    /**
     * Метод getPathFile выбирает путь до фала в зависимости от валюты
     */
    private String getPathFile() {
        if (currency.equals("USD")) {
            return "src\\main\\resources\\RC_F01_06_2002_T18_06_2022_USD.csv";
        } else if (currency.equals("EUR")) {
            return "src\\main\\resources\\RC_F01_06_2002_T18_06_2022_EUR.csv";
        } else if (currency.equals("TRY")) {
            return "src\\main\\resources\\RC_F01_06_2002_T18_06_2022_TRY.csv";
        }
        return "";
    }


    /**
     * Метод getCourseListFromFile читает 7 запис из файла с информацией о курсах
     * лист из 7 последних записей курса
     */
    private List<Course> mapCourseListFromFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(pathFile));

        List<Course> list= new ArrayList<>();
        String row = "";
        int countStr = 0;

        csvReader.readLine(); // пропускаем первую строку файла с описанием колонок
        while ((row = csvReader.readLine()) != null && countStr < 7) {

            String[] data = row.split(";");

            list.add(new Course(Integer.parseInt(data[0].replace(" ", "")),
                    LocalDate.parse(data[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    BigDecimal.valueOf(Double.parseDouble((data[2].substring(2, data[2].length() - 1).replace(",", "."))))));

            countStr++;
        }
        csvReader.close();
        return list;
    }

     public List<Course> getCourseList() {
        return this.courseList;
    }



}
