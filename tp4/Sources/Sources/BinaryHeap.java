import java.util.*; 

public class BinaryHeap<AnyType extends Comparable<? super AnyType>> extends AbstractQueue<AnyType>
{
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Nombre d'elements
    private AnyType [ ] array;    // Tableau contenant les donnees (premier element a l'indice 1)
    private boolean min;
    private int modifications;    // Nombre de modifications apportees a ce monceau
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( boolean min )
    {
		this.min = min;
		currentSize = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( AnyType[] items, boolean min )
    {
		this.min = min;
		this.array = (AnyType[]) new Comparable[items.length + 1];
		for (AnyType item : items) {
			this.offer(item);
		}
		if (this.min == true) {
			buildMinHeap();
		}
		else {
			buildMaxHeap();
		}
    }
    
    public boolean offer( AnyType x )
    {
		if (x == null)
		    throw new NullPointerException("Cannot insert null in a BinaryHeap");
		
		if( currentSize + 1 == array.length )
		    doubleArray();
		
		int adequatePosition = ++currentSize;
		
		if (min) {
			// Percolating up the values at the adequate position for min heap
			for (; adequatePosition > 1 && x.compareTo(array[adequatePosition/2]) < 0; adequatePosition /= 2 ) {
				array[adequatePosition] = array[adequatePosition/2];
			}
		}
		else {
			// Percolating up the values at the adequate position for max heap
			for (; adequatePosition > 1 && x.compareTo(array[adequatePosition/2]) > 0; adequatePosition /= 2 ) {
				array[adequatePosition] = array[adequatePosition/2];
			}
		}
		
		array[adequatePosition] = x;		
		modifications++;
		
		return true;
    }
    
    public AnyType peek()
    {
		if(!isEmpty())
		    return array[1];
		
		return null;
    }
    
    public AnyType poll()
    {
    	if (this.isEmpty()) {
    		return null;
    	}
    	
    	AnyType root = array[1];
    	array[1] = array[currentSize--];
    	
    	if (min) {
    		percolateDownMinHeap(1, currentSize);
    	}
    	else {
    		percolateDownMaxHeap(1, currentSize);
    	}
    	modifications++;
    	return root;
    }
    
    public Iterator<AnyType> iterator()
    {
		return new HeapIterator();
    }
    
    private void buildMinHeap()
    {
    	for (int i = currentSize/2; i > 0 ;  i--) {
    		percolateDownMinHeap(i, currentSize);
    	}
    }
    
    private void buildMaxHeap()
    {
    	for (int i = currentSize/2; i > 0 ;  i--) {
    		percolateDownMaxHeap(i, currentSize);
    	}
    }
    
    public boolean isEmpty()
    {
		return currentSize == 0;
    }
    
    public int size()
    {
		return currentSize;
    }
    
    public void clear()
    {
		currentSize = 0;
		modifications = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    private static int leftChild( int i, boolean heapIndexing )
    {
		return ( heapIndexing ? 2*i : 2*i+1 );
    }
    
    private void swapReferences( int index1, int index2 )
    {
		swapReferences(array, index1, index2);
    }
    
    private static <AnyType extends Comparable<? super AnyType>>
				    void swapReferences( AnyType[] array, int index1, int index2 )
    {
		AnyType tmp = array[ index1 ];
		array[ index1 ] = array[ index2 ];
		array[ index2 ] = tmp;
    }
    
    @SuppressWarnings("unchecked")
	private void doubleArray()
    {
		AnyType [ ] newArray;
		
		newArray = (AnyType []) new Comparable[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
		    newArray[ i ] = array[ i ];
		array = newArray;
    }
    
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMinHeap( int hole, int size )
    {
		percolateDownMinHeap(array, hole, size, true);
    }
    
    /**
     * @param array   Tableau d'element
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>>
				    void percolateDownMinHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
    	AnyType elementToPlace = array[hole];
    	int newHole = hole;
    	
    	for (; hole * 2 <= size ; hole = newHole ) {
    		
	    	int leftChildIndex = leftChild(hole, heapIndexing);
	    	int rightChildIndex = leftChild(hole, heapIndexing) + 1;
	    	
	    	// if there is no right child
	    	if (rightChildIndex > size) {
	    		if (array[leftChildIndex].compareTo(elementToPlace) < 0 ) {
	    			swapReferences(array, hole, leftChildIndex);
	    		}
	    		return;
	    	}
	    
	    	// if the left child is smaller than the right child and smaller than the element to place
	    	if (array[leftChildIndex].compareTo(array[rightChildIndex]) < 0 && array[leftChildIndex].compareTo(elementToPlace) < 0 ) {
	    		swapReferences(array, hole, leftChildIndex);
	    		newHole = leftChildIndex;
	    	}
	    	// if the right child is smaller than the left child and smaller than the element to place
	    	else if (array[rightChildIndex].compareTo(array[leftChildIndex]) < 0 && array[rightChildIndex].compareTo(elementToPlace) < 0 ) {
	    		swapReferences(array, hole, rightChildIndex);
	    		newHole = rightChildIndex;
	    	}
	    	
	    	// if the element to place is at its right place
	    	else if (elementToPlace.compareTo(array[leftChildIndex]) < 0 && elementToPlace.compareTo(array[rightChildIndex]) < 0) {
	    		return;
	    	}
	    	
    	}

    }
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMaxHeap( int hole, int size )
    {
		percolateDownMaxHeap(array, hole, size, true);
    }
    
    /**
     * @param array         Tableau d'element
     * @param hole          Position a percoler
     * @param size          Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>> 
				    void percolateDownMaxHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
    	AnyType elementToPlace = array[hole];
    	int newHole = hole;
    	
    	for (; hole * 2 <= size ; hole = newHole ) {
    		
	    	int leftChildIndex = leftChild(hole, heapIndexing);
	    	int rightChildIndex = leftChild(hole, heapIndexing) + 1;
	    	
	    	// if there is no right child
	    	if (rightChildIndex > size) {
	    		if (array[leftChildIndex].compareTo(elementToPlace) > 0 ) {
	    			swapReferences(array, hole, leftChildIndex);
	    		}
	    		return;
	    	}
	    	
	    	// if the left child is larger than the right child and larger than the element to place
	    	if (array[leftChildIndex].compareTo(array[rightChildIndex]) > 0 && array[leftChildIndex].compareTo(elementToPlace) > 0 ) {
	    		swapReferences(array, hole, leftChildIndex);
	    		newHole = leftChildIndex;
	    	}
	    	// if the right child is larger than the left child and larger than the element to place
	    	else if (array[rightChildIndex].compareTo(array[leftChildIndex]) > 0 && array[rightChildIndex].compareTo(elementToPlace) > 0 ) {
	    		swapReferences(array, hole, rightChildIndex);
	    		newHole = rightChildIndex;
	    	}
	    	
	    	// if the element to place is at its right place
	    	else if (elementToPlace.compareTo(array[leftChildIndex]) > 0 && elementToPlace.compareTo(array[rightChildIndex]) > 0) {
	    		return;
	    	}
	    	
    	}
    }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSort( AnyType[] a )
    {
    	int size = a.length;
    	while (size > 0) {
    		for (int i = size/2 ; i >= 0 ; i--) {
    			percolateDownMaxHeap(a, i, size-1, false);
    		} 
    		// Swapping the element at the root to the end;
    		swapReferences(a, 0, --size);
    	}
    }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSortReverse( AnyType[] a )
    {
    	int size = a.length;
    	while (size > 0) {
    		for (int i = size/2 ; i >= 0 ; i--) {
    			percolateDownMinHeap(a, i, size-1, false);
    		} 
    		// Swapping the element at the root to the end;
    		swapReferences(a, 0, --size);
    	}
    }
    
    public String nonRecursivePrintFancyTree()
    {
		String outputString = "";
		String prefix = "|__";
		outputString += prefix + array[1];

		for (int i = 2, j = 1 ; i*2 <= this.size() ; i = i *= 2, j++) {
			outputString += "\n";
			
			for ( int k = j ; k > 0; k--) {
				outputString += "|";
				outputString += "    ";
			}
			outputString += prefix + array[i];
		}
		
		return outputString;
    }
    
    public String printFancyTree()
    {
		return printFancyTree(1, "");
    }
    
    private String printFancyTree( int index, String prefix)
    {
		String outputString = "";
		
		outputString = prefix + "|__";
		
		if( index <= currentSize )
		    {
			boolean isLeaf = index > currentSize/2;
			
			outputString += array[ index ] + "\n";
			
			String _prefix = prefix;
			
			if( index%2 == 0 )
			    _prefix += "|  "; // un | et trois espace
			else
			    _prefix += "   " ; // quatre espaces
			
			if( !isLeaf ) {
			    outputString += printFancyTree( 2*index, _prefix);
			    outputString += printFancyTree( 2*index + 1, _prefix);
			}
		    }
		else
		    outputString += "null\n";
		
		return outputString;
    }
    
    private class HeapIterator implements Iterator {
	
    	private int pos = 1;
    	private int initialModifications = modifications;
    	
		public boolean hasNext() {
			if(pos == currentSize) {
				return false;
			}
			return true;
		}
	
		public Object next() throws NoSuchElementException, 
					    ConcurrentModificationException, 
					    UnsupportedOperationException {
			if (modifications != initialModifications) {
				throw new ConcurrentModificationException();
			}
		    if(this.hasNext()) {
		    	return array[pos++];
		    }
		    else {
		    	throw new NoSuchElementException();
		    }
		}
		
		public void remove() {
		    throw new UnsupportedOperationException();
		}
	}
}
