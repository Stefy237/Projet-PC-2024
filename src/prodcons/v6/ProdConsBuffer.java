package prodcons.v6;

import utils.IProdConsBuffer;
import utils.Message;

public class ProdConsBuffer  implements IProdConsBuffer{

    protected int bufSz;

    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;

    protected int remainingCopies = 0;
    protected int activeConsumers = 0;
    protected boolean messageFullyConsumed = false;

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
        remainingCopies = n;
        messageFullyConsumed = false;
        activeConsumers = 0;

        for(int i=0; i<n; i++) {
            messages[in] = m;
            in = (in + 1)%bufSz;
        }

        nfull+=n;

        notifyAll();
        
        while (!messageFullyConsumed) {
            wait();
        }
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        while (nfull == 0 || messageFullyConsumed) {
            wait();
        }

        Message m = messages[out];
        activeConsumers++;

        if(activeConsumers == remainingCopies) {
            messageFullyConsumed = true;
            messages[out] = null;
            out = (out + 1)%bufSz;
            nfull -= remainingCopies;
        } else {
            while (!messageFullyConsumed) {
                wait();
            }
        }
        
        notifyAll(); 

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
