package lovemetal.command.impl;

import lovemetal.command.AbstractCommand;
import lovemetal.util.MessageUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class HelpCommand extends AbstractCommand {

    private static final List<String> helpMessage = List.of(
            " \uD83C\uDF40 SoundFetchBot - Музыка и видео бесплатно",
            "",
            " \uD83D\uDCC1 Команды: ",
            "  ● /audio *ссылка* - скачать звук из видео",
            ""
    );

    public HelpCommand() {
        super("help");
    }

    @Override
    public void onCommand(Update update, String[] args) {
        MessageUtil.sendMessage(helpMessage, update.getMessage().getChatId());
    }
}
