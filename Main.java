import cs2030.simulator.EventSimulator;

/**
 * Description: Main class which initialises the EventSimulator class which simulates the events and
 * obtain the statistics by the EventSimulator.
 */
public class Main {

    /**
     * Main driver method.
     *
     * @param args null
     */
    public static void main(String[] args) {
        EventSimulator events = EventSimulator.storeEvents();
        events.simulate();
    }
}
