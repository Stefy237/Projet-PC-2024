package utils;

public class Message {
    
    private int prodTime;
    private int consTime;
    private int number;

    public Message(int prodTime) {
        this.prodTime = prodTime;
        this.consTime = ((int) Math.random()) * 3000;
    }

    public Message(int prodTime, int constTime) {
        this.prodTime = prodTime;
        this.consTime = constTime;
    }

    public void create() {
        try {
            Thread.sleep(prodTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void proceed() {
        try {
            Thread.sleep(consTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
