import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BinarySearchTree<T extends Comparable<? super T> > {

    private BinaryNode<T> root;

    public BinarySearchTree() { }

    public BinarySearchTree(T item) {
    	BinaryNode<T> node = new BinaryNode<T>(item);
    	root = node;
    }

    // O(log(n))
    public void insert(T item) {
    	root.insert(item);
    }

    // O(log(n))
    public boolean contains(T item) {
    	return root.contains(item);
    }

    // TODO: trouver la hauteur de l'arbre
    // O(n)
    public int getHeight() {
        return root.getHeight();
    }

    // TODO: placer dans une liste les items de l'arbre en ordre
    //Passe une liste a la racine pour que ses enfants la
   // remplissent en ordre logique
    // O(n)
    public List<BinaryNode<T>> getItemsInOrder() {
    	List<BinaryNode<T>> list = new ArrayList<BinaryNode<T>>();
    	if (root != null) {
    		root.fillListInOrder(list);
    		return list;
    	}
    	return null;
    }

    // TODO: retourner la liste d'item en String selon le bon format
    // O(n)
    public String toStringInOrder() {
    	List<BinaryNode<T>> list = getItemsInOrder();
    	String listItems = "[";
    	for (int i = 0 ; i < list.size() ; i++) {
    		listItems += list.get(i).getData() + ", ";
    	}
    	// To remove the ", "
    	listItems = listItems.substring(0,listItems.length()-2) + "]";
    	return listItems;
    }
}