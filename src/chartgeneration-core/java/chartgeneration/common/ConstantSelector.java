package chartgeneration.common;

import java.util.List;

/**
 * A ConstantSelector is a special {@link FieldSelector} that always returns the
 * specified constant value (regardless of the given data row)
 * 
 * @author Rayson Zhu
 * @see FieldSelector
 */
public class ConstantSelector implements FieldSelector {
	/**
	 * the constant
	 * 
	 * @see #select(List)
	 */
	private Object constant;

	public ConstantSelector() {

	}

	/**
	 * the constant
	 * 
	 * @param constant
	 *            a constant
	 */
	public ConstantSelector(Object constant) {
		this.constant = constant;
	}

	/**
	 * This method just returns the specified constant, regardless of the
	 * value of data row
	 * @param row ignored data row
	 * @see #setConstant(Object)
	 * @see #getConstant()
	 */
	@Override
	public Object select(List<?> row) {
		return constant;
	}

	/**
	 * get the constant
	 * 
	 * @return a constant
	 */
	public Object getConstant() {
		return constant;
	}

	/**
	 * set the constant
	 * 
	 * @param constant
	 *            a constant
	 */
	public void setConstant(Object constant) {
		this.constant = constant;
	}

}
