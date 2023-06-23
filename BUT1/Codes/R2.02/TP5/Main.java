import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Mouse Movement Example");
        setSize(800, 500);
        setMinimumSize(new Dimension(300, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#282a36"));
        contentPane.setLayout(new BorderLayout());

        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.decode("#282a36"));

        JLabel counterLabel = new JLabel("Distance Travelled: 0 pixels");
        counterLabel.setBackground(Color.decode("#44475a"));
        counterLabel.setForeground(Color.decode("#f8f8f2"));
        counterLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(blankPanel, BorderLayout.CENTER);
        contentPane.add(counterLabel, BorderLayout.SOUTH);

        setContentPane(contentPane);

        // Create an instance of the Listener class
        Listener listener = new Listener(counterLabel);
        blankPanel.addMouseMotionListener(listener);

		setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main example = new Main();
            example.setVisible(true);
        });
    }
}
