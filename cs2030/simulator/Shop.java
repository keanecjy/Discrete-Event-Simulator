package cs2030.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description: Shop class manages all customers and servers and their relative events
 * in the shop. A random generator is used to determine the service time of each server
 * and whether each server can rest after finishing serving a customer.
 * There are 2 types of servers, human servers and self-checkout (SCO) servers.
 * Self-checkout counters have a shared queue.
 * There are 2 types of customers, typical customers and greedy customers.
 * The typical customer joins the first queue he sees that is not full, while the
 * greedy customer joins the shortest queue.
 * A statistics class is also used to keep track of the relevant statistics of this shop.
 */
public class Shop {
    // Array of servers
    private final Server[] servers;
    // Array of customers
    private final Customer[] customers;
    private final RandomGenerator rand;
    private final double probOfRest;
    private final double probOfGreedy;
    // Statistics to track the stats of the shop
    private final Statistics stat;

    /**
     * Initialises Shop with number of human servers, number of self-checkout
     * servers, max queue length, number of customers, random generator,
     * probability of server resting and probability of customer being greedy.
     *
     * @param numOfHumanServers number of servers
     * @param numSelfCheckout number of self-checkout servers
     * @param maxQueueLength maximum length of queue
     * @param numCustomers number of customers
     * @param rand random generator
     * @param probOfRest probability of server resting
     * @param probOfGreedy probability of customer being greedy
     */
    protected Shop(int numOfHumanServers, int numSelfCheckout, int maxQueueLength, int numCustomers,
            RandomGenerator rand, double probOfRest, double probOfGreedy) {
        int totalServers = numOfHumanServers + numSelfCheckout;
        servers = new Server[totalServers];
        initialiseServers(numOfHumanServers, numSelfCheckout, maxQueueLength);
        customers = new Customer[numCustomers];
        this.rand = rand;
        this.probOfRest = probOfRest;
        this.probOfGreedy = probOfGreedy;
        stat = new Statistics();
    }

    /**
     * Initialises all servers, and limit each server with a maximum queue length. 
     * A shared queue is used for the self-checkout servers.
     *
     * @param numOfHumanServers number of servers
     * @param numSelfCheckout number of self-checkout servers
     * @param maxQueueLength maximum queue length
     */
    private void initialiseServers(int numOfHumanServers, int numSelfCheckout, int maxQueueLength) {
        for (int i = 0; i < numOfHumanServers; i++) {
            servers[i] = new HumanServer(i + 1, maxQueueLength, new LinkedList<Customer>());
        }
        Queue<Customer> sharedQueue = new LinkedList<>();
        for (int i = numOfHumanServers; i < numOfHumanServers + numSelfCheckout; i++) {
            servers[i] = new SCOServer(i + 1, maxQueueLength, sharedQueue);
        }
    }

    /**
     * Outputs the statistics of the shop.
     */
    protected void getStatistics() {
        stat.generate();
    }

    /**
     * Manages a customer arrival event: A typical customer will find the first server
     * which is able to serve him immediately, generating a serve event.
     * If he is not able to, a typical customer will then find the first queue
     * that is still not full to join, while a greedy customer finds the shortest
     * queue, generating a wait event.
     * If there are no available servers and all queues are full, then customer leaves the shop,
     * and a leave event is generated.
     *
     * @param event next event to be simulated
     */
    protected Event manageArrival(Event event) {
        int customerId = event.getCustomerId();
        double arrivalTime = event.getTime();
        Customer customer;
        if (rand.genCustomerType() < probOfGreedy) {
            customer = new GreedyCustomer(customerId, arrivalTime);
        } else {
            customer = new TypicalCustomer(customerId, arrivalTime);
        }
        customers[customerId - 1] = customer;
        System.out.println(event + customer.toString() + " arrives");

        // Checks if customer can be served immediately by an idling server.
        for (Server server : servers) {
            if (server.isIdling()) {
                return new ServedEvent(customerId, server.getID(), arrivalTime);
            }
        }
        Server current;
        if (customer.isGreedy()) {
            current = greedyFind();
        } else {
            current = typicalFind();
        }
        if (current != null) {
            return new WaitEvent(customerId, current.getID(), arrivalTime);
        } else {
            return new LeaveEvent(customerId, arrivalTime);
        }
    }

    /**
     * Greedy customer which proceeds to find the shortest queue.
     *
     * @return server with the shortest queue (if available)
     */
    private Server greedyFind() {
        int shortestQueue = Integer.MAX_VALUE;
        Server current = null;
        for (Server server : servers) {
            int currentQueueLength = server.queueSize();
            if (server.hasNotFullQueue() && currentQueueLength < shortestQueue) {
                shortestQueue = currentQueueLength;
                current = server;
            }
        }
        return current;
    }

    /**
     * Typical customer proceeds to the first queue that is not full.
     *
     * @return first server which has a not full queue (if available)
     */
    private Server typicalFind() {
        for (Server server : servers) {
            if (server.hasNotFullQueue()) {
                return server;
            }
        }
        return null;
    }

    /**
     * Manages a serve event: Server to serve a customer, generating a done event.
     *
     * @param event event
     * @return done event for simulation
     */
    protected DoneEvent manageServe(Event event) {
        int customerId = event.getCustomerId();
        int serverId = event.getServerId();
        Server server = servers[serverId - 1];
        Customer customer = customers[customerId - 1];
        double waitingTime = event.getTime() - customer.getArrivalTime();
        stat.updateWait(waitingTime);
        double serviceTime = rand.genServiceTime();
        double completionTime = serviceTime + event.getTime();
        server.serve();
        System.out.println(event + customer.toString() + " served by " + server);
        return new DoneEvent(customerId, serverId, completionTime);
    }

    /**
     * Manages a wait event: Customer queues at a server's queue. No event is generated.
     */
    protected void manageWait(Event event) {
        int customerId = event.getCustomerId();
        int serverId = event.getServerId();
        Server server = servers[serverId - 1];
        Customer customer = customers[customerId - 1];
        server.addToQueue(customer);
        System.out.println(event + customer.toString() + " waits to be served by " + server);
    }

    /**
     * Manages a done event: Server to either rest (generating rest event),
     * serve the next customer in the queue (generating serve event),
     * or idle if there are no customers in queue (no event generated).
     *
     * @param event event
     * @return event for simulation (if any)
     */
    protected Event manageDone(Event event) {
        int customerId = event.getCustomerId();
        int serverId = event.getServerId();
        Server server = servers[serverId - 1];
        Customer customer = customers[customerId - 1];
        double currentTime = event.getTime();
        stat.updateServed();
        System.out.println(event + customer.toString() + " done serving by " + server);
        // Check if server is able to rest
        if (server.canRest() && rand.genRandomRest() < probOfRest) {
            return new ServerRestEvent(server.getID(), currentTime);
        } else if (server.hasNextCustomer()) {
            Customer nextCustomer = server.serveNextCustomer();
            return new ServedEvent(nextCustomer.getID(), serverId, currentTime);
        } else {
            server.idle();
            return null;
        }
    }

    /**
     * Manages a server rest event: Server rests and generates a server back event.
     *
     * @param event event
     * @return server back event for simulation
     */
    protected ServerBackEvent manageRest(Event event) {
        int serverId = event.getServerId();
        double restTime = rand.genRestPeriod();
        double nextServiceTime = event.getTime() + restTime;
        return new ServerBackEvent(serverId, nextServiceTime);
    }

    /**
     * Manages a server back event: Server comes back from rest and immediately
     * serves the next customer in queue (generate serve event). If no customers in
     * queue, then no event is generated and server idles.
     *
     * @param event event
     * @return serve event only if there is a customer at server's queue
     */
    protected ServedEvent manageBack(Event event) {
        int serverId = event.getServerId();
        Server server = servers[serverId - 1];
        if (server.hasNextCustomer()) {
            Customer nextCustomer = server.serveNextCustomer();
            return new ServedEvent(nextCustomer.getID(), serverId, event.getTime());
        } else {
            server.idle();
            return null;
        }
    }

    /**
     * Manages a customer leave event. Updates the number of customers who left the shop.
     */
    protected void manageLeave(Event event) {
        int customerId = event.getCustomerId();
        Customer customer = customers[customerId - 1];
        System.out.println(event + customer.toString() + " leaves");
        stat.updateLeave();
    }
}
