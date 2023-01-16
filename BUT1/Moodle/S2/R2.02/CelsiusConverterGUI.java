import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CelsiusConverterGUI extends JFrame {

  private JLabel celsiusLabel;
  private JButton convertButton;
  private JLabel fahrenheitLabel;
  private JTextField tempTextField;

  /**
   * Program entry point
   *
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            CelsiusConverterGUI frame = new CelsiusConverterGUI();
            frame.pack();
            frame.setVisible(true);
          }
        });
  }

  /** Initialize the CelsiusConverterGUI frame components. */
  public CelsiusConverterGUI() {

    setTitle("Celsius Converter");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    tempTextField = new JTextField();
    celsiusLabel = new JLabel();
    convertButton = new JButton();
    fahrenheitLabel = new JLabel();
    celsiusLabel.setText("Celsius");
    convertButton.setText("Convert");
    convertButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            convertButtonActionPerformed(evt);
          }
        });
    fahrenheitLabel.setText("Fahrenheit");

    getContentPane().setLayout(new GridLayout(2, 2));
    add(tempTextField);
    add(celsiusLabel);
    add(convertButton);
    add(fahrenheitLabel);
  }

  /** Parse degrees Celsius as a double and convert to Fahrenheit */
  private void convertButtonActionPerformed(ActionEvent evt) {
    int tempFahr = (int) ((Double.parseDouble(tempTextField.getText())) * 1.8 + 32);
    fahrenheitLabel.setText(tempFahr + " Fahrenheit");
  }
}
