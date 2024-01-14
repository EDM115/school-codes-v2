/**
 * This class is the main class, text based adventure game. Users can walk around some scenery. That's all. It should really be extended to make it more interesting!
 * 
 * To play this adventure game, see the class GameLauncher.
 * 
 * This main class creates and initialises all the others : it creates all rooms, creates the parser and starts the game. It also evaluates and executes the commands that the parser returns.
 */
public class Game {
    /**
     * The parser that reads the user input.
     */
    private Parser parser;

    /**
     * The current room the player is in.
     */
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExit("north", null);
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("north", null);
        theatre.setExit("east", null);
        theatre.setExit("south", null);
        theatre.setExit("west", outside);

        pub.setExit("north", null);
        pub.setExit("east", outside);
        pub.setExit("south", null);
        pub.setExit("west", null);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", null);
        lab.setExit("west", null);

        office.setExit("north", null);
        office.setExit("east", null);
        office.setExit("south", null);
        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {            
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    /**
     * Print out information about the player's current location.
     */
    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Adventure !");
        System.out.println("World of Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        CommandWord commandWord = command.getCommandWord();
    
        return switch (commandWord) {
            case HELP:
                printHelp();
                yield false;
            case GO:
                goRoom(command);
                yield false;
            case QUIT:
                yield quit(command);
            case LOOK:
                printLocationInfo();
                yield false;
            case UNKNOWN:
                System.out.println("I don't understand what you mean...");
                yield false;
        };
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are :");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new room, otherwise print an error message.
     * @param command The command to be processed.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where ?");

            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door !");
        } else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see whether we really quit the game.
     * @param command The command to be processed.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what ?");
            return false;
        }
        return true;  // signal that we want to quit
    }
}
