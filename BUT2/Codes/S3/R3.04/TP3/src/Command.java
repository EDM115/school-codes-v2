/**
 * This class is part of the "World of Adventure" application. 
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second word (for example, if the command was "take map", then the two strings obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid command words. If the user entered an invalid command (a word that is not known) then the command word is &lt;null&gt;.
 *
 * If the command had only one word, then the second word is &lt;null&gt;.
 */
public class Command {
    /**
     * Command words known to the game.
     */
    private CommandWord commandWord;

    /**
     * Second word of the command. Can be null.
     */
    private String secondWord;

    /**
     * Create a command object. First and second word must be supplied, but either one (or both) can be null.
     * @param commandWord The first word of the command. Null if the command was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the command was not understood, the result is null.
     * @return The command word.
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * Return the second word of this command. Returns null if there was no second word.
     * @return The second word of this command. Returns null if there was no second word.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Return a boolean indicating whether this command was not understood.
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * Return a boolean indicating whether the command has a second word.
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
