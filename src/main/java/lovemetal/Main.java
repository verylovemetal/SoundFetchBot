package lovemetal;

import lombok.Getter;
import lombok.SneakyThrows;
import lovemetal.command.CommandRegistry;
import lovemetal.command.impl.DownloadAudioCommand;
import lovemetal.command.impl.HelpCommand;
import lovemetal.listener.MessageListener;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Getter
public class Main {

    @Getter
    private static final Main instance = new Main();

    @Getter
    private static final String botToken = "";

    @Getter
    private final TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(botToken, new MessageListener());

        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistry.getInstance().registerCommands(List.of(
                new DownloadAudioCommand(),
                new HelpCommand()
        ));
    }
}