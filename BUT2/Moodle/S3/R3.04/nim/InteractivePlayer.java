package nim;

/**
 * A player in the game simple nim that gets moves from a client.
 */
public class InteractivePlayer implements Player {
  
  private String name;                // This Player's name.
  private int numberToTake;           // Number of sticks to take on this Player's
                                      // next turn.
  private int sticksTaken;            // Number of sticks takne on this Player's previous
                                      // turn. 0 if no previous turn.
    
  /**
   * Create a new InteractivePlayer with the specified name.
   * @ensure     this.name().equals(name) && this.sticksTaken() == 0
   */
  public InteractivePlayer (String name) {
    this.name = name;
    this.numberToTake = 0;
    this.sticksTaken = 0;

  }
  
  
  public String name () {
    return name;
  }
  
  public int sticksTaken () {
    return sticksTaken;
  }
  
  public void takeTurn (Pile pile, int maxOnATurn) {
	int pileSize = pile.sticks();
    int number = 1;
    if (numberToTake > 0 && numberToTake <= maxOnATurn
          && numberToTake <= pileSize)
      number = numberToTake;
    pile.remove(number);
    sticksTaken = number;
  }
 
  /**
   * Set the number of sticks this InteractivePlayer should take on its
   * next turn.
   * @require    number > 0
   */
  public void setNumberToTake (int number) {
    this.numberToTake = number;
  }
  
 }
