package lovemetal.listener;

import lovemetal.command.AbstractCommand;
import lovemetal.command.CommandRegistry;
import lovemetal.util.MessageUtil;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageListener implements LongPollingSingleThreadUpdateConsumer {

    private static final List<String> helpMessage = List.of(
            " \uD83C\uDF40 SoundFetchBot - Музыка и видео бесплатно",
            "",
            " \uD83D\uDCC1 Команды: ",
            "  ● /audio *ссылка* - скачать звук из видео",
            ""
    );

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;
        if (update.getMessage().getText().startsWith("/")) {
            String providedCommand = update.getMessage().getText().substring(1).split(" ")[0];
            AbstractCommand foundedCommand = CommandRegistry.getInstance().getCommandByName(providedCommand);

            if (foundedCommand == null) {
                MessageUtil.sendMessage(helpMessage, update.getMessage().getChatId());
                return;
            }

            String[] args = update.getMessage().getText().split(" ");
            CompletableFuture.runAsync(() -> foundedCommand.onCommand(update, args));
        } else {
            MessageUtil.sendMessage(helpMessage, update.getMessage().getChatId());
        }
    }
}
