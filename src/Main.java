import java.util.*;

public class Main {
    public static int total;
    public static int maxQue;
    public static double maxTime = 480;

    public static ArrayList<Customer> arriveList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer.lambda = (double) 1 / scanner.nextDouble();
        Server.mu = scanner.nextDouble();
        total = scanner.nextInt();
        maxQue = scanner.nextInt();
        double curTime = 0;
        Queue<Customer> queue = new LinkedList<>();
        Server server = new Server();
        for (int i = 1; i <= total; i++) {
            Customer curCustomer = new Customer(curTime);
            arriveList.add(curCustomer);
            curTime = curCustomer.arriveTime;
            if (curTime > maxTime) {
                break;
            }
            while (true) {
                server.update(curTime);
                if (server.busy || queue.isEmpty()) {
                    break;
                }
                server.serve(queue.poll(), server.freeTime);
            }
            System.out.println(curTime + ": 顾客 " + curCustomer.id + " 到达了");
            if (server.busy) {
                if (queue.size() >= maxQue) {
                    System.out.println(curTime + ": 队列人数已满, 顾客 " + curCustomer.id + " 离开了");
                } else {
                    queue.add(curCustomer);
                    System.out.println(curTime + ": 顾客 " + curCustomer.id + " 进入了队列");
                }
            } else {
                server.serve(curCustomer, curTime);
            }
        }
        while (!queue.isEmpty() || server.busy) {
            server.update(Double.POSITIVE_INFINITY);
            if (!queue.isEmpty()) {
                server.serve(queue.poll(), server.freeTime);
            }
        }
    }
}
