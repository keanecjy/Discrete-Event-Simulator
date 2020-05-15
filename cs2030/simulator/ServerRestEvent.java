package cs2030.simulator;

/**
 * ServerRestEvent: Server takes a rest after he/she finishes serving a customer.
 */
public class ServerRestEvent extends Event {
    /**
     * Constructor to initialise a server rest event for simulation.
     *
     * @param serverId server id
     * @param time event time
     */
    protected ServerRestEvent(int serverId, double time) {
        super(1, serverId, time, EventType.SERVER_REST);
    }
}
