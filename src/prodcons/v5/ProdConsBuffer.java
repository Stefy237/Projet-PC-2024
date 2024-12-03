package prodcons.v5;

import java.util.Arrays;

import utils.*;

public class ProdConsBuffer implements IProdConsBuffer {
    protected int bufSz;
    protected Message[] messages;

    protected int nfull = 0;
    protected int tot = 0;
    protected int nProd = 0;
    boolean firstCome = true;
    Message[] msgs;

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
    public synchronized Message get() throws InterruptedException {
        while (nfull == 0) {
            wait();
        }
        nfull--;
        Message m = messages[out];
        messages[out] = null;
        out = (out + 1)%bufSz;
        notifyAll(); 
        
        return m;
    }

    @Override
    public synchronized Message[] get(int k) throws InterruptedException { 
        if(firstCome) {
            msgs = new Message[k];
            for (int i=0; i<msgs.length; i++) {
                msgs[i] = null;
            }
            firstCome = false;
        }

        if(k <= bufSz) {
            while (nfull < k) {
                wait();
            }
            nfull = nfull -  k;
            
            int pos=0;
            int i =0;
            while (msgs[i] != null) {
                i++;
            }
            pos = i;
            
            int j = 0;
            while(j < k) {
                msgs[pos + j] = messages[out];
                messages[out] = null;
                out = (out + 1)%bufSz; 
                j++;
            }

            firstCome = true; 
            notifyAll();  
        } else {
            while(nfull < bufSz) {
                wait();
            }
            nfull = 0;

            int pos=0;
            int i =0;
            while (msgs[i] != null) {
                i++;
            }
            pos = i;

            int j = 0;
            while(j < bufSz) {
                msgs[pos + j] = messages[out];
                messages[out] = null;
                out = (out + 1)%bufSz; 
                j++;
            }
            get(k-bufSz);
            
        }

        return msgs;
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
