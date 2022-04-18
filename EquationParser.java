/*
 * Equation Parser (supports single variable operations):
 * 
 * This implementation supports one variable calculus
 * This includes basic derivation and integration
 * 
 * Not supported: parenthesis, division, multiple variables, division
 * For this functionality, see ExpressionParser.java
 * 
 */

import java.util.*;

public class EquationParser {

	static class Equation {

		ArrayList<Term> terms; // terms in order
		char variable; // variable (typically 'x')

		public Equation(String rawEquation, char variable) {
			this.variable = variable;
			terms = new ArrayList<Term>();

			// remove all white space including leading and trailing whitespace
			rawEquation = rawEquation.replaceAll("\\s", "");
			
			// parse rawEquation
			parse(rawEquation);
		}

		public Equation(ArrayList<Term> terms) {
			this.terms = terms;
		}
		
		// process rawEquation (does not have whitespace) based on specified variable
		// variable should usually be 'x'
		// return true if rawEquation is a valid equation and has been parsed
		// return false if rawEquation is not a valid equation
		private void parse(String rawEquation) {
			int pos1 = 0;
			for (int pos2 = 1; pos2 < rawEquation.length(); pos2++) {
				char curr = rawEquation.charAt(pos2);
				if (curr == '+' || curr == '-') {
					terms.add(new Term(rawEquation.substring(pos1, pos2), variable));
					pos1 = pos2;
				}
			}
			terms.add(new Term(rawEquation.substring(pos1), variable));
		}

		// evaluate this equation at variable=value
		public double evaluateAtValue(double value) {
			double ans = 0;
			for (Term t : terms)
				ans += t.evaluateAtValue(value);
			return ans;
		}

		// return the equation that is the derivative of this equation
		public Equation getDerivative() {
			ArrayList<Term> newTerms = new ArrayList<Term>();
			
			for (Term t : terms) {
				if (t.isConstant())
					continue;
				newTerms.add(t.derive());
			}
			return new Equation(newTerms);
		}

		// return the equation that is the derivative of this equation
		public Equation getIntegral() {
			ArrayList<Term> newTerms = new ArrayList<Term>();
			for (Term t : terms)
				newTerms.add(t.integrate());
			return new Equation(newTerms);
		}

		public String toString() {
			if(terms.isEmpty())
				return "0";
			
			Collections.sort(terms);
			int count = 0;
			
			StringBuilder stb = new StringBuilder();
			for(Term t: terms) {
				String curr = t.toString();
				if(count == 0)
					stb.append(curr);
				else {
					if(curr.charAt(0) == '-')
						stb.append(" - " + curr.substring(1));
					else
						stb.append(" + " + curr);
				}
				count++;
			}
			return stb.toString();
		}
		
		// each term of equation
		// in the form coefficient * variable ^ exponent
		private class Term implements Comparable<Term> {
			private double coefficient = 1, exponent = 1;

			// build term based on coefficient and exponent
			public Term(double coefficient, double exponent) {
				this.coefficient = coefficient;
				this.exponent = exponent;
			}

			// build term based on string, given variable
			// Example: 5x^4, 5.0, 6*x, 9.3x^3.2
			public Term(String str, char variable) {
				if(str.charAt(0) == '+')
					str = str.substring(1);
				String[] half = str.split(variable + "");
				
				// just a single variable
				if (half.length == 0)
					return;
				
				// there is a coefficient
				if (half.length > 1) {
					if (half[0].length() > 0)
						coefficient = parseMult(half[0]);
					exponent = parseMult(half[1].substring(1));
				}
				// there is either only a coefficient or an exponent
				else {
					if (half[0].charAt(0) == '^') { // just an exponent
						exponent = parseMult(half[0].substring(1));
						coefficient = 1;
					} else if (half.length != 0) {
						exponent = str.charAt(str.length() - 1) == variable ? 1 : 0;
						coefficient = parseMult(half[0]);
					}
				}
			}

			// parse a string with multiplication symbols such as 5*42
			private static double parseMult(String str) {
				double ret = 1;
				for (String num : str.split("\\*"))
					ret *= Double.parseDouble(num);
				return ret;
			}

			// returns true if this term is a constant
			public boolean isConstant() {
				return exponent == 0;
			}

			// evaluate term when variable = value
			public double evaluateAtValue(double value) {
				if (exponent == 0)
					return coefficient;
				return coefficient * (Math.pow(value, exponent));
			}

			// return the derivative of this term
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
				// uncomment this out to print as integers
				//int c = (int)(coefficient);
				//int e = (int)(exponent);
				
				double c = coefficient;
				double e = exponent;
				
				String ret = "";
				if(e == 0 || Math.abs(c) != 1)
					ret = c+"";
				else if(c == -1)
					ret = "-";
				if(e > 0)
					ret += variable;
				if(e > 1)
					ret += "^"  + e;
				return ret;
			}

			// sorts terms based on exponent in descending order
			// if exponents are the same, sort by coefficient in ascending order
			@Override
			public int compareTo(Term oth) {
				if (oth.exponent > exponent)
					return 1;
				if (exponent > oth.exponent)
					return -1;
				if (oth.coefficient > coefficient)
					return -1;
				if (coefficient > oth.coefficient)
					return 1;
				return 0;
			}
		}

	}

	public static void main(String[] args) {
		Equation eq = new Equation("1.0x^5 +2.2x^3+3.23x^4 + 4- 3.2x", 'x');
		System.out.println(eq.terms);
		System.out.println(eq);
		System.out.println("Value when x=5.2: " + eq.evaluateAtValue(5.2));
		System.out.println("Original Equation: " + eq);
		System.out.println("Derivative: " + eq.getDerivative());
		System.out.println("Integral: " + eq.getIntegral() + " + C");
		System.out.println();

		Equation eq2 = new Equation("1259759148122142127141294124912x", 'x');
		System.out.println(eq2.terms);
		System.out.println(eq2);
		System.out.println("Value when x=3.0: " + eq2.evaluateAtValue(3.));
		System.out.println("Original Equation: " + eq2);
		System.out.println("Derivative: " + eq2.getDerivative());
		System.out.println("Integral: " + eq2.getIntegral() + " + C");
		System.out.println();
		
		Equation eq3 = new Equation("x", 'x');
		System.out.println(eq3.terms);
		System.out.println(eq3);
		System.out.println("Value when x=3.0: " + eq3.evaluateAtValue(3.));
		System.out.println("Original Equation: " + eq3);
		System.out.println("Derivative: " + eq3.getDerivative());
		System.out.println("Integral: " + eq3.getIntegral() + " + C");
		System.out.println();
		
		Equation eq4 = new Equation("X^4 + X^3 + X^2 + X", 'X');
		System.out.println(eq4.terms);
		System.out.println(eq4);
		System.out.println("Value when x=3.0: " + eq4.evaluateAtValue(3.));
		System.out.println("Original Equation: " + eq4);
		System.out.println("Derivative: " + eq4.getDerivative());
		System.out.println("Integral: " + eq4.getIntegral() + " + C");
		System.out.println();
	}
}
