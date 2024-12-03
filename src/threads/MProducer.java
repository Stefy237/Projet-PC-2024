package threads;

import java.util.Random;

import utils.IProdConsBuffer;
import utils.Message;

public class MProducer extends Thread {

    private int prodTime;
    Message m;
    IProdConsBuffer buffer;

    public MProducer(IProdConsBuffer buffer ) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int n = new Random().nextInt((buffer.getBufSz() - 1)) + 1;
        prodTime = new Random().nextInt(2000);

        m = new Message(prodTime);
        m.create();

        try {
            buffer.put(m, n);
            System.out.println("message " + m.getNumber() + " produce by " + getName() + " in " + n + " examplary");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
