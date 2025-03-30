package lovemetal.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lovemetal.Main;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

@UtilityClass
public class MessageUtil {

    public void sendMessage(List<String> message, Long chatId) {
        String text = String.join("\n", message);
        sendMessage(text, chatId);
    }

    @SneakyThrows
    public Integer sendMessage(String message, Long chatID) {
        SendMessage sendMessage = new SendMessage(chatID.toString(), message);
        sendMessage.setChatId(chatID);
        sendMessage.setText(message);

        Message sentMessage = Main.getInstance().getTelegramClient().execute(sendMessage);
        return sentMessage.getMessageId();
    }

    @SneakyThrows
    public void editMessage(String message, Integer messageID, Long chatID) {
        EditMessageText editMessageText = new EditMessageText(message);
        editMessageText.setChatId(chatID);
        editMessageText.setMessageId(messageID);

        Main.getInstance().getTelegramClient().execute(editMessageText);
    }

    @SneakyThrows
    public void removeMessage(Integer messageID, Long chatID) {

        DeleteMessage deleteMessage = new DeleteMessage(chatID.toString(), messageID);
        Main.getInstance().getTelegramClient().execute(deleteMessage);
    }
}
