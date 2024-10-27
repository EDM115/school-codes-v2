import java.awt.Color;
import java.util.Iterator;

public class FeuTricolor implements Iterable<Color> {
  private final Color rouge = Color.RED;
  private final Color jaune = Color.YELLOW;
  private final Color vert = Color.GREEN;

  @Override
  public Iterator<Color> iterator() {
    return new Iterator<Color>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return true; // Retourne toujours true pour une itération infinie
      }

      @Override
      public Color next() {
        Color color;
        switch (index) {
          case 0:
            color = vert;
            break;
          case 1:
            color = jaune;
            break;
          case 2:
            color = rouge;
            break;
          default:
            color = vert;
        }
        index = (index + 1) % 3;
        return color;
      }
    };
  }
}
