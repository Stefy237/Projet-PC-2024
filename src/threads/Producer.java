package threads;

import java.util.Random;

import utils.*;

public class Producer extends Thread {

    private int prodTime;
    private int minProd = 1;
    private int maxProd = 5;
    private int nProd;
    Message m;
    IProdConsBuffer buffer;

    public Producer(IProdConsBuffer buffer) {
        this.buffer = buffer;
        nProd = (new Random().nextInt(maxProd - minProd))  + minProd;
    }

    @Override
    public void run() {
        int i = 0;
        while (i<nProd) {
            prodTime = new Random().nextInt(2000);
            m = new Message(prodTime);
            m.create();
            try {
                buffer.put(m);
                System.out.println("message " + m.getNumber() + " produce by " + getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
    
}
