package cs2030.simulator;

/**
 * Statistics class tracks the statistics of the shop.
 * Statistics include average waiting time of customers,
 * total number of customers served, and number of customers who left.
 */
public class Statistics {
    private int noCustomersLeft = 0;
    private int numServed = 0;
    private double totalWaitingTime = 0;

    /**
     * Generates the summary of statistics after all events are simulated.
     * Output format: [Average waiting time, number of customers served, number of customers left]
     */
    protected void generate() {
        double averageWait;
        if (numServed > 0) {
            averageWait = totalWaitingTime / numServed;
        } else {
            averageWait = 0;
        }
        System.out.println(String.format("[%.3f %d %d]", averageWait, numServed, noCustomersLeft));
    }

    /**
     * Increments number of customers who left the shop.
     */
    protected void updateLeave() {
        noCustomersLeft++;
    }

    /**
     * Increments number of customers served.
     */
    protected void updateServed() {
        numServed++;
    }

    /**
     * Increments total waiting time based on this customers waiting time.
     *
     * @param waitingTime waiting time of a customer
     */
    protected void updateWait(double waitingTime) {
        totalWaitingTime += waitingTime;
    }
}
