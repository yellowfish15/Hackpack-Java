import java.io.*;

class LineSegment {

	// (x1, y1) and (x2, y2) are end-points of this line segment
	double x1, x2, y1, y2, s;
	boolean isVertical = false;

	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		// (x1, y1) will always be left of (x2, y2) if line is not vertical
		if (x1 > x2) {
			this.x1 = x2;
			this.x2 = x1;
			this.y1 = y2;
			this.y2 = y1;
		}
		if (x2 == x1) {
			s = Double.POSITIVE_INFINITY;
			isVertical = true;
		} else {
			s = (y2 - y1) / (x2 - x1);
		}
	}

	// check if a point is in the rectangle formed by this line segment
	public boolean pointInSegment(double x, double y) {
		return x >= x1 && y >= Math.min(y1, y2) && x <= x2 && y <= Math.max(y1, y2);
	}

	// check if point is on this line segment exactly
	public boolean pointOnLine(double x, double y) {
		return y == (s)*(x-x1)+y1;
	}
	
	// check if this line segment intersects line segment oth
	public boolean doIntersect(LineSegment oth) {
		if (s == oth.s || (isVertical && oth.isVertical))
			return false;

		if (this.isVertical)
			return oth.x1 <= x1 && oth.x2 >= x1 && Math.max(y1, y2) >= Math.max(oth.y1, oth.y2)
					&& Math.min(y1, y2) <= Math.min(oth.y1, oth.y2);
		else if (oth.isVertical)
			return x1 <= oth.x1 && x2 >= oth.x1 && Math.max(oth.y1, oth.y2) >= Math.max(y1, y2)
					&& Math.min(oth.y1, oth.y2) <= Math.min(y1, y2);

		double x = (oth.y1 - y1 + s * x1 - oth.s * oth.x1) / (s - oth.s);
		double y = y1 + s * (x - x1);
		return pointInSegment(x, y) && oth.pointInSegment(x, y);
	}

	public static void main(String[] args) throws IOException {

		LineSegment seg1 = new LineSegment(0, 0, 2, 3);
		LineSegment seg2 = new LineSegment(0, 0, 4, 2);
		LineSegment seg3 = new LineSegment(0, 4, 3, 1);
		System.out.println(seg2.doIntersect(seg3));
		System.out.println(seg1.doIntersect(seg3));

	}
}