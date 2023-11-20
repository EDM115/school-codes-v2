package nim;

/**
 * An automated player in the game simple nim.
 */
public class AutomatedPlayer implements Player {
  
  private String name;       // this Player's name.
  private int sticksTaken;   // number of sticks removed on this Player's most
                             // recent turn. Initially 0.
  
  /**
   * Create a new AutomatedPlayer with the specified name.
   * @ensure     this.name().equals(name)
   */
  public AutomatedPlayer (String name) {
    this.name = name;
    this.sticksTaken = 0;
  }
  
  public String name () {
    return name;
  }
  
  
  public int sticksTaken () {
    return sticksTaken;
  }
  
 
  public void takeTurn (Pile pile, int maxOnATurn) {
    pile.remove(1);
    sticksTaken = 1;
  }
  
}
