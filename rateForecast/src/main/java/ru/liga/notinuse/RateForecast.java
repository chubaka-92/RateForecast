/*
package ru.liga.notinuse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

*/
/**
*
 * ПРОТОТИП. слабонервным не смотреть
 *
 * *//*

public class RateForecast {
    public static void main(String[] args) throws IOException {
*/
/*        Scanner sc = new Scanner(System.in);

        System.out.println("Введите команду 'rate TRY tomorrow' чтоб узнать курс на завтра");
        System.out.println("либо 'rate USD week' чтоб узнать курс на неделю");
        String[] arrAction=sc.nextLine().split(" ");

        System.out.println(arrAction[1]);
        System.out.println(arrAction[2]);

        sc.close();*//*


        String fileUSD = "input\\RC_F01_06_2002_T17_06_2022_USD.csv";
        String fileEUR = "input\\RC_F01_06_2002_T17_06_2022_EUR.csv";
        String fileTRY = "input\\RC_F01_06_2002_T17_06_2022_TRY.csv";

        String[][] arrWeek = getWeekFromFile(fileTRY);

*/
/*        for (String[] arrSt:arrWeek){
            System.out.println(Arrays.toString(arrSt));
        }*//*


        System.out.println("******НА завтра******");
        String tomorrow = bilderResult(arrWeek);
        System.out.println(tomorrow);

        ///////часть для недели
        System.out.println("******На неделю******");

        //у нас 7 дней
        for (int l =0; l<7;l++){

            //временный массив
            String[][] tempArrWeek = new String[7][3];

            //формируем прогноз поочередно
            for (int i =0; i<tempArrWeek.length;i++){
                //заходим 1 раз чтоб сформировать прогноз на будущий день
                if(i==0){
                    String tempTomorrow = bilderResult(arrWeek);
                    System.out.println(tempTomorrow);
                    String tomorrowShort = tempTomorrow.replace(" -", "").replace(",", ".");
                    String[] arrTomorro = tomorrowShort.split(" ");

                    tempArrWeek[i][0] = "1";
                    for (int j = 1; j<3; j++){
                        tempArrWeek[i][j] = arrTomorro[j];
                    }
                }
                //дописываем массив записями последних дней
                else {
                    for (int j = 0; j<3; j++){
                        tempArrWeek[i][j] =arrWeek[i-1][j];
                    }
                }

            }

            arrWeek= Arrays.copyOfRange(tempArrWeek,0,tempArrWeek.length);

        }

    }


    */
/**
     * Сборщик резултата прогноза курса
     * *//*

    private static String bilderResult(String[][] arrWeek) {
        double curs = getRateTomorrow(arrWeek);
        String cursSt = String.valueOf(curs).replace(".", ",");
        //System.out.println(cursSt);


        //получаем дату
        LocalDate nextDay;
        if(LocalDate.now().isEqual(LocalDate.parse(arrWeek[0][1],DateTimeFormatter.ofPattern("dd.MM.yyyy")))){
            nextDay = LocalDate.parse(arrWeek[0][1],DateTimeFormatter.ofPattern("dd.MM.yyyy")).plusDays(1);
        } else if(LocalDate.now().isBefore(LocalDate.parse(arrWeek[0][1],DateTimeFormatter.ofPattern("dd.MM.yyyy")))){
            nextDay = LocalDate.parse(arrWeek[0][1],DateTimeFormatter.ofPattern("dd.MM.yyyy")).plusDays(1);
        } else {
            nextDay =LocalDate.now().plusDays(1);
        }
        //

        String nextDaySt =nextDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));


        //получаем день недели
        String nextDayWeek =nextDay.getDayOfWeek()
                .getDisplayName(TextStyle.SHORT,new Locale("ru","RU"));
        String nextDayWeekSt = nextDayWeek.substring(0, 1).toUpperCase() + nextDayWeek.substring(1);

        return nextDayWeekSt +" "+ nextDaySt+ " - " + cursSt;
    }

    */
/**
     * расчет курса валюты на завтра
     * *//*

    private static double getRateTomorrow(String[][] arrWeek) {
        double resRate;
        double sum = 0;
        for (String[] arrSt: arrWeek){
            sum= sum + Double.parseDouble(arrSt[2])/Double.parseDouble(arrSt[0]);
        }
        resRate=sum/ arrWeek.length;
        return Math.ceil(resRate*10000)/10000;
    }

    private static String[][] getWeekFromFile(String filePath) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));

        String row = "";
        int lengh = 0;
        String[][] arrWeek = new String[7][3];

        while ((row= csvReader.readLine())!=null && lengh<=7){
            String[] data = row.split(";");

            for (int i = 0; i<3 && lengh>0;i++){
                if (i==2){
                    arrWeek[lengh-1][i] = data[i].substring(2,data[i].length()-1).replace(",",".");
                } else {
                    arrWeek[lengh - 1][i] = data[i];
                }
            }
            lengh++;
        }
        csvReader.close();
        return arrWeek;
    }


}
*/
