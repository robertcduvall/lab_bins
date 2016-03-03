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
    public List<Integer> readData (Scanner input,int total) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
            //Add the integer to total
            total += results.get(results.size() - 1);
        }
        return results;
    }

    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        int total = 0;
        List<Integer> data = b.readData(input, total);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));

        int diskId = 1;

        populatePriorityQueue(data, pq, diskId);

        System.out.println("total size = " + total / 1000000.0 + "GB");
        System.out.println();
        System.out.println("worst-fit method");
        System.out.println("number of pq used: " + pq.size());
        
        emptyPriorityQueue(pq);
        System.out.println();

        Collections.sort(data, Collections.reverseOrder());
        pq.add(new Disk(0));

        diskId = 1;
        populatePriorityQueue(data, pq, diskId);

        System.out.println();
        System.out.println("worst-fit decreasing method");
        System.out.println("number of pq used: " + pq.size());
       
        emptyPriorityQueue(pq);
        System.out.println();
    }
    public static void populatePriorityQueue(List<Integer> data, PriorityQueue<Disk> pq,int diskId){
    	//Adds each file size to a disk with free space
    	for (Integer size : data) {
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
        }
    }
    public static void emptyPriorityQueue(PriorityQueue<Disk> pq){
    	 while (!pq.isEmpty()) {
             System.out.println(pq.poll());
         }
    }
}
