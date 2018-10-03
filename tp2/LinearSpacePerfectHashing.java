import java.util.Random;
import java.util.ArrayList;

public class LinearSpacePerfectHashing<AnyType>
{
	static int p = 46337;

	QuadraticSpacePerfectHashing<AnyType>[] data;
	int a, b;

	LinearSpacePerfectHashing()
	{
		a=b=0; data = null;
	}

	LinearSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			a = b = 0;
			data = null;
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;
			data = new QuadraticSpacePerfectHashing[1];
			data[0].SetArray(array);
			return;
		}

		a = generator.nextInt(p-1)+1;
		b = generator.nextInt(p);
		// m = n
		// the idea is to have an array of QuadraticSpacePerfectHashing and within each element is a QuadraticSpaceHashing array
		// this way, collisions are handled effectively.
		data = new QuadraticSpacePerfectHashing[array.size()];
		
		// loops over each cle_i. we will be adding a QuadraticSpace (vertical)
		for (int i = 0; i < array.size(); i++) {
			ArrayList<AnyType> hashList = new ArrayList<>();
			//loops the array. assigns key to each item. if there is a hit, add to the hashList we will add at the current i
			for (AnyType item : array) {
				if (getKey(item) == i) {
					hashList.add(item);
				}
			}
			data[i] = new QuadraticSpacePerfectHashing<AnyType>(hashList);
		}
	}

	public int Size()
	{
		if( data == null ) return 0;

		int size = 0;
		for(int i=0; i<data.length; ++i)
		{
			size += (data[i] == null ? 1 : data[i].Size());
		}
		return size;
	}

	public boolean containsKey(int key)
	{
		if (data == null) {
			return false;
		}
		return data[key] != null;
	}
	
	public int getKey (AnyType x) {
		return (((a * x.hashCode() + b) % p) % data.length);
	}
	
	public boolean containsValue (AnyType x) {
		int key = getKey(x);
		if (data != null || data[key] != null) {
			return data[key].containsValue(x);
		}
		return false;
	}
	
	public void remove (AnyType x) {
		data[getKey(x)].remove(x);
	}

	public String toString () {
		String result = "";
		for (int i = 0 ; i < data.length; i++) {
				result += "[clé " + i + "] ->" + data[i].toString() + "\n";
		}
		return result; 
	}

	public void makeEmpty () {
		for (int i = 0 ; i < data.length ; i++) {
			if (data[i] != null) {
				data[i].makeEmpty();
			}
		}
		data = null;
   	}
	
}
