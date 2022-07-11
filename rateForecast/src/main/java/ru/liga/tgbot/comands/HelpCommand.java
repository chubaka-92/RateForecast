package ru.liga.tgbot.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand {

    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        logger.info("Вызван /help");
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Чтоб получить тот или иной курс тебе нужно ввести: \n" +
                        "1) Первое слово 'rate'\n" +
                        "2) Валюта. Пока на выбор доступны (USD, EUR, TRY, AMD и BGN)\n" +
                        "3) Если хочешь курс на завтра то пиши '-date tomorrow' " +
                        "если на конкретную дату то '-date 15.07.22', ЛИБО можешь указать прогноз на период '-period week/month'\n" +
                        "4) укажи алгоритм расчета '-alg sevendays/lastyears/myst/linear'\n" +
                        "5) Если указал период week или month то можно вывести курс в формате списка '-output list' либо постоить график'-output graphic'\n" +
                        "\n" +
                        "Примеры готовых команд: rate USD -date tomorrow -alg sevenday\n" +
                        "rate EUR -date 10.07.2022 -alg myst\n" +
                        "rate EUR -period week -alg lastyears -output list");
    }
}