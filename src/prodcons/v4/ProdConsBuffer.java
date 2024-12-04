package prodcons.v4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import utils.IProdConsBuffer;
import utils.Message;

public class ProdConsBuffer implements IProdConsBuffer{
    protected int bufSz;
    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;

    protected int in = 0;
    protected int out = 0;

    protected ReentrantLock lock = new ReentrantLock(true);
    protected Condition notFull = lock.newCondition();
    protected Condition notEmpty = lock.newCondition();
    
    public ProdConsBuffer(int bufSz) {
        this.bufSz = bufSz;
        messages = new Message[bufSz];
    }

    @Override
    public void put(Message m) throws InterruptedException {
        lock.lock();
        
        try{
            while (nfull == bufSz) {
                notFull.await();
            }
    
            m.setNumber(tot);
            nfull++;
            tot++;
            messages[in] = m;
            in = (in + 1)%bufSz;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(Message m, int n) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public Message get() throws InterruptedException {
        lock.lock();

        try {
            while (nfull == 0) {
                notEmpty.await();;
            }
                
            nfull--;
            Message m = messages[out];
            messages[out] = null;
            out = (out + 1)%bufSz;
            notFull.signalAll();
            
            return m;
        } finally {
            lock.unlock();
        }
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
    public int getBufSz() {
        return bufSz;
    }
    
}
