package ru.liga.tgbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.model.Course;
import ru.liga.types.Commands;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ContentBot {
    private static final Logger logger = LoggerFactory.getLogger(ContentBot.class);
    private SendMessage messageText;

    private SendPhoto photo;

    private final String format;
    private final String chatId;
    private final Map<String, List<Course>> coursesAll;

    public ContentBot(String outPut, Map<String, List<Course>> coursesAll, String chatId) {
        this.format = outPut;
        this.coursesAll = coursesAll;
        this.chatId = chatId;
    }


    public void setContentResult() {
        if (format.equals(Commands.GRAPHIC.getLowerCommand())) {
            setPhoto();
        } else {
            StringBuilder text = new StringBuilder();
            for (Map.Entry<String, List<Course>> coursesEntity : coursesAll.entrySet()) {
                text.append(coursesEntity.getKey() + ":\n");
                for (Course course : coursesEntity.getValue()) {
                    text.append(course + "\n");
                }
            }
            setMessageText(text.toString());
        }
        setChatId();
    }

    public SendMessage getMessageText() {
        return messageText;
    }

    private void setMessageText(String messageText) {
        this.messageText = new SendMessage();
        this.messageText.setText(messageText);
    }

    public String getFormat() {
        return format;
    }

    public SendPhoto getPhoto() {
        return photo;
    }

    private void setPhoto() {
        this.photo = new SendPhoto();
        this.photo.setPhoto(new InputFile(new File("src/main/resources/lineChart.png")));
    }

    private void setChatId() {
        logger.debug("setChatId was called. Определение Id чата для отправки сообщения: " + chatId);
        if (messageText != null) {
            messageText.setChatId(chatId);
        } else {
            photo.setChatId(chatId);
        }
    }
}
