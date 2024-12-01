package utils;

public interface IProdConsBuffer {
    
    public void put(Message m) throws InterruptedException;

    public Message get() throws InterruptedException;  //get message in     FIFO order, the oldest message in the buffer (the first one in the buffer)

    public Message[] get(int k) throws InterruptedException;

    public int nmsg(); //return de nober of messages currently available in the buffer

    public int totmsg(); //return the total number of messages that have been put in the buffer since its creation
}
