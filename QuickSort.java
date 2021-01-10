
public class QuickSort<T extends Comparable<? super T>> implements Sorter<T> {

	private Partitionable<T> partAlgo;
	private int MIN_SIZE;

	public QuickSort(Partitionable<T> part) {

		partAlgo = part;
		MIN_SIZE = 3;

	}

	public void setMin(int m) {
		MIN_SIZE = m;
	}

	public void sort(T[] a, int size) {
		sort(a, 0, size - 1);
	}

	public void sort(T[] array, int first, int last) {
		if(last - first + 1 < MIN_SIZE)
			insertionSort(array, first, last);
		else {
			// create the partition: Smaller | Pivot | Larger
			int pivotIndex = partAlgo.partition(array, first, last);

			// sort subarrays Smaller and Larger
			sort(array, first, pivotIndex - 1);
			sort(array, pivotIndex + 1, last);
		} // end if
	}

	public <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last) {
		int unsorted, index;

		for (unsorted = first + 1; unsorted <= last; unsorted++) { 

			T firstUnsorted = a[unsorted];

			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

	private <T extends Comparable<? super T>> void insertInOrder(T element, T[] a, int begin, int end) {
		int index;

		for (index = end; (index >= begin) && (element.compareTo(a[index]) < 0); index--) {
			a[index + 1] = a[index]; // make room
		} // end for

		// Assertion: a[index + 1] is available
		a[index + 1] = element; // insert
	} // end insertInOrder
}
