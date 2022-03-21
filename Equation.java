/*
 * Create an equation parser
 * - Implement infix evaluation!!
 * - Implement derivative and integral stuff
 * 
 */

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Equation {

	// raw equation such as
	// 1.0x^5+2.2x^3+3.23x^4+4-3.2x
	// String rawEquation;
	final String SEP = (char) (31) + ""; // separator use to parse rawEquation
	ArrayList<Term> terms; // terms
	ArrayList<Character> operators; // operators

	// each term of equation
	// in the form coefficient * variable ^ exponent
	private class Term {
		double coefficient;
		double exponent;

		// build term based on coefficient and exponent
		public Term(double coefficient, double exponent) {
			this.coefficient = coefficient;
			this.exponent = exponent;
		}

		// build term based on string, given variable
		// Example: 5x^4, 5.0, 6*x, 9.3x^3.2
		public Term(String str, char variable) {
			String[] half = str.split(variable + "");
			coefficient = 1;
			if (half.length > 1) { // there is a coefficient
				if (half[0].length() > 0)
					coefficient *= Double.parseDouble(half[0]);
				exponent = Double.parseDouble(half[1].substring(1));
			} else { // there is either only a coefficient or an exponent
				exponent = 1;
				if (half[0].charAt(0) == '^') { // just an exponent
					exponent = Double.parseDouble(half[0].substring(1));
				} else if (half.length != 0) { // just a coefficient
					exponent = str.charAt(str.length() - 1) == variable ? 1 : 0;
					coefficient *= Double.parseDouble(half[0]);
				}
			}
		}

		// evaluate term when variable = value
		public double evaluateAtValue(double value) {
			if (exponent == 0)
				return coefficient;
			return coefficient * (Math.pow(value, exponent));
		}

		// return the power rule derivative of this term as a new term
		public Term derive() {
			if (exponent == 0)
				return new Term(0, 0);
			return new Term(coefficient * exponent, exponent - 1);
		}

		// return the reverse power rule integral of this term as a new term
		// don't forget to add C (constant if needed)
		public Term integrate() {
			return new Term(coefficient / (exponent + 1), exponent + 1);
		}

		public String toString() {
			return coefficient + "x^" + exponent;
		}
	}

	public Equation(String rawEquation, char variable) {
		terms = new ArrayList<Term>();
		operators = new ArrayList<Character>();

		// remove all white space including leading and trailing whitespace
		rawEquation = rawEquation.replaceAll("\\s", "");
		// parse rawEquation
		parse(rawEquation, variable);
	}

	public Equation(ArrayList<Term> terms, ArrayList<Character> operators) {
		this.terms = terms;
		this.operators = operators;
	}

	// return true if c is an operator character (+,-,*,/)
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}

	// return true if c is a digit (0-9)
	private boolean isDigit(char c) {
		return (c >= 48 && c <= 57);
	}

	// return precedence of operator (*=2, /=2, +=1, -=1)
	private int getPrecedence(char operator) {
		if (operator == '*')
			return 2;
		if (operator == '/')
			return 2;
		if (operator == '+')
			return 1;
		if (operator == '-')
			return 1;
		return 0; // not a valid operator
	}

	// return operation between a and b
	private double evaluate(double a, double b, char operator) {
		if (operator == '*')
			return a * b;
		if (operator == '/')
			return a / b;
		if (operator == '+')
			return a + b;
		if (operator == '-')
			return a - b;
		return -1;
	}

	// process rawEquation (does not have whitespace) based on specified variable
	// variable should usually be 'x'
	// return true if rawEquation is a valid equation and has been parsed
	// return false if rawEquation is not a valid equation
	private boolean parse(String rawEquation, char variable) {
		int prevPos = 0;
		for (int currPos = 0; currPos < rawEquation.length(); currPos++) {
			char curr = rawEquation.charAt(currPos);
			if (isOperator(curr)) {
				// equation cannot end with operator!
				if (currPos == rawEquation.length() - 1)
					return false;
				// equation cannot start with operator unless it is '-'
				if (curr != '-' && currPos == 0)
					return false;
				// equation cannot have more than two operators in a row
				if (currPos > 0 && isOperator(rawEquation.charAt(currPos + 1))
						&& isOperator(rawEquation.charAt(currPos - 1)))
					return false;
				if (currPos > 0) {
					char prevChar = rawEquation.charAt(currPos - 1);
					// previous character is an operator
					// (also means this operator must be '-')
					if (isOperator(prevChar))
						prevPos = currPos;
					// previous character is variable, digit, or period
					else if (prevChar == variable || prevChar == '.' || isDigit(prevChar)) {
						terms.add(new Term(rawEquation.substring(prevPos, currPos), variable));
						operators.add(curr);
						prevPos = currPos + 1;
					}
				}
			}
		}
		// add last term in equation
		terms.add(new Term(rawEquation.substring(prevPos), variable));
		return true;
	}

	// evaluate this equation at value
	// operator precedence (highest to lowest): *, /, +, -
	public double evaluateAtValue(double value) {
		int operandPos = 0, operatorPos = 0;
		Deque<Character> operatorStack = new LinkedList<Character>();
		Deque<Double> operandStack = new LinkedList<Double>();
		while (operandPos < terms.size()) {
			// evaluate operand
			if (operandPos == operatorPos) {
				double val = terms.get(operandPos).evaluateAtValue(value);
				operandStack.push(val);
				operandPos++;
			} else { // evaluate operator
				char operator = operators.get(operatorPos);
				if (!operatorStack.isEmpty()) {
					while (!operatorStack.isEmpty() && getPrecedence(operator) < getPrecedence(operatorStack.peek())) {
						char topOp = operatorStack.pop();
						double b = operandStack.pop();
						double a = operandStack.pop();
						operandStack.push(evaluate(a, b, topOp));
					}
				}
				operatorStack.push(operator);
				operatorPos++;
			}
		}
		while (!operatorStack.isEmpty()) {
			char topOp = operatorStack.pop();
			double b = operandStack.pop();
			double a = operandStack.pop();
			operandStack.push(evaluate(a, b, topOp));
		}
		return operandStack.pop();
	}

	// return the equation that is the derivative of this equation
	public Equation getDerivative() {
		ArrayList<Character> operatorsCopy = new ArrayList<Character>();
		for (char operator : operators)
			operatorsCopy.add(operator);
		ArrayList<Term> newTerms = new ArrayList<Term>();
		for (Term t : terms)
			newTerms.add(t.derive());
		// remove all terms that have a coefficient of 0
		for (int i = newTerms.size() - 1; i > 0; i--) {
			if (newTerms.get(i).coefficient == 0) {
				newTerms.remove(i);
				operatorsCopy.remove(i - 1);
			}
		}
		if (newTerms.get(0).coefficient == 0)
			newTerms.remove(0);
		return new Equation(newTerms, operatorsCopy);
	}

	// return the equation that is the derivative of this equation
	public Equation getIntegral() {
		ArrayList<Character> operatorsCopy = new ArrayList<Character>();
		for (char operator : operators)
			operatorsCopy.add(operator);
		ArrayList<Term> newTerms = new ArrayList<Term>();
		for (Term t : terms)
			newTerms.add(t.integrate());
		return new Equation(newTerms, operatorsCopy);
	}

	public String toString() {
		StringBuilder stb = new StringBuilder();
		int operandPos = 0;
		while (operandPos < terms.size() - 1) {
			stb.append(terms.get(operandPos));
			stb.append(' ');
			stb.append(operators.get(operandPos));
			stb.append(' ');
			operandPos++;
		}
		stb.append(terms.get(operandPos));
		return stb.toString();
	}

	public static void main(String[] args) {
		Equation eq = new Equation("1.0x^5 +2.2x^3+3.23x^4 + 4- 3.2x", 'x');
		System.out.println(eq.terms);
		System.out.println(eq.operators);
		System.out.println(eq);
		System.out.println("Value when x=5.2: " + eq.evaluateAtValue(5.2));
		System.out.println("Original Equation: " + eq);
		System.out.println("Derivative: " + eq.getDerivative());
		System.out.println("Integral: " + eq.getIntegral() + " + C");
		System.out.println();

		Equation eq2 = new Equation("-1.0*5*x^5 +2.2*4*x^3+3.23x^4 + 4*3- 3.2x", 'x');
		System.out.println(eq2.terms);
		System.out.println(eq2.operators);
		System.out.println(eq2);
		System.out.println("Value when x=3.0: " + eq2.evaluateAtValue(3.));
		System.out.println("Original Equation: " + eq2);
		System.out.println("Derivative: " + eq2.getDerivative());
		System.out.println("Integral: " + eq2.getIntegral() + " + C");
		System.out.println();
	}
}