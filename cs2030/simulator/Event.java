package cs2030.simulator;

/**
 * Event abstract class which represents an event to be simulated in the simulator.
 * Each event class has a customer id, server id, event time, and type of event. An event
 * can either be a server-type event (2 types: Server_Rest, Server_Back)
 * or a customer-type event (5 types: Arrives, Served, Leaves, Waits, Done).
 */
public abstract class Event {
    private final int customerId;
    private final int serverId;
    private final double time;
    private final EventType type;

    /**
     * Constructor which initialises an event.
     *
     * @param customerId customer id
     * @param serverId server id
     * @param time event time
     * @param type event type
     */
    protected Event(int customerId, int serverId, double time, EventType type) {
        this.customerId = customerId;
        this.serverId = serverId;
        this.time = time;
        this.type = type;
    }

    /**
     * Returns server id of server involved in the event.
     *
     * @return server id
     */
    protected int getServerId() {
        return serverId;
    }

    /**
     * Returns customer id of customer involved in the event.
     *
     * @return event id
     */
    protected int getCustomerId() {
        return customerId;
    }

    /**
     * Returns event type.
     *
     * @return event type
     */
    protected EventType getType() {
        return type;
    }

    /**
     * Returns current event time.
     *
     * @return event time.
     */
    protected double getTime() {
        return time;
    }

    /**
     * String representation of event time.
     *
     * @return string representation of event time
     */
    @Override
    public String toString() {
        return String.format("%.3f ", time);
    }
}
