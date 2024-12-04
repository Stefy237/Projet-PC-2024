package prodcons.vadd.o1;

import utils.IProdConsBuffer;
import utils.Message;
import utils.Task;

public class ProdConsBuffer implements IProdConsBuffer {

    protected int bufSz;
    protected Task[] tasks;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;

    protected int in = 0;
    protected int out = 0;
    
    public ProdConsBuffer(int bufSz) {
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
    public void put(Message m, int n) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public int getBufSz() {
        return bufSz;
    }
}
