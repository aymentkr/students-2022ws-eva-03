package whz.pti.eva.pay;

import java.io.Serializable;

/**
 * The Class AccountResponse.
 */
public class AccountResponse implements Serializable  {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The code. */
	private String code;
	
	/**
	 * Instantiates a new account response.
	 */
	public AccountResponse() {
		
	}
    
    /**
     * Instantiates a new account response.
     *
     * @param code the code
     */
    public AccountResponse(String code) {
        this.code = code;
    }
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
