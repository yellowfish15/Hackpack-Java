/*
 * Boolean Algebra Expression Parser:
 * 
 * Given some input expression, evaluate the boolean value
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class ExpressionBooleanAlgebra {

	static class BooleanExpression {

		String exp;
		HashMap<Character, Boolean> vars; // maps variable character to its value
		ArrayList<Character> postfixEq; // stores the expression parsed in post-fix format

		public BooleanExpression(String rawExpression) {
			this.exp = rawExpression.replaceAll(" ", "");
			vars = new HashMap<Character, Boolean>();
			postfixEq = new ArrayList<Character>();

			parse();
		}

		// replace a variable with a value
		// Example: replace 'A' with true
		public void setVariable(char variable, boolean value) {
			vars.put(variable, value);
		}

		// return boolean value of expression
		public boolean evaluate() {
			Deque<Boolean> stack = new LinkedList<Boolean>();
			for (Character c : postfixEq) {
				if (c == '+') {
					boolean a = stack.pop(), b = stack.pop();
					stack.push(a | b);
				} else if (c == '*') {
					boolean a = stack.pop(), b = stack.pop();
					stack.push(a & b);
				} else if (c == '^') {
					boolean a = stack.pop(), b = stack.pop();
					stack.push(a ^ b);
				} else if (c == '!') {
					boolean a = stack.pop();
					stack.push(!a);
				} else
					stack.push(vars.get(c));
			}
			return stack.pop();
		}

		// parses the in-fix expression into post-fix form
		private void parse() {
			Deque<Character> s = new LinkedList<Character>();
			for (int i = 0; i < exp.length(); i++) {
				char c = exp.charAt(i);
				if (c == '(')
					s.push(c);
				else if (c == ')') {
					// pop and append all operators until the "(" is encountered
					while (!s.isEmpty() && s.peek() != '(')
						postfixEq.add(s.pop());
					// pop the "("
					s.pop();
				} else if (c == '!' || c == '+' || c == '*' || c == '^') {
					// pop and append each operator that has greater or
					// equal precedence than the current operator
					while (!s.isEmpty() && precedence(s.peek()) >= precedence(c))
						postfixEq.add(s.pop());
					// push the current operator
					s.push(c);
				} else
					postfixEq.add(c);
			}
			// pop and append all remaining operators
			while (!s.isEmpty())
				postfixEq.add(s.pop());
		}

		private int precedence(char p) {
			if (p == '!')
				return 4;
			if (p == '*')
				return 3;
			if (p == '^')
				return 2;
			if (p == '+')
				return 1;
			return 0;
		}

	}

	public static void main(String[] args) {
		BooleanExpression exp1 = new BooleanExpression("A+B");
		exp1.setVariable('A', true);
		exp1.setVariable('B', true);
		System.out.println(exp1.evaluate());
		
		BooleanExpression exp2 = new BooleanExpression("(A+B)^(A+C)*(!A+C)");
		exp2.setVariable('A', true);
		exp2.setVariable('B', true);
		exp2.setVariable('C', true);
		System.out.println(exp2.evaluate());
		
		BooleanExpression exp3= new BooleanExpression("A*B+!C");
		exp3.setVariable('A', false);
		exp3.setVariable('B', false);
		exp3.setVariable('C', false);
		System.out.println(exp3.evaluate());
		
		BooleanExpression exp4= new BooleanExpression("(A+B)*!(A+B)");
		exp4.setVariable('A', false);
		exp4.setVariable('B', true);
		exp4.setVariable('C', true);
		System.out.println(exp4.evaluate());
		
		BooleanExpression exp5= new BooleanExpression("(!A*B)^(B+C)");
		exp5.setVariable('A', false);
		exp5.setVariable('B', false);
		exp5.setVariable('C', true);
		System.out.println(exp5.evaluate());
		
	}

}
