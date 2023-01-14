package whz.pti.eva.domain.pizza;

import whz.pti.eva.common.BaseEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Class Pizza.
 */
@Entity
public class Pizza extends BaseEntity<Long> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 614617171738400465L;

	/** The name. */
	private String name;
	
	/** The price large. */
	private BigDecimal priceLarge;
	
	/** The price medium. */
	private BigDecimal priceMedium;
	
	/** The price small. */
	private BigDecimal priceSmall;
	
	
	/**
	 * Instantiates a new pizza.
	 */
	public Pizza() {
	}
	
	/**
	 * Instantiates a new pizza.
	 *
	 * @param name the name
	 * @param priceLarge the price large
	 * @param priceMedium the price medium
	 * @param priceSmall the price small
	 */
	public Pizza(String name, BigDecimal priceLarge, BigDecimal priceMedium, BigDecimal priceSmall) {
		this.name = name;
		this.priceLarge = priceLarge;
		this.priceMedium = priceMedium;
		this.priceSmall = priceSmall;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price large.
	 *
	 * @return the price large
	 */
	public BigDecimal getPriceLarge() {
		return priceLarge;
	}

	/** 
	 * Sets the price large.
	 *
	 * @param priceLarge the new price large
	 */
	public void setPriceLarge(BigDecimal priceLarge) {
		this.priceLarge = priceLarge;
	}

	/**
	 * Gets the price medium.
	 *
	 * @return the price medium
	 */
	public BigDecimal getPriceMedium() {
		return priceMedium;
	}

	/**
	 * Sets the price medium.
	 *
	 * @param priceMedium the new price medium
	 */
	public void setPriceMedium(BigDecimal priceMedium) {
		this.priceMedium = priceMedium;
	}

	/**
	 * Gets the price small.
	 *
	 * @return the price small
	 */
	public BigDecimal getPriceSmall() {
		return priceSmall;
	}

	/**
	 * Sets the price small.
	 *
	 * @param priceSmall the new price small
	 */
	public void setPriceSmall(BigDecimal priceSmall) {
		this.priceSmall = priceSmall;
	}
	
	/**
	 * Sets the name of the pizza and returns this instance.
	 *
	 * @param name the new name of the pizza
	 * @return this
	 */
	public Pizza withName(String name) {
		this.setName(name);
		
		return this;
	}
	
	/**
	 * Sets the price large of the pizza and returns this instance.
	 *
	 * @param priceLarge the price large
	 * @return this
	 */
	public Pizza withPriceLarge(BigDecimal priceLarge) {
		this.setPriceLarge(priceLarge);
		
		return this;
	}
	
	/**
	 * Sets the price medium of the pizza and returns this instance.
	 *
	 * @param priceMedium the price medium
	 * @return this
	 */
	public Pizza withPriceMedium(BigDecimal priceMedium) {
		this.setPriceMedium(priceMedium);
		
		return this;
	}
	
	/**
	 * Sets the price small of the pizza and returns this instance.
	 *
	 * @param priceSmall the price small
	 * @return this
	 */
	public Pizza withPriceSmall(BigDecimal priceSmall) {
		this.setPriceSmall(priceSmall);
		
		return this;
	}
	
	/**
	 * Return a string in the form "Small / 1.00"
	 * (price corresponds to the small price) 
	 * 
	 * @return Return a string in the form "Small / 1.00"
	 */
	public String getSmallSizePriceString() {
		return "Small / " + priceSmall.toString(); 
	}
	
	/**
	 * Return a string in the form "Medium / 2.20"
	 * (price corresponds to the medium price) 
	 * 
	 * @return Return a string in the form "Medium / 2.20"
	 */
	public String getMediumSizePriceString() {
		return "Medium / " + priceMedium.toString(); 
	}
	
	/**
	 * Return a string in the form "Large / 3.40"
	 * (price corresponds to the large price) 
	 * 
	 * @return Return a string in the form "Large / 3.40"
	 */
	public String getLargeSizePriceString() {
		return "Large / " + priceLarge.toString(); 
	}
}
