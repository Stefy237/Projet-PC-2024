package prodcons.vadd.o2;

import utils.IProdConsBuffer;
import utils.Message;

public class TaskExecutor implements IProdConsBuffer {

    protected int bufSz;
    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;
    protected int maxCons = 5;

    protected int in = 0;
    protected int out = 0;
    
    public TaskExecutor(int bufSz) {
        this.bufSz = bufSz;
        messages = new Message[bufSz];
    }

    @Override
    public synchronized void put(Message t) throws InterruptedException {
        while (nfull == bufSz) {
            wait();
        }
        t.setNumber(tot);
        nfull++;
        tot++;
        messages[in] = t;
        in = (in + 1)%bufSz;
        notifyAll();
    }

    @Override
    public void put(Message m, int n) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public Message get() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public void execute() {
        int nCons = 1;
        while (nCons !=0 && nfull != 0) {
            
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
    
}
