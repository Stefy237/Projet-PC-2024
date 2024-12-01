package threads;

import utils.*;

public class Consumer extends Thread{

    IProdConsBuffer buffer;
    
    public Consumer(IProdConsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Message m = buffer.get();
                m.proceed();
                System.out.println("message consume by " + getName());
            } catch (InterruptedException e) {
                System.out.println("consumer " + getName() + " was interrupted"); 
                break;
            }
        }
    } 
}
