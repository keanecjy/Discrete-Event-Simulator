package cs2030.simulator;

import java.util.Queue;

/**
 * Description: Server class whereby each server has its own server id, waiting list,
 * idle state and maximum queue length. Customers that come to the server are either served
 * immediately or put in the customers queue (First In First Out queue). A server
 * can either be idling or not idling (resting or serving).
 */
public abstract class Server {

    private final int serverId;
    private boolean idling;
    private final Queue<Customer> customersQueue;
    private final int maxQueueLength;

    /**
     * Constructor which initialises the server with its server id and maxQueueLength of
     * the waiting list. Initial state of server is idling.
     *
     * @param serverId id of server
     * @param maxQueueLength length of the queue
     * @param customersQueue customer queue
     */
    protected Server(int serverId, int maxQueueLength, Queue<Customer> customersQueue) {
        this.serverId = serverId;
        this.maxQueueLength = maxQueueLength;
        this.customersQueue = customersQueue;
        idling = true;
    }

    /**
     * Retrieves server id of server.
     *
     * @return server id
     */
    protected int getID() {
        return serverId;
    }

    /**
     * Checks if the current server is not serving anyone and not resting.
     *
     * @return true iff current server is not serving and not resting
     */
    protected boolean isIdling() {
        return idling;
    }

    /**
     * Retrieves size of queue.
     *
     * @return size of queue
     */
    protected int queueSize() {
        return customersQueue.size();
    }

    /**
     * Checks if the current server has a not full queue.
     *
     * @return true iff the queue is not full
     */
    protected boolean hasNotFullQueue() {
        return customersQueue.size() < maxQueueLength;
    }

    /**
     * Adds customer to tail of this queue.
     *
     * @param customer customer
     */
    protected void addToQueue(Customer customer) {
        customersQueue.offer(customer);
    }

    /**
     * Checks if there is a next customer in the queue.
     *
     * @return true iff there is a next customer in the queue
     */
    protected boolean hasNextCustomer() {
        return !customersQueue.isEmpty();
    }

    /**
     * Returns the next customer in the queue.
     *
     * @return next customer in queue
     */
    protected Customer serveNextCustomer() {
        return customersQueue.poll();
    }

    /**
     * Changes the server's idling status to false.
     */
    protected void serve() {
        idling = false;
    }

    /**
     * Changes idling status of server to true.
     */
    protected void idle() {
        idling = true;
    }

    /**
     * Checks if server is able to rest.
     *
     * @return true iff server can rest
     */
    protected abstract boolean canRest();
}
