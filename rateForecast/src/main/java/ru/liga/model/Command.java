package ru.liga.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.types.Commands;
import ru.liga.types.Currencies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * класс Command
 **/
public class Command {

    private static final Logger logger = LoggerFactory.getLogger(Command.class);
    private final String[] arrAction;

    public Command(String input) {
        this.arrAction = input.split(" ");
    }

    /**
     * метод getCurrencies возвращает массив валют
     *
     * @return String[] массив валют
     */
    public String[] getCurrencies() {
        logger.debug("Определение валют для расчета курса");
        logger.info("Определение валют для расчета курса");
        List<String> currencies = new ArrayList<>();
        for (int i = 1; !(arrAction[i].equals(Commands.DATE.getLowerCommand())
                || arrAction[i].equals(Commands.PERIOD.getLowerCommand())); i++) {
            if (arrAction[i].contains(",")) {
                String[] cur = arrAction[i].split(",");
                for (String dt : cur) {
                    checkCurrency(dt);
                    currencies.add(dt);
                }
                break;
            } else {
                checkCurrency(arrAction[i]);
                currencies.add(arrAction[i]);
            }
        }

        if (currencies.size() == 0){
            logger.debug("Валюта не определена");
            throw new RuntimeException("Валюта не определена");
        }

        String[] resCur = new String[currencies.size()];
        for (int i = 0; i < currencies.size(); i++) {
            resCur[i] = currencies.get(i);
        }
        logger.debug("Валюты определены: "+ Arrays.toString(resCur));
        return resCur;
    }

    /**
     * метод getPeriod возвращает дату или период на которую необходимо расчетать курс валют
     *
     * @return String команда
     */
    public List<LocalDate> getPeriod() {
        logger.debug("Определение периода для расчета курса");
        logger.info("Определение периода для расчета курса");
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 1;!(arrAction[i].equals(Commands.ALG.getLowerCommand())); i++) {
            if (arrAction[i].equals(Commands.DATE.getLowerCommand())) {
                if (arrAction[i + 1].equals(Commands.TOMORROW.getLowerCommand())) {
                    dates.add(LocalDate.now().plusDays(1));
                    return dates;
                } else {
                    dates.add(LocalDate.parse(arrAction[i + 1], DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    return dates;
                }
            } else if (arrAction[i].equals(Commands.PERIOD.getLowerCommand())) {
                checkPeriods(arrAction[i + 1]);
                return generateDates(arrAction[i + 1]);
            }
        }
        return dates;
    }

    /**
     * метод getAlg возвращает алгоритм для расчета курса валюты
     *
     * @return String алгоритм
     */
    public String getAlg() {
        logger.info("Определение алгоритма для расчета курса");
        StringBuilder alg = new StringBuilder();
        for (int i = 1; i < arrAction.length; i++) {
            if (arrAction[i].equals(Commands.ALG.getLowerCommand())) {
                checkAlg(arrAction[i + 1]);
                alg.append(arrAction[i + 1]);
                break;
            }
        }
        logger.debug("Алгоритм определен: "+ alg);
        return alg.toString();
    }

    /**
     * метод getGraph возвращает, формат результата (список/график)
     *
     * @return String формат
     */
    public String getOutPut() {
        logger.info("Определение формата результата");
        for (int i = 1; i < arrAction.length; i++) {
            if (arrAction[i].equals(Commands.OUTPUT.getLowerCommand())) {
                checkOutPut(arrAction[i + 1]);
                logger.debug("Формата результата определен: "+ arrAction[i + 1]);
                return arrAction[i + 1];
            }
        }
        return "";
    }

    //Метод checkAlg для проверки значения алгоритма
    private void checkAlg(String alg) {
        logger.debug("Проверка алгоритма");
        if (alg.equals(Commands.SEVENDAYS.getLowerCommand())
                || alg.equals(Commands.MYST.getLowerCommand())
                || alg.equals(Commands.LINEREG.getLowerCommand())
                || alg.equals(Commands.LASTYEARS.getLowerCommand())) {
        } else {
            logger.debug("Алгоритм " + alg + " не определен");
            throw new RuntimeException("Алгоритм " + alg + " не определен");
        }
    }

    //Метод checkOutPut для проверки значения формата результатов
    private void checkOutPut(String format) {
        logger.debug("Проверка формата результатов");
        if (format.equals(Commands.LIST.getLowerCommand())
                || format.equals(Commands.GRAPHIC.getLowerCommand())) {
        } else {
            logger.debug("Формат результата" + format + " не определен");
            throw new RuntimeException("Формат результата " + format + " не определен");
        }
    }


    //Метод checkCurrency для проверки значения валюты
    private void checkCurrency(String currency) {
        logger.debug("Проверка типа валюты");
        if (currency.equals(Currencies.EUR.name())
                || currency.equals(Currencies.BGN.name())
                || currency.equals(Currencies.AMD.name())
                || currency.equals(Currencies.USD.name())
                || currency.equals(Currencies.TRY.name())) {
        } else {
            logger.debug("Валюта " + currency + " не определена");
            throw new RuntimeException("Валюта " + currency + " не определена");
        }
    }

    //Метод checkPeriods для проверки значения периода
    private void checkPeriods(String period) {
        logger.debug("Проверка периода");
        if (period.equals(Commands.WEEK.getLowerCommand())
                || period.equals(Commands.MONTH.getLowerCommand())) {
        } else {
            logger.debug("Период " + period + " не определен");
            throw new RuntimeException("Период " + period + " не определен");
        }
    }

    //Метод generateDates генерит список дней на которые необходимо произвести расчет курса
    private List<LocalDate> generateDates(String period) {
        logger.debug("Генерация дат для расчета курсов");
        List<LocalDate> dates = new ArrayList<>();
        LocalDate startDay = LocalDate.now().plusDays(1);
        LocalDate endDay;
        if (period.equals(Commands.WEEK.getLowerCommand())) {
            endDay = startDay.plusWeeks(1);
            for (LocalDate date = startDay; date.isBefore(endDay); date = date.plusDays(1)) {
                dates.add(date);
            }
            return dates;
        } else if (period.equals(Commands.MONTH.getLowerCommand())) {
            endDay = startDay.plusMonths(1);
            for (LocalDate date = startDay; date.isBefore(endDay); date = date.plusDays(1)) {
                dates.add(date);
            }
            return dates;
        }
        return dates;
    }
}
