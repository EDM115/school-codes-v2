import java.util.Iterator;

public class FeuilleSalaire implements Iterable<Object> {
  private final String employeur;
  private final double salaireBrut;
  private final int heuresPayees;
  private final double taxes;
  private final double salaireNet;

  public FeuilleSalaire(String employeur, double salaireBrut, int heuresPayees, double taxes, double salaireNet) {
    this.employeur = employeur;
    this.salaireBrut = salaireBrut;
    this.heuresPayees = heuresPayees;
    this.taxes = taxes;
    this.salaireNet = salaireNet;
  }

  @Override
  public Iterator<Object> iterator() {
    return new Iterator<Object>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < 5;
      }

      @Override
      public Object next() {
        Object value;
        switch (index) {
          case 0:
            value = employeur;
            break;
          case 1:
            value = salaireBrut;
            break;
          case 2:
            value = heuresPayees;
            break;
          case 3:
            value = taxes;
            break;
          case 4:
            value = salaireNet;
            break;
          default:
            throw new IllegalStateException("Unexpected index: " + index);
        }
        index++;
        return value;
      }
    };
  }
}
