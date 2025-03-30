package lovemetal.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;

@UtilityClass
public class VideoUtil {

    @SneakyThrows
    public String getVideoTitle(String URL) {
        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp.exe", "--get-title", URL
        );

        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Ошибка при выполнении yt-dlp. Код выхода: " + exitCode);
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "Windows-1251")
        );

        String videoTitle = reader.readLine();
        reader.close();

        return videoTitle;
    }

    @SneakyThrows
    public Duration getVideoDuration(String URL) {
            ProcessBuilder pb = new ProcessBuilder(
                    "yt-dlp.exe", "--get-duration", URL
            );

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String durationStr = reader.readLine();
            process.waitFor();

            if (durationStr != null) {
                String[] timeParts = durationStr.split(":");
                int durationInSeconds = 0;

                if (timeParts.length == 2) {
                    durationInSeconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
                } else if (timeParts.length == 3) {
                    durationInSeconds = Integer.parseInt(timeParts[0]) * 3600 + Integer.parseInt(timeParts[1]) * 60 + Integer.parseInt(timeParts[2]);
                }

                return Duration.ofSeconds(durationInSeconds);
            }

        return Duration.ZERO;
    }

    public boolean isValidURL(String URL) {
        if (URL == null || URL.isEmpty()) return false;
        return URL.contains("youtube.com") || URL.contains("youtu.be") || URL.contains("tiktok.com");
    }

    public String sanitizeURL(String URL) {
        if (!URL.contains("youtube.com") && !URL.contains("youtu.be")) {
            return URL;
        }

        if (!URL.contains("&list")) return URL;

        int index = URL.indexOf("&list");
        return URL.substring(0, index);
    }

    public String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[<>:\"/|?*]", "_");
    }
}
