package prodcons.v1;

import threads.*;

public class TestProdCons {
    
    public static void main(String[] args) {
        int nProd = 3;
        int nCons = 2;
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

        for(Consumer c : consThreads) {
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Producer p : prodThreads) {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All work done");
    }
}
