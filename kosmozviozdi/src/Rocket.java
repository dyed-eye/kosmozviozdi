import java.awt.event.KeyEvent;

enum Direction{
    UP,DOWN,LEFT,RIGHT,NONE
}
public class Rocket {
    static int gas = 0;
    private double x = 0.73 * Main.settings[2], y = 0.787 * Main.settings[3], speedY = 0, speedX = 0, deltaSpeedX = 0, deltaSpeedY = 0;
    private int deltaW = 1;
    private int deltaS = 6;
    private Direction rocketDirection = Direction.NONE;
    static int[] hitbox = new int[149];
    static int fuel = 300;
    private int fuelConsumption = 4;
    static int height = 149, width = 72;

    public void getReady(){
        x = 0.48 * Main.settings[2];
        y = 0.787 * Main.settings[3];
        speedY = 0;
        speedX = 0;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getHeight(){
        return height;
    }
    public double getWidth(){
        return width;
    }
    public void setSpeed(){
        speedX += deltaSpeedX;
        speedY += deltaSpeedY;
        deltaSpeedX = 0;
        deltaSpeedY = 0;
    }

    public void move(){
        switch(rocketDirection){
            case UP:
                if (fuel > 0){
                    speedY -= deltaW;
                    gas = 1;
                    fuel -= fuelConsumption;
                }
                rocketDirection = Direction.NONE;
                break;
            case DOWN:
                if (speedY < 6)
                    speedY += deltaW;
                rocketDirection = Direction.NONE;
                break;
            case LEFT:
                if (speedX >= -deltaS) {
                    speedX -= deltaW;
                }
                rocketDirection = Direction.NONE;
                break;
            case RIGHT:
                if (speedX <= deltaS) {
                    speedX += deltaW;
                }
                rocketDirection = Direction.NONE;
                break;
            default:
                rocketDirection = Direction.NONE;
                break;
        }
        double deltaSpeedX = 0;
        double deltaSpeedY = 0;
        for (int i = 0; i <= Space.planetNum; i++){
                if (Space.object[i][2] != 0){
                    double r = (Math.sqrt((Space.object[i][0] - x)*(Space.object[i][0] - x) + (Space.object[i][1] - y)*(Space.object[i][1] - y)));
                    deltaSpeedX += Space.G * Space.m0 * Space.object[i][3] * (Space.object[i][0] - x) / Math.pow(r, 3);
                    deltaSpeedY += Space.G * Space.m0 * Space.object[i][3] * (Space.object[i][1] - y) / Math.pow(r, 3);
                }
        }
        if (x < width / 2) {
            x = (int)(width / 2);
            if (speedX < 0) {speedX = 0;}
        }else{
            if (x > Main.settings[2] - width / 2){
                x = (int)(Main.settings[2] - width / 2);
                if (speedX > 0) {speedX = 0;}
            }
        }
        if (y < height / 2) {
            y = (int)(height / 2);
            if (speedY < 0) {speedY = 0;}
            deltaSpeedY = 0;
        }else{
            if (y > Main.settings[3] - height / 2){
                y = (int)(Main.settings[3] - height / 2);
                if (speedY > 0) {speedY = 0;}
                deltaSpeedX = 0;
            }
        }
        x += speedX + deltaSpeedX;
        y += speedY + deltaSpeedY;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            rocketDirection = Direction.UP;
        }
        if(key == KeyEvent.VK_UP){
            rocketDirection = Direction.UP;
        }
        if(key == KeyEvent.VK_S){
            rocketDirection = Direction.DOWN;
        }
        if(key == KeyEvent.VK_DOWN){
            rocketDirection = Direction.DOWN;
        }
        if(key == KeyEvent.VK_A){
            rocketDirection = Direction.LEFT;
        }
        if(key == KeyEvent.VK_LEFT){
            rocketDirection = Direction.LEFT;
        }
        if(key == KeyEvent.VK_D){
            rocketDirection = Direction.RIGHT;
        }
        if(key == KeyEvent.VK_RIGHT){
            rocketDirection = Direction.RIGHT;
        }
        if(key == KeyEvent.VK_ESCAPE){
            if (Main.state == Main.State.PAUSE){
                Main.state = Main.State.GAME;
            } else {
                if (Main.state == Main.State.GAME) {
                    Main.state = Main.State.PAUSE;
                } else {
                    if (Main.state == Main.State.MENU){
                        Main.state = Main.State.QUIT;
                    } else {
                        if (Main.state == Main.State.QUIT){
                            Main.state = Main.State.MENU;
                        } else {
                            if (Main.state == Main.State.SETTINGS){
                                Main.state = Main.State.MENU;
                            } else {
                                if (Main.state == Main.State.CLEAR){
                                    Main.state = Main.State.SETTINGS;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(key == KeyEvent.VK_ENTER){
            if (Main.state == Main.State.GAMEOVER){
                Main.state = Main.State.MENU;
            } else {
                if (Main.state == Main.State.MENU){
                    getReady();
                    Space.clear();
                    Main.state = Main.State.GAME;
                    Main.menuAnimationTimer = 0;
                }
            }
        }
    }
    static void keyReleased(KeyEvent e){}


    static void setHitbox() {
        for (int y = 0; y < 149; y++){
                hitbox[y] = 0;
        }
        hitbox[1] = 1;
        hitbox[2] = 3;
        hitbox[3] = 4;
        hitbox[4] = 5;
        hitbox[5] = 6;
        hitbox[6] = 6;
        hitbox[7] = 8;
        hitbox[8] = 10;
        hitbox[9] = 11;
        hitbox[10] = 11;
        hitbox[11] = 12;
        hitbox[12] = 13;
        hitbox[13] = 14;
        hitbox[14] = 15;
        hitbox[15] = 16;
        hitbox[16] = 17;
        hitbox[17] = 18;
        hitbox[18] = 18;
        hitbox[19] = 19;
        hitbox[20] = 20;
        hitbox[21] = 20;
        hitbox[22] = 21;
        hitbox[23] = 21;
        hitbox[24] = 22;
        hitbox[25] = 23;
        hitbox[26] = 23;
        hitbox[27] = 24;
        hitbox[28] = 24;
        hitbox[29] = 25;
        hitbox[30] = 25;
        hitbox[31] = 26;
        hitbox[32] = 26;
        hitbox[33] = 27;
        hitbox[34] = 27;
        hitbox[35] = 28;
        hitbox[36] = 28;
        hitbox[37] = 29;
        hitbox[38] = 29;
        hitbox[39] = 29;
        hitbox[40] = 29;
        hitbox[41] = 29;
        hitbox[42] = 30;
        hitbox[43] = 30;
        hitbox[44] = 30;
        hitbox[45] = 30;
        hitbox[46] = 31;
        hitbox[47] = 31;
        hitbox[48] = 31;
        hitbox[49] = 31;
        hitbox[50] = 31;
        hitbox[51] = 31;
        hitbox[52] = 31;
        hitbox[53] = 31;
        hitbox[54] = 31;
        hitbox[55] = 31;
        hitbox[56] = 31;
        hitbox[57] = 31;
        hitbox[58] = 31;
        hitbox[59] = 31;
        hitbox[60] = 31;
        hitbox[61] = 31;
        hitbox[62] = 31;
        hitbox[63] = 31;
        hitbox[64] = 31;
        hitbox[65] = 31;
        hitbox[66] = 31;
        hitbox[67] = 31;
        hitbox[68] = 31;
        hitbox[69] = 31;
        hitbox[70] = 30;
        hitbox[71] = 30;
        hitbox[72] = 30;
        hitbox[73] = 30;
        hitbox[74] = 30;
        hitbox[75] = 30;
        hitbox[76] = 29;
        hitbox[77] = 29;
        hitbox[78] = 29;
        hitbox[79] = 29;
        hitbox[80] = 29;
        hitbox[81] = 29;
        hitbox[82] = 29;
        hitbox[83] = 29;
        hitbox[84] = 28;
        hitbox[85] = 28;
        hitbox[86] = 28;
        hitbox[87] = 28;
        hitbox[88] = 28;
        hitbox[89] = 27;
        hitbox[90] = 27;
        hitbox[91] = 27;
        hitbox[92] = 27;
        hitbox[93] = 28;
        hitbox[94] = 29;
        hitbox[95] = 29;
        hitbox[96] = 30;
        hitbox[97] = 31;
        hitbox[98] = 32;
        hitbox[99] = 33;
        hitbox[100] = 34;
        hitbox[101] = 35;
        hitbox[102] = 35;
        hitbox[103] = 35;
        hitbox[104] = 35;
        hitbox[105] = 35;
        hitbox[106] = 35;
        hitbox[107] = 35;
        hitbox[108] = 35;
        hitbox[109] = 35;
        hitbox[110] = 35;
        hitbox[111] = 35;
        hitbox[112] = 35;
        hitbox[113] = 35;
        hitbox[114] = 35;
        hitbox[115] = 35;
        hitbox[116] = 34;
        hitbox[117] = 34;
        hitbox[118] = 34;
        hitbox[119] = 34;
        hitbox[120] = 34;
        hitbox[121] = 34;
        hitbox[122] = 34;
        hitbox[123] = 34;
        hitbox[124] = 34;
        hitbox[125] = 34;
        hitbox[126] = 34;
        hitbox[127] = 34;
        hitbox[128] = 34;
        hitbox[129] = 34;
        hitbox[130] = 34;
        hitbox[131] = 34;
        hitbox[132] = 34;
        hitbox[133] = 34;
        hitbox[134] = 34;
        hitbox[135] = 34;
        hitbox[136] = 34;
        hitbox[137] = 34;
        hitbox[138] = 34;
        hitbox[139] = 34;
        hitbox[140] = 33;
        hitbox[141] = 33;
        hitbox[142] = 33;
        hitbox[143] = 33;
        hitbox[144] = 33;
        hitbox[145] = 33;
        hitbox[146] = 33;
    }
}