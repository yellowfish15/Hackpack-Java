import java.util.ArrayList;

public class OverlappingRectangles {

	static class Rectangle {
		
		// (x1, y1) is lower-left corner of rectangle
		// (x2, y2) is upper-right corner of rectangle
		long x1, y1, x2, y2;
		
		public Rectangle(long x1, long y1, long x2, long y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		// get area of rectangle
		public long getArea() {
			return Math.abs((x2-x1)*(y2-y1));
		}
		
		// check if rectangle is just a line
		public boolean isLine() {
			return x1 == x2 || y1 == y2;
		}
		
		public String toString() {
			return "(x1: " + x1 + ", y1: " + y1 + ") (x2: " + x2 + ", y2: " + y2 + ")"; 
		}
		
	}
	
	// returns true if two rectangles intersect
	public static boolean doIntersect(Rectangle a, Rectangle b) {
		// if either rectangle is a line, return false
		if(a.isLine() || b.isLine())
			return false;
		// if one rectangle is on left side of other
		if(a.x1 >= b.x2 || a.x2 <= b.x1)
			return false;
		// if one rectangle is above the other
		if(a.y1 >= b.y2 || a.y2 <= b.y1)
			return false;
		return true;
	}
	
	// return the intersecting rectangle region between rectangle a and b
	// return null if rectangles do not intersect
	public static Rectangle intersection(Rectangle a, Rectangle b) {
		if(!doIntersect(a, b))
			return null;
		long x1 = Math.max(a.x1, b.x1);
		long x2 = Math.min(a.x2, b.x2);
		long y1 = Math.max(a.y1, b.y1);
		long y2 = Math.min(a.y2, b.y2);
		return new Rectangle(x1, y1, x2, y2);
	}
	
	// return the intersecting rectangle region between all rectangles
	// return null if not all rectangles share an intersection
	public static Rectangle intersection(ArrayList<Rectangle> rects) {
		long x1 = Long.MIN_VALUE;
		long x2 = Long.MAX_VALUE;
		long y1 = Long.MIN_VALUE;
		long y2 = Long.MAX_VALUE;
		for(Rectangle r: rects) {
			x1 = Math.max(r.x1, x1);
			x2 = Math.min(r.x2, x2);
			y1 = Math.max(r.y1, y1);
			y2 = Math.min(r.y2, y2);
		}
		if(x1 > x2 || y1 > y2)
			return null;
		return new Rectangle(x1, y1, x2, y2);
	}
	
	// given a list of rectangles, return total area covered by all rectangles
	// uses Principle of Inclusion/Exclusion (PIE)
	public static long getTotalOverlappingArea(ArrayList<Rectangle> rects) {
		// remove all rectangles that are lines
		for(int i = rects.size()-1; i>= 0; i--)
			if(rects.get(i).isLine())
				rects.remove(i);

		// bitmask
		long ans = 0;
		int numSpots = rects.size(); // complexity will be 2^numSpots
		for(int i = 1; i < 1<<numSpots; i++) {
			String conv = String.format("%"+numSpots+"s", Integer.toBinaryString(i)).replace(' ', '0');
			ArrayList<Rectangle> subset = new ArrayList<Rectangle>();
			for(int j = 0; j < numSpots; j++)
				if(conv.charAt(j) == '1')
					subset.add(rects.get(j));
			Rectangle overlap = intersection(subset);
			if(overlap == null)
				continue;
			if(subset.size()%2 == 0)
				ans -= overlap.getArea();
			else
				ans += overlap.getArea();
				
		}
		return ans;
	}
	
	public static void main(String[] args) {
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(new Rectangle (0, 0, 1000000, 1000000));
		rects.add(new Rectangle (100, 100, 101, 101));
		System.out.println(getTotalOverlappingArea(rects));
		
		rects.clear();
		rects.add(new Rectangle (0, 0, 3, 3));
		rects.add(new Rectangle (2, 2, 5, 5));
		System.out.println(getTotalOverlappingArea(rects));
		
		rects.clear();
		rects.add(new Rectangle (0, 0, 1, 1));
		System.out.println(getTotalOverlappingArea(rects));
		
		rects.clear();
		rects.add(new Rectangle (0, 0, 5, 5));
		System.out.println(getTotalOverlappingArea(rects));
		
		
		// best test case
		rects.clear();
		rects.add(new Rectangle (0, 0, 5, 5));
		rects.add(new Rectangle (2, 2, 8, 6));
		rects.add(new Rectangle (3, -2, 7, 4));
		rects.add(new Rectangle (10, 8, 14, 10));
		System.out.println(getTotalOverlappingArea(rects));
	}

}
