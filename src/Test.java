import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        TreeMap<String,String> tMap= new TreeMap<>(new StringComparator());
        HashMapSC<String,String> hMapSC = new HashMapSC<>(100000);
        HashMapLP<String,String> hMapLP = new HashMapLP<>(100000);
    
        readFile(tMap, hMapSC, hMapLP, "users.txt");

        File f = new File("user_list.txt");
        try{
            Scanner read = new Scanner(f);
            int tMTot = 0, hMSCTot = 0, hMLPTot = 0;
            System.out.println("Testing get()");
            System.out.printf("%-20s\t%-10s\t%-10s\t%-10s\n", "username:", "TreeMap:", "HashMapSC:", "HashMapLP:");
            int i = 0;
            while(read.hasNextLine()){
                String[] line = read.nextLine().split(" ");
                String w = line[0];

                tMap.get(w);
                hMapSC.get(w);
                hMapLP.get(w);

                int tMIter = TreeMap.iterations;
                int hMSCIter = HashMapSC.iterations;
                int hMLPIter = HashMapLP.iterations;

                tMTot += tMIter;
                hMSCTot += hMSCIter;
                hMLPTot += hMLPIter;

                if(i%5 == 0){
                    System.out.printf("%-20s\t%-10d\t%-10d\t%-10d\n", w, tMIter, hMSCIter, hMLPIter);
                }

                i++;
            }
            System.out.printf("%-20s\t%-10d\t%-10d\t%-10d\n", "average", tMTot/100, hMSCTot/100, hMLPTot/100);
            read.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
            System.exit(0);
        }

        System.out.println("\nTesting put():");
        testPut("users.txt");
    }

    public static void readFile(TreeMap<String,String> tMap, HashMapSC<String,String> hMapSC, HashMapLP<String,String> hMapLP, String fn){
        File f = new File(fn);
        try{
            Scanner read = new Scanner(f);
            while(read.hasNextLine()){
                String liner = read.nextLine();
                String[] line = liner.split(" ");

                tMap.add(line[0],line[1]);
                hMapSC.put(line[0],line[1]);
                hMapLP.put(line[0],line[1]);

                // if (line[0].equals("xxv903")){
                //     System.out.println("LP Maop contains xxv903" + hMapLP.containsKey(line[0]));
                // }
            }
            read.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
            System.exit(0);
        }
    }

    public static void testPut(String fn){
        File f = new File(fn);
        try{
            
            System.out.printf("%-20s\t%-10s\t%-10s\n", "Size:", "HashMapSC:", "HashMapLP:");
            // int scTot = 0, lpTot = 0;
            for (int i = 50000; i <= 500000; i+= 50000){
                Scanner read = new Scanner(f);
                HashMapSC<String,String> hMapSC1 = new HashMapSC<>(i);
                HashMapLP<String,String> hMapLP1 = new HashMapLP<>(i);
                int scCol = 0, lpCol = 0;
                while (read.hasNextLine()){
                    String[] line = read.nextLine().split(" ");
                    hMapSC1.put(line[0],line[1]);
                    hMapLP1.put(line[0],line[1]); 
                    // scCol += HashMapSC.collisions;
                    // lpCol += HashMapLP.collisions;
                }
                scCol = HashMapSC.collisions;
                lpCol = HashMapLP.collisions;
                // scCol = hMapSC1.getCol();
                // lpCol = hMapLP1.getCol();
                // scTot += scCol;
                // lpTot += lpCol;
                System.out.printf("%-20d\t%-10d\t%-10d\n", i, scCol, lpCol);
                read.close();
            }
            
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
            System.exit(0);
        }
    
    }
}


//DISCUSSING RESULTS FOR THE GET METHOD:
// For both types of HashMap, the iteratiosn hover around 1 per key. THis is due to each key being assigned a unique code based on it's value.
// For the separate chaining hash map, it checks  if the value is contained in the linked list at the index, and will typically return one.
// For the linear probing hashmap, it iterates over the hashmap starting from the unique index for the hashcode until it finds the next null element. Given such a large sized hashmap, these iterations will also be very low in most cases.
// The TreeMap is a type of binary search tree. Less than 30 iterations is probalby pretty good for a tree, since the worst case is logarithmic for trees. 
// All of the results are within reason given the type of data structure that each implements. 

//DISCUSSING NUMBER OF COLLISIONS
// For the separarate chaining hashmap, the number of iterations decreases as the size of the hashmap increases.
// Since there are more indexes available, some keys that would be mapped to the saem location can have their own unique location and decrease the number of collisions, which checks out
// For the linear probing hashmap, the number of collisions are generally higher.
// Since we must look for the next open index rather than adding to a linked list at the hashed index when the index is not null, the number of collisions will of course be higher, and this matches up. 
//In both cases of the hashmaps, the collisions are the same no matter what the size when we make the capacity anything above 300000. This is due to the fact that we are making the size of the hashmap the next hgihest power of 2.
// After a certain point, the hashmap will have the same power-of-2-capacity, so the collisions will be the same. 