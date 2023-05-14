import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

// Cette classe permet d'afficher le calendrier
// /!\ Pour une raison que je n'ai pas réussi à identifier, les jours changent 2 par 2
public class ShowCalendar extends JFrame {
    
    private Calendar calendar;
    private JLabel dayLabel;
    private JLabel dateLabel;
    private JComboBox<String> monthComboBox;
    private JLabel yearLabel;
	private int lastKeyCode = -1;
    
	// Le constructeur de la classe ShowCalendar
    public ShowCalendar() {
        calendar = new Calendar();
        initComponents();
    }
    
    private void initComponents() {
		// On initialise la fenêtre avec un titre, une taille, une taille minimale et une action à la fermeture
        setTitle("Calendrier");
        setSize(500, 250);
		setMinimumSize(new Dimension(300, 150));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		// On créé un panel qui contiendra les différents éléments de la fenêtre
        JPanel panel = new JPanel(new BorderLayout());
        
		// On créé les différents éléments de la fenêtre
        dayLabel = new JLabel(calendar.getDayName());
        dateLabel = new JLabel(Integer.toString(calendar.getDayNumber()));
        monthComboBox = new JComboBox<>(calendar.getMonthsNames());
        yearLabel = new JLabel(Integer.toString(calendar.getYear()));

		// On initialise le mois sélectionné dans la liste déroulante
		monthInit(monthComboBox);
        
		// On ajoute les différents éléments au panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(dayLabel);
        centerPanel.add(dateLabel);
        centerPanel.add(monthComboBox);
		centerPanel.add(yearLabel);
        
		// On ajoute le panel à la fenêtre avec les élements centrés
        panel.add(centerPanel, BorderLayout.CENTER);
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
        
		// On ajoute un listener sur la liste déroulante
        setKeyListener();
    }

	// On ajoute un KeyListener à la fenêtre qui permet de changer de jour en appuyant sur les flèches gauche et droite
    private void setKeyListener() {
        addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode != lastKeyCode) {
					if (keyCode == KeyEvent.VK_LEFT) {
						calendar.previousDay();
					} else if (keyCode == KeyEvent.VK_RIGHT) {
						calendar.nextDay();
					}
					updateDateLabels();
				}
				lastKeyCode = keyCode;
			}

			public void keyTyped(KeyEvent e) {
			}

			// On remet lastKeyCode à -1 quand on relâche la touche
			public void keyReleased(KeyEvent e) {
				lastKeyCode = -1;
			}
		});

		// On ajoute un FocusListener à la fenêtre qui permet de la mettre au premier plan quand elle a le focus
		setFocusable(true);
		requestFocusInWindow();
    }

	// On met à jour les labels de la fenêtre avec les valeurs du calendrier
    public void updateDateLabels() {
        dayLabel.setText(calendar.getDayName());
        dateLabel.setText(Integer.toString(calendar.getDayNumber()));
		monthComboBox.setSelectedIndex(calendar.getMonthNumber() - 1);
        yearLabel.setText(Integer.toString(calendar.getYear()));
    }

	// Méthode utilitaire pour passer l'objet Calendar à la classe EditCalendar
	public Calendar getCalendar() {
		return calendar;
	}

	// On initialise le mois sélectionné dans la liste déroulante
	public void monthInit(JComboBox<String> monthComboBox) {
		monthComboBox.setSelectedIndex(calendar.getMonthNumber() - 1);
		monthComboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				int selectedMonth = monthComboBox.getSelectedIndex() + 1;
				calendar.setMonth(selectedMonth);
				updateDateLabels();
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});

		// On ajoute un FocusListener à la liste déroulante qui permet de la mettre au premier plan quand elle a le focus
		monthComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ShowCalendar.this.requestFocusInWindow();
			}
		});
	}
    
	// Méthode main qui lance l'application	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ShowCalendar showCalendar = new ShowCalendar();
				EditCalendar editCalendar = new EditCalendar(showCalendar.getCalendar(), showCalendar);
                showCalendar.setVisible(true);
            }
        });
    }
}
