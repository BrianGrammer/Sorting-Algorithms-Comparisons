public class MedOfThree<T extends Comparable<? super T>> implements Partitionable<T>{
	
	public int partition(T[] a, int first, int last) {
		
		T pivot = findPivot(a, first, last);
		int indexFromLeft = first;
		int indexFromRight = last - 1;
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
	
	private T findPivot(T[] b, int first, int last) {
		int mid = (first + last)/2;	
		
		//Sorts the 3 possible pivots
		if((b[first].compareTo(b[mid]))>0) {
			swap(b, first, mid);
		}
		if((b[first].compareTo(b[last]))>0) {
			swap(b, first, last);
		}
		if((b[mid].compareTo(b[last]))>0) {
			swap(b, mid, last);
		}
		
		//Swaps middle and last values
		swap(b, mid, last);
		return b[last];
	}
	
}