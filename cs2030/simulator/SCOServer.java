package cs2030.simulator;

import java.util.Queue;

/**
 * SCOServer, self-checkout server is a subclass of Server which is not able to rest.
 * All SCOServers share the same queue.
 */
public class SCOServer extends Server {

    /**
     * Uses Server's constructor to initialise the server with its
     * server id and maxQueueLength of the waiting list, and a shared customer queue.
     *
     * @param serverId id of server
     * @param maxQueueLength length of the queue
     * @param customersQueue customer queue
     */
    protected SCOServer(int serverId, int maxQueueLength, Queue<Customer> customersQueue) {
        super(serverId, maxQueueLength, customersQueue);
    }

    /**
     * Always false since self-checkout servers cannot rest.
     *
     * @return false
     */
    @Override
    protected boolean canRest() {
        return false;
    }

    /**
     * String representation of this server with a unique server id.
     *
     * @return string representation of self-checkout server
     */
    @Override
    public String toString() {
        return "self-check " + getID();
    }
}

