package prodcons.vadd.o2;

import threads.TConsumer;
import utils.IProdConsBuffer;
import utils.Message;
import utils.Task;

public class TaskExecutor implements IProdConsBuffer {

    protected int bufSz;
    protected Task[] tasks;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;
    protected int maxCons = 5;
    protected int nCons = 0;

    protected int in = 0;
    protected int out = 0;
    
    public TaskExecutor (int bufSz) {
        this.bufSz = bufSz;
        tasks = new Task[bufSz];
    }

    @Override
    public synchronized void put(Message t) throws InterruptedException {
        while (nfull == bufSz) {
            wait();
        }
        t.setNumber(tot);
        nfull++;
        tot++;
        tasks[in] = (Task) t;
        in = (in + 1)%bufSz;
        notifyAll();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        while (nfull == 0) {
            wait();
        }
        nfull--;
        Message t = tasks[out];
        tasks[out] = null;
        out = (out + 1)%bufSz;
        notifyAll(); 
        
        return t;
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public void execute() {
        while (nfull != 0) {
            while(nCons < maxCons && nfull > nCons) {
                TConsumer c = new TConsumer(this);
                c.start();

                nCons++;

                boolean unUse = false;

                while(c.getState() == Thread.State.WAITING && !c.isInterrupted()) {
                    long lastActivityTime = System.currentTimeMillis();
        
                    unUse = (System.currentTimeMillis() - lastActivityTime) > 3000; 

                    if(unUse) {
                        c.interrupt();
                        nCons--;
                        try {
                            c.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public int nmsg() {
        return nfull;
    }

    @Override
    public int totmsg() {
        return tot;
    }

    public int getNumberOfTerminatedProdThread() {
        return nProd;
    }

    public synchronized void setNumberOfTerminatedProdThread() {
        nProd++;
    }

    @Override
    public int getBufSz() {
        return bufSz;
    }

    @Override
    public void put(Message m, int n) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }
    
}
