import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuadraticSpacePerfectHashing<AnyType> 
{
	static int p = 46337;

	int a, b;
	AnyType[] items;

	QuadraticSpacePerfectHashing()
	{
		a=b=0; items = null;
	}

	QuadraticSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public int Size()
	{
		if( items == null ) return 0;

		return items.length;
	}

	public boolean containsKey(int key)
	{
		// on verifie simplement que le tableau, a l'index (qui est en fait la cle), contient quelque chose.
		if (items == null) {
			return false;
		}
		return items[key] != null;
	}

	public boolean containsValue(AnyType x )
	{
		if (Size() == 0 || items[getKey(x)] == null) {
			return false;
		}
		return items[getKey(x)].equals(x);
	}

	public void remove (AnyType x) {
		if(containsValue(x)){
			items[getKey(x)] = null;
		}
	}

	public int getKey (AnyType x) {
		return ((a * x.hashCode() + b) % p) % Size();
	}

	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			a = b = 0;
			items = null;
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;
			items = (AnyType[]) new Object[1];
			items[0] = array.get(0);
			return;
		}
		
		boolean collision;
				
		do {
			// Because I'm lazy, this redoes the items array each time there is a collision, instead of finding a new placeholder for the colliding item.
			// this yields perfect hashing
			int m = array.size() * array.size();
			items = (AnyType[]) new Object[m];
			collision = false;
			a = generator.nextInt(p-1)+1;
			b = generator.nextInt(p);	
			
			for (AnyType item : array) {
				if (items[getKey(item)] != null) {
					collision = true;
					break;
				}
				items[getKey(item)] = item;
			}
		} while (collision);

	}

	
	
	public String toString () {
		String result = "";
		for (int i = 0 ; i < Size(); i++) {
			if (containsKey(i)) {
				result += "(clé_" + i + ", " + items[i].toString() + ") ";
			}	
		}
		return result; 
	}

	public void makeEmpty () {
		for (int i = 0 ; i> items.length ; i++) {
			items[i] = null;
		}
   	}
}
