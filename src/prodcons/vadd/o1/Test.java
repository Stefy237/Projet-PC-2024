package prodcons.vadd.o1;

import threads.TConsumer;
import threads.TProducer;

public class Test {

        public static void main(String[] args) {
        int nProd = 5;
        int nCons = 3;
        int bufSiz = 1;

        ProdConsBuffer buffer = new ProdConsBuffer(bufSiz);
        TProducer[] prodThreads = new TProducer[nProd];
        TConsumer[] consThreads = new TConsumer[nCons];

        for(int i=0; i<prodThreads.length; i++) {
            prodThreads[i] = new TProducer(buffer);
        }

        for(int i=0; i<consThreads.length; i++) {
            consThreads[i] = new TConsumer(buffer);
        }

        for(TConsumer c : consThreads) {
            c.start();
        }

        for (TProducer p : prodThreads) {
            p.start();
        }

        for (TProducer p : prodThreads) {
            try {
                p.join();
                buffer.setNumberOfTerminatedProdThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
        while (buffer.getNumberOfTerminatedProdThread() != nProd && buffer.nmsg() != 0) {} 

        for(TConsumer c : consThreads) {
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
