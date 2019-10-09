import java.util.Random;

public class Server implements Comparable<Server> {
    private static Random random = new Random();
    public static double mu;
    private static int cntId = 0;
    public int id;
    public boolean busy = false;
    private Customer curCustomer;
    public double freeTime = 0;

    private double generateTime() {
        return -Math.log(random.nextDouble()) * mu; // - ln(rand()) / lambda
    }

    public void serve(Customer customer, double curTime) {
        customer.serve_time = curTime;
        busy = true;
        curCustomer = customer;
        freeTime = curTime + generateTime();
        System.out.println(curTime + ": 窗口 " + id + " 开始了 对顾客 " + curCustomer.id + " 的服务");
    }

    public void update(double curTime) {
        if (busy && freeTime < curTime) {
            busy = false;
            System.out.println(freeTime + ": 窗口 " + id + " 结束了 对顾客 " + curCustomer.id + " 的服务");
            curCustomer = null;
        }
    }

    public Server() {
        cntId++;
        id = cntId;
    }

    @Override
    public int compareTo(Server o) {
        if (!this.busy && !o.busy) {
            return Integer.compare(this.id, o.id);
        } else if (!this.busy && o.busy) {
            return -1;
        } else if (this.busy && !o.busy) {
            return 1;
        } else {
            int k = Double.compare(this.freeTime, o.freeTime);
            return k != 0 ? k : Integer.compare(this.id, o.id);
        }
    }
}
