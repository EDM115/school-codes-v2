import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Listener extends MouseMotionAdapter {
    private int prevX;
    private int prevY;
    private int totalDistance;
    private final JLabel counterLabel;

    public Listener(JLabel counterLabel) {
        this.counterLabel = counterLabel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (prevX != 0 && prevY != 0) {
            int deltaX = Math.abs(e.getX() - prevX);
            int deltaY = Math.abs(e.getY() - prevY);
            totalDistance += Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            counterLabel.setText("Distance Travelled : " + totalDistance + " pixels");
        }
        prevX = e.getX();
        prevY = e.getY();
    }
}
