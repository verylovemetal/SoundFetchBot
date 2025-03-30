package lovemetal.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lovemetal.Main;
import lovemetal.tracker.DownloadingUserTracker;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

@UtilityClass
public class FileUtil {

    @SneakyThrows
    public void sendFile(File audioFile, Long chatID) {
        SendAudio sendAudio = new SendAudio(chatID.toString(), new InputFile(audioFile));
        Main.getInstance().getTelegramClient().execute(sendAudio);

        DownloadingUserTracker.getInstance().removeUser(chatID);
        audioFile.delete();
    }

    @SneakyThrows
    public void sendFile(String path, Long chatID) {
        SendAudio sendAudio = new SendAudio(chatID.toString(), new InputFile(new File(path)));
        Main.getInstance().getTelegramClient().execute (sendAudio);
    }

    public String getFolderPathByUUID(Long chatID) {
        File directory = new File("downloads/" + chatID + "/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory.getPath();
    }
}
