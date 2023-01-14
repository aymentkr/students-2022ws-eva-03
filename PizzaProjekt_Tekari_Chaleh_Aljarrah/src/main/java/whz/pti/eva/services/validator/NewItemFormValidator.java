package whz.pti.eva.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import whz.pti.eva.domain.cart.NewItemForm;

/**
 * The Class NewItemFormValidator.
 */
@Component
public class NewItemFormValidator implements Validator {

	/**
	 * Supports.
	 *
	 * @param clazz the clazz
	 * @return true, if successful
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(NewItemForm.class);
	}

	/**
	 * Validate.
	 *
	 * @param target the target
	 * @param errors the errors
	 */
	@Override
	public void validate(Object target, Errors errors) {
		NewItemForm newItemForm = (NewItemForm) target;
		if (newItemForm.getQuantity() < 1) {
			errors.reject("quantity", "at least a quantity of one is needed!");
		}
	}
}
