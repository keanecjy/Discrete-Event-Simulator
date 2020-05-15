package cs2030.simulator;

/**
 * GreedyCustomer is a subclass of Customer which is greedy.
 * Searches for the shortest not full queue out of all queues in the shop (if available).
 */
public class GreedyCustomer extends Customer {

    /**
     * Assigns a customer to its unique ID and arrivalTime.
     *
     * @param id id of the customer
     * @param arrivalTime arrivalTime of customer
     */
    protected GreedyCustomer(int id, double arrivalTime) {
        super(id, arrivalTime);
    }

    /**
     * Always true since a greedy customer is always greedy.
     *
     * @return true
     */
    @Override
    protected boolean isGreedy() {
        return true;
    }

    /**
     * Returns a string representation of a greedy customer.
     *
     * @return String representation of greedy customer
     */
    @Override
    public String toString() {
        return super.toString() + "(greedy)";
    }
}
