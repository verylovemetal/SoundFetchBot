package lovemetal.file;

import java.io.File;

public interface IFileLoader {
    File getDownloadedFile(String url, String outputFileName, Integer messageID, Long chatID);
}
