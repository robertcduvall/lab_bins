import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input); 
        Function<List<Integer>, List<Integer>> func = l -> l.stream().sorted().collect(Collectors.toList());
        fitDisksandPrint(data, func);
//        System.out.println("total size = " + returnTotal(data) / 1000000.0 + "GB");       
//        worstFitMethod(data, false);
//        Collections.sort(data, Collections.reverseOrder());
//        worstFitMethod(data, true);
    }
    
    private static void fitDisksandPrint(List<Integer> list, Function<List<Integer>, List<Integer>> func) {
        List<Integer> transformed = func.apply(list);
        transformed.forEach(System.out::println);
    }
    
    /**
     * Conducts the worst-fit bin packing method given an input of file sizes
     *
     * @param list of file sizes and whether the the method should be decreasing or not
     * @return N/A
     */
    public static void worstFitMethod(List<Integer> data, Boolean isDecreasing) {
        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));
        int diskId = 1;
        for (Integer size : data) {
            Disk d = pq.peek();
            boolean decreasingFlag = isDecreasing ? d.freeSpace() >= size : d.freeSpace() > size;
            if (decreasingFlag) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }
        String name = isDecreasing ? "worst-fit decreasing method" : "worst-fit method";
        printPQMethodInfo(pq, name);
    }
    
    /**
     * Prints information about a priority-queue based bin-packing method
     *
     * @param The resulting priority queue and the name of the method
     * @return N/A
     */
    public static void printPQMethodInfo(PriorityQueue<Disk> pq, String name) {
        System.out.println();
        System.out.println(name);
        System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }
    
    /**
     * Returns the total size specified by a list of file sizes
     *
     * @param list of file sizes 
     * @return the total file size
     */
    public static int returnTotal(List<Integer> data) {
        int total = 0;
        for (Integer size : data) {
            total += size;
        }
        return total;
    }
}