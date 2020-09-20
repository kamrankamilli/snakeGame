import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame implements KeyListener
{

	JPanel j = new JPanel();
	public static int width = 800;
	public static int height = 600;
	Snake s;
	JLabel gameOver = new JLabel();
	
	public Window() {
		
		this.setTitle("Snake");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setBackground(Color.LIGHT_GRAY);
		gameOver.setBounds((width/2)-40,0,100,20);
		this.addKeyListener(this);
		this.setContentPane(j);
		this.setVisible(true);
		
		s=new Snake(this.getGraphics(),3);

		
		
	}
	public void startGame()
	{
		s.play();
		gameOver();
	}
	public void gameOver() {
		gameOver.setText("Game Over!");
		j.add(gameOver);
		j.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP && s.direction !=3)
			s.direction=1;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && s.direction !=4)
			s.direction=2;
		if(e.getKeyCode()==KeyEvent.VK_DOWN && s.direction !=1)
			s.direction=3;
		if(e.getKeyCode()==KeyEvent.VK_LEFT && s.direction !=2)
			s.direction=4;
		if(e.getKeyCode()==KeyEvent.VK_W)
			s.speed = s.speed/2;
		
		if(e.getKeyCode()==KeyEvent.VK_S)
			s.speed = s.speed*2;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}