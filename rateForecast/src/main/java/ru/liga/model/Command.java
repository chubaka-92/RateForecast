package ru.liga.model;

import ru.liga.enums.Commands;
import ru.liga.enums.Currencies;

/**
 * класс Command
 *
 **/
public class Command {
    private final String[] arrAction;

    public Command(String input) {
        this.arrAction = input.split(" ");
    }

    /**
     * метод getCurrency возвращает название валюты
     * */
    public String getCurrency() {
        if(arrAction[1].equals(Currencies.EUR.name())
                || arrAction[1].equals(Currencies.USD.name())
                || arrAction[1].equals(Currencies.TRY.name())){
            return arrAction[1];
        } else {
            throw new RuntimeException("Валюта "+ arrAction[1] +" не определена");
        }
    }

    /**
     * метод getCommand возвращает команду для расчета курса валюты
     * */
    public String getCommand() {
            if(arrAction[2].equals(Commands.week.name()) || arrAction[2].equals(Commands.tomorrow.name())){
                return arrAction[2];
            } else {
                throw new RuntimeException("Команда "+ arrAction[2] +" не определена");
            }
    }
}
