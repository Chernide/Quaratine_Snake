import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;

public class Sprite {

	private int num;
	private int x_pos; 
	private int y_pos; 
	private int width; 
	private int height;
	private int next_x; 
	private int next_y; 
	private int old_x; 
	private int old_y;
	private int direction; 

	
	public Sprite() {}; 
	public Sprite(int x, int y, int w, int h){
		this.x_pos = x;
		this.y_pos = y; 
		this.old_x = x; 
		this.old_y = y;
		this.next_x = -1; 
		this.next_y = -1;
		this.width = w; 
		this.height = h;
		this.direction = 0;
	}
	
	public int get_next_x() {
		return this.next_x;
    }
	
    public void set_next_x(int x) {
        this.next_x = x;
    }
    
	public int get_direction() {
		return this.direction;
    }
	
    public void set_direction(int d) {
        this.direction = d;
    }
	
	public int get_next_y() {
		return this.next_y;
    }
	
    public void set_next_y(int y) {
        this.next_y = y;
    }
	
	public int get_old_x() {
		return this.old_x;
    }
	
    public void set_old_x(int x) {
        this.old_x = x;
    }
	
	public int get_old_y() {
		return this.old_y;
    }
	
    public void set_old_y(int y) {
        this.old_y = y;
    }
	
	public int getX_pos() {
		return x_pos;
    }
    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }
    public int getY_pos() {
        return y_pos;
    }
    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
  
    
    public Rectangle getBounds(){
		int x = this.getX_pos();
		int y = this.getY_pos();
		int w = this.getWidth();
		int h = this.getHeight();
		Rectangle bounds = new Rectangle(x,y,w,h);
    	return bounds;
    	
    }
    
    public void drawSprite(Graphics2D g2){
    	g2.fill(getBounds());
    }

}
