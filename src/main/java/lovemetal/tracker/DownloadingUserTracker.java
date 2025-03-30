package lovemetal.tracker;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DownloadingUserTracker {

    @Getter
    private static final DownloadingUserTracker instance = new DownloadingUserTracker();

    private final List<Long> users = new ArrayList<>();

    public void addUser(long userID) {
        users.add(userID);
    }

    public void removeUser(long userID) {
        users.removeIf(foundID -> foundID == userID);
    }

    public boolean isUserDownloading(long userID) {
        return users.contains(userID);
    }
}
