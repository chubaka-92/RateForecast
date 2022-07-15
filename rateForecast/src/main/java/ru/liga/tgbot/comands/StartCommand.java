package ru.liga.tgbot.comands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Старт"
 */
public class StartCommand extends ServiceCommand {

    private static final Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        logger.info("Вызван Start");
        sendAnswer(absSender, chat.getId(),
                "Дарова бандит! я умею тупить над твоими запросами, и иногда выдовать прогноз курса валют." +
                        "набирай команду /help для подробностей команд");
    }
}