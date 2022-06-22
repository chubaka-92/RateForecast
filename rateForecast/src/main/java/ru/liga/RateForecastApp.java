package ru.liga;

import ru.liga.model.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Курс валюты на завтра
 * "rate TRY tomorrow" Вт 22.02.2022 - 6,11;
 * Курс валюты на 7 дней
 * "rate USD week"
 * Вт 22.02.2022 - 75,45
 * Ср 23.02.2022 - 76,12
 * Чт 24.02.2022 - 77,34
 * Пт 25.02.2022 - 78,23
 * Сб 26.02.2022 - 80,11
 * Вс 27.02.2022 - 82,10
 * Пн 28.02.2022 - 90,45
 */

public class RateForecastApp {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Введите команду 'rate TRY tomorrow' чтоб узнать курс на завтра");
        System.out.println("либо 'rate USD week' чтоб узнать курс на неделю");
        System.out.println("Достуные валюты: USD, EUR, TRY.");
        String[] arrAction = sc.nextLine().split(" ");

        sc.close();

        String currency = arrAction[1];
        String pathFile = getPathFile(currency);
        String action = arrAction[2];
        ArrayList<Course> courseList;

        if (!pathFile.equals("")) {

            courseList = getCourseListFromFile(pathFile);

            if (action.equals("tomorrow")) {
                System.out.println(rateForecastTomorrow(courseList));

            } else if (action.equals("week")) {
                for (Course cr : rateForecastToWeek(courseList)) {
                    System.out.println(cr);
                }

            } else {
                System.out.println("мая не панимать какой прогноз твая хатеть");
            }

        } else {
            System.out.println("Файл для прогноза валюты " + currency + " отсутствует.");
        }

    }

    /**
     * Метод getPathFile выбирает путь до фала в зависимости от валюты
     */
    public static String getPathFile(String currency) {
        if (currency.equals("USD")) {
            return "input\\RC_F01_06_2002_T17_06_2022_USD.csv";
        } else if (currency.equals("EUR")) {
            return "input\\RC_F01_06_2002_T17_06_2022_EUR.csv";
        } else if (currency.equals("TRY")) {
            return "input\\RC_F01_06_2002_T17_06_2022_TRY.csv";
        }
        return "";
    }

    /**
     * Метод getCourseListFromFile читает 7 запис из файла с информацией о курсах
     * лист из 7 последних записей курса
     */
    public static ArrayList<Course> getCourseListFromFile(String path) throws IOException {
        ArrayList<Course> list = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));

        String row = "";
        int countStr = 0;

        csvReader.readLine(); // пропускаем первую строку файла с описанием колонок
        while ((row = csvReader.readLine()) != null && countStr < 7) {

            String[] data = row.split(";");

            list.add(new Course(Integer.parseInt(data[0].replace(" ", "")),
                    LocalDate.parse(data[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    Double.parseDouble(data[2].substring(2, data[2].length() - 1).replace(",", "."))));

            countStr++;
        }
        csvReader.close();

        return list;
    }

    /**
     * расчет курса валюты на завтра
     */
    public static Course rateForecastTomorrow(ArrayList<Course> courseList) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        while (tomorrow.isAfter(courseList.get(0).getDay())) {
            addNewCourse(courseList);
        }
        return courseList.get(0);
    }

    /**
     * Добавление нового курса в courseList
     * <p>
     * 1) получаем  новую стоимость валюты
     * 2) добавляем в начало листа новый курс
     * 3) убираем лишний курс(самый старый из списка)
     */
    private static void addNewCourse(ArrayList<Course> courseList) {

        double resRate = getNewRate(courseList);
        courseList.add(0, new Course(1, courseList.get(0).getDay().plusDays(1), resRate));
        courseList.remove(7);
    }

    /**
     * расчет курса валюты на следующий день
     */
    private static double getNewRate(List<Course> courseList) {
        double resRate;
        double sum = 0;
        for (Course cr : courseList) {
            sum = sum + (cr.getCours() / cr.getNominal());
        }
        resRate = sum / courseList.size();
        return resRate;
    }


    /**
     * расчет курса валюты на неделю
     */
    public static ArrayList<Course> rateForecastToWeek(ArrayList<Course> courseList) {

        LocalDate toWeek = LocalDate.now().plusDays(7);

        while (toWeek.isAfter(courseList.get(0).getDay())) {
            addNewCourse(courseList);
        }
        courseList.sort(Comparator.comparing(Course::getDay));
        return courseList;
    }


}
