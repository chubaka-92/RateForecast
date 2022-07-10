package ru.liga.types;

/**
 * Типы команд планирования курса валют
 */
public enum Commands {
    RATE("rate"),
    TOMORROW("tomorrow"), WEEK("week"), MONTH("month"),
    DATE("-date"), PERIOD("-period"),
    ALG("-alg"),
    SEVENDAYS("sevendays"), LASTYEARS("lastyears"), MYST("myst"), LINEREG("linereg"),
    OUTPUT("-output"),
    LIST("list"), GRAPHIC("graphic");


    private final String lowerCommand;

    Commands(String lowerComand) {
        this.lowerCommand = lowerComand;
    }

    public String getLowerCommand() {
        return lowerCommand;
    }

}
