import javax.swing.*;

public class HelloWorldSwing extends JFrame {

    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new Runnable() {
            //public void run() {
                HelloWorldSwing frame = new HelloWorldSwing(args);
                frame.pack();
                frame.setVisible(true);
            //}
        //});
    }

    /**
     * Initialize the HelloWorldSwing frame components
     * @param args the command line arguments
     */
    public HelloWorldSwing(String[] args) {

        setTitle("HelloWorldSwing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());

        // Uses the first command line argument as the label text, or "Hello World" as a fallback
        if (args.length == 0 || args == null) {
            args = new String[] {"Hello World"};
        }
        for (String arg : args) {
            JLabel label = new JLabel(arg);
            add(label);
        }
    }
}
