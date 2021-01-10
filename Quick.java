// CS 0445 Spring 2020
// Simple version of QuickSort.  This is similar to the version in the Carrano
// text, except that it uses A[last] as the pivot and partitions based on that
// value.  Compare this to the version of Quicksort in the text (I have copied
// it into the file TextMergeQuick.java)

public class Quick
{
	public static <T extends Comparable<? super T>>
		   void quickSort(T[] array, int n)
	{
		quickSort(array, 0, n-1);
	} // end quickSort

	public static <T extends Comparable<? super T>>
		   void quickSort(T[] array, int first, int last)
	{
		if (first < last)
		{
			int pivotIndex = partition(array, first, last);
			quickSort(array, first, pivotIndex-1);
			quickSort(array, pivotIndex+1, last);
		} 
	}  

	private static <T extends Comparable<? super T>>
	        int partition(T[] a, int first, int last)
	{
		int pivotIndex = last;  
		T pivot = a[pivotIndex];

		int indexFromLeft = first; 
		int indexFromRight = last - 1; 

		boolean done = false;
		while (!done)
		{
			while (a[indexFromLeft].compareTo(pivot) < 0)
				indexFromLeft++;

			while (a[indexFromRight].compareTo(pivot) > 0 && indexFromRight > first)
				indexFromRight--;

			if (indexFromLeft < indexFromRight)
			{
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			}
			else 
				done = true;
		}

		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;

		return pivotIndex; 
	}

	private static void swap(Object [] a, int i, int j)
	{
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp; 
	}
}