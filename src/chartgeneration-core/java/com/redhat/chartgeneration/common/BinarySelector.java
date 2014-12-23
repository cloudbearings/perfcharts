package com.redhat.chartgeneration.common;

import java.util.List;

/**
 * A BinarySelector combines the results of two other FieldSelectors. It calls
 * specified {@link FieldSelector}s, collects the values, and gives out a result after
 * performing some calculations on them.
 * 
 * @author Rayson Zhu
 *
 * @see FieldSelector
 */
public abstract class BinarySelector implements FieldSelector {
	/**
	 * the first FieldSelector
	 */
	private FieldSelector firstOperand;
	/**
	 * the second FieldSelector
	 */
	private FieldSelector secondOperand;

	/**
	 * constructor
	 */
	public BinarySelector() {

	}

	/**
	 * constructor
	 * 
	 * @param firstOperand
	 *            The first FieldSelector
	 * @param secondOperand
	 *            The second FieldSelector
	 */
	public BinarySelector(FieldSelector firstOperand,
			FieldSelector secondOperand) {
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
	}

	/**
	 * Perform some operations on the values extracted by two other {@link FieldSelector}s, and return
	 * the outcome value.
	 * 
	 * @see #setFirstOperand(FieldSelector)
	 * @see #setSecondOperand(FieldSelector)
	 */
	@Override
	public abstract Object select(List<?> row);

	/**
	 * get the first FieldSelector
	 * 
	 * @return the first FieldSelector
	 */
	public FieldSelector getFirstOperand() {
		return firstOperand;
	}

	/**
	 * set the first FieldSelector
	 * 
	 * @param firstOperand
	 *            the first FieldSelector
	 */
	public void setFirstOperand(FieldSelector firstOperand) {
		this.firstOperand = firstOperand;
	}

	/**
	 * get the second FieldSelector
	 * 
	 * @return the second FieldSelector
	 */
	public FieldSelector getSecondOperand() {
		return secondOperand;
	}

	/**
	 * set the second FieldSelector
	 * 
	 * @param secondOperand
	 *            the second FieldSelector
	 */
	public void setSecondOperand(FieldSelector secondOperand) {
		this.secondOperand = secondOperand;
	}
}
