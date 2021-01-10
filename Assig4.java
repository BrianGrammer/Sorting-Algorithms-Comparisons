import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author Brian Grammer
 *
 *NOTES: For QuickSort.java, I took parts of the code from the book, Quick.java, and TextMergeQuick.java. These files were included, so I assumed I was supposed to use them for implementation.
 *		Also, I used the book as a guide for programming the other implementations of QuickSort, i.e. SimplePivot, RandomPivot, and MedOfThree. To clarify: I wrote the code for those, but I used the book as a guide.
 *		I also used the book as a guide for MergeSort.java. 
 *		Again, since these files were provided and at my access, I assumed I could use them. 
 */

public class Assig4 {

	public static Random R = new Random();
	ArrayList<Long> times = new ArrayList<Long>();
	ArrayList<Integer> recurses = new ArrayList<Integer>();
	private ArrayList<Sorter<Integer>> sorts;
	private Integer[] A;
	private int size, runs;
	private long start, timeTotal;
	private boolean rando;

	public static void main(String[] args) {
		// Change to String args[] when finished
		new Assig4(args[0], args[1], args[2]);
	}

	public void fillArray() {
		for (int i = 0; i < A.length; i++) {
			// Values will be 0 <= X < 1 billion
			A[i] = new Integer(R.nextInt(1000000000));
		}
	}
	
	//Fills array with incrementing values
	public void fillArraySorted() {
		for (int i = 0; i < A.length; i++) {
			A[i] = i;
		}
	}

	//Constructor
	public Assig4(String sz, String r, String ra) {
		
		
		//Assign Variables
		size = Integer.parseInt(sz);
		runs = Integer.parseInt(r);
		rando = Boolean.parseBoolean(ra);

		// Put the sorting objects into the ArrayLists
		sorts = new ArrayList<Sorter<Integer>>();
		sorts.add(new QuickSort<Integer>(new SimplePivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfThree<Integer>()));
		sorts.add(new QuickSort<Integer>(new RandomPivot<Integer>()));
		sorts.add(new MergeSort<Integer>());

		A = new Integer[size];
		
		//Rando var name is confusing. When rando ==false, random array is filled. The boolean is correct, just the name is confusing.
		if (rando == false) {
			// Iterate through all of the sorts and test each one
			for (int i = 0; i < sorts.size(); i++) {
				R.setSeed(123456); // This will enable all sorts to use the same data. If
				// you have multiple runs with the same algorithm you should only
				// set this one time for each algorithm so that the different runs
				// will have different data.

				for (int k = 3; k <= 73; k += 5) {
					for (int j = 0; j < runs; j++) {
						
						fillArray();
						sorts.get(i).setMin(k);
						// Start time
						start = System.nanoTime();
						sorts.get(i).sort(A, A.length);
						// End time
						timeTotal += System.nanoTime() - start;
						// Add time to respective place in the ArrayList
						times.add(timeTotal);
						
						// Reset long values
						timeTotal = 0;
						start = 0;
					}
				}
			}
		} else {
			//Incremented, presorted array
			for (int i = 0; i < sorts.size(); i++) {

				for(int k = 3; k<=73; k+=5) {
					for (int j = 0; j < runs; j++) {
						
						fillArraySorted();

						sorts.get(i).setMin(k);
						// Start time
						start = System.nanoTime();
						sorts.get(i).sort(A, A.length);
						// End time
						timeTotal += System.nanoTime() - start;
						// Add time to respective place in the ArrayList
						times.add(timeTotal);
	
						// Reset long values
						timeTotal = 0;
						start = 0;
					}
				}
			}
		}
		toStringC();

	}

	public void toStringC() {
		// StringBuilder is the final output
		StringBuilder b = new StringBuilder();
		// Strings used in output
		String boolRand, sortBest = "", sortWorst = "";
		// Stores the average runtimes for each algorithm
		ArrayList<Double> aveTimes = new ArrayList<Double>();
		double ave = 0;

		// Determines whether to output 'presorted' or 'random' based on the boolean
		if (rando == false)
			boolRand = "Random";
		else
			boolRand = "Presorted";

		// Finds the average run times for each algorithm
		// Also converts nanoseconds to seconds
		long total = 0;
		
		//Adds total
		for (int i = 0; i < 60; i++) {
			for (int j = 0; j < runs; j++) {
				total += times.get(j+(i*20));
				
			}
			
			ave = total / runs;
			ave /= 1000000000.0;
			aveTimes.add(ave);
			ave = 0;
			total = 0;
		}
	
		// Gets smallest and biggest average run times from the ArrayList.
		int min = aveTimes.indexOf(Collections.min(aveTimes));
		int max = aveTimes.indexOf(Collections.max(aveTimes));
		
		//Splits main arrayList into 4, according to how they were sorted
		ArrayList<Double> simpAve = new ArrayList<Double>(aveTimes.subList(0, 15));
		ArrayList<Double> medAve = new ArrayList<Double>( aveTimes.subList(15, 30));
		ArrayList<Double> randAve = new ArrayList<Double>( aveTimes.subList(30, 45));
		ArrayList<Double> mergeAve = new ArrayList<Double>( aveTimes.subList(45, 60));
		
		//Gets mins and maxes for each algorithm arraylist
		int minSimp = simpAve.indexOf(Collections.min(simpAve));
		int maxSimp = simpAve.indexOf(Collections.max(simpAve));
		
		int minMed = medAve.indexOf(Collections.min(medAve));
		int maxMed = medAve.indexOf(Collections.max(medAve));

		int minRand = randAve.indexOf(Collections.min(randAve));
		int maxRand = randAve.indexOf(Collections.max(randAve));
		
		int minMerge = mergeAve.indexOf(Collections.min(mergeAve));
		int maxMerge = mergeAve.indexOf(Collections.max(mergeAve));

		//Returns name of algorithm used to sort values. This is used for the output
		sortBest = getAlg(min);
		sortWorst = getAlg(max);
		
		
		//Full output
		b.append("Initialization information:\r\n" + "	Array size: " + size + "\r\n" + "	Number of runs per test: "
				+ runs + "\r\n" + "	Initial data: " + boolRand + "\r\n\n");

		b.append("After the tests, here is the best setup:\r\n" + "	Algorithm: " + sortBest + "\r\n"
				+ "	Data status: " + boolRand + "\r\n" + "	Min Recurse:	"+getRec(sortBest, min)+"\r\n" + "	Average: " + aveTimes.get(min)
				+ " sec\r\n" + "\r\n" + "After the tests, here is the worst setup:\r\n" + "	Algorithm: " + sortWorst
				+ "\r\n" + "	Data status: " + boolRand + "\r\n" + "	Min Recurse:	"+getRec(sortWorst, max)+"\r\n" + "	Average: "
				+ aveTimes.get(max) + " sec\r\n" + "\r\n" + "Here are the per algorithm results:\r\n"
				+ "Algorithm: Simple Pivot QuickSort\r\n" + "	Best Result: \r\n" + "		Min Recurse: "+getRec(minSimp)+"\r\n"
				+ "		Average: " + simpAve.get(minSimp)+ " sec\r\n" + "	Worst Result:\r\n" + "		Min Recurse: "+getRec(maxSimp)+ "\r\n"
				+ "		Average: " +simpAve.get(maxSimp) +" sec\r\n" + "\r\n" + "Algorithm: Median of Three QuickSort\r\n"
				+ "	Best Result:\r\n" + "		Min Recurse: "+getRec(minMed)+"\r\n" + "		Average: "+medAve.get(minMed)+" sec\r\n"
				+ "	Worst Result:\r\n" + "		Min Recurse: "+getRec(maxMed)+"\r\n" + "		Average: " +medAve.get(maxMed)+" sec\r\n" + "\r\n"
				+ "Algorithm: Random Pivot QuickSort\r\n" + "	Best Result:\r\n" + "		Min Recurse: "+getRec(minRand)+"\r\n"
				+ "		Average: "+ randAve.get(minRand)+" sec\r\n" + "	Worst Result:\r\n" + "		Min Recurse: "+getRec(maxRand)+"\r\n"
				+ "		Average: "+ randAve.get(maxRand) +" sec\r\n" + "\r\n" + "Algorithm: MergeSort\r\n" + "	Best Result:\r\n"
				+ "		Min Recurse: "+getRec(minMerge)+"\r\n" + "		Average: "+mergeAve.get(minMerge)+" sec\r\n" + "	Worst Result:\r\n"
				+ "		Min Recurse: "+getRec(maxMerge)+"\r\n" + "		Average: "+ mergeAve.get(maxMerge)+" sec");

		System.out.println(b);

	}
	
	
	//HELPER METHODS
	
	//Returns a string of how the value was sorted according to its place in the main arraylist
	private String getAlg(int index) {
		String t = "";
		if(index>=0 && index<15) {
			t = "Simple Pivot QuickSort";
			return t;
		}
		if(index>=15 && index<30) {
			t = "Median of Three QuickSort";
			return t;
		}
		if(index>=30 && index<45) {
			t = "Random Pivot QuickSort";
			return t;
		}
		else {
			t = "MergeSort";
			return t;
		}
	}
	
	//Helps me easily calculate min recursion
	private int getRec(int b) {
		return (3 + (5*b));
	}
	
	//Helps me easily calculate min, with an extra argument
	private int getRec(String s, int b) {
		int div = 0;
		if(s.equals("Simple Pivot QuickSort"))
			div = 1;
		else if(s.equals("Median of Three QuickSort"))
			div = 2;
		else if(s.equals("Random Pivot QuickSort"))
			div = 3;
		else if(s.equals("MergeSort"))
			div = 4;
		b= b/div;
		return 3 + 5*b;
	}
}