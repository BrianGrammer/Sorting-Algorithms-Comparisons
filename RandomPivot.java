import java.util.Random;

public class RandomPivot<T extends Comparable<? super T>> implements Partitionable<T>{

	public int partition(T[] a, int first, int last) {
		
		findPivot(a, first, last);
		T pivot = a[last];
		int indexFromLeft = first;
		int indexFromRight = last-1;
		int pivotIndex = last;

		boolean done = false;
		while (!done) {
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;
			
			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			if (indexFromLeft < indexFromRight) {
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			} else
				done = true;
		}
		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;

		return pivotIndex;
		
	}
	
	//Taken from Quick.java
	private void swap(T[] a, int i, int j) {
		T temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	} 
	
	private void findPivot(T[] b, int first, int last) {
		int randomPivot;
		Random r = new Random();
		randomPivot = r.nextInt(last-first)+first;
		swap(b, randomPivot, last);
	}
	
}