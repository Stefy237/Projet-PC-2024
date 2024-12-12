package prodcons.v6;

import threads.*;

public class TestProdCons {
    
        public static void main(String[] args) {
        int nProd = 3;
        int nCons = 6;
        int bufSiz = 5;

        ProdConsBuffer buffer = new ProdConsBuffer(bufSiz);
        MProducer[] prodThreads = new MProducer[nProd];
        Consumer[] consThreads = new Consumer[nCons];

        for(int i=0; i<prodThreads.length; i++) {
            prodThreads[i] = new MProducer(buffer);
        }

        for(int i=0; i<consThreads.length; i++) {
            consThreads[i] = new Consumer(buffer);
        }

        for(Consumer c : consThreads) {
            c.start();
        }

        for (MProducer p : prodThreads) {
            p.start();
        }

        for (MProducer p : prodThreads) {
            try {
                p.join();
                buffer.setNumberOfTerminatedProdThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
        while (buffer.getNumberOfTerminatedProdThread() != nProd && buffer.nmsg() != 0) {} 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
