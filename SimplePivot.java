public class SimplePivot<T extends Comparable<? super T>> implements Partitionable<T> {

	public int partition(T[] a, int first, int last) {
		int pivotIndex = last;
		T pivot = a[pivotIndex];
		int indexFromLeft = first;
		int indexFromRight = last - 1;

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
	private void swap(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	} 

}