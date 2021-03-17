import javax.swing.*;

public class Frame {
    private JFrame frame;

    public Frame() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(new Canvas());
        frame.pack();
        frame.setVisible(true);
    }
}
