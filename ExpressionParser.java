/*
 * Expression Parser: 
 * 
 * Parse and evaluate any arithmetic equation
 * 
 */

import java.util.ArrayList;
import java.util.Stack;

public class ExpressionParser {

	String exp;

	public ExpressionParser(String rawEquation) {
		this.exp = rawEquation.replaceAll(" ", "");
	}

	public double evaluatePostfix() {
		ArrayList<String> eq = infixToPostfix();
		Stack<Double> stack = new Stack<Double>();
		for (String s : eq) {
			if (s.equals("+")) {
				double a = stack.pop(), b = stack.pop();
				stack.add(a + b);
			} else if (s.equals("-")) {
				double a = stack.pop(), b = stack.pop();
				stack.add(b - a);
			} else if (s.equals("*")) {
				double a = stack.pop(), b = stack.pop();
				stack.add(b * a);
			} else if (s.equals("/")) { // may need to check for division by zero
				double a = stack.pop(), b = stack.pop();
				stack.add(b / a);
			} else if (s.equals("^")) {
				double a = stack.pop(), b = stack.pop();

				stack.add(Math.pow(b, a));
			} else {
				stack.add(Double.parseDouble(s));
			}
		}
		double x = stack.pop();
		if (x == -0.0) {
			return 0.0;
		}
		return x;
	}

	public ArrayList<String> infixToPostfix() {
		String[] terms = expressionToArray();
		Stack<String> stack = new Stack<String>();
		ArrayList<String> postfix = new ArrayList<String>();
		stack.add("#");
		for (String term : terms) {
			if (term.matches("[.0-9]+") || term.matches("-[.0-9]+")) {
				postfix.add(term);
			} else if (term.equals("(")) {
				stack.add("(");
			} else if (term.equals("^")) {
				stack.add("^");
			} else if (term.equals(")")) {
				while (!stack.peek().equals("#") && !stack.peek().equals("(")) {
					postfix.add(stack.pop());
				}
				stack.pop();
			} else {
				if (getPrecedence(term) > getPrecedence(stack.peek())) {
					stack.add(term);
				} else {
					while (!stack.peek().equals("#") && getPrecedence(term) <= getPrecedence(stack.peek())) {
						postfix.add(stack.pop());
					}
					stack.add(term);
				}
			}
		}
		while (!stack.peek().equals("#")) {
			postfix.add(stack.pop());
		}
		return postfix;
	}

	public String[] expressionToArray() {
		String temp = "";
		for (int i = 0; i < exp.length(); i++) {
			String curr = exp.substring(i, i + 1);
			String next = i < exp.length() - 1 ? exp.substring(i + 1, i + 2) : "";
			String prev = i > 0 ? exp.substring(i - 1, i) : "";
			if (isDigit(curr)) {
				if (next.equals("(")) {
					temp += curr + " *";
				} else {
					temp += curr;
				}
			} else if (curr.equals(".")) {
				temp += ".";
			} else if (curr.equals("-")) {
				if (i == 0 && next.equals("(")) {
					temp += "-1 *";
				} else if (i > 0 && prev.equals("(")) {
					temp += "-";
				} else {
					temp += " - ";
				}
			} else if (curr.equals(")") && next.equals("(")) {
				temp += " ) *";
			} else {
				temp += " " + curr + " ";
			}
		}
		temp = temp.trim().replaceAll("  ", " ");
		return temp.split(" ");
	}

	public int getPrecedence(String x) {
		if (x.matches("[\\+\\-]")) {
			return 1;
		} else if (x.matches("[\\*\\/]")) {
			return 2;
		} else if (x.equals("^")) {
			return 3;
		}
		return 0;
	}

	public boolean isNumber(String x) {
		return x.matches("[.0-9]+") || x.matches("-[.0-9]+");
	}

	// changes vars if needed
	public boolean isVar(String x) {
		return x.matches("[xyt]") || x.matches("-[xyt]");
	}

	public boolean isAllOperator(String x) {
		return x.matches("[\\^\\-\\+\\*\\/]");
	}

	public boolean isOperator(String x) {
		return x.matches("[\\^\\+\\*\\/]");
	}

	public boolean isOtherValidChars(String x) {
		return x.matches("[\\.\\(\\)]");
	}

	public boolean isAlpha(String x) {
		return !(isAllOperator(x) || isDigit(x) || isOtherValidChars(x));
	}

	public boolean isDigit(String x) {
		return x.matches("[0-9]");
	}

	public static void main(String[] args) {
		ExpressionParser eq = new ExpressionParser("8((1+9))");
		System.out.println(eq.evaluatePostfix());
		
		ExpressionParser eq2 = new ExpressionParser("8");
		System.out.println(eq2.evaluatePostfix());
		
		ExpressionParser eq3 = new ExpressionParser("5*2^(2*6)");
		System.out.println(eq3.evaluatePostfix());
	}

}