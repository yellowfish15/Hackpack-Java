/*
 * Expression Parser: 
 * 
 * Parse and evaluate any arithmetic equation with multiple variables
 * 
 * Not supported: calculus 
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ExpressionParser {

	static class Expression {

		String exp; // infix expression in String form
		HashMap<Character, Double> vars; // maps variable character to its value
		ArrayList<String> postfixEq; // stores the expression parsed in post-fix format

		public Expression(String rawEquation) {
			this.exp = rawEquation.replaceAll(" ", "");
			vars = new HashMap<Character, Double>();
			postfixEq = new ArrayList<String>();

			parse();
		}

		// replace a variable with a value
		// Example: replace x with 5.24
		public void setVariable(char variable, double value) {
			vars.put(variable, value);
		}

		// returns the value of this expression
		public double evaluatePostfix() {
			Stack<Double> stack = new Stack<Double>();
			for (String s : postfixEq) {
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
					if(a == 0) 
						return Double.NEGATIVE_INFINITY;
					stack.add(b / a);
				} else if (s.equals("^")) {
					double a = stack.pop(), b = stack.pop();
					stack.add(Math.pow(b, a));
				} else {
					try {
						stack.add(Double.parseDouble(s));
					} catch (NumberFormatException e) {
						if (s.charAt(0) == '-')
							stack.add(-vars.get(s.charAt(1)));
						else
							stack.add(vars.get(s.charAt(0)));
					}
				}
			}
			double x = stack.pop();
			if (x == -0.0) {
				return 0.0;
			}
			return x;
		}

		// converts infix expression into post-fix
		private void parse() {
			String[] terms = expressionToArray();
			Stack<String> stack = new Stack<String>();
			stack.push("#");
			for (String term : terms) {
				if (isNumber(term) || isVariable(term)) {
					postfixEq.add(term);
				} else if (term.equals("(")) {
					stack.add("(");
				} else if (term.equals("^")) {
					stack.add("^");
				} else if (term.equals(")")) {
					while (!stack.peek().equals("#") && !stack.peek().equals("(")) {
						postfixEq.add(stack.pop());
					}
					stack.pop();
				} else {
					if (getPrecedence(term) > getPrecedence(stack.peek())) {
						stack.add(term);
					} else {
						while (!stack.peek().equals("#") && getPrecedence(term) <= getPrecedence(stack.peek())) {
							postfixEq.add(stack.pop());
						}
						stack.add(term);
					}
				}
			}
			while (!stack.peek().equals("#")) {
				postfixEq.add(stack.pop());
			}
		}

		private String[] expressionToArray() {
			StringBuilder temp = new StringBuilder();
			for (int i = 0; i < exp.length(); i++) {
				String curr = exp.substring(i, i + 1);
				String next = i < exp.length() - 1 ? exp.substring(i + 1, i + 2) : "";
				String prev = i > 0 ? exp.substring(i - 1, i) : "";
				if (isLetter(curr)) {
					if (prev.equals("-"))
						temp.append(curr).append(" ");
					else
						temp.append(" ").append(curr).append(" ");
					if (isDigit(next) || isLetter(next))
						temp.append("*");
				} else if (isDigit(curr)) {
					if (next.equals("(") || isLetter(next)) {
						temp.append(curr).append(" * ");
					} else {
						temp.append(curr);
					}
				} else if (curr.equals(".")) {
					temp.append(".");
				} else if (curr.equals("-")) {
					if (i == 0 && next.equals("(")) {
						temp.append("-1 *");
					} else if (i > 0 && prev.equals("(")) {
						temp.append("-");
					} else if (i == 0 || i > 0 && isOperator(prev)) {
						temp.append(" -");
					} else {
						temp.append(" - ");
					}
				} else if (curr.equals(")") && next.equals("(")) {
					temp.append(" ) *");
				} else {
					temp.append(" ").append(curr).append(" ");
				}
			}
			String ret = temp.toString();
			ret = ret.trim().replaceAll("[ \t]+", " ");
			return ret.split(" ");
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

		public boolean isVariable(String x) {
			return x.matches("[a-zA-Z]") || x.matches("-[a-zA-Z]");
		}

		public boolean isLetter(String x) {
			return x.matches("[a-zA-Z]");
		}
	}

	public static void main(String[] args) {
		Expression eq = new Expression("8((1+9))");
		System.out.println(eq.evaluatePostfix());

		Expression eq2 = new Expression("8");
		System.out.println(eq2.evaluatePostfix());

		Expression eq3 = new Expression("5*2^(2*6)");
		System.out.println(eq3.evaluatePostfix());

		Expression eq4 = new Expression("52xw+x^2");
		eq4.setVariable('x', 5.2);
		eq4.setVariable('w', 2.322);
		System.out.println(eq4.evaluatePostfix());

		Expression eq5 = new Expression("-x - 2y - t");
		eq5.setVariable('x', 1);
		eq5.setVariable('y', 2);
		eq5.setVariable('t', 5);
		System.out.println(eq5.evaluatePostfix());
	}

}