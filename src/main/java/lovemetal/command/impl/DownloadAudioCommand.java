package lovemetal.command.impl;

import lovemetal.command.AbstractCommand;
import lovemetal.file.impl.FileMP3Downloader;
import lovemetal.runnable.RunnableTask;
import lovemetal.tracker.DownloadingUserTracker;
import lovemetal.util.FileUtil;
import lovemetal.util.MessageUtil;
import lovemetal.util.VideoUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DownloadAudioCommand extends AbstractCommand {

    public DownloadAudioCommand() {
        super("audio");
    }

    @Override
    public void onCommand(Update update, String[] args) {
        Long chatID = update.getMessage().getChatId();
        if (args.length < 2) {
            MessageUtil.sendMessage("❌ Укажите ссылку!", chatID);
            return;
        }

        int messageID = MessageUtil.sendMessage("⏳ Проверка видео...", chatID);
        String providedURL = args[1];
        if (!VideoUtil.isValidURL(providedURL)) {
            MessageUtil.sendMessage("⚠️ Вы можете указать только:* YouTube, TikTok или SoundCloud!", chatID);
            return;
        }

        if (VideoUtil.getVideoDuration(providedURL).toMinutes() >= 30) {
            MessageUtil.sendMessage("⏳ Видео превышает 30 минут!", chatID);
            return;
        }

        if (DownloadingUserTracker.getInstance().isUserDownloading(chatID)) {
            int systemMessageID = MessageUtil.sendMessage("⏳ Вы уже начали скачивание!", chatID);
            int userMessageID = update.getMessage().getMessageId();

            new RunnableTask()
                    .setRunnable(() -> {
                        MessageUtil.removeMessage(systemMessageID, chatID);
                        MessageUtil.removeMessage(userMessageID, chatID);
                    })
                    .setDelay(500)
                    .setTimeUnit(TimeUnit.MILLISECONDS)
                    .schedule();
            return;
        }

        DownloadingUserTracker.getInstance().addUser(chatID);
        MessageUtil.editMessage("✅ Принято на скачивание, ожидайте...", messageID, chatID);

        providedURL = VideoUtil.sanitizeURL(providedURL);
        File file = FileMP3Downloader.getInstance().getDownloadedFile(
                providedURL,
                VideoUtil.getVideoTitle(providedURL),
                messageID,
                chatID);

        FileUtil.sendFile(file, chatID);
        MessageUtil.editMessage("✅ Спасибо за использование!", messageID, chatID);
        new RunnableTask()
                .setRunnable(() -> MessageUtil.removeMessage(messageID, chatID))
                .setDelay(2)
                .setTimeUnit(TimeUnit.SECONDS)
                .schedule();
    }
}
