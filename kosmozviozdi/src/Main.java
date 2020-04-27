import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.util.Scanner;


public class Main extends JPanel implements ActionListener {
    private Image bg0 = new ImageIcon("data/textures/bg0.png").getImage();
    private Image bg1 = new ImageIcon("data/textures/bg1.png").getImage();
    private Image bg2 = new ImageIcon("data/textures/bg2.png").getImage();
    private Image r0 = new ImageIcon("data/textures/r0.png").getImage();
    private Image r1 = new ImageIcon("data/textures/r1.png").getImage();
    private Image go = new ImageIcon("data/textures/gameover.png").getImage();
    private Image p0 = new ImageIcon("data/textures/p0.png").getImage();
    private Image p1 = new ImageIcon("data/textures/p1.png").getImage();
    private Image p2 = new ImageIcon("data/textures/p2.png").getImage();
    private Image p3 = new ImageIcon("data/textures/p3.png").getImage();
    private Image p4 = new ImageIcon("data/textures/p4.png").getImage();
    private Image p5 = new ImageIcon("data/textures/p5.png").getImage();
    private Image p6 = new ImageIcon("data/textures/p6.png").getImage();
    private Image p7 = new ImageIcon("data/textures/p7.png").getImage();
    private Image p8 = new ImageIcon("data/textures/p8.png").getImage();
    private Image p9 = new ImageIcon("data/textures/p9.png").getImage();
    private Image star = new ImageIcon("data/textures/star.png").getImage();
    private Image button0 = new ImageIcon("data/textures/button0.png").getImage();
    private Image button1 = new ImageIcon("data/textures/button1.png").getImage();
    private Image button2 = new ImageIcon("data/textures/button2.png").getImage();
    private Image button3 = new ImageIcon("data/textures/button3.png").getImage();
    private Image button_choosed = new ImageIcon("data/textures/button_choosed.png").getImage();
    private Image button_unchoosed = new ImageIcon("data/textures/button_unchoosed.png").getImage();
    private Image yes_mark = new ImageIcon("data/textures/yes_mark.png").getImage();
    private Image settings_screen = new ImageIcon("data/textures/settings_screen.png").getImage();
    private Image logo = new ImageIcon("data/textures/red_kosmozviozdi.png").getImage();
    private Image rightArrow = new ImageIcon("data/textures/right.png").getImage();
    private Image leftArrow = new ImageIcon("data/textures/left.png").getImage();
    static Image cursorImg = new ImageIcon("data/textures/cursorImg.png").getImage();
    static Image cursorHandImg = new ImageIcon("data/textures/cursorHandImg.png").getImage();
    static Image nullCursor = new ImageIcon("data/textures/null.png").getImage();
    private Timer t = new Timer(15,this);
    private JFrame frame;
    private Rocket rocket = new Rocket();
    private Space space = new Space();
    public enum State{MENU, GAME, SETTINGS, GAMEOVER, PAUSE, QUIT, CLEAR}
    static State state = State.MENU;
    static int[] settings = new int[5];
    private int xb0 = (int) (0.104 * settings[2]), yb0 = (int) (0.185 * settings[3]), xb1 = (int) (0.026 * settings[2]), yb1 = (int) (0.833 * settings[3]), xb2 = (int) (0.104 * settings[2]), yb2 = (int) (0.37 * settings[3]), xb3 = (settings[2] - button3.getWidth(null)) / 2, yb3 = (settings[3] - button3.getHeight(null)) / 2 + (int) (0.046 * settings[3]), xb4 = (int) (0.74 * settings[2]), yb4 = yb1;
    private int previewWidth = (int) (0.23 * settings[2]), previewHeight = (int) (0.23 * settings[3]), preview0X = (int) (0.26 * settings[2]), preview1X = (int) (settings[2] / 4 + preview0X), preview2X = (int) (settings[2] / 4 + preview1X), previewY = (int) (0.185 * settings[3]);
    private int yesMarkDiameter = (int) (0.083 * settings[3]), logoX = (int) (0.52 * settings[2]), logoY = (int) (0.185 * settings[3]), animationTime = 200, chooseY = (int) (0.65 * settings[3]), interval = (int) (0.052 * settings[2]), chooseDiameter = yesMarkDiameter, choose0X = (int) (settings[2] / 4 + interval), choose1X = (int) (choose0X + chooseDiameter + interval), choose2X = (int) (choose1X + chooseDiameter + interval);
    private int choose3X = (int) (choose2X + chooseDiameter + interval), choose4X = (int) (choose3X + chooseDiameter + interval), choose5X = (int) (choose4X + chooseDiameter + interval), choose6X = (int) (choose5X + chooseDiameter + interval), arrowY = chooseY - 2 * interval;
    static int menuAnimationTimer = -1;

    public static void main(String[] args) {
        getData();
        JFrame frame = new JFrame("Космозвёзды β");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setBounds(0, 0, settings[2], settings[3]);
        frame.setBackground(Color.black);
        frame.add(new Main(frame));
        frame.setVisible(true);
        Rocket.setHitbox();
        frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
    }
    public Main(JFrame frame){
        this.frame = frame;
        t.start();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                rocket.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                rocket.keyReleased(e);
            }
        });
        addMouseListener(new mouseListener());
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {
                if (state != State.GAME){
                    int x = e.getX();
                    int y = e.getY();
                    if (state == State.MENU) {
                        if ((x >= xb0 && x <= button0.getWidth(null) + xb0) && (y >= yb0 && y <= button0.getHeight(null) + yb0)) {
                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                        } else {
                            if ((x >= xb1 && x <= button1.getWidth(null) + xb1) && (y >= yb1 && y <= button1.getHeight(null) + yb1)) {
                                frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                            } else {
                                if ((x >= xb2 && x <= button2.getWidth(null) + xb2) && (y >= yb2 && y <= button2.getHeight(null) + yb2)) {
                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                } else {
                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
                                }
                            }
                        }
                    } else{
                        if (state == State.SETTINGS){
                            if ((x >= xb1 && x <= button1.getWidth(null) + xb1) && (y >= yb1 && y <= button1.getHeight(null) + yb1)) {
                                frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                            } else {
                                if ((x >= xb4 && x <= button1.getWidth(null) + xb4) && (y >= yb4 && y <= button1.getHeight(null) + yb4)) {
                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                } else {
                                    if ((x >= preview0X && x <= previewWidth + preview0X) && (y >= previewY && y <= previewHeight + previewY)){
                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                    } else {
                                        if ((x >= preview1X && x <= previewWidth + preview1X) && (y >= previewY && y <= previewHeight + previewY)) {
                                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                        } else{
                                            if ((x >= preview2X && x <= previewWidth + preview2X) && (y >= previewY && y <= previewHeight + previewY)) {
                                                frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                            } else {
                                                if (4 * ((x - (choose0X + chooseDiameter/2)) * (x - (choose0X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                } else {
                                                    if (4 * ((x - (choose1X + chooseDiameter/2)) * (x - (choose1X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                    } else {
                                                        if (4 * ((x - (choose2X + chooseDiameter/2)) * (x - (choose2X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                        } else {
                                                            if (4 * ((x - (choose3X + chooseDiameter/2)) * (x - (choose3X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                            } else {
                                                                if (4 * ((x - (choose4X + chooseDiameter/2)) * (x - (choose4X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                                } else {
                                                                    if (4 * ((x - (choose5X + chooseDiameter/2)) * (x - (choose5X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                                    } else {
                                                                        if (4 * ((x - (choose6X + chooseDiameter/2)) * (x - (choose6X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                                        } else {
                                                                            if (4 * ((x - (choose1X + chooseDiameter/2)) * (x - (choose1X + chooseDiameter/2)) + (y - (arrowY + chooseDiameter/2)) * (y - (arrowY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                                frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                                            } else {
                                                                                if (4 * ((x - (choose5X + chooseDiameter/2)) * (x - (choose5X + chooseDiameter/2)) + (y - (arrowY + chooseDiameter/2)) * (y - (arrowY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                                                                } else {
                                                                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else{
                            if (state == State.PAUSE){
                                if ((x >= xb1 && x <= button1.getWidth(null) + xb1) && (y >= yb1 && y <= button1.getHeight(null) + yb1)) {
                                    frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                } else {
                                    if ((x >= xb0 && x <= button0.getWidth(null) + xb0) && (y >= yb0 && y <= button0.getHeight(null) + yb0)) {
                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                    } else {
                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
                                    }
                                }
                            } else{
                                if (state == State.QUIT){
                                    if ((x >= xb3 && x <= button3.getWidth(null) + xb3) && (y >= yb3 && y <= button3.getHeight(null) + yb3)){
                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                    } else {
                                        frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
                                    }
                                } else {
                                    if (state == State.CLEAR){
                                        if ((x >= xb3 && x <= button3.getWidth(null) + xb3) && (y >= yb3 && y <= button3.getHeight(null) + yb3)) {
                                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorHandImg, new Point(0, 0), "null"));
                                        } else {
                                            frame.setCursor(frame.getToolkit().createCustomCursor(cursorImg, new Point(0, 0), "null"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    frame.setCursor(frame.getToolkit().createCustomCursor(nullCursor, new Point(0, 0), "null"));
                }
            }
        });
    }


    private void clearData(){
        try {
            FileWriter logw = new FileWriter("data/log.txt");
            logw.write("0\n0-1920-1080-5");
            logw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void getData(){
        try {
            FileReader logr = new FileReader("data/log.txt");
            Scanner scan = new Scanner(logr);
            String highScoreString = scan.nextLine();
            String settingsString = scan.nextLine();
            String[] settingsStringArray = settingsString.split("-");
            for (int i = 1; i < 5; i++){
                settings[i] = Integer.parseInt(settingsStringArray[i-1]);
            }
            settings[0] = Integer.parseInt(highScoreString.trim());
            logr.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void setData(int parameter, int value){
        getData();
        settings[parameter] = value;
        try {
            FileWriter logw = new FileWriter("data/log.txt");
            logw.write(settings[0] + "\n" + settings[1] + "-" + settings[2] + "-" + settings[3] + "-" + settings[4]);
            logw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        if (settings[1] == 0){
            g.drawImage(bg0, 0, Space.bgPhase - frame.getHeight(), settings[2], settings[3], null);
            g.drawImage(bg0, 0, Space.bgPhase, settings[2], settings[3], null);
        } else {
            if (settings[1] == 1){
                g.drawImage(bg1, 0, Space.bgPhase - frame.getHeight(), settings[2], settings[3], null);
                g.drawImage(bg1, 0, Space.bgPhase, settings[2], settings[3], null);
            } else {
                g.drawImage(bg2, 0, Space.bgPhase - frame.getHeight(), settings[2], settings[3], null);
                g.drawImage(bg2, 0, Space.bgPhase, settings[2], settings[3], null);
            }
        } //background set
        if (Rocket.gas == 1) {
            g.drawImage(r1, (int) rocket.getX() - r0.getWidth(null) / 2, (int) (rocket.getY() - r0.getHeight(null) / 2), null);
            Rocket.gas = 0;
        } else {
            g.drawImage(r0, (int) rocket.getX() - r0.getWidth(null) / 2, (int) rocket.getY() - r0.getHeight(null) / 2, null);
        } //fire animation
        if (state == State.GAME) {
            if (collision()) {
                Sound.playSound("data/music/gameover.wav");
                state = State.GAMEOVER;
            } else {
                if (menuAnimationTimer >= 0 && menuAnimationTimer <= animationTime){
                    try {
                        Font molodo;
                        molodo = Font.createFont(Font.TRUETYPE_FONT, new File("data/fonts/Molodo.otf")).deriveFont(Font.PLAIN,(int)(0.072 * settings[3]));
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        ge.registerFont(molodo);
                        g.setFont(molodo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch(FontFormatException e) {
                        e.printStackTrace();
                    }
                    int xb0Animation = xb0 - menuAnimationTimer * (button0.getWidth(null) + xb0) / animationTime;
                    int xb1Animation = xb1 - menuAnimationTimer * (button1.getWidth(null) + xb1) / animationTime;
                    int xb2Animation = xb2 - menuAnimationTimer * (button2.getWidth(null) + xb2) / animationTime;
                    int logoYAnimation = logoY - menuAnimationTimer * (logo.getHeight(null) + logoY) / animationTime;
                    g.drawImage(button0, xb0Animation, yb0, (int)(button0.getWidth(null) * settings[2] / 1920),(int)(button0.getHeight(null) * settings[3] / 1080), null);
                    g.setColor(Color.BLACK);
                    g.drawString("<PLAY>", xb0Animation + (int)(0.03125 * settings[2]), yb0 + (int)(0.076 * settings[3]));
                    g.drawImage(button2, xb2Animation, yb2, (int)(button2.getWidth(null) * settings[2] / 1920),(int)(button2.getHeight(null) * settings[3] / 1080),  null);
                    g.drawString("<SETTINGS>", xb2Animation + (int)(0.013 * settings[2]), yb2 + (int)(0.0787 * settings[3]));
                    g.drawImage(button1, xb1Animation, yb1, (int)(button1.getWidth(null) * settings[2] / 1920),(int)(button1.getHeight(null) * settings[3] / 1080),  null);
                    g.drawString("</EXIT>", xb1Animation + (int)(0.047 * settings[2]), yb1 + (int)(0.083 * settings[3]));
                    g.drawImage(logo, logoX, logoYAnimation, (int)(logo.getWidth(null) * settings[2] / 1920),(int)(logo.getHeight(null) * settings[3] / 1080), null);
                    menuAnimationTimer++;
                } //animation
                try {
                    Font molodo;
                    molodo = Font.createFont(Font.TRUETYPE_FONT, new File("data/fonts/Molodo.otf")).deriveFont(Font.PLAIN,(int)(0.044 * settings[3]));
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(molodo);
                    g.setFont(molodo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(FontFormatException e) {
                    e.printStackTrace();
                }
                if (Space.object[0][2] != 0) {
                    g.drawImage(star, (int) (Space.object[0][0] - Space.object[0][2]), (int) (Space.object[0][1] - Space.object[0][2]), (int) (2 * Space.object[0][2]), (int) (2 * Space.object[0][2]), null);
                    for (int i = 1; i <= Space.planetNum; i++) {
                        if (Space.object[i][5] >= 90) {
                            g.drawImage(p0, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                        } else {
                            if (Space.object[i][5] >= 80) {
                                g.drawImage(p1, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                            } else {
                                if (Space.object[i][5] >= 70) {
                                    g.drawImage(p2, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                } else {
                                    if (Space.object[i][5] >= 60) {
                                        g.drawImage(p3, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                    } else {
                                        if (Space.object[i][5] >= 50) {
                                            g.drawImage(p4, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                        } else {
                                            if (Space.object[i][5] >= 40) {
                                                g.drawImage(p5, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                            } else {
                                                if (Space.object[i][5] >= 30) {
                                                    g.drawImage(p6, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                } else {
                                                    if (Space.object[i][5] >= 20) {
                                                        g.drawImage(p7, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                    } else {
                                                        if (Space.object[i][5] >= 10) {
                                                            g.drawImage(p8, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                        } else {
                                                            g.drawImage(p9, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                String points = Integer.toString(Space.score);
                String score = "Score: " + points;
                String highScore = "High score: ";
                if (Space.score > settings[0]){
                    highScore += points;
                } else {
                    highScore += Integer.toString(settings[0]);
                }
                g.setColor(Color.MAGENTA);
                g.drawString(score, (int)(0.093 * settings[3]), (int)(0.093 * settings[3]));
                g.drawString(highScore, (int)(0.093 * settings[3]), (int)(0.14 * settings[3]));
                if (Rocket.fuel > 0) {
                    g.setColor(Color.ORANGE);
                    g.drawString("Fuel: ", (int)(0.093*settings[3]), (int)(0.93*settings[3]));
                    g.fillRect((int)(0.104 * settings[2]), (int)(0.884 * settings[3]), (int)(0.156 * settings[2] * Rocket.fuel / 300), (int)(0.046 * settings[3]));
                    g.setColor(Color.RED);
                    g.drawRect((int)(0.104 * settings[2]), (int)(0.884 * settings[3]), (int)(0.156 * settings[2]), (int)(0.046 * settings[3]));
                } else {
                    g.setColor(new Color(102, 255, 153));
                    g.drawString("You're out of fuel!", (int)(0.052 * settings[2]), (int)(0.926 * settings[3]));
                }
            }
        } else {
            try {
                Font molodo;
                molodo = Font.createFont(Font.TRUETYPE_FONT, new File("data/fonts/Molodo.otf")).deriveFont(Font.PLAIN,(int)(0.072 * settings[3]));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(molodo);
                g.setFont(molodo);
            } catch (IOException e) {
                e.printStackTrace();
            } catch(FontFormatException e) {
                e.printStackTrace();
            }
            if (state == State.MENU) {
                g.setColor(Color.BLACK);
                g.drawImage(button0, xb0, yb0, (int)(button0.getWidth(null) * settings[2] / 1920),(int)(button0.getHeight(null) * settings[3] / 1080), null);
                g.setColor(Color.BLACK);
                g.drawString("<PLAY>", xb0 + (int)(0.03125 * settings[2]), yb0 + (int)(0.076 * settings[3]));
                g.drawImage(button2, xb2, yb2, (int)(button2.getWidth(null) * settings[2] / 1920),(int)(button2.getHeight(null) * settings[3] / 1080),  null);
                g.drawString("<SETTINGS>", xb2 + (int)(0.013 * settings[2]), yb2 + (int)(0.0787 * settings[3]));
                g.drawImage(button1, xb1, yb1, (int)(button1.getWidth(null) * settings[2] / 1920),(int)(button1.getHeight(null) * settings[3] / 1080),  null);
                g.drawString("</EXIT>", xb1 + (int)(0.047 * settings[2]), yb1 + (int)(0.083 * settings[3]));
                g.drawImage(logo, logoX, logoY, (int)(logo.getWidth(null) * settings[2] / 1920),(int)(logo.getHeight(null) * settings[3] / 1080), null);
                Space.score = 0;
            } else {
                if (state == State.SETTINGS) {
                    g.drawImage(settings_screen,0,0, settings[2], settings[3], null);
                    g.drawImage(button1, xb1, yb1, null);
                    g.drawImage(button1, xb4, yb4, null);
                    g.drawImage(bg0, preview0X, previewY, previewWidth, previewHeight,null);
                    g.drawImage(bg1, preview1X, previewY, previewWidth, previewHeight,null);
                    g.drawImage(bg2, preview2X, previewY, previewWidth, previewHeight,null);
                    if (settings[1] == 0){
                        g.drawImage(yes_mark, preview0X + (previewWidth - yesMarkDiameter) / 2, previewY + (previewHeight - yesMarkDiameter) / 2, yesMarkDiameter, yesMarkDiameter, null);
                    } else {
                        if (settings[1] == 1){
                            g.drawImage(yes_mark, preview1X + (previewWidth - yesMarkDiameter) / 2, previewY + (previewHeight - yesMarkDiameter) / 2, yesMarkDiameter, yesMarkDiameter, null);
                        } else {
                            g.drawImage(yes_mark, preview2X + (previewWidth - yesMarkDiameter) / 2, previewY + (previewHeight - yesMarkDiameter) / 2, yesMarkDiameter, yesMarkDiameter, null);
                        }
                    } //placing yes_mark
                    g.drawImage(button_unchoosed, choose0X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose1X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose2X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose3X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose4X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose5X, chooseY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(button_unchoosed, choose6X, chooseY, chooseDiameter, chooseDiameter,null);
                    switch (settings[4]){
                        case 1:
                            g.drawImage(button_choosed, choose0X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 2:
                            g.drawImage(button_choosed, choose1X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 3:
                            g.drawImage(button_choosed, choose2X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 4:
                            g.drawImage(button_choosed, choose3X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 5:
                            g.drawImage(button_choosed, choose4X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 6:
                            g.drawImage(button_choosed, choose5X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                        case 7:
                            g.drawImage(button_choosed, choose6X, chooseY, chooseDiameter, chooseDiameter,null);
                            break;
                    }
                    g.drawImage(leftArrow, choose1X, arrowY, chooseDiameter, chooseDiameter,null);
                    g.drawImage(rightArrow, choose5X, arrowY, chooseDiameter, chooseDiameter,null);
                    g.setColor(Color.BLACK);
                    g.drawString("BACKGROUND", (int)(0.042 * settings[2]), (int)(0.287 * settings[3]));
                    g.drawString("CHOOSE", (int)(0.042 * settings[2]), (int)(0.37 * settings[3]));
                    g.drawString("RESOLUTION", (int)(0.042 * settings[2]), (int)(0.528 * settings[3]));
                    g.drawString("SPEED", (int)(0.042 * settings[2]), (int)(0.685 * settings[3]));
                    g.drawString("DECREMENT", (int)(0.042 * settings[2]), (int)(0.769 * settings[3]));
                    g.drawString("<SETTINGS>",(int)(0.427 * settings[2]),(int)(0.093 * settings[3]));
                    g.drawString("</MENU>", xb1 + (int)(0.047 * settings[2]), yb1 + (int)(0.083 * settings[3]));
                    g.drawString("<CLEAR DATA>", xb4 + (int)(0.023 * settings[2]), yb4 + (int)(0.083 * settings[3]));
                    String resolution = Integer.toString(settings[2]) + " X " + Integer.toString(settings[3]);
                    g.drawString(resolution, (int)(0.547 * settings[2]), arrowY + (int)(0.065 * settings[3]));
                } else {
                    if (state == State.GAMEOVER){
                        if (Space.object[0][2] != 0) {
                            g.drawImage(star, (int) (Space.object[0][0] - Space.object[0][2]), (int) (Space.object[0][1] - Space.object[0][2]), (int) (2 * Space.object[0][2]), (int) (2 * Space.object[0][2]), null);
                            for (int i = 1; i <= Space.planetNum; i++) {
                                if (Space.object[i][5] >= 90) {
                                    g.drawImage(p0, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                } else {
                                    if (Space.object[i][5] >= 80) {
                                        g.drawImage(p1, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                    } else {
                                        if (Space.object[i][5] >= 70) {
                                            g.drawImage(p2, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                        } else {
                                            if (Space.object[i][5] >= 60) {
                                                g.drawImage(p3, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                            } else {
                                                if (Space.object[i][5] >= 50) {
                                                    g.drawImage(p4, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                } else {
                                                    if (Space.object[i][5] >= 40) {
                                                        g.drawImage(p5, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                    } else {
                                                        if (Space.object[i][5] >= 30) {
                                                            g.drawImage(p6, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                        } else {
                                                            if (Space.object[i][5] >= 20) {
                                                                g.drawImage(p7, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                            } else {
                                                                if (Space.object[i][5] >= 10) {
                                                                    g.drawImage(p8, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                                } else {
                                                                    g.drawImage(p9, (int) (Space.object[i][0] - Space.object[i][2]), (int) (Space.object[i][1] - Space.object[i][2]), (int) (2 * Space.object[i][2]), (int) (2 * Space.object[i][2]), null);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        g.drawImage(go, (int)(0.339 * settings[2]), (int)(0.296 * settings[3]), (int)(go.getWidth(null) * settings[2] / 1920),(int)(go.getHeight(null) * settings[3] / 1080), null);
                        g.setColor(Color.RED);
                        g.drawString("Press <ENTER> to return in the main menu", (int)(0.208 * settings[2]), (int)(0.685 * settings[3]));
                        if (Space.score > settings[0]){
                            setData(0,Space.score);
                        }
                        g.setColor(Color.MAGENTA);
                        String points = Integer.toString(Space.score);
                        String score = "Score: " + points;
                        g.drawString(score, (int)(0.443 * settings[2]), (int)(0.759 * settings[3]));
                    } else {
                        if (state == State.PAUSE){
                            g.drawImage(r0, (int) rocket.getX() - r0.getWidth(null) / 2, (int) rocket.getY() - r0.getHeight(null) / 2, null);
                            g.drawImage(button0, xb0, yb0, (int)(button0.getWidth(null) * settings[2] / 1920),(int)(button0.getHeight(null) * settings[3] / 1080), null);
                            g.setColor(Color.BLACK);
                            g.drawString("<CONTINUE>", xb0 + (int)(0.01 * settings[2]), yb0 + (int)(0.072 * settings[3]));
                            g.drawImage(button1, xb1, yb1, (int)(button1.getWidth(null) * settings[2] / 1920),(int)(button1.getHeight(null) * settings[3] / 1080), null);
                            g.drawString("</MENU>", xb1 + (int)(0.047 * settings[2]), yb1 + (int)(0.0815 * settings[3]));
                        } else {
                            if (state == State.QUIT){
                                g.drawImage(button3, xb3, yb3,(int)(button3.getWidth(null) * settings[2] / 1920),(int)(button3.getHeight(null) * settings[3] / 1080), null);
                                g.setColor(new Color(150,0,100));
                                g.drawString("</QUIT>", xb3 + (int)(0.052 * settings[2]), yb3 + (int)(0.07 * settings[3]));
                                g.setColor(Color.WHITE);
                                g.drawString("Are You sure You want to quit the game?", xb3 - (int)(0.156 * settings[2]), yb3 - (int)(0.0648 * settings[3]));
                            } else {
                                if (state == State.CLEAR){
                                    g.drawImage(button3, xb3, yb3,(int)(button3.getWidth(null) * settings[2] / 1920),(int)(button3.getHeight(null) * settings[3] / 1080), null);
                                    g.setColor(new Color(150,0,100));
                                    g.drawString("</CLEAR>", xb3 + (int)(0.0417 * settings[2]), yb3 + (int)(0.0676 * settings[3]));
                                    g.setColor(Color.WHITE);
                                    g.drawString("Are You sure You want to clear the data?", xb3 - (int)(0.156 * settings[2]), yb3 - (int)(0.065 * settings[3]));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean collision(){
        boolean collision = false;
        if (Space.score != 0){
            for (int i = 0; i <= Space.planetNum; i++){
                if (Space.object[i][2] != 0){
                    for (int y = 0; y < rocket.getHeight(); y++){
                        double r1 = Math.sqrt((rocket.getX() - Rocket.hitbox[y] - 2 - Space.object[i][0])*(rocket.getX() - Rocket.hitbox[y] - 2 - Space.object[i][0]) + (rocket.getY() + y - rocket.getHeight() / 2 - Space.object[i][1])*(rocket.getY() + y - rocket.getHeight() / 2 - Space.object[i][1]));
                        double r2 = Math.sqrt((rocket.getX() + Rocket.hitbox[y] + 2 - Space.object[i][0])*(rocket.getX() + Rocket.hitbox[y] + 2 - Space.object[i][0]) + (rocket.getY() + y - rocket.getHeight() / 2 - Space.object[i][1])*(rocket.getY() + y - rocket.getHeight() / 2 - Space.object[i][1]));
                        if (r1 <= Space.object[i][2] || r2 <= Space.object[i][2]){
                            collision = true;
                        }
                    }
                }
            }
        }
        return collision;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
        if (state == State.GAME) {
            rocket.move();
            space.move();
            if (space.bgPhase % 20 == 0) Space.score++;
            if (Space.score % 100 == 99) Sound.playSound("data/music/points.wav");
            if (Space.systemOutOfScreen()){
                Space.clear();
                rocket.setSpeed();
                space.createSystem();
            }
        }
    }
    private class mouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e){
            if (state != State.GAME){
                int x = e.getX();
                int y = e.getY();
                if (state == State.MENU) { //(int)(button0.getWidth(null) * settings[2] / 1920),(int)(button0.getHeight(null) * settings[3] / 1080),
                    if ((x >= xb0 && x <= (int)(button0.getWidth(null) * settings[2] / 1920) + xb0) && (y >= yb0 && y <= (int)(button0.getHeight(null) * settings[3] / 1080) + yb0)) {
                        rocket.getReady();
                        Space.clear();
                        state = State.GAME;
                        menuAnimationTimer = 0;
                    } else {
                        if ((x >= xb1 && x <= (int)(button1.getWidth(null) * settings[2] / 1920) + xb1) && (y >= yb1 && y <= (int)(button1.getHeight(null) * settings[3] / 1080) + yb1)) {
                            System.exit(2077);
                        } else {
                            if ((x >= xb2 && x <= (int)(button2.getWidth(null) * settings[2] / 1920) + xb2) && (y >= yb2 && y <= (int)(button2.getHeight(null) * settings[3] / 1080) + yb2)) {
                                state = State.SETTINGS;
                            }
                        }
                    }
                } else{
                    if (state == State.SETTINGS){
                        if ((x >= xb1 && x <= (int)(button1.getWidth(null) * settings[2] / 1920) + xb1) && (y >= yb1 && y <= (int)(button1.getHeight(null) * settings[3] / 1080) + yb1)) {
                            state = State.MENU;
                        } else {
                            if ((x >= xb4 && x <= (int)(button1.getWidth(null) * settings[2] / 1920) + xb4) && (y >= yb4 && y <= (int)(button1.getHeight(null) * settings[3] / 1080) + yb4)) {
                                state = State.CLEAR;
                            } else {
                                if ((x >= preview0X && x <= previewWidth + preview0X) && (y >= previewY && y <= previewHeight + previewY)){
                                    setData(1,0);
                                } else {
                                    if ((x >= preview1X && x <= previewWidth + preview1X) && (y >= previewY && y <= previewHeight + previewY)) {
                                        setData(1,1);
                                    } else{
                                        if ((x >= preview2X && x <= previewWidth + preview2X) && (y >= previewY && y <= previewHeight + previewY)) {
                                            setData(1,2);
                                        } else {
                                            if (4 * ((x - (choose0X + chooseDiameter/2)) * (x - (choose0X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                setData(4,1);
                                            } else {
                                                if (4 * ((x - (choose1X + chooseDiameter/2)) * (x - (choose1X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                    setData(4,2);
                                                } else {
                                                    if (4 * ((x - (choose2X + chooseDiameter/2)) * (x - (choose2X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                        setData(4,3);
                                                    } else {
                                                        if (4 * ((x - (choose3X + chooseDiameter/2)) * (x - (choose3X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                            setData(4,4);
                                                        } else {
                                                            if (4 * ((x - (choose4X + chooseDiameter/2)) * (x - (choose4X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                setData(4,5);
                                                            } else {
                                                                if (4 * ((x - (choose5X + chooseDiameter/2)) * (x - (choose5X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                    setData(4,6);
                                                                } else {
                                                                    if (4 * ((x - (choose6X + chooseDiameter/2)) * (x - (choose6X + chooseDiameter/2)) + (y - (chooseY + chooseDiameter/2)) * (y - (chooseY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                        setData(4,7);
                                                                    } else {
                                                                        if (4 * ((x - (choose1X + chooseDiameter/2)) * (x - (choose1X + chooseDiameter/2)) + (y - (arrowY + chooseDiameter/2)) * (y - (arrowY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                            if (settings[2] == 1920){
                                                                                setData(2,1600);
                                                                                setData(3,900);
                                                                            } else {
                                                                                if (settings[2] == 1600){
                                                                                    setData(2,1280);
                                                                                    setData(3,720);
                                                                                } else {
                                                                                    if (settings[2] == 1280){
                                                                                        setData(2, 1920);
                                                                                        setData(3,1080);
                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            if (4 * ((x - (choose5X + chooseDiameter/2)) * (x - (choose5X + chooseDiameter/2)) + (y - (arrowY + chooseDiameter/2)) * (y - (arrowY + chooseDiameter/2))) <= chooseDiameter * chooseDiameter){
                                                                                if (settings[2] == 1920){
                                                                                    setData(2,1280);
                                                                                    setData(3,720);
                                                                                } else {
                                                                                    if (settings[2] == 1600){
                                                                                        setData(2,1920);
                                                                                        setData(3,1080);
                                                                                    } else {
                                                                                        if (settings[2] == 1280){
                                                                                            setData(2, 1600);
                                                                                            setData(3,900);
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else{
                        if (state == State.PAUSE){
                            if ((x >= xb1 && x <= (int)(button1.getWidth(null) * settings[2] / 1920) + xb1) && (y >= yb1 && y <= (int)(button1.getHeight(null) * settings[3] / 1080) + yb1)) {
                                state = State.MENU;
                                if (Space.score > settings[0]){
                                    setData(0,Space.score);
                                }
                                Sound.playSound("data/music/gameover.wav");
                            } else {
                                if ((x >= xb0 && x <= (int)(button0.getWidth(null) * settings[2] / 1920) + xb0) && (y >= yb0 && y <= (int)(button0.getHeight(null) * settings[3] / 1080) + yb0)) {
                                    state = State.GAME;
                                }
                            }
                        } else{
                            if (state == State.QUIT){
                                if ((x >= xb3 && x <= (int)(button3.getWidth(null) * settings[2] / 1920) + xb3) && (y >= yb3 && y <= (int)(button3.getHeight(null) * settings[3] / 1080) + yb3)){
                                    System.exit(2077);
                                }
                            } else {
                                if (state == State.CLEAR){
                                    if ((x >= xb3 && x <= (int)(button3.getWidth(null) * settings[2] / 1920) + xb3) && (y >= yb3 && y <= (int)(button3.getHeight(null) * settings[3] / 1080) + yb3)) {
                                        clearData();
                                        getData();
                                        state = State.SETTINGS;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}