package lovemetal.file.impl;

import lombok.Getter;
import lombok.SneakyThrows;
import lovemetal.file.IFileLoader;
import lovemetal.util.FileUtil;
import lovemetal.util.MessageUtil;
import lovemetal.util.VideoUtil;

import java.io.File;
import java.io.IOException;

@Getter
public class FileMP3Downloader implements IFileLoader {

    @Getter
    private static final FileMP3Downloader instance = new FileMP3Downloader();

    @SneakyThrows
    @Override
    public File getDownloadedFile(String url, String outputFileName, Integer messageID, Long chatID) {
        outputFileName = VideoUtil.sanitizeFileName(outputFileName);
        File mp3File = new File(FileUtil.getFolderPathByUUID(chatID) + "/" + outputFileName + ".mp3");

        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp.exe", "-x", "--audio-format", "mp3",
                "--ffmpeg-location", "ffmpeg",
                "-o", FileUtil.getFolderPathByUUID(chatID) + "/" + outputFileName + ".%(ext)s",
                url
        );

        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Ошибка при выполнении yt-dlp. Код выхода: " + exitCode);
        }

        if (!mp3File.exists()) {
            throw new IOException("Файл " + outputFileName + " не был создан.");
        }

        MessageUtil.editMessage("📤 Отправка...", messageID, chatID);
        return mp3File;
    }
}
