import java.util.HashSet;

public class SubsetHashing {
	
    /* Return true si Tab2 [] est un sous-ensemble de Tab1 [] */
	static boolean isSubset(int Tab1[], int Tab2[], int m, int n)
    {
		HashSet<Integer> set = new HashSet<Integer>();
		for (Integer tab1 : Tab1) {
			set.add(tab1);
		}
		
		for (Integer tab2 : Tab2) {
			boolean contains = set.contains(tab2);
			if (contains == false) {
				return false;
			}
		}
        return true;
    } 
 
    public static void main(String[] args) 
    { 
        int T1[] = {5, 11, 12, 1, 10, 3, 7};
        int T2[] = {11, 1, 10};
         
        int m = T1.length;
        int n = T2.length;
     
        if(isSubset(T1, T2, m, n))
            System.out.print("Tab2 [] est un sous-ensemble de Tab1 [] ");
        else
            System.out.print("Tab2 [] n'est pas un sous-ensemble de Tab1 []"); 
    }
}


