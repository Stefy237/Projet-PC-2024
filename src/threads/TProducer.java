package threads;

import java.util.Random;

import utils.IProdConsBuffer;
import utils.Task;

public class TProducer extends Thread {

    private int runTime;
    private int minProd = 1;
    private int maxProd = 5;
    private int nProd;
    Task t;
    IProdConsBuffer buffer;

    public TProducer(IProdConsBuffer buffer) {
        this.buffer = buffer;
        nProd = (new Random().nextInt(maxProd - minProd))  + minProd;
    }

    @Override
    public void run() {
        int i = 0;
        while (i<nProd) {
            runTime = new Random().nextInt(2000);
            t = new Task (runTime);
            t.create();
            try {
                buffer.put(t);
                System.out.println("Task " + t.getNumber() + " submit by " + getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
    
}
