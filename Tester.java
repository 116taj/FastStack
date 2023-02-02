package comp2402a4;

import java.util.Iterator;
import java.util.Random;

public class Tester {

    static <T> void showContents(Iterable<Integer> ds) {
        System.out.print("[");
        Iterator<Integer> it = ds.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext()) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }
    static void sparrowTest(RevengeOfSparrow rs, int n) {
        System.out.println(rs.getClass().getName());

        Random rand = new Random(2402);
        for (int j = 0; j < n; j++) {
            int x = rand.nextInt(3*n/2);
            System.out.println("push(" + x + ")");
            rs.push(x);
            showContents(rs);
            int i = rand.nextInt(rs.size());
            System.out.println("get("+ i +") = " + rs.get(i));
            System.out.println("max() = " + rs.max());
            int k = rand.nextInt(rs.size()+1);
            System.out.println("ksum("+ k +") = " + rs.ksum(k));
        }
        for (int j = 0; j < n/2; j++) {
            int i = rand.nextInt(rs.size());
            int x = rand.nextInt(3*n/2);
            System.out.println("set(" + i + ", " + x + ") = " + rs.set(i, x));
            showContents(rs);
            i = rand.nextInt(rs.size());
            System.out.println("get("+ i +") = " + rs.get(i));
            System.out.println("max() = " + rs.max());
            int k = rand.nextInt(rs.size()+1);
            System.out.println("ksum("+ k +") = " + rs.ksum(k));
        }

        while (rs.size() > 0) {
            System.out.println("pop() = " + rs.pop());
            showContents(rs);
            if(rs.size() > 0){
                int i = rand.nextInt(rs.size());
                System.out.println("get("+ i +") = " + rs.get(i));
                System.out.println("max() = " + rs.max());
                int k = rand.nextInt(rs.size()+1);
                System.out.println("ksum("+ k +") = " + rs.ksum(k));
            }
        }
    }

    static void sparrowTestE(RevengeOfSparrow rs, int n) {
        Random rand = new Random(2402);
        for (int j = 0; j < n; j++) {
            int x = rand.nextInt(3*n/2);
            rs.push(x);
            int i = rand.nextInt(rs.size());
            rs.get(i);
            rs.max();
            int k = rand.nextInt(rs.size()+1);
            rs.ksum(k);
        }
        for (int j = 0; j < n/2; j++) {
            int i = rand.nextInt(rs.size());
            int x = rand.nextInt(3*n/2);
            rs.set(i, x);
            i = rand.nextInt(rs.size());
            rs.get(i);
            rs.max();
            int k = rand.nextInt(rs.size()+1);
            rs.ksum(k);
        }

        while (rs.size() > 0) {
            rs.pop();
            if(rs.size() > 0){
                int i = rand.nextInt(rs.size());
                rs.get(i);
                rs.max();
                int k = rand.nextInt(rs.size()+1);
                rs.ksum(k);
            }
        }
    }
    public static void main(String[] args) {
        int customTest = 0;
        if (customTest == 1){
            FastSparrow fs = new FastSparrow();
            System.out.println("push 44");
            fs.push(44);
            System.out.println("push 127");
            fs.push(127);
            System.out.println("push 78");
            fs.push(78);
            System.out.println("push 5");
            fs.push(5);
            System.out.println("push 17");
            fs.push(17);
            System.out.println("push 4");
            fs.push(4);
            System.out.println("push 6");
            fs.push(6);
            System.out.println("push 1");
            fs.push(1);
            System.out.println("set 7 to be 6");
            //fs.set(0, 6);
            System.out.println("set 11 to be 48");
            //fs.set(2, 48);
            System.out.println("set 48 to be 17");
            //fs.set(2, 17);
            System.out.println("set 4 to be 2");
            //fs.set(5, 2);
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop();
            System.out.println("pop");
            fs.pop(); 
        } else if (customTest == 2){    
            long start = System.nanoTime();
            sparrowTestE(new SlowSparrow(), 10000);
            long stop = System.nanoTime();
            double elapsed = ((double)(stop-start))*1e-9;
            System.out.println("10000 slowly done(" + elapsed + "s)"); 
            start = System.nanoTime();
            sparrowTestE(new FastSparrow(), 1000000);
            stop = System.nanoTime();
            elapsed = ((double)(stop-start))*1e-9;
            System.out.println("1000000 quickly done(" + elapsed + "s)"); 
        } else {
            sparrowTest(new SlowSparrow(), 20);
            sparrowTest(new FastSparrow(), 20);
        }
    }
}
