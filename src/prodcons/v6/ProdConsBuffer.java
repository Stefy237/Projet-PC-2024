package prodcons.v6;

import utils.IProdConsBuffer;
import utils.Message;

public class ProdConsBuffer  implements IProdConsBuffer{

    protected int bufSz;

    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;

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
        while(nfull == bufSz - n - 1) {
            wait();
        }
        m.setNumber(tot);
        nfull+=n;
        tot+=n;

        int i = 0;
        while (i<n) {
            messages[in] = m;
            in = (in + 1)%bufSz;
            i++;
        }

        notifyAll();
    }

    @Override
    public synchronized Message get() throws InterruptedException {
        while (nfull == 0) {
            wait();
        }

        int n = 1;
        Message m = messages[out];
        while(messages[(out + n)%bufSz] != null && m.getNumber() == messages[(out + n)%bufSz].getNumber()) {
            n++;
        }
        nfull--;
        
        messages[out] = null;
        out = (out + 1)%bufSz;
        n--;

        while (n != 0) {
            wait();
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
