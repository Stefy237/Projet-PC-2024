package prodcons.v5;

import threads.*;

public class TestProdCons {

    public static void main(String[] args) {
        int nProd = 6;
        int nCons = 4;
        int bufSiz = 5;

        ProdConsBuffer buffer = new ProdConsBuffer(bufSiz);
        Producer[] prodThreads = new Producer[nProd];
        MConsumer[] consThreads = new MConsumer[nCons];

        for(int i=0; i<prodThreads.length; i++) {
            prodThreads[i] = new Producer(buffer);
        }

        for(int i=0; i<consThreads.length; i++) {
            consThreads[i] = new MConsumer(buffer);
        }

        for(MConsumer c : consThreads) {
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
         
        while (buffer.getNumberOfTerminatedProdThread() != nProd) {} 
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        for(MConsumer c : consThreads) {
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
