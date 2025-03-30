package lovemetal.command;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public abstract class AbstractCommand {

    private final String command;

    public AbstractCommand(String command) {
        this.command = command;
    }

    public abstract void onCommand(Update update, String[] args);
}
