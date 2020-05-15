package cs2030.simulator;

/**
 * WaitEvent: Customer waits in the queue for his/her turn to be served.
 */
public class WaitEvent extends Event {
    /**
     * Constructor which initialises a wait event to be simulated.
     *
     * @param customerId customer id
     * @param serverId server id
     * @param time event time
     */
    protected WaitEvent(int customerId, int serverId, double time) {
        super(customerId, serverId, time, EventType.WAITS);
    }
}
