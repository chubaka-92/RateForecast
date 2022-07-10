package ru.liga.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {

    private Command command;

    @Test
    void checkGetCurrenciesOne() {
        command = new Command("rate EUR -date 18.07.2022 -alg linereg");
        assertThat(command.getCurrencies()[0])
                .isEqualTo("EUR");
    }

    @Test
    void checkGetCurrenciesMulti() {
        command = new Command("rate EUR,USD,AMD -date 18.07.2022 -alg linereg");
        String[] expCur = {"EUR", "USD", "AMD"};
        String[] actCur = command.getCurrencies();
        for (int i = 0; i < actCur.length; i++) {
            assertEquals(expCur[i], actCur[i]);
        }
    }

    @Test
    void checkNegativeGetCurrenciesEmpty() {
        command = new Command("rate -date 18.07.2022 -alg linereg");
        assertThatThrownBy(() -> command.getCurrencies())
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void checkNegativeGetCurrenciesNoValidCurrecy() {
        command = new Command("rate LOL -date 18.07.2022 -alg linereg");
        assertThatThrownBy(() -> command.getCurrencies())
                .isExactlyInstanceOf(RuntimeException.class);
    }


    @Test
    void checkGetPeriodDate() {
        command = new Command("rate EUR -date 18.07.2022 -alg linereg");
        assertThat(command.getPeriod().get(0))
                .isEqualTo("2022-07-18");
    }

    @Test
    void checkGetPeriodTomorrow() {
        command = new Command("rate EUR -date tomorrow -alg linereg");
        assertThat(command.getPeriod().get(0))
                .isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    void checkGetPeriodWeek() {
        command = new Command("rate EUR -period week -alg linereg");
        List<LocalDate> expDates = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            expDates.add(LocalDate.now().plusDays(i));
        }
        assertThat(command.getPeriod())
                .isEqualTo(expDates);
    }

    @Test
    void checkGetPeriodMonth() {
        command = new Command("rate EUR -period month -alg linereg");
        List<LocalDate> expDates = new ArrayList<>();

        LocalDate startDay = LocalDate.now().plusDays(1);
        LocalDate endDay = startDay.plusMonths(1);
        for (LocalDate date = startDay; date.isBefore(endDay); date = date.plusDays(1)) {
            expDates.add(date);
        }
        assertThat(command.getPeriod())
                .isEqualTo(expDates);
    }

    @Test
    void checkNegativeGetPeriod() {
        command = new Command("rate EUR -period Когданибудь -alg linereg");
        assertThatThrownBy(() -> command.getPeriod())
                .isExactlyInstanceOf(RuntimeException.class);
    }


    @Test
    void checkGetAlg() {
        command = new Command("rate EUR -date tomorrow -alg myst");
        assertThat(command.getAlg())
                .isEqualTo("myst");
    }

    @Test
    void checkNegativeGetAlg() {
        command = new Command("rate EUR -date tomorrow -alg lolniy");
        assertThatThrownBy(() -> command.getAlg())
                .isExactlyInstanceOf(RuntimeException.class);
    }


    @Test
    void checkGetOutPut() {
        command = new Command("rate EUR -date tomorrow -alg myst -output graphic");
        assertThat(command.getOutPut())
                .isEqualTo("graphic");
    }

    @Test
    void checkNegativeGetOutPut() {
        command = new Command("rate EUR -date tomorrow -alg myst -output cherezLol");
        assertThatThrownBy(() -> command.getOutPut())
                .isExactlyInstanceOf(RuntimeException.class);
    }

}