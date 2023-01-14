package whz.pti.eva.pay;

/**
 * The Class Transfer.
 */
public class Transfer {

		 /** The to. */
	 	String to;
	    
    	/** The amount. */
    	int amount;

	    /**
    	 * Instantiates a new transfer.
    	 */
    	public Transfer() {
	    }

	    /**
    	 * Instantiates a new transfer.
    	 *
    	 * @param to the to
    	 * @param amount the amount
    	 */
    	public Transfer(String to, int amount) {
	        this.to = to;
	        this.amount = amount;
	    }

	    /**
    	 * Gets the to.
    	 *
    	 * @return the to
    	 */
    	public String getTo() {
	        return to;
	    }

	    /**
    	 * Gets the amount.
    	 *
    	 * @return the amount
    	 */
    	public int getAmount() {
	        return amount;
	    }
	}
