package cs2030.simulator;

/**
 * ArrivalEvent: Customer arrives at the shop.
 */
public class ArrivalEvent extends Event {
    /**
     * Constructor which initialises an arrival event to be simulated.
     *
     * @param customerId customer id
     * @param time event time
     */
    protected ArrivalEvent(int customerId, double time) {
        super(customerId, 0, time, EventType.ARRIVES);
    }
}
