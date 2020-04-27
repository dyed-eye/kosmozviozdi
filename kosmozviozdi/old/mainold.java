import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
public class Main extends JFrame {
    private Image rocket;
    Main() {
        setTitle("Космозвёзды");
        setBounds(0,0,1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            rocket = ImageIO.read(new File("r0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(true);
        setResizable(false);
        setBackground(Color.BLACK);

    }
    @Override
    public void paint (Graphics g) {
        if (rocket != null)
            g.drawImage(rocket,923,780,null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        //System.out.println("Released");
    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        //System.out.println("Entered");
    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        //System.out.println("Exited");
    }
}
