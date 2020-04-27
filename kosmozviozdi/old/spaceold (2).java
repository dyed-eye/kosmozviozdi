public class Space {
    static int score = 0;
    static int maxPlanets = 5;
    static int maxSatellites = 0;
    static int bgPhase = 0;
    //public static int bgPhase1 = 0;
    //public static int bgPhase2 = 1080;
    static double[][][] object = new double[maxPlanets + 1][maxSatellites + 1][6]; // star system - Object[0][0]_ for the star and Object[i][0]_ for the planets and Object[i][j]_ for the satellites;
                                                                                          // zero for x, uno for y, dos for radius, tres for relative weight (relative weight of rocket equals zero), cuatro for speed, cinco for type
    public static double G = 6.67408 * Math.pow(10, -11);
    public static double m0 = 4 * Math.pow(10, 11);
    private int minDistance = (int) (0.03 * Main.settings[2]);
    private int speedDecrement = 15 + Main.settings[4] * 2; //для наглядности скорости уменьшены, в реальности планеты находятся на куда большем расстоянии от звезды и движутся медленнее

    static void clear(){
        for (int i = 0; i <= maxPlanets; i++){
            for (int j = 0; j <= maxSatellites; j++){
                for (int n = 0; n < 6; n++){
                    object[i][j][n] = 0;
                }
            }
        }
    }
    
    public void createSystem(){
        object[0][0][2] = Math.random() * 200 + 150;
        int multiplier;
        double random = Math.random();
        if (random < 0.5){
            multiplier = -1;
        } else {
            multiplier = 1;
        }
        object[0][0][0] = Main.settings[2] / 2 + multiplier * (Math.random() * (0.36 * Main.settings[2] - object[0][0][2] / 2) + 0.14 * Main.settings[2] + object[0][0][2]);
        object[0][0][3] = Math.random() * 150000 + 50000;
        int planetNum = (int) (Math.random()*(maxPlanets - 1) + 1);
        for (int i = 1; i <= planetNum; i++){
            object[i][0][2] = Math.random() * 25 + 50;
            do {
                object[i][0][0] = Math.random() * 920;
            } while (notCorrectSpawn(i, 0, planetNum));
            object[i][0][3] = Math.random() * 4000 + 1000;
            object[i][0][4] = Math.sqrt(G * (object[0][0][3] * m0) / Math.sqrt((object[0][0][0] - object[i][0][0])*(object[0][0][0] - object[i][0][0]) + (object[0][0][1] - object[i][0][1])*(object[0][0][1] - object[i][0][1]))) / speedDecrement;
            object[i][0][5] = Math.random() * 100;
            /*int satelliteChance = (int) (Math.random() * 100);
            int satelliteNum = 3;
            if (satelliteChance <= 50){
                satelliteNum = 0;
            } else {
                if (satelliteChance <= 80) {
                    satelliteNum = 1;
                } else {
                    if (satelliteChance <= 95) {
                        satelliteNum = 2;
                    }
                }
            }
            for (int j = 1; j <= satelliteNum; j++){
                object[i][j][2] = Math.random() * 20 + 10;
                do {
                    object[i][j][0] = Math.random() * 1920;
                } while (thereIsntAnythingNearby(i, j));
                object[i][j][3] = Math.random() * 150 + 50;
            }*/
        }
        initialPhase(planetNum);
    }


    private boolean notCorrectSpawn(int l1, int l2, int planetNum){
        int n = 0;
        for (int i = 0; i <= planetNum; i++){
            for (int j = 0; j <= maxSatellites; j++){
                if (i != l1 || j != l2) {
                    if (object[i][j][2] != 0 && (Math.abs(object[i][j][0] - object[l1][l2][0]) <= object[i][j][2] + object[l1][l2][2] + minDistance)) {
                        n++;
                    }
                }
            }
        }
        /*if (planetNum == 5){
            if (object[l1][l2][0] <= Main.settings[2] * (l1 * 0.015 + (l1 - 1) * 0.11 + 0.36) || object[l1][l2][0] >= (l1 * 0.125 + 0.36) * Main.settings[2]) n++;
        } else {
            if (planetNum == 4){
                if (object[l1][l2][0] <= Main.settings[2] * (l1 * 0.016 + (l1 - 1) * 0.14 + 0.36) || object[l1][l2][0] >= (l1 * 0.156 + 0.36) * Main.settings[2]) n++;
            } else {
                if (planetNum == 3){
                    if (object[l1][l2][0] <= Main.settings[2] * (l1 * 0.017 + (l1 - 1) * 0.19 + 0.36) || object[l1][l2][0] >= (l1 * 0.207 + 0.36) * Main.settings[2]) n++;
                }
            }
        }*/
        boolean b = (n != 0);
        return b;
    }

    private void initialPhase(int planetNum){ //начальная фаза
        double deltaY = object[0][0][2];
        for (int i = 1; i <= planetNum; i++){
            double alpha = Math.random() * 2 * Math.PI;
            double r = (Math.sqrt((object[0][0][0] - object[i][0][0])*(object[0][0][0] - object[i][0][0]) + (object[0][0][1] - object[i][0][1])*(object[0][0][1] - object[i][0][1])));
            object[i][0][0] = object[i][0][0] + r * (1 - Math.cos(alpha));
            object[i][0][1] = r * Math.sin(alpha);
            if (object[i][0][1] + object [i][0][2] > deltaY){
                deltaY = object[i][0][1] + object [i][0][2];
            }
        }
        for (int i = 0; i <= planetNum; i++){
            if (object[i][0][2] != 0){
                object[i][0][1] -= deltaY;
            }
        }
    }

    static boolean systemOutOfScreen(){
        int outScreen = 1;
        for (int i = 0; i <= maxPlanets; i++){
            for (int j = 0; j <= maxSatellites; j++){
                if (object[i][j][2] != 0) {
                    if (object[i][j][1] > Main.settings[3] + object[i][j][2] || (object[i][j][0] > Main.settings[2] + object[i][j][2])) {
                        outScreen *= 1;
                    } else {
                        outScreen *= 0;
                    }
                }
            }
        }
        return (outScreen == 1);
    }

    public void move(){
        for (int i = 1; i <= maxPlanets; i++){
            for (int j = 0; j <= maxSatellites; j++) {
                if (object[i][j][2] != 0) {
                    double r = (Math.sqrt((object[0][0][0] - object[i][j][0])*(object[0][0][0] - object[i][j][0]) + (object[0][0][1] - object[i][j][1])*(object[0][0][1] - object[i][j][1])));
                    if (r != 0) {
                        if (object[0][0][0] >= 960) {
                            object[i][j][1] += (2 + (object[i][j][4] * (object[0][0][0] - object[i][j][0])) / r);
                            object[i][j][0] -= (object[i][j][4] * (object[0][0][1] - object[i][j][1])) / r;
                        } else {
                            object[i][j][1] += (2 - (object[i][j][4] * (object[0][0][0] - object[i][j][0])) / r);
                            object[i][j][0] += (object[i][j][4] * (object[0][0][1] - object[i][j][1])) / r;
                        }
                    } else {
                        clear();
                        createSystem();
                    }
                }
                /*if (object[i][j][1] >= 1180) {
                    for (int n = 0; n < 4; n++) {
                        object[i][j][n] = 0;
                    }
                }*/
            }
        }
        object[0][0][1] += 2;
        if (bgPhase == 1080){
            bgPhase = 0;
        } else {bgPhase++;}
        /*bgPhase1--;
        bgPhase2--;
        if (bgPhase == 2160){
            bgPhase1 = 0;
        } else {
            if (bgPhase == 1080){
                bgPhase2 = 1000;
            }
        }*/
    }
}