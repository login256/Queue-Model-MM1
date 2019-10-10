import java.util.Random;

public class Customer {
    public static double lambda;
    static Random random = new Random();
    static int cntId = 0;
    public int id;
    public double timeAfterLast;
    public double arriveTime;
    public double serveTime;

    private int getPoisson() {
        double L = Math.exp(-lambda);
        int k = 0;
        double p = 1;
        do {
            k++;
            p = p * random.nextDouble();
        }
        while (p > L);
        return k - 1;
    }

    private double generateTime() {
        double re = 0;
        while (true) {
            int v = getPoisson();
            if (v != 0) {
                re += 1 / (double) v;
                break;
            } else {
                re += 1;
            }
        }
        return re;
    }

    public Customer(double lastTime) {
        cntId++;
        id = cntId;
        timeAfterLast = generateTime();
        arriveTime = lastTime + timeAfterLast;
    }

}
