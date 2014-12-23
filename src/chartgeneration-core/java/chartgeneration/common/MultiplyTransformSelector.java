package chartgeneration.common;

import java.util.List;

/**
 * A MultiplyTransformSelector is {@link BinarySelector} that multiplies the
 * values extracted by the other two {@link FieldSelector}s and regards the
 * product as its extracted value.
 * 
 * @author Rayson Zhu
 *
 */
public class MultiplyTransformSelector extends BinarySelector {
	public MultiplyTransformSelector() {

	}

	public MultiplyTransformSelector(FieldSelector firstOperand,
			FieldSelector secondOperand) {
		super(firstOperand, secondOperand);
	}

	/**
	 * Multiply the values extracted by two other {@link FieldSelector}s, and
	 * return the product.
	 * 
	 * @see #setFirstOperand(FieldSelector)
	 * @see #setSecondOperand(FieldSelector)
	 */
	@Override
	public Object select(List<?> row) {
		Number a = (Number) getFirstOperand().select(row);
		Number b = (Number) getSecondOperand().select(row);
		return a.longValue() * b.longValue();
	}
}
