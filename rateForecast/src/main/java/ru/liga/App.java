package ru.liga;

import ru.liga.model.Command;
import ru.liga.service.RateForecastService;

import java.util.Scanner;

public class App {
    public static void main(String[] args){
        System.out.println("Введите команду 'rate TRY tomorrow' чтоб узнать курс на завтра");
        System.out.println("либо 'rate USD week' чтоб узнать курс на неделю");
        System.out.println("Достуные валюты: USD, EUR, TRY.");

        Scanner sc = new Scanner(System.in);

        new RateForecastService(new Command(sc.nextLine())).rateForecast();

        sc.close();

    }
}
