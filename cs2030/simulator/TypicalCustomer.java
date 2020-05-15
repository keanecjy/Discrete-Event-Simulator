package cs2030.simulator;

/**
 * TypicalCustomer is a subclass of Customer which is not greedy.
 * Proceeds to the first not full queue of the shop (if available).
 */
public class TypicalCustomer extends Customer {

    /**
     * Assigns a customer to its unique ID and arrivalTime.
     *
     * @param id id of the customer
     * @param arrivalTime arrivalTime of customer
     */
    protected TypicalCustomer(int id, double arrivalTime) {
        super(id, arrivalTime);
    }

    /**
     * Always false since a typical customer is not greedy.
     *
     * @return false
     */
    @Override
    protected boolean isGreedy() {
        return false;
    }
}
