import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;


import javax.swing.JPanel;
import javax.swing.Timer;
public class Snake extends JPanel
{
	public int score;
	public int direction=4;
	public int length;
	Graphics g;
	public boolean play = true;
	public int nfood = 15;
	public int nnfood = 15;
	Scanner sc;
	ArrayList<Body> b =new ArrayList<Body>();
	ArrayList<Food> f =new ArrayList<Food>();
	ArrayList<NonFood> nf =new ArrayList<NonFood>();
	Timer t;
	static int speed=200;
	
	
	
	public Snake(Graphics g, int length) {
		
		this.g=g;
		this.length=length;
		sc=new Scanner(System.in);
	}
	public void play()
	{

		createSnake();
		while(play==true)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Window.width, Window.height);
			createNonFood();
			createFood();
			drawFood();
			drawNonFood();
			drawSnake();
			sleep(speed);
			move();
			checkCollision();
		}
	}
	public void sleep(int time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
	}
	public void createNonFood() {
		int randX;
		int randY;
		Boolean create = true;
		while(nf.size()<nnfood) {
			create = true;
			int widthWindow = ((Window.width-20)/10)-2;
			int heightWindow = ((Window.height-20)/10)-2;
			randX = (int)((Math.random()*(widthWindow))+3);
			randY = (int)((Math.random()*(heightWindow))+3);
			randX = (randX*10);
			randY = (randY*10);
			
			for(int i=0; i <b.size(); i++) {
				Body exist = b.get(i);
				if(randX == exist.posX && randX == exist.posY )
				{
					create=false;
				}
				
			}
			if(create == true)
			{
				nf.add(new NonFood(randX,randY, Color.BLUE));
			}
		}
	}
	
	public void createFood() {
		int randX;
		int randY;
		Boolean create = true;
		while(f.size()<nfood) {
			create = true;
			int widthWindow = ((Window.width-20)/10)-2;
			int heightWindow = ((Window.height-20)/10)-2;
			
			randX = (int)((Math.random()*(widthWindow))+3);
			randY = (int)((Math.random()*(heightWindow))+3);
			randX = (randX*10);
			randY = (randY*10);
			
			for(int i=0; i <b.size(); i++) {
				Body exist = b.get(i);
				if(randX == exist.posX && randX == exist.posY )
				{
					create=false;
				}
				
			}
			if(create == true)
			{
				f.add(new Food(randX,randY, Color.WHITE));
			}
		}
	}
	
	public void createSnake() {
		for(int i = 0; i<this.length; i++)
		{
			int height;
			height = ((int) Window.height/2)/10;
			height*=10;
			if(i==0)
			{
				b.add(new Body(Window.width/2+((i)*10),height, Color.RED)); 
			}
			else if(i>0)
				b.add(new Body(Window.width/2+((i)*10),height, Color.GREEN.darker()));
		}
	}
	
	
	public void drawFood() 
	{
		for(int i=0; i<f.size(); i++)
		{
			Food food = f.get(i);
			
			g.setColor(food.c);
			g.fillOval(food.posX, food.posY, 10, 10);
		}
	}
	public void drawNonFood() {
		for(int i=0; i<nf.size(); i++)
		{
			NonFood nonfood = nf.get(i);
			
			g.setColor(nonfood.c);
			g.fillOval(nonfood.posX, nonfood.posY, 10, 10);
		}
	}
	public void drawSnake() {
		for(int i=0; i<b.size(); i++)
		{
			Body body = b.get(i);
			
			g.setColor(body.c);
			g.fillOval(body.posX, body.posY, 10, 10);
			showScore();
			showTimer();
			showSpeed();
		}
	}
	public void showSpeed() {
		g.setFont(new Font("TimesRoman",Font.PLAIN,15));
		g.setColor(Color.WHITE);
		g.drawString("Speed: "+speed, 10, Window.height-45);	
	}
	public void showTimer() {
		g.setFont(new Font("TimesRoman",Font.PLAIN,15));
		g.setColor(Color.WHITE);
		g.drawString("Time: " , 10, Window.height-25);
	}
	public void showScore() {
		g.setFont(new Font("TimesRoman",Font.PLAIN,15));
		g.setColor(Color.WHITE);
		g.drawString("Score: "+score, 10, Window.height-10);
		g.drawString("Length: "+b.size(), 10, Window.height-60);
	}
	public void checkCollision() {
		for(int i = 0; i<f.size(); i++) {
			Food checkFood = f.get(i);
			Body checkSnake = b.get(0);
			Body lastPosition = b.get(b.size()-1);
			if(checkFood.posX == checkSnake.posX && checkFood.posY == checkSnake.posY) {
				f.remove(i);
				b.add(new Body(200+((lastPosition.posX)+10),0,Color.GREEN.darker()));
				length++;
				score++;
			}
		}
		for(int i = 0; i<nf.size(); i++) {
			NonFood checkNonFood = nf.get(i);
			Body checkSnake = b.get(0);
			Body lastPosition = b.get(b.size()-1);
			if(checkNonFood.posX == checkSnake.posX && checkNonFood.posY == checkSnake.posY) {
				nf.remove(i);
				b.remove(lastPosition);
				length--;
				score--;
				
			}
		}
		for( int i = 1; i<b.size();i++)
		{
			Body bodySnake = b.get(i);
			Body headSnake = b.get(0);
			if(headSnake.posX == bodySnake.posX && headSnake.posY==bodySnake.posY)
				play=false;
		}
		Body headSnake = b.get(0);
		if(headSnake.posX<10)
			play=false;
		if(headSnake.posX>(Window.width-20))
			play=false;
		if(headSnake.posY<30)
			play=false;
		if(headSnake.posY>(Window.height-20))
			play=false; 
		if(b.size()<2)
			play=false;
	}
	public void move()
	{
		int px,py;
		for(int i=b.size()-1; i>0; i--)
		{
			Body body= b.get(i-1);
			px = body.posX;
			py = body.posY;
			body= b.get(i);
			body.posX = px;
			body.posY = py;
			
		}
		Body b1 = b.get(0); 
		if (direction == 1)
			b1.posY-=10;
		
		if (direction == 2)
			b1.posX+=10;
		
		if (direction == 3)
			b1.posY+=10;
		 
		if (direction == 4)
			b1.posX-=10;
		
	}
}