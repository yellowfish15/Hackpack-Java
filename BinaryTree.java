import java.util.ArrayList;

public class BinaryTree {

	// binary tree implementation
	static class Tree {
		
		class Node {
			int value;
			Node par, left, right;

			public Node(int value) {
				this.value = value;
				par = left = right = null;
			}
		}

		ArrayList<Node> roots; // stores roots only
		ArrayList<Node> nodes; // stores all nodes

		public Tree() {
			roots = new ArrayList<Node>();
			nodes = new ArrayList<Node>();
		}
		
		public void addNode(Node par, Node left, Node right) {
			
		}

		// implement postorder, preodrder, inorder traversal
		public void printPostOrder(Node node) {
			if (node == null)
				return;
			
			// first recur on left subtree
			printPostOrder(node.left);

			// then recur on right subtree
			printPostOrder(node.right);

			// now deal with the node
			System.out.println(node.value + " ");
		}

		public void printInOrder(Node node) {
			if (node == null)
				return;
			// first recur on left subtree
			printPostOrder(node.left);

			// now deal with the node
			System.out.println(node.value + " ");

			// then recur on right subtree
			printPostOrder(node.right);
		}

		public void printPreOrder(Node node) {
			// now deal with the node
			System.out.println(node.value + " ");

			// first recur on left subtree
			printPostOrder(node.left);

			// then recur on right subtree
			printPostOrder(node.right);
		}

		public void printEulerTour(Node node) {
			// now deal with the node
			System.out.println(node.value + " ");

			// first recur on left subtree
			printPostOrder(node.left);

			// then recur on right subtree
			printPostOrder(node.right);

			// now deal with the node
			System.out.println(node.value + " ");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
