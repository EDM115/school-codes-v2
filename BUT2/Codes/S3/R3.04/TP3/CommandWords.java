import java.util.HashMap;

/**
 * This class is part of the "World of Advenrture" application. 
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 */
public class CommandWords {
    /**
     * A mapping between a command word and the CommandWord associated with it.
     */
    private HashMap<String, CommandWord> validCommands;

    
    /**
     * This is the constructor of the CommandWords class.
     * It initializes a HashMap called validCommands and adds key-value pairs to it.
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("look", CommandWord.LOOK);
    }

    /**
     * This method returns the CommandWord associated with the command word.
     * @param commandString The command word.
     * @return The CommandWord associated with the command word.
     */
    public CommandWord getCommandWord(String commandString) {
        CommandWord command = validCommands.get(commandString);
        if (command != null) {
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }
}

