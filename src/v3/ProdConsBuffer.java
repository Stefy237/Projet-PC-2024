package v3;

import java.util.concurrent.Semaphore;

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
    
    Semaphore notFull;
    Semaphore notEmpty;
    Semaphore mutex;

    public ProdConsBuffer(int bufSz) {
        this.bufSz = bufSz;
        messages = new Message[bufSz];

        notFull = new Semaphore(bufSz);
        notEmpty = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    @Override
    public void put(Message m) throws InterruptedException {
        notFull.acquire();
        mutex.acquire();

        messages[in] = m;
        in = (in + 1)%bufSz;

        m.setNumber(tot);
        nfull++;
        tot++;

        mutex.release();
        notEmpty.release();
    }

    @Override
    public Message get() throws InterruptedException {
        notEmpty.acquire();
        mutex.acquire();

        Message m = messages[out];
        out = (out + 1)%bufSz;

        nfull--;
        mutex.release();
        notFull.release();

        return m;
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
    
}
