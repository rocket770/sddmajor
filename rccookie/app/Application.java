package rccookie.app;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Application extends JFrame {

    private static final long serialVersionUID = 6619999336418625459L;

    public Application(int width, int height, String title) {
        setSize(width, height);
        setTitle(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public Application(String title) {
        this(600, 400, title);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    public static void main(String[] args) {
        Application app = new Application(600, 400, "Application");
        app.add(new World(300, 200));
        app.add(new JButton("Hello"));
    }
}
