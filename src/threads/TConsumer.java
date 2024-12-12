package threads;

import utils.IProdConsBuffer;
import utils.Task;

public class TConsumer extends Thread {

    IProdConsBuffer buffer;
    private long lastActivityTime;
    
    public TConsumer(IProdConsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Task t = (Task) buffer.get();
                t.run();
                lastActivityTime = System.currentTimeMillis();
                System.out.println("Task " + t.getNumber() + " execute by " + getName());
            } catch (InterruptedException e) {
                System.out.println("consumer " + getName() + " was interrupted"); 
                break;
            }
        }
    } 

    public boolean isUnuse() {
        return (System.currentTimeMillis() - lastActivityTime) > 3000;
    }
    
}
