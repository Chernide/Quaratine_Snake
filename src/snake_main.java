import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO; 
import javax.swing.ImageIcon; 
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.Random;

public class snake_main extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L; 
	private static final int PREF_W = 900; 
	private static final int PREF_H = 900;
	private static final int SEG_W = 30; 
	private static final int SEG_H = 30;
	private RenderingHints hints = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	private Font font_1 = new Font("Cooper Black", Font.PLAIN, 60);
	private Font bigger = new Font("Cooper Black", Font.BOLD, 25);
	private ArrayList<Sprite> segments; 
	private Sprite apple;
	private boolean EndGame = false;
	private int timer = 200;
	Random rand = new Random();
	private boolean apple_on_screen = false;
	
	private Image snake_d;
	private Image snake_u;
	private Image snake_l;
	private Image snake_r;
	private Image apple_img; 
	private int score = 0;
	private int apples_eaten = 0;
	
	public snake_main() {
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		
		segments = new ArrayList<>(); 
		
		Sprite x = new Sprite(((PREF_W/2)-SEG_W), ((PREF_H/2)-SEG_H), SEG_W, SEG_H);
		segments.add(x);
		

		apple = new Sprite(rand.nextInt(PREF_W/SEG_W) * SEG_W, rand.nextInt(PREF_H/SEG_H) * SEG_H, SEG_W, SEG_H);
		apple_on_screen = true;
		
		snake_d = new ImageIcon(this.getClass().getResource("snake_down.png")).getImage();
		snake_u = new ImageIcon(this.getClass().getResource("snake_up.png")).getImage();
		snake_l = new ImageIcon(this.getClass().getResource("snake_left.png")).getImage();
		snake_r = new ImageIcon(this.getClass().getResource("snake_right.png")).getImage();
		
		apple_img = new ImageIcon(this.getClass().getResource("apple4.png")).getImage();
		
		new Timer(timer, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		}).start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(hints);
		g2.setColor(Color.black);
		g2.fillRect(0, 0, PREF_W, PREF_H);
		if(!EndGame) {
			g2.setColor(Color.white);
			segments.get(0).drawSprite(g2);
			for(int i = 1; i < segments.size(); i++) {
				g2.setColor(Color.green);
				segments.get(i).drawSprite(g2);
				g2.setColor(Color.black);
				g2.drawRect(segments.get(i).getX_pos(), segments.get(i).getY_pos(), SEG_W, SEG_H);
			}
			g2.setColor(Color.red);
			g2.drawImage(apple_img, apple.getX_pos(), apple.getY_pos(), SEG_W, SEG_H, this);
			
			g2.setColor(Color.black);
			g2.setFont(bigger);
			g2.drawString("Apples Eaten: "+apples_eaten, 15, PREF_H+50);

			g2.drawString("Score: " + score, 700, PREF_H+50);

		} else {
			g2.setFont(font_1);
			g2.setColor(Color.white);
			FontMetrics fm = g2.getFontMetrics();
			String s = "Game Over!";
			int x = (PREF_W - fm.stringWidth(s))/2;
			int y = ((PREF_H - fm.getHeight())/2) + fm.getAscent();
			g2.drawString("GAME OVER!", x, y);
			g2.setFont(bigger);
			FontMetrics fm2 = g2.getFontMetrics();
			String s2 = "Press 'r' to restart";
			int x2 = (PREF_W - fm2.stringWidth(s2))/2;
			g2.drawString(s2, x2, y+fm.getAscent());
			g2.setColor(Color.black);
			g2.drawString("Apples Eaten: "+apples_eaten, 15, PREF_H+50);
			g2.drawString("Score: " + score, 700, PREF_H+50);

		}
		
		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H+100);
	}
	
	public void moveSnake() {
		for(int i = segments.size() - 1; i > 0; i--) {
			segments.get(i).setX_pos(segments.get(i - 1).getX_pos());
			segments.get(i).setY_pos(segments.get(i - 1).getY_pos());
			segments.get(i).set_direction(segments.get(i-1).get_direction());
		}
		
		if(segments.get(0).get_direction() == 1) {
			segments.get(0).setX_pos(segments.get(0).getX_pos() - SEG_W);
			if(segments.get(0).getX_pos() < 0) {
				EndGame = true;
			}
		} else if(segments.get(0).get_direction() == 2) {
			segments.get(0).setY_pos(segments.get(0).getY_pos() - SEG_H);
			if(segments.get(0).getY_pos() < 0) {
				EndGame = true;
			}
		} else if(segments.get(0).get_direction() == 3) {
			segments.get(0).setX_pos(segments.get(0).getX_pos() + SEG_W);
			if(segments.get(0).getX_pos() > PREF_W) {
				EndGame = true;
			}
		} else if(segments.get(0).get_direction() == 4) {
			segments.get(0).setY_pos(segments.get(0).getY_pos() + SEG_H);
			if(segments.get(0).getY_pos() > PREF_H) {
				EndGame = true;
			}
		}
//		}
	}
	
	public void place_apple() {
		boolean valid = false;
		int x_pos = 0;
		int y_pos = 0;
		while(!valid) {
			x_pos = (rand.nextInt(PREF_W/SEG_W) * SEG_W);
			y_pos = (rand.nextInt(PREF_H/SEG_H) * SEG_H);
			for(int i = 0; i < segments.size(); i++) {
				if(segments.get(i).getX_pos() != x_pos) {
					valid = true;
				} else if(segments.get(i).getY_pos() != y_pos) {
					valid = true;
				}
			}
		}
		apple.setX_pos(x_pos);
		apple.setY_pos(y_pos);
		apple_on_screen = true;
	}
	
	public void add_segment() {
		Sprite new_seg = new Sprite(); 
		int i = segments.size();
		if(segments.get(i - 1).get_direction() == 1) {
			new_seg.setX_pos(segments.get(i - 1).getX_pos() + SEG_W);
			new_seg.setY_pos(segments.get(i - 1).getY_pos());
			new_seg.set_direction(segments.get(i-1).get_direction());
		} else if (segments.get(i - 1).get_direction() == 2) {
			new_seg.setX_pos(segments.get(i - 1).getX_pos());
			new_seg.setY_pos(segments.get(i - 1).getY_pos() + SEG_H);
			new_seg.set_direction(segments.get(i-1).get_direction());
		} else if (segments.get(i - 1).get_direction() == 3) {
			new_seg.setX_pos(segments.get(i - 1).getX_pos() - SEG_W);
			new_seg.setY_pos(segments.get(i - 1).getY_pos());
			new_seg.set_direction(segments.get(i-1).get_direction());
		}  else if (segments.get(i - 1).get_direction() == 4) {
			new_seg.setX_pos(segments.get(i - 1).getX_pos());
			new_seg.setY_pos(segments.get(i - 1).getY_pos() - SEG_H);
			new_seg.set_direction(segments.get(i-1).get_direction());
		}
		new_seg.setWidth(SEG_W);
		new_seg.setHeight(SEG_H);
		segments.add(new_seg);
	}
	
	
	public void check_apple_eating() {
		if(segments.get(0).getBounds().intersects(apple.getBounds())) {
			add_segment();
			apples_eaten++;
			place_apple();
			score += 50;
			apple_on_screen = true;
			if (timer > 50) {
				timer = timer - 50;
			}
			else if (timer > 15){
				timer = timer - 5;
			}
		}
	}
	
	public void check_head_collision() {
		for(int i = 1; i < segments.size(); i++) {
			if(segments.get(0).getBounds().intersects(segments.get(i).getBounds())) {
				EndGame = true;
			}
		}
	}
	
	public void reset() {
		apples_eaten = 0; 
		score = 0;
		EndGame = false; 
		Sprite x = new Sprite(((PREF_W/2)-SEG_W), ((PREF_H/2)-SEG_H), SEG_W, SEG_H);
		x.set_direction(0);
		segments.clear();
		segments.add(x);
		
	}
	
	public void update() {
		if(!EndGame) {
			moveSnake();
			if(!apple_on_screen) {
				place_apple();
			} else {
				check_apple_eating();
			}
			check_head_collision(); 
		} else {
			repaint();
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT && segments.get(0).get_direction() != 3) {
			segments.get(0).set_direction(1);
		} else if (key == KeyEvent.VK_UP && segments.get(0).get_direction() != 4) {
			segments.get(0).set_direction(2);
		} else if (key == KeyEvent.VK_RIGHT && segments.get(0).get_direction() != 1) {
			segments.get(0).set_direction(3);
		} else if (key == KeyEvent.VK_DOWN && segments.get(0).get_direction() != 2) {
			segments.get(0).set_direction(4);
		} else if (key == KeyEvent.VK_R) {
			reset();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
      
	}

	@Override
	public void keyTyped(KeyEvent e){
       
	}
	
	public static void createAndShowGUI() {
		snake_main gamePanel = new snake_main(); 
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(gamePanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
		
	

}
