package ru.liga.model;

public class Command {

    private final String currency;

    private final String command;

    private final String[] arrAction;

    public Command(String input) {
        this.arrAction = input.split(" ");
        this.currency = arrAction[1];
        this.command = arrAction[2];
    }

    public String getCurrency() {
        return currency;
    }

    public String getCommand() {
        return command;
    }
}
