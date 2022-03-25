/*
 * Bitmask Implementation
 * 
 */

public class Bitmask {

	public static void main(String[] args) {
		
		// EXAMPLE #1: Go from repbin to 0
		String bin = "010101000001010100";
		//String bin = "010";
		int repbin = Integer.parseUnsignedInt(bin, 2);
		for(int i = repbin; i >= 0; i--) {
			String conv = String.format("%"+bin.length()+"s", Integer.toBinaryString(i)).replace(' ', '0');
			System.out.println(conv);
		}
		System.out.println();
		
		// EXAMPLE #2: Go from 0 to 2^numSpots
		int numSpots = 10; // complexity will be 2^numSpots
		for(int i = 0; i < 1<<numSpots; i++) {
			String conv = String.format("%"+numSpots+"s", Integer.toBinaryString(i)).replace(' ', '0');
			System.out.println(conv);
		}

	}

}
