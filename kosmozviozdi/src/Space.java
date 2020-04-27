public class Space {
    static int score = 0;
    static int maxPlanets = 5;
    static int planetNum = 0;
    static int bgPhase = 0;
    static double[][] object = new double[maxPlanets + 1][6]; // star system - Object[0]_ for the star and Object[i]_ for the planets;
                                                             // zero for x, uno for y, dos for radius, tres for relative weight (relative weight of rocket equals zero), cuatro for speed, cinco for type
    public static double G = 6.67408 * Math.pow(10, -11);
    public static double m0 = 4 * Math.pow(10, 11);
    private int minDistance = (int) (0.03 * Main.settings[2]);
    private int speedDecrement = 15 + Main.settings[4] * 2; //для наглядности скорости уменьшены, в реальности планеты находятся на куда большем расстоянии от звезды и движутся медленнее

    static void clear(){
        for (int i = 0; i <= maxPlanets; i++){
            for (int n = 0; n < 6; n++){
                object[i][n] = 0;
            }
        }
    }
    
    public void createSystem(){
        object[0][2] = Math.random() * 0.37 * Main.settings[3] + 0.185 * Main.settings[3];
        int multiplier;
        double random = Math.random();
        if (random < 0.5){
            multiplier = -1;
        } else {
            multiplier = 1;
        }
        object[0][0] = Main.settings[2] / 2 + multiplier * (Math.random() * (0.36 * Main.settings[2] - object[0][2] / 2) + 0.14 * Main.settings[2] + object[0][2]);
        object[0][3] = Math.random() * 50000 + 100000;
        planetNum = (int) (Math.random()*(maxPlanets - 1) + 1);
        for (int i = 1; i <= planetNum; i++){
            object[i][2] = Math.random() * 0.023 * Main.settings[3] + 0.046 * Main.settings[3];
            int triesToSpawn = 0;
            do {
                object[i][0] = Math.random() * 0.85 * Main.settings[3];
                if (triesToSpawn != 50) {
                    triesToSpawn++;
                } else {
                    planetNum = i - 1;
                    break;
                }
            } while (notCorrectSpawn(i, planetNum));
            object[i][3] = Math.random() * 4000 + 1000;
            object[i][4] = Math.sqrt(G * (object[0][3] * m0) / Math.sqrt((object[0][0] - object[i][0])*(object[0][0] - object[i][0]) + (object[0][1] - object[i][1])*(object[0][1] - object[i][1]))) / speedDecrement;
            object[i][5] = Math.random() * 100;
        }
        initialPhase(planetNum);
    }


    private boolean notCorrectSpawn(int l1, int planetNum){
        int n = 0;
        for (int i = 0; i <= planetNum; i++){
                if (i != l1) {
                    if (object[i][2] != 0 && (Math.abs(object[i][0] - object[l1][0]) <= object[i][2] + object[l1][2] + minDistance)) {
                        n++;
                    }
            }
        }
        boolean b = (n != 0);
        return b;
    }

    private void initialPhase(int planetNum){ //начальная фаза
        double deltaY = object[0][2];
        for (int i = 1; i <= planetNum; i++){
            double alpha = Math.random() * 2 * Math.PI;
            double r = (Math.sqrt((object[0][0] - object[i][0])*(object[0][0] - object[i][0]) + (object[0][1] - object[i][1])*(object[0][1] - object[i][1])));
            object[i][0] = object[i][0] + r * (1 - Math.cos(alpha));
            object[i][1] = r * Math.sin(alpha);
            if (object[i][1] + object [i][2] > deltaY){
                deltaY = object[i][1] + object [i][2];
            }
        }
        for (int i = 0; i <= planetNum; i++){
            if (object[i][2] != 0){
                object[i][1] -= deltaY;
            }
        }
    }

    static boolean systemOutOfScreen(){
        int outScreen = 1;
        for (int i = 0; i <= planetNum; i++){
            if (object[i][2] != 0) {
                if (object[i][1] > Main.settings[3] + object[i][2] || (object[i][0] > Main.settings[2] + object[i][2])) {
                    outScreen *= 1;
                } else {
                    outScreen *= 0;
                }
            }
        }
        return (outScreen == 1);
    }

    public void move(){
        for (int i = 1; i <= planetNum; i++){
            if (object[i][2] != 0) {
                double r = (Math.sqrt((object[0][0] - object[i][0])*(object[0][0] - object[i][0]) + (object[0][1] - object[i][1])*(object[0][1] - object[i][1])));
                if (r != 0) {
                    if (object[0][0] >= 0.85 * Main.settings[3]) {
                        object[i][1] += (2 + (object[i][4] * (object[0][0] - object[i][0])) / r);
                        object[i][0] -= (object[i][4] * (object[0][1] - object[i][1])) / r;
                    } else {
                        object[i][1] += (2 - (object[i][4] * (object[0][0] - object[i][0])) / r);
                        object[i][0] += (object[i][4] * (object[0][1] - object[i][1])) / r;
                    }
                } else {
                    clear();
                    createSystem();
                }
            }
        }
        object[0][1] += 2;
        if (bgPhase == Main.settings[3]){
            bgPhase = 0;
        } else {bgPhase++;}
    }
}