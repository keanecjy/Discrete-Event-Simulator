package cs2030.simulator;

/**
 * Description: Customer class, where each customer has an id and an arrivalTime.
 * A customer can either be typical or greedy. If he is typical, he will look for
 * the first queue that has a not full queue. If he is greedy, then he searches for the
 * shortest not full queue out of all queues in the shop.
 */
public abstract class Customer {

    private final int id;
    private final double arrivalTime;

    /**
     * Assigns a customer to its unique ID and arrivalTime.
     *
     * @param id id of the customer
     * @param arrivalTime arrivalTime of customer
     */
    protected Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Retrieves the arrivalTime of the customer.
     *
     * @return arrivalTime of customer
     */
    protected double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Returns customer id.
     *
     * @return customer id
     */
    protected int getID() {
        return id;
    }

    /**
     * Checks if the customer is greedy or not.
     *
     * @return true iff the customer is greedy
     */
    protected abstract boolean isGreedy();

    /**
     * String representation of customer with id.
     *
     * @return id of customer
     */
    @Override
    public String toString() {
        return id + "";
    }
}
