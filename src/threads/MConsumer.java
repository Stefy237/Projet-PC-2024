package threads;

import java.util.Random;

import utils.IProdConsBuffer;
import utils.Message;

public class MConsumer extends Thread {

    IProdConsBuffer buffer;
    
    public MConsumer(IProdConsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                int k = new Random().nextInt(5) + 1;
                Message[] messages = buffer.get(k);

                for(Message m : messages) {
                    m.proceed();
                    System.out.println("message " + m.getNumber() + " consume by " + getName());
                }
                
            } catch (InterruptedException e) {
                System.out.println("consumer " + getName() + " was interrupted"); 
                break;
            }
        }
    }
    
}
