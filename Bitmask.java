
public class Bitmask {

	public static void main(String[] args) {
		
		String bin = "010101000001010100";
		//String bin = "010";
		int repbin = Integer.parseUnsignedInt(bin, 2);
		for(int i = repbin; i >= 0; i--) {
			String conv = Integer.toBinaryString(i);
			for(int j = 0; j < bin.length() - conv.length(); j++)
				System.out.print("0");
			System.out.println(conv);
		}

	}

}
