package whz.pti.eva.pay;

import java.util.Objects;

public class Payment {

	private String amount;

	private String currentTime;

	@Override
	public int hashCode() {
		return Objects.hash(amount, currentTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(currentTime, other.currentTime);
	}
	
}
