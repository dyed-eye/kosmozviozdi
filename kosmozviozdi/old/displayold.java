import javax.swing.*;

public class Display {
    public static void main(String[] args){
        JFrame frame = new JFrame("Космозвёзды α");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new Main(frame));
        frame.setVisible(true);
    }
}