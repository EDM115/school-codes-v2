import javax.swing.*;

public class HelloWorldSwing extends JFrame {

  /**
   * Program entry point
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            HelloWorldSwing frame = new HelloWorldSwing();
            frame.pack();
            frame.setVisible(true);
          }
        });
  }

  /** Initialize the HelloWorldSwing frame components. */
  public HelloWorldSwing() {

    setTitle("HelloWorldSwing");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add the ubiquitous "Hello World" label.
    JLabel label = new JLabel("Hello World");
    add(label);
  }
}
