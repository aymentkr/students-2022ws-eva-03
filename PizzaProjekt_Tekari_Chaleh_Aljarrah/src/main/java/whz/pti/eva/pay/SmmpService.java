package whz.pti.eva.pay;

public interface SmmpService {
	PayActionResponse doPayAction(String from, String to, String amount);
}
