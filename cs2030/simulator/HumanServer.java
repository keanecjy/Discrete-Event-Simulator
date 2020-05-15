package cs2030.simulator;

import java.util.Queue;

/**
 * HumanServer is a subclass of Server which is able to rest.
 */
public class HumanServer extends Server {

    /**
     * Uses Server's constructor to initialise the server with its
     * server id and maxQueueLength of the waiting list, and customer queue.
     *
     * @param serverId id of server
     * @param maxQueueLength length of the queue
     * @param customersQueue customer queue
     */
    protected HumanServer(int serverId, int maxQueueLength, Queue<Customer> customersQueue) {
        super(serverId, maxQueueLength, customersQueue);
    }

    /**
     * Always true since human servers can rest.
     *
     * @return true
     */
    @Override
    protected boolean canRest() {
        return true;
    }

    /**
     * String representation of this server with a unique server id.
     *
     * @return string representation of human server
     */
    @Override
    public String toString() {
        return "server " + getID();
    }
}
