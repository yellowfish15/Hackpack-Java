import java.util.ArrayList;

public class BinaryTree {

	static class Node implements Comparable<Node>{
		// each unique node has a unique id
		// different nodes could have the same values
		int id, value;
		Node par, left, right;

		public Node(int id) {
			this.id = id;
			value = id; // value is set to id by default
			par = left = right = null;
		}
		
		public Node(int id, int value) {
			this(id);
			this.value = value;
		}
		
		public Node(int id, int value, Node left, Node right) {
			this(id, value);
			this.left = left;
			this.right = right;
		}

		public boolean equals(Node oth) {
			return this.id == oth.id;
		}

		@Override
		public int hashCode() {
			return this.id;
		}

		public String toString() {
			String ret = "(Node #: " + this.id + ", Value: " + this.value;
			if(par != null)
				ret += ", Parent #: " + par.id;
			else
				ret += ", Parent #: null";
			if(left != null)
				ret += ", Left #: " + left.id;
			else
				ret += ", Left #: null";
			if(right != null)
				ret += ", Right #: " + right.id;
			else
				ret += ", Right #: null";
			return ret + ")\n";
		}

		// sort nodes by id
		@Override
		public int compareTo(Node oth) {
			return this.id-oth.id;
		}

	}
	
	// binary tree implementation
	static class Tree {

		ArrayList<Node> roots; // stores roots only
		ArrayList<Node> nodes; // stores all nodes

		public Tree() {
			roots = new ArrayList<Node>();
			nodes = new ArrayList<Node>();
		}

		// find node in given ArrayList and merge it with n
		// flag meaning:
		// flag==0: keep node in list if found, do not add it if it is not found
		// flag==1: keep node in list if found, add it if it is not found
		// flag==2: remove node in list if found, do not add it if it is not found
		public Node merge(Node node, ArrayList<Node> list, int flag) {
			boolean found = false;
			for(int i = list.size()-1; i >= 0; i--) {
				Node n = list.get(i);
				if(n.id == node.id) {
					// always take newer value if it exists
					n.left = node.left==null?n.left:node.left;
					n.right = node.right==null?n.right:node.right;
					n.par = node.par==null?n.par:node.par;
					node = n;
					list.remove(i);
					found = true;
					break;
				}
			}
			if(flag == 1) {
				list.add(node);
			} else if(flag == 0 && found) {
				list.add(node);
			}
			return node;
		}
		
		// if you are only adding one node just pass in (node, null, null)
		public void addNode(Node par, Node left, Node right) {

			if (par == null)
				return;

			if (left != null) {
				left.par = par;
				left = merge(left, nodes, 1);
				left = merge(left, roots, 2);
				par.left = left;
			}

			if (right != null) {
				right.par = par;
				right = merge(right, nodes, 1);
				right = merge(right, roots, 2);
				par.right = right;
			}

			par = merge(par, nodes, 1);

			if (par.par == null)
				par = merge(par, roots, 1);

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
			System.out.print(node.value + " ");
		}

		public void printInOrder(Node node) {
			if (node == null)
				return;
			
			// first recur on left subtree
			printInOrder(node.left);

			// now deal with the node
			System.out.print(node.value + " ");

			// then recur on right subtree
			printInOrder(node.right);
		}

		public void printPreOrder(Node node) {
			if(node == null)
				return;
			
			// now deal with the node
			System.out.print(node.value + " ");

			// first recur on left subtree
			printPreOrder(node.left);

			// then recur on right subtree
			printPreOrder(node.right);
		}
		
		public String toString() {
			return "Roots: " + roots.toString() + "\nNodes: " + nodes.toString();
		}
	}

	public static void main(String[] args) {

		Tree tree = new Tree();
		tree.addNode(new Node(1), new Node(2), null);
		tree.addNode(new Node(5), null, new Node(1));
		tree.addNode(new Node(1), null, new Node(3));
		
		System.out.println(tree);
		System.out.println();
		
		System.out.print("Pre Order: ");
		tree.printPreOrder(tree.roots.get(0));
		System.out.print("\nPost Order: ");
		tree.printPostOrder(tree.roots.get(0));
		System.out.print("\nIn Order: ");
		tree.printInOrder(tree.roots.get(0));
	}

}
