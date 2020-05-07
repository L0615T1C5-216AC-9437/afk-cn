package afk;

public class pd {
    //data
    private float x;
    private float y;
    private int bb;
    private int ms;
    private int afk;

    public pd() {
        this.x = 0;
        this.y = 0;
        this.bb = 0;
        this.ms = 0;
        this.afk = 0;
    }
    //get
    public float getX() {return x;}
    public float getY() {return y;}
    public int getBb() {return bb;}
    public int getMs() {return ms;}
    public int getAfk() {return afk;}
    //put
    public void putX(int value) {this.x = value;}
    public void putY(int value) {this.y = value;}
    public void putBb(int value) {this.bb = value;}
    public void putMs(int value) {this.ms = value;}
    public void putAfk(int value) {this.afk = value;}
    //iteration
    public void addBb() {this.bb++;}
    public void addMs() {this.ms++;}
    public void addAfk() {this.afk++;}
}
