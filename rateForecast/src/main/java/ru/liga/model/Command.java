package ru.liga.model;

import ru.liga.types.Commands;
import ru.liga.types.Currencies;

/**
 * класс Command
 **/
public class Command {
    private final String[] arrAction;

    public Command(String input) {
        this.arrAction = input.split(" ");
    }

    /**
     * метод getCurrency возвращает название валюты
     *
     * @return String валюта
     */
    public String getCurrency() {
        if (arrAction[1].equals(Currencies.EUR.name())
                || arrAction[1].equals(Currencies.USD.name())
                || arrAction[1].equals(Currencies.TRY.name())) {
            return arrAction[1];
        } else {
            throw new RuntimeException("Валюта " + arrAction[1] + " не определена");
        }
    }

    /**
     * метод getCommand возвращает команду для расчета курса валюты
     *
     * @return String команда
     */
    public String getCommand() {
        if (arrAction[0].equals(Commands.RATE.getLowerCommand())
                && (arrAction[2].equals(Commands.WEEK.getLowerCommand())
                || arrAction[2].equals(Commands.TOMORROW.getLowerCommand()))) {
            return arrAction[2];
        } else {
            throw new RuntimeException("Команда " + arrAction[2] + " не определена");
        }
    }
}
