package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Description: This class simulates the events of the customers and servers,
 * printing the statistics of average waiting time, number of customers served,
 * and number of customers left. A random generator is used to generate the events
 * (customers' arrival) for simulation.
 */
public class EventSimulator {
    private final PriorityQueue<Event> events;
    private final Shop shop;

    /**
     * Initialises EventSimulator with a priority queue of events and
     * a shop which manages the events.
     */
    private EventSimulator(PriorityQueue<Event> events, Shop shop) {
        this.events = events;
        this.shop = shop;
    }

    /**
     * Schedules the events in the priority queue.
     * Outputs statistics from the shop at the end of simulation.
     */
    public void simulate() {
        while (!events.isEmpty()) {
            getNextEvent();
        }
        shop.getStatistics();
    }

    /**
     * Simulates the events based on the type of event.
     */
    private void getNextEvent() {
        Event event = events.poll();
        EventType type = event.getType();
        Event next = null;
        if (type == EventType.ARRIVES) {
            next = shop.manageArrival(event); 
        } else if (type == EventType.SERVE) {
            next = shop.manageServe(event);
        } else if (type == EventType.WAITS) {
            shop.manageWait(event);
        } else if (type == EventType.DONE) {
            next = shop.manageDone(event);
        } else if (type == EventType.SERVER_REST) {
            next = shop.manageRest(event);
        } else if (type == EventType.SERVER_BACK) {
            next = shop.manageBack(event);
        } else { // Leaves
            shop.manageLeave(event);
        }
        if (next != null) {
            events.add(next);
        }
    }

    /**
     * Uses the scanner object to retrieve input and initialises event simulator object
     * with a priority queue of events (generated from random generator) and
     * a shop to manage the events generated.
     *
     * @return Event Simulator for simulating events
     */
    public static EventSimulator storeEvents() {
        Scanner sc = new Scanner(System.in);
        int seed = sc.nextInt();

        int numOfHumanServers = sc.nextInt();
        if (numOfHumanServers < 0) {
            throw new ArithmeticException("Number of servers must be non-negative");
        }

        int numSelfCheckout = sc.nextInt();
        if (numSelfCheckout < 0) {
            throw new ArithmeticException("Number of self-checkout counters must be non-negative");
        }
        
        int maxQueueLength = sc.nextInt();
        if (maxQueueLength < 0) {
            throw new ArithmeticException("Queue length must be non-negative");
        }

        int numCustomers = sc.nextInt();
        if (numCustomers < 0) {
            throw new ArithmeticException("Number of customers must be non-negative");
        }

        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();

        double probOfResting = sc.nextDouble();
        if (probOfResting < 0) {
            throw new ArithmeticException("Probability of resting has to be non-negative");
        }

        double probOfGreedy = sc.nextDouble();
        if (probOfGreedy < 0) {
            throw new ArithmeticException("Probability of greedy has to be non-negative");
        }

        sc.close();

        PriorityQueue<Event> events = new PriorityQueue<>(new EventComparator());
        RandomGenerator rand = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
        double arrivalTime = 0;
        for (int customerId = 1; customerId <= numCustomers; customerId++) {
            events.add(new ArrivalEvent(customerId, arrivalTime));
            arrivalTime += rand.genInterArrivalTime();
        }
        return new EventSimulator(events, new Shop(numOfHumanServers, numSelfCheckout,
                maxQueueLength, numCustomers, rand, probOfResting, probOfGreedy));
    }
}
