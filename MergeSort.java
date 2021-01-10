public class MergeSort<T extends Comparable<? super T>> implements Sorter<T> {

	private int MIN_SIZE; // min size to recurse, use InsertionSort
							// for smaller sizes to complete sort

	public MergeSort() {
		MIN_SIZE = 3;
	}

	public void sort(T[] a, int size) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Comparable[size];
		sort(a, temp, 0, size - 1);

	}

	private void sort(T[] a, T[] temp, int first, int last) {
		if (last - first + 1 < MIN_SIZE)
			insertionSort(a, first, last);

		else {
			int mid = (first + last) / 2;
			sort(a, temp, first, mid);
			sort(a, temp, mid + 1, last);
			merge(a, temp, first, mid, last);
		}
	}

	private void merge(T[] a, T[] temp, int first, int mid, int last) {
		int bH1 = first;
		int eH1 = mid;
		int bH2 = mid + 1;
		int eH2 = last;
		int index = bH1;

		while ((bH1 <= eH1) && (bH2 <= eH2)) {
			if (a[bH1].compareTo(a[bH2]) <= 0) {
				temp[index++] = a[bH1++];
			} else {
				temp[index++] = a[bH2++];
			}
		}

		while (bH1 <= eH1) {
			temp[index++] = a[bH1++];
		}

		while (bH2 <= eH2) {
			temp[index++] = a[bH2++];
		}

		for (index = first; index <= last; index++)
			a[index] = temp[index];

	}

	public void setMin(int minSize) {
		MIN_SIZE = minSize;
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