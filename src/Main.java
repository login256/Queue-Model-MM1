import java.util.*;

public class Main {
    public static int total;
    public static int maxQue;
    public static double maxTime = 480;
    public static int numServer;

    public static ArrayList<Customer> arriveList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer.lambda = (double) 1 / scanner.nextDouble();
        Server.mu = scanner.nextDouble();
        total = scanner.nextInt();
        maxQue = scanner.nextInt();
        numServer = scanner.nextInt();
        TreeSet<Server> servers = new TreeSet<>();
        for (int i = 1; i <= numServer; i++) {
            servers.add(new Server());
        }
        double curTime = 0;
        Queue<Customer> queue = new LinkedList<>();
        for (int i = 1; i <= total; i++) {
            Customer curCustomer = new Customer(curTime);
            arriveList.add(curCustomer);
            curTime = curCustomer.arriveTime;
            if (curTime > maxTime) {
                break;
            }
            while (true) {
                Server minServer = servers.first();
                servers.remove(minServer);
                double v = minServer.update(curTime);
                Statisticer.sumQueS += v * queue.size();
                servers.add(minServer);
                if (servers.first().busy || queue.isEmpty()) {
                    break;
                }
                minServer = servers.first();
                servers.remove(minServer);
                minServer.serve(queue.poll(), minServer.freeTime);
                servers.add(minServer);
            }
            System.out.println(curTime + ": 顾客 " + curCustomer.id + " 到达了");
            if (servers.first().busy) {
                if (queue.size() >= maxQue) {
                    System.out.println(curTime + ": 队列人数已满, 顾客 " + curCustomer.id + " 离开了");
                } else {
                    queue.add(curCustomer);
                    System.out.println(curTime + ": 顾客 " + curCustomer.id + " 进入了队列");
                }
            } else {
                Server minServer = servers.first();
                servers.remove(minServer);
                minServer.serve(curCustomer, curTime);
                servers.add(minServer);
            }
        }
        while (!queue.isEmpty() || servers.first().busy) {
            Server minServer = servers.first();
            servers.remove(minServer);
            double v = minServer.update(Double.POSITIVE_INFINITY);
            curTime = minServer.freeTime;
            Statisticer.sumQueS += v * queue.size();
            if (!queue.isEmpty()) {
                minServer.serve(queue.poll(), minServer.freeTime);
            }
            servers.add(minServer);
        }
        System.out.println(Statisticer.sumWaitTime / Statisticer.cntCustomer);
        System.out.println(Statisticer.sumQueS / curTime);
        System.out.println(Statisticer.sumSerS / curTime);
    }
}
