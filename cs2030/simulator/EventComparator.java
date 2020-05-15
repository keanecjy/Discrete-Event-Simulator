package cs2030.simulator;

import java.util.Comparator;

/**
 * EventComparator class, which arranges the events based on their time.
 * If there are 2 events with the same time, the event with the smaller customer
 * id has the priority.
 */
public class EventComparator implements Comparator<Event> {

    /**
     * Overrides compare method to compare 2 event classes. Events are first ordered
     * by time, then customer id (lower has a higher priority)
     *
     * @param e1 1st Event
     * @param e2 2nd Event
     * @return -1 if e1 has a higher priority than e2.
     */
    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getTime() < e2.getTime()) {
            return -1;
        } else if (e1.getTime() > e2.getTime()) {
            return 1;
        } else { // same event times - pick Event with smaller customer ID
            if (e1.getCustomerId() < e2.getCustomerId()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
