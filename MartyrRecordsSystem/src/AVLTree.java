
public class AVLTree<T extends Comparable<T>> {
	TNode<T> root;

	private TNode<T> rotateRight(TNode<T> N) {
		TNode<T> nodeC = N.left;
		N.setLeft(nodeC.getLeft());
		nodeC.setRight(N);
		return nodeC;
	}

	private TNode<T> rotateLeft(TNode<T> N) {
		TNode<T> nodeC = N.right;
		N.setRight(nodeC.getLeft());
		nodeC.setLeft(N);
		return nodeC;
	}

	private TNode<T> rotateLeftRight(TNode<T> N) {
		TNode<T> nodeC = N.left;
		N.left = rotateLeft(nodeC);
		return rotateRight(N);
	}

	private TNode<T> rotateRightLeft(TNode<T> N) {
		TNode<T> nodeC = N.right;
		N.right = rotateRight(nodeC);
		return rotateLeft(N);
	}

//	private void updateHeight(TNode<T> n) {
//		n.height = 1 + Math.max(height(n.left), height(n.right));
//
//	}

	public int height() {
		if (root == null)
			return 0;
		return height(root);
	}

	private int height(TNode<T> n) {
		if (n == null)
			return 0;
		int leftHeight = height(n.left);
		int rightHeight = height(n.right);
		return 1 + Math.max(leftHeight, rightHeight);
	}

	public int getBalance(TNode<T> n) {
		return (n == null) ? 0 : height(n.left) - height(n.right);
	}

	private TNode<T> rebalance(TNode<T> nodeN) {
		int diff = getBalance(nodeN);
		if (diff > 1) { // addition was in node's left subtree
			if (getBalance(nodeN.left) > 0)
				nodeN = rotateRight(nodeN);
			else
				nodeN = rotateLeftRight(nodeN);
		} else if (diff < -1) { // addition was in node's right subtree
			if (getBalance(nodeN.right) < 0)
				nodeN = rotateLeft(nodeN);
			else
				nodeN = rotateRightLeft(nodeN);
		}
		return nodeN;
	}

	public void insert(T data) {
		if (root == null) {
			root = new TNode<>(data);
		} else {
			TNode<T> rootNode = root;
			addEntry(data, rootNode);
			root = rebalance(rootNode);
		}
	}

	public void addEntry(T data, TNode<T> rootNode) {
		assert rootNode != null;// if statement,if root is null don't continue
								// the code
		if (data.compareTo((T) rootNode.data) < 0) { // right into left subtree
			if (rootNode.hasLeft()) {
				TNode<T> leftChild = rootNode.left;
				addEntry(data, leftChild);
				rootNode.left = rebalance(leftChild);
			} else
				rootNode.left = new TNode<T>(data);
		} else { // right into right subtree
			if (rootNode.hasRight()) {
				TNode<T> rightChild = rootNode.right;
				addEntry(data, rightChild);
				rootNode.right = rebalance(rightChild);
			} else
				rootNode.right = new TNode<T>(data);

		}
	}

	public TNode<T> delete(T data) {
		TNode<T> temp = delete(data);
		if (temp != null) {
			TNode<T> rootNode = root;
			root = rebalance(rootNode);
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public T find(T data) {
		return (T) find(data, root);
	}

	public TNode<T> find(T data, TNode<T> node) {
		if (node != null) {
			int comp = node.data.compareTo(data);
			if (comp == 0)
				return node;
			else if (comp > 0 && node.hasLeft())
				return find(data, node.left);
			else if (comp < 0 && node.hasRight())
				return find(data, node.right);
		}
		return null;
	}

}