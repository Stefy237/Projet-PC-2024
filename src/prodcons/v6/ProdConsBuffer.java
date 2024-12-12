package prodcons.v6;

import java.util.concurrent.Semaphore;

import utils.IProdConsBuffer;
import utils.Message;

public class ProdConsBuffer  implements IProdConsBuffer{

    protected int bufSz;

    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;

    protected Semaphore notRemaining = new Semaphore(0, true);
    protected Semaphore stillRemaining = new Semaphore(0, true);
    protected int remainingMessage;

    protected int in = 0;
    protected int out = 0;

    public ProdConsBuffer(int bufSz) {
        this.bufSz = bufSz;
        messages = new Message[bufSz];
    }
    
    @Override
    public synchronized void put(Message m) throws InterruptedException {
        while (nfull == bufSz) {
            wait();
        }
        m.setNumber(tot);
        nfull++;
        tot++;
        messages[in] = m;
        in = (in + 1)%bufSz;
        notifyAll();
    }

    @Override
    public synchronized void put(Message m, int n) throws InterruptedException {
        while(nfull + n > bufSz) {
            wait();
        }
        m.setNumber(tot);
        tot+=n;
        remainingMessage = n;

        for(int i=0; i<n; i++) {
            messages[in] = m;
            in = (in + 1)%bufSz;
        }

        nfull+=n;

        notifyAll();
        System.out.println("Producer put " + n + " copies of message " + m.getNumber());
        notRemaining.acquire();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        while (nfull == 0) {
            wait();
        }

        Message m = messages[out];
        messages[out] = null;
        out = (out + 1)%bufSz;

        if(--remainingMessage !=0) {
            stillRemaining.acquire();
        } else {
            for(int i=0; i<stillRemaining.getQueueLength(); i++) {
                stillRemaining.release();
            }
            notRemaining.release();
        }
        
        nfull--;
        notifyAll(); 
        //System.out.println("Consumer got message " + m.getNumber());

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

    public int getBufSz() {
        return bufSz;
    }
    
}
