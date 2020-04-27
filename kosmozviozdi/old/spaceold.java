public class Space {
    public static int num = 10;
    public static double[][] Object = new double[num][4]; // array of "num" of objects: x, y, radius, weight
    private int planetTimer = 0;

    public static void Clear(){
        for (int i = 0; i < num; i++){
            Object[i][0] = 0;
            Object[i][2] = 0;
            Object[i][1] = 0;
            Object[i][3] = 0;
        }
    }

    public void createPlanet(){
        switch (planetTimer){
            case 1:
                for (int i = 0; i < num; i++){
                    if (Object[i][2] == 0){
                        do {
                            Object[i][0] = Math.random() * 1920;
                            Object[i][2] = Math.random() * 30 + 1;
                            Object[i][3] = Math.random() * 100 + 1;
                        } while(thereIsntAnythingNearby(i));
                    } 
                }
            case 20:
                planetTimer = 0;
            default:
                planetTimer++;
        }
    }

    private boolean thereIsntAnythingNearby(int l){
        boolean b1;
        int n = 0;
        for (int i = 0; i < num; i++){
            if (Object[i][2] == 0) break;
            else{
                if (i != l){
                    if (Math.abs(Object[i][0] - Object[l][0]) <= Object[i][2] + Object[l][2] && Math.abs(Object[i][1] - Object[l][1]) <= Object[i][2] + Object[l][2]){
                        n++;
                    }
                }
            }
        }
        b1 = (n != 0);
        return b1;
    }

    public void move(){
        for (int i = 0; i < num; i++){
            if (Object[i][2] != 0) Object[i][1] += 2;
            if (Object[i][1] >= 1180){
                for (int j = 0; j < 3; j++){
                    Object[i][j] = 0;
                }
            }
        }
    }
}