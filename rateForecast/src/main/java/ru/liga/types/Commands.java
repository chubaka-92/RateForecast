package ru.liga.types;

/**
 * Типы команд планирования курса валют
 */
public enum Commands {
    RATE("rate"), TOMORROW("tomorrow"), WEEK("week");

    private final String lowerCommand;

    Commands(String lowerComand) {
        this.lowerCommand = lowerComand;
    }

    public String getLowerCommand() {
        return lowerCommand;
    }

}
