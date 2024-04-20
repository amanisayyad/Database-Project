
public class TNode<T extends Comparable<T>> {
	T data;
	TNode<T> left;
	TNode<T> right;
	int height;

	public TNode(T data) {
		this.data = data;
	}

	public TNode() {

	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public TNode<T> getLeft() {
		return left;
	}

	public void setLeft(TNode<T> left) {
		this.left = left;
	}

	public TNode<T> getRight() {
		return right;
	}

	public void setRight(TNode<T> right) {
		this.right = right;
	}

	public boolean isLeaf() {
		return (left == null && right == null);
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	public int getHeight() {
		return height;
	}

	public boolean isEmpty() {
		return left == null && right == null;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String toString() {
		return "[" + data + "]";
	}

}