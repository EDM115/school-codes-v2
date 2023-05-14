import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Cette classe permet d'afficher le chronomètre
public class ShowChrono {
    private static JLabel timeLabel;
    private static JButton startStopButton;
    private static JButton resetButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Création de la fenêtre avec un titre, une taille et un comportement à la fermeture
                JFrame frame = new JFrame("Chronomètre");
				frame.setSize(800, 150);
				frame.setMinimumSize(new Dimension(200, 100));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel panel = new JPanel(new BorderLayout());

                // Création du label qui affichera le temps
                timeLabel = new JLabel("00:00");
                timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(timeLabel, BorderLayout.CENTER);

                // Création des boutons
                startStopButton = new JButton("Stop");
                resetButton = new JButton("Reset");
                resetButton.setEnabled(false);

                // Ajout des actions aux boutons
                startStopButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (EditChrono.isChronometerRunning()) {
                            EditChrono.stopChronometer();
                            startStopButton.setText("Start");
                            resetButton.setEnabled(true);
                        } else {
                            EditChrono.startChronometer();
                            startStopButton.setText("Stop");
                            resetButton.setEnabled(false);
                        }
                    }
                });

                resetButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        EditChrono.resetChronometer();
                        resetButton.setEnabled(false);
                    }
                });

                // Ajout des boutons au panel
                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.add(startStopButton);
                buttonPanel.add(resetButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);

                // Ajout du panel à la fenêtre
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
				frame.setLocationRelativeTo(null);

                EditChrono.startChronometer(); // Démarre le chronomètre automatiquement
            }
        });
    }

    // Met à jour le temps affiché
    public static void updateTime(String time) {
        timeLabel.setText(time);
    }
}
