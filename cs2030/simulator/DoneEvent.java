package cs2030.simulator;

/**
 * DoneEvent: Customer is served.
 */
public class DoneEvent extends Event {
    /**
     * Constructor which initialises a done event to be simulated.
     *
     * @param customerId customer id
     * @param serverId server id
     * @param time event time
     */
    protected DoneEvent(int customerId, int serverId, double time) {
        super(customerId, serverId, time, EventType.DONE);
    }

}
