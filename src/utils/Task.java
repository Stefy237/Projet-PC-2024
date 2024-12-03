package utils;

public class Task extends Message implements Runnable{

    private int runTime;
    
    public Task (int runTime) {
        super(runTime);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
