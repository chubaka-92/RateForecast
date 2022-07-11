package ru.liga.tgbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.model.Command;
import ru.liga.model.Course;
import ru.liga.service.CourseMakerService;
import ru.liga.tgbot.comands.HelpCommand;
import ru.liga.tgbot.comands.StartCommand;
import ru.liga.types.Commands;

import java.util.List;
import java.util.Map;

public final class Bot extends TelegramLongPollingCommandBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramLongPollingCommandBot.class);
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public Bot(String botName, String botToken) {
        super();
        logger.info("создание бота");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help", "Помощь"));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }


    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        logger.info("Обработка запроса начата");
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        Command command = new Command(msg.getText());
        ContentBot contentBot;

        try {
            Map<String, List<Course>> coursesAll = new CourseMakerService(command).forecastingСourses();

            contentBot = new ContentBot(command.getOutPut(), coursesAll, String.valueOf(chatId));

            contentBot.setContentResult();

            setAnswer(contentBot);
        } catch (Exception e) {
            logger.debug("ERROR: " + e.getMessage());
            setAnswer(chatId, userName, e.getMessage());
        }

        logger.info("Обработка запроса закончена");
    }

    /**
     * Формирование имени пользователя
     *
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        logger.debug("getUserName Start");
        User user = msg.getFrom();
        String userName = user.getUserName();
        logger.debug(" getUserName end");
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     *
     * @param chatId   id чата
     * @param userName имя пользователя
     * @param text     текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        logger.debug("setAnswer Start");
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.debug("Не удалось отправить сообщение: " + e.getMessage());
        }
        logger.debug("setAnswer end");
    }

    /**
     * Отправка ответа
     *
     * @param contentBot контент ответа
     */
    private void setAnswer(ContentBot contentBot) {
        try {
            if (contentBot.getFormat().equals(Commands.GRAPHIC.getLowerCommand())) {
                execute(contentBot.getPhoto());
            } else {
                execute(contentBot.getMessageText());
            }
        } catch (Exception e) {
            logger.debug("Не удалось отправить сообщение: " + e.getMessage());
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        logger.info("onUpdatesReceived Start");
        super.onUpdatesReceived(updates);
        logger.info("onUpdatesReceived end");

    }
}
