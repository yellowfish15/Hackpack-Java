/*
 * Fraction Class:
 * 
 * Convert floating-point number into two-part fraction
 * Parse string into two-part fraction
 * 
 * Take reciprocal of fraction
 * Conduct operations on fractions (+, -, *, /)
 * 
 */

class Fraction {

	int numerator, denominator;

	// convert floating-point number to fraction
	public Fraction(double d) {
		parseString(convertDecimalToFraction(d));

	}

	public Fraction(int numerator, int denominator) {
		if (denominator < 0) {
			numerator *= -1;
			denominator = -denominator;
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public Fraction(String input) {
		parseString(input);
	}

	// Parse input string and convert it into two-part fraction
	// Possible inputs (all use whole numbers):
	// 5/12, -4/4, -6/2, 5, -5, 5 2/7
	public void parseString(String input) {
		input = input.trim();
		String[] tokens = input.split(" "); // checks for mixed numbers
		if (tokens.length == 1) { // either proper/improper fraction or whole number
			String[] parts = tokens[0].split("/");
			if (parts.length == 1) {
				numerator = Integer.parseInt(parts[0]);
				denominator = 1;
			} else {
				numerator = Integer.parseInt(parts[0]);
				denominator = Integer.parseInt(parts[1]);
			}
		} else {
			int whole = Integer.parseInt(tokens[0]);
			String[] parts = tokens[1].split("/");
			denominator = Integer.parseInt(parts[1]);
			numerator = Math.abs(whole) * denominator + Integer.parseInt(parts[0]);
			if (whole < 0) {
				numerator = -numerator;
			}
		}
	}

	// converts a decimal d into a fractional string
	public String convertDecimalToFraction(double num) {
		if (num < 0) {
			return "-" + convertDecimalToFraction(-num);
		}
		double tolerance = 1.0E-6;
		double h1 = 1;
		double h2 = 0;
		double k1 = 0;
		double k2 = 1;
		double b = num;
		do {
			double a = Math.floor(b);
			double aux = h1;
			h1 = a * h1 + h2;
			h2 = aux;
			aux = k1;
			k1 = a * k1 + k2;
			k2 = aux;
			b = 1 / (b - a);
		} while (Math.abs(num - h1 / k1) > num * tolerance);

		int n = (int)(h1);
		int d = (int)(k1);
		return n + "/" + d;
	}

	// find greatest common divisor of a and b
	public int GCD(int a, int b) {
		while (b != 0) {
			a %= b;
			int temp = a;
			a = b;
			b = temp;
		}
		return a;
	}

	// simplify this fraction
	public void simplify() {
		int gcd = Math.abs(GCD(numerator, denominator));
		numerator /= gcd;
		denominator /= gcd;
	}

	public Fraction getReciprocal() {
		Fraction ret = new Fraction(denominator, numerator);
		ret.simplify();
		return ret;
	}

	// return (this fraction) + (other fraction)
	public Fraction add(Fraction oth) {
		int newNum = numerator * oth.denominator + oth.numerator * denominator;
		int newDen = denominator * oth.denominator;
		Fraction ret = new Fraction(newNum, newDen);
		ret.simplify();
		return ret;
	}

	// return (this fraction) - (other fraction)
	public Fraction subtract(Fraction oth) {
		int newNum = numerator * oth.denominator - oth.numerator * denominator;
		int newDen = denominator * oth.denominator;
		Fraction ret = new Fraction(newNum, newDen);
		ret.simplify();
		return ret;
	}

	// return (this fraction) * (other fraction)
	public Fraction multiply(Fraction oth) {
		int newNum = numerator * oth.numerator;
		int newDen = denominator * oth.denominator;
		Fraction ret = new Fraction(newNum, newDen);
		ret.simplify();
		return ret;
	}

	// return (this fraction) * 1/(other fraction)
	public Fraction divide(Fraction oth) {
		return multiply(oth.getReciprocal());
	}

	// return whole number without denominator if denominator equals 1
	// return proper fraction if numerator < denominator
	// return mixed number otherwise
	public String toString() {
		simplify();
		// return whole number
		if (denominator == 1)
			return numerator + "";

		// return just the proper/improper fraction
		// return numerator + "/" + denominator;

		// return proper fraction
		if (Math.abs(numerator) < denominator)
			return numerator + "/" + denominator;

		// return mixed number
		int whole = numerator / denominator;
		int reducedNum = Math.abs(numerator) % denominator;
		return whole + " " + reducedNum + "/" + denominator;
	}

	public static void main(String[] args) {
		Fraction f1 = new Fraction("-5 1/12");
		Fraction f2 = new Fraction("2/7");
		Fraction f3 = new Fraction("14");
		Fraction f4 = new Fraction("-13");
		Fraction f5 = new Fraction(0.125);
		Fraction f6 = new Fraction(0.33333333333);

		Fraction[] fracs = { f1, f2, f3, f4, f5, f6 };

		// print out fractions
		for (Fraction frac : fracs)
			System.out.println(frac);
		System.out.println();

		// print out reciprocals of all fractions
		for (Fraction frac : fracs)
			System.out.println("Reciprocal of " + frac + " = " + frac.getReciprocal());
		System.out.println();

		// print out all fractions added with each other
		for (int i = 0; i < fracs.length; i++)
			for (int j = i + 1; j < fracs.length; j++)
				System.out.println(fracs[i] + " + " + fracs[j] + " = " + fracs[i].add(fracs[j]));
		System.out.println();

		// print out all fractions subtracted with each other
		for (int i = 0; i < fracs.length; i++)
			for (int j = i + 1; j < fracs.length; j++)
				System.out.println(fracs[i] + " - " + fracs[j] + " = " + fracs[i].subtract(fracs[j]));
		System.out.println();

		// print out all fractions multiplied with each other
		for (int i = 0; i < fracs.length; i++)
			for (int j = i + 1; j < fracs.length; j++)
				System.out.println(fracs[i] + " x " + fracs[j] + " = " + fracs[i].multiply(fracs[j]));
		System.out.println();

		// print out all fractions divided with each other
		for (int i = 0; i < fracs.length; i++)
			for (int j = i + 1; j < fracs.length; j++)
				System.out.println(fracs[i] + " ÷ " + fracs[j] + " = " + fracs[i].divide(fracs[j]));

	}

}
