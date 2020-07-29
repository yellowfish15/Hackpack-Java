/*
 * Binary Indexed (Fenwick) Tree:
 * 
 * Below is an implementation of a BIT sum
 * 
 */

class BIT {

	int[] tree;

	public BIT(int N) {
		tree = new int[N + 1];
	}

	int getSum(int index) {
		int sum = 0;
		index++;
		while (index > 0) {
			sum += tree[index];
			index -= index & (-index);
		}
		return sum;
	}

	int getSum(int a, int b) {
		return getSum(b) - getSum(a - 1);
	}

	void update(int index, int val) {
		index++;
		while (index < tree.length) {
			tree[index] += val;
			index += index & (-index);
		}
	}

	public static void main(String[] args) {

		BIT b = new BIT(5);
		b.update(0, 2);
		b.update(1, 3);
		System.out.println(b.getSum(2));

	}

}
