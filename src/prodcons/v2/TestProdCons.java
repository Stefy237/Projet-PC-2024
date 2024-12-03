package prodcons.v2;

import threads.Consumer;
import threads.Producer;

public class TestProdCons {
    
        public static void main(String[] args) {
        int nProd = 5;
        int nCons = 3;
        int bufSiz = 1;

        ProdConsBuffer buffer = new ProdConsBuffer(bufSiz);
        Producer[] prodThreads = new Producer[nProd];
        Consumer[] consThreads = new Consumer[nCons];

        for(int i=0; i<prodThreads.length; i++) {
            prodThreads[i] = new Producer(buffer);
        }

        for(int i=0; i<consThreads.length; i++) {
            consThreads[i] = new Consumer(buffer);
        }

        for(Consumer c : consThreads) {
            c.start();
        }

        for (Producer p : prodThreads) {
            p.start();
        }

        for (Producer p : prodThreads) {
            try {
                p.join();
                buffer.setNumberOfTerminatedProdThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
        while (buffer.getNumberOfTerminatedProdThread() != nProd && buffer.nmsg() != 0) {} 

        for(Consumer c : consThreads) {
            try {
                c.interrupt();
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All work done");
    }
}
