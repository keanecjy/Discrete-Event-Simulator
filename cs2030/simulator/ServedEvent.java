package cs2030.simulator;

/**
 * ServedEvent: Customer is served.
 */
public class ServedEvent extends Event {
    /**
     * Constructor which initialises a served event to be simulated.
     * @param customerId customer id
     * @param serverId server id
     * @param time event time
     */
    protected ServedEvent(int customerId, int serverId, double time) {
        super(customerId, serverId, time, EventType.SERVE);
    }
}
