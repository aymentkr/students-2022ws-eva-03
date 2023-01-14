package whz.pti.eva.pay;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * The Class StmmServiceImpl.
 */
@Service
public class SmmpServiceImpl implements SmmpService {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(SmmpServiceImpl.class);

    /** The My url. */
    @Value("${my.smmp.url}")
    String MyUrl;

    /** The plain creds. */
    @Value("${my.smmp.plainCreds}")
    String plainCreds;
    

	/**
	 * Do pay action.
	 *
	 * @param from the from
	 * @param to the to
	 * @param amount the amount
	 * @return the pay action response
	 */
	@Override
	public PayActionResponse doPayAction(String from, String to, String amount) {
		String token1 ="", token2 ="", token3 ="";
		PayActionResponse payActionResponse =
	    new PayActionResponse().payment(false).description("unbekanntes Problem. Transfer nicht erfolgreich");
		
		String[] tokens = amount.split("\\s+");
		if(tokens.length == 0) return payActionResponse.description("false Syntaxt");
		if(tokens.length >= 4) return payActionResponse.description("falsche Syntax - Eingabe zu lang / zuviele Worte");
		for (int i=0; i<tokens.length; i++) {
            if (i == 0) token1 = tokens[0];
            if (i == 1) token2 = tokens[1];
            if (i == 2) token3 = tokens[2];
        }
		 if ((token1.equals("get") || token1.equals("delete") || token1.equals("open") || token1.equals("suspend")) && (token2.equals("")) && (token3.equals(""))) {
	            return smmpAccountCommunication(token1, token2, token3, from, payActionResponse);
	        }
		return null;
	}
    

	/**
	 * Smmp account communication.
	 *
	 * @param token1 the token 1
	 * @param token2 the token 2
	 * @param token3 the token 3
	 * @param from the from
	 * @param payActionResponse the pay action response
	 * @return the pay action response
	 */
	private PayActionResponse smmpAccountCommunication(String token1, String token2, String token3, String from, PayActionResponse payActionResponse) {

        String uriReturn;
        ResponseEntity<?> response = null;
        RestTemplate restTemplate = new RestTemplate();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
        switch (token1) {
            case "get":
                uriReturn = MyUrl + from + "/account";
                response = restTemplate.exchange(uriReturn, HttpMethod.GET, request, AccountResponse.class);
                break;
            case "delete":
                uriReturn = MyUrl + from + "/deleted";
                response = restTemplate.exchange(uriReturn, HttpMethod.DELETE, request, AccountResponse.class);
                break;
            case "open":
                uriReturn = MyUrl + from + "/opened";
                response = restTemplate.exchange(uriReturn, HttpMethod.PUT, request, AccountResponse.class);
                break;
            case "suspend":
            	uriReturn = MyUrl + from + "/suspended";
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> requestPut = new HttpEntity<>("suspended", headers);
                response = restTemplate.exchange(uriReturn, HttpMethod.PUT, requestPut, AccountResponse.class);
                break;
            case "transfer":
                headers.setContentType(MediaType.APPLICATION_JSON);
                Transfer transfer = new Transfer(token2, Integer.valueOf(token3));
                HttpEntity<Transfer> requestPost = new HttpEntity<>(transfer, headers);
                uriReturn = MyUrl + from + "/payment";
                response = restTemplate.exchange(uriReturn, HttpMethod.POST, requestPost, AccountResponse.class);
                break;
            default:
                return payActionResponse.description("falsche Syntax - Befehl unbekannt !");
        }

        } catch(ResourceAccessException e){
            response = new ResponseEntity<Object>(new AccountResponse(token1 +" " + "ist nicht erfolgreich gewesen :: vlt. smmp-Dienst nicht erreichbar"), HttpStatus.OK);
        }

        catch(Exception e){
            response = new ResponseEntity<Object>(new AccountResponse(token1+" " + " ist nicht erfolgreich gewesen :: vlt. Empfaenger unbekannt"), HttpStatus.OK);
        }

        AccountResponse accountResponse = (AccountResponse) response.getBody();
        payActionResponse.description(accountResponse.getCode());

        if (response.getStatusCode().equals(HttpStatus.OK))
        {
            payActionResponse.payment(true);
        }
        else {
            payActionResponse.payment(false);
        }
        return payActionResponse;
    }

}

