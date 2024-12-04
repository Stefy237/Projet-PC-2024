package threads;

import utils.IProdConsBuffer;
import utils.Task;

public class TConsumer extends Thread {

    IProdConsBuffer buffer;
    
    public TConsumer(IProdConsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Task t = (Task) buffer.get();
                t.run();
                System.out.println("Task " + t.getNumber() + " execute by " + getName());
            } catch (InterruptedException e) {
                System.out.println("consumer " + getName() + " was interrupted"); 
                break;
            }
        }
    } 

    public boolean isUnuse() {
        boolean use = false;

        while(getState() == Thread.State.WAITING && !isInterrupted()) {
            long lastActivityTime = System.currentTimeMillis();

            use = (System.currentTimeMillis() - lastActivityTime) < 3000; 
        }

        return use;
    }
    
}
