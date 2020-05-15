package cs2030.simulator;

/**
 * ServerBackEvent: Server returns from rest and immediately
 * serves the next customer in queue (if any).
 */
public class ServerBackEvent extends Event {
    /**
     * Constructor which initialises server back event for simulation.
     *
     * @param serverId server id
     * @param time event time
     */
    protected ServerBackEvent(int serverId, double time) {
        super(1, serverId,
                time, EventType.SERVER_BACK);
    }
}
