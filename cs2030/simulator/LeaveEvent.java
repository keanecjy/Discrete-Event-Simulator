package cs2030.simulator;

/**
 * LeaveEvent: Customer leaves the shop since there are no available servers (to queue/serve).
 */
public class LeaveEvent extends Event {
    /**
     * Constructor which initialises a leave event for simulation.
     *
     * @param customerId customer id
     * @param time event time
     */
    protected LeaveEvent(int customerId, double time) {
        super(customerId, 0, time, EventType.LEAVES);
    }
}
