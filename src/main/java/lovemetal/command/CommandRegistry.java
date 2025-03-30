package lovemetal.command;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CommandRegistry {

    @Getter
    private static final CommandRegistry instance = new CommandRegistry();

    private final Map<String, AbstractCommand> commands = new HashMap<>();

    public void registerCommands(List<AbstractCommand> providedCommandList) {
        providedCommandList.forEach(providedCommand -> {
            commands.put(providedCommand.getCommand(), providedCommand);
        });
    }

    public AbstractCommand getCommandByName(String command) {
        return commands.get(command);
    }
}
