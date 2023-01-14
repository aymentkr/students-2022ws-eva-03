package whz.pti.eva.pay;

/**
 * The Class PayActionResponse.
 */
public class PayActionResponse {
	
	/** The payment. */
	private boolean payment;
	  
  	/** The description. */
  	private String token1 = "", token2 = "", token3 = "", description = "";

	    /**
    	 * Instantiates a new pay action response.
    	 */
    	public PayActionResponse() {
	    }


	    /**
    	 * Instantiates a new pay action response.
    	 *
    	 * @param payment the payment
    	 * @param token1 the token 1
    	 * @param token2 the token 2
    	 * @param token3 the token 3
    	 * @param description the description
    	 */
    	public PayActionResponse(boolean payment, String token1, String token2, String token3, String description) {
			this.payment = payment;
			this.token1 = token1;
			this.token2 = token2;
			this.token3 = token3;
			this.description = description;
		}


		/**
    	 * Payment.
    	 *
    	 * @param payment the payment
    	 * @return the pay action response
    	 */
    	public PayActionResponse payment(boolean payment) {
	        this.payment = payment;
	        return this;
	    }


	    /**
    	 * Gets the description.
    	 *
    	 * @return the description
    	 */
    	public String getDescription() {
	        return description;
	    }

	    /**
    	 * Description.
    	 *
    	 * @param description the description
    	 * @return the pay action response
    	 */
    	public PayActionResponse description(String description) {
	        this.description = description;
	        return this;
	    }

	}
