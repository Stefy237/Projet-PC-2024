package prodcons.vadd.o2;

import threads.TProducer;

public class Test {

    public static void main(String[] args) {
        int nProd = 3;
        int bufSiz = 2;
    
        TaskExecutor buffer = new TaskExecutor(bufSiz);
        TProducer[] prodThreads = new TProducer[nProd];

        for(int i=0; i<prodThreads.length; i++) {
            prodThreads[i] = new TProducer(buffer);
        }
        
        for (TProducer p : prodThreads) {
            p.start();
        }

        buffer.execute();

        for (TProducer p : prodThreads) {
            try {
                p.join();
                buffer.setNumberOfTerminatedProdThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All work done");

    }
}
