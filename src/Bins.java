import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

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

    //print the values a the priority queue.
    public void printQ (PriorityQueue pq)
    {
        System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }    
    
    /** 
     * We observed that the methods of worstFit in order and worstFit decreasing order 
    * ed the same exact code. The only difference was that the Worst fit decreasing order 
    * code 
    */
    
    public PriorityQueue<Disk> worstFitOverall (List<Integer> allData)
    {
    PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
    pq.add(new Disk(0));
    int diskId = 1;
         int total = 0;
         for (Integer size : allData) {
             Disk d = pq.peek();
             if (d.freeSpace() >= size) {
                 pq.poll();
                 d.add(size);
                 pq.add(d);
             } else {
                 Disk d2 = new Disk(diskId);
                 diskId++;
                 d2.add(size);
                 pq.add(d2);
             }
             total += size;
         }
         System.out.println("total size = " + total / 1000000.0 + "GB");
         System.out.println();
         return pq;
    }
    
    public void worstFitInOrder (List<Integer> data)
    {
        System.out.println("worst-fit method");
        printQ(worstFitOverall(data));
    }
    
    public void worstFitDecreasing (List<Integer> data)
    {     
        Collections.sort(data, Collections.reverseOrder());
        System.out.println("worst-fit decreasing method");
        printQ(worstFitOverall(data));
    }
  
    /**
     * The main program.
     */
    
    // Run worst fit methods both in order and decreasing
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        b.worstFitInOrder(data);
        b.worstFitDecreasing(data);
    }
}