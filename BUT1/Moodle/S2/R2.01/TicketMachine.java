/**
 * TicketMachine models a naive ticket machine that issues
 * flat-fare tickets.
 * The price of a ticket is specified via the constructor.
 * It is a naive machine in the sense that it trusts its users
 * to insert enough money before trying to print a ticket.
 * It also assumes that users enter sensible amounts.
 *
 * @author David J. Barnes and Michael Kolling
 * modified by IB
 */
public class TicketMachine {
    // The price of a ticket from this machine.
    private int price;
    // The amount of money entered by a customer so far.
    private int balance;
    // The total amount of money collected by this machine.
    private int total;

    /**
     * Create a machine that issues tickets of the given price.
     * Note that the price must be greater than zero, and there
     * are no checks to ensure this.
     * @param ticketCost : the price of the ticket
     */
    public TicketMachine(int ticketCost)  {
        this.price = ticketCost;
        this.balance = 0;
        this.total = 0;
    }

    /**
     * Get the price of a ticket.
     * @return the price
     */
    public int getPrice()  {
        return this.price;
    }

    /**
     * Get the amount of money already inserted for the next ticket.
     * @return the amount inserted 
     */
    public int getBalance()    {
        return this.balance;
    }
    
    /**
     * Get the total collected with all the inserted amounts 
     * @return the total collected
     */
    public int getTotal()    {
        return this.total;
    }

    /**
     * Receive an amount of money in cents from a customer.
     * @param amount : the amount inserted by a customer
     */
    public void insertMoney(int amount)    {
        this.balance = this.balance + amount;
    }

    /**
     * Print a ticket.
     * Update the total collected and reduce the balance to zero.
     */
    public void printTicket() {
        // Simulate the printing of a ticket.
        System.out.println("##################");
        System.out.println("# IUT de Vannes");
        System.out.println("# Ticket");
        System.out.println("# " + price + " euros.");
        System.out.println("##################");
        System.out.println();

        // Update the total collected with the balance.
        this.total = this.total + this.balance;
        // Clear the balance.
        this.balance = 0;
    }
} // end TicketMachine
