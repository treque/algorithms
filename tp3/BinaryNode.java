import java.util.List;

public class BinaryNode<T extends Comparable<? super T> > {
    private T data;
    private BinaryNode<T> right;
    private BinaryNode<T> left;

    // O(1)
    public BinaryNode(T data) {
    	this.right = this.left = null;
    	this.data = data;
    }

    // O(1)
    public T getData() {
    	
        return this.data;
    }

    // TODO: on ajoute une nouvelle donnee au bon endroit
    // O(log(n))
    public void insert(T item) {
    	if (item.compareTo(this.data) <= 0) {
    		if (this.left == null) {
    			this.left.insert(item);
    		}
    		this.left = new BinaryNode<T>(item);
    	}
		else {
			if (this.right == null) {
				this.right.insert(item);
			}
			this.right = new BinaryNode<T>(item);
		}
    }

    // O(log(n)) 
    public boolean contains(T item) {
    	// We have to declare a boolean variable instead of returning false. Otherwise, recursion would not work.
    	boolean found = false;
    	if (this.data == item) {
    		return true;
    	}
    	else if (item.compareTo(this.data) <= 0 && this.left != null) {
    		found = this.left.contains(item);
    	}
    	else if (item.compareTo(this.data) > 0 && this.right != null) {
    		found = this.right.contains(item);
    	}
    	return found;
    }

    // O(n)
    public int getHeight() {
        if (this.left == null && this.right == null) {
        	return 0;
        }
        else if (this.left == null) {
        	return 1 + this.right.getHeight();
        }
        else if (this.right == null) {
        	return 1 + this.left.getHeight();
        }
        return 1 + Math.max(this.right.getHeight(), this.left.getHeight());
    }

    // TODO: l'ordre d'insertion dans la liste est l'ordre logique
    // de manière que le plus petit item sera le premier inseré
    // O(n)
    public void fillListInOrder(List<BinaryNode<T>> result) {
    	if (this == null) {
    		return;
    	}	
    	// FIND A FIXXXXXXXXXXXXX
    	this.left.fillListInOrder(result);
    	result.add(this);
    	this.right.fillListInOrder(result);
    }
}
