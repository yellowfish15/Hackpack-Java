/*
 * Significant Digits file
 * Get # of significant digits in a number
 * Get number in significant digit format
 * 
 */

import java.math.BigDecimal;

public class SignificantDigits {

	// count the number of significant digits in string s
	// assuming trailing zeroes on a number w/out decimals are not significant
	// assuming trailing zeroes to right of decimal are significant
	public static int numsigdigs(String s) {
		int sigdigs = 0;
		int sigzeroes = 0;
		boolean pastdecimal = false;
		if (s.charAt(0) == '-')
			s = s.substring(1);// Ignore a negative sign.
		for (int i = 0; i < s.length(); i++)// For each digit.
			if (s.charAt(i) == '.')// Determine if we are past the decimal place and don't count anything.
				pastdecimal = true;
			else if (s.charAt(i) != '0')// If this digit is not zero.
			{
				sigdigs++;
				sigdigs += sigzeroes;// Add any zeroes that are significant digits.
				sigzeroes = 0;// Reset the number of zeroes.
			} else if (sigdigs >= 1)// Count the zeroes we have encountered so far IF they are significant.
				sigzeroes++;
		if (pastdecimal)// Any zero to the right of the decimal is a significant digit.
			sigdigs += sigzeroes;
		return sigdigs;
	}
	
	// given a double, return the number in significant figure string form
	public static String rounded(double d,int sigdigs) {
		boolean negative=false;
		if(d<0) {
			d=-d;
			negative=true;
		}
		int digitsleftofdecimal;
		double tempd=d;
		for(digitsleftofdecimal=0;tempd>=1;digitsleftofdecimal++) 
			tempd/=10;
		int zeroesrightofdecimal=0;
		if(d<1)
			for(zeroesrightofdecimal=0;tempd<1;zeroesrightofdecimal++)
				tempd*=10;
		tempd=d;
		int shift=tempd>1?sigdigs-digitsleftofdecimal:sigdigs+zeroesrightofdecimal-1;
		tempd=tempd*Math.pow(10, shift);
		tempd=Math.round(tempd);
		tempd=tempd/Math.pow(10, shift);
		String s=BigDecimal.valueOf(tempd).toPlainString();
		String scinot="";
		int count=0;
		if(negative) scinot+='-';
		if(tempd<1) {
			int firstnonzerodig=0;
			for(int m=0;m<s.length();m++)
				if(s.charAt(m)>'0'&&s.charAt(m)<='9') {
					firstnonzerodig=m;
					break;
				}
			s=s.substring(firstnonzerodig);
		}
		else
			if(s.indexOf(".")>0)
				s=s.substring(0, s.indexOf("."))+s.substring(s.indexOf(".")+1);
		if(sigdigs==1)
			scinot+=s.charAt(count++);
		else
			scinot+=s.charAt(count++)+".";
		for(int x=count;x<sigdigs;x++)
			scinot+=s.substring(x, x+1);
		if(tempd>=1)
			scinot+="E"+(digitsleftofdecimal-1);
		else
			scinot+="E"+(-zeroesrightofdecimal);
		return scinot;
}

	public static void main(String[] args) {
		System.out.println(numsigdigs("3.566000"));
		System.out.println(rounded(5.349, 2)); // print 5.349 to two significant digits
	}

}
