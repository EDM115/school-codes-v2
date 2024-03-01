import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is connected to other rooms via exits.  The exits are labelled north,  east, south, west.  For each direction, the room stores a reference to the neighboring room, or null if there is no exit in that direction.
 */
public class Room {
    /**
     * The room's description.
     */
    public String description;

    /**
     * An HashMap that stores the exits of the room.
     */
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has no exits. "description" is something like "a kitchen" or "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room. Every direction either leads to another room or is null (no exit there).
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Return the room that is reached if we go from this room in direction "direction". If there is no room in that direction, return null.
     * @param direction The direction of the exit.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Return a description of the room's exits, for example, "Exits : north west".
     * @return A description of the available exits.
     */
    public String getExitString() {
        StringBuilder exitString = new StringBuilder("Exits :");
        for (String key : exits.keySet()) {
            if (exits.get(key) != null) {
                exitString.append(" ").append(key);
            }
        }
        return exitString.toString();
    }

    /**
     * Return the description of this room.
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return a string describing the room's long description, of the form: You are in the kitchen. Exits: north west.
     * @return The long description of the room.
     */
    public String getLongDescription() {
        return "You are " + description + ". " + this.getExitString();
    }
}
