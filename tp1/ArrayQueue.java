
public class ArrayQueue<AnyType> implements Queue<AnyType>
{
	private int size = 0;		//Nombre d'elements dans la file.
	private int startindex = 0;	//Index du premier element de la file
	private AnyType[] table;
   
	@SuppressWarnings("unchecked")
	public ArrayQueue() 
	{
		table = (AnyType[]) new Object[1];
	}
	
	//Indique si la file est vide
	public boolean empty() 
	{ 
		return size == 0; 
	}
	
	//Retourne la taille de la file
	public int size() 
	{ 
		return size; 
	}
	
	//Retourne l'element en tete de file
	//Retourne null si la file est vide
	//complexité asymptotique: O(1)
	public AnyType peek()
	{
		if (empty()) {
			return null;
		}
		else {
			return table[startindex];
		}
		
	}
	
	//Retire l'element en tete de file
	//complexité asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		if(!empty()) {
			table[startindex++] = null;
			size--;
		}
		else {
			throw new EmptyQueueException();
		}
	}
	
	//Ajoute un element a la fin de la file
	//Double la taille de la file si necessaire (utiliser la fonction resize definie plus bas)
	//complexité asymptotique: O(1) ( O(N) lorsqu'un redimensionnement est necessaire )
	public void push(AnyType item)
	{
		if (size + startindex == table.length) {
			resize(2);
		}
		table[size + startindex] = item;
		size++;
		
	}
   
	//Redimensionne la file. La capacite est multipliee par un facteur de resizeFactor.
	//Replace les elements de la file au debut du tableau
	//complexité asymptotique: O(N)
	@SuppressWarnings("unchecked")
	private void resize(int resizeFactor)
	{
		if (empty()) {
			table = (AnyType[]) new Object[1];
		}
		else {
			AnyType[] oldTable = (AnyType[]) new Object[size];
			for (int i = 0 ; i < size ; i++) {
				oldTable[i] = table[i + startindex];
			}
			table = (AnyType[]) new Object[size * resizeFactor];
			for (int i = 0; i < size; i++) {
				table[i] = oldTable[i];
			}
		}
		startindex = 0;
	}   
}

