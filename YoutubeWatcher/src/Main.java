import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;

import javax.swing.JComboBox;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 6931474089027994376L;
	
	public static boolean running = false;
	public static Window win;
	public static Window jcb_win;
	
	public String video_id = "YbJOTdZBX1g"; //<-- Put youtube id here (The text after ?v= in the link)
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		new Calculations(video_id);
		
		win = new Window(800, 600, "YouTube Watcher", this, null);
		
		String[] timeTypes = new String[] {"Second", "Minute", "Hour", "Day", "Week", "Month"};
		JComboBox<String> jcb = new JComboBox<String>(timeTypes);
		jcb_win = new Window(100,100, "Extra thing", jcb, jcb);
		
		
		this.start();
		
		
	}
	

	public void start() {
		running = true;
		this.run();
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Calculations.calculations();
			render();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		Graphics2D g;
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		g = (Graphics2D) bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		g.setColor(Color.BLUE);
		if(Calculations.views_inc == 0)
			g.drawString("Views: " + Calculations.views, 800/3, 600/3 - 100);
		else
			g.drawString("Views: " + Calculations.views + " (" + isPositive(Calculations.views_inc) + Calculations.views_inc + " Views)", 800/3, 600/3 - 100);
		
		g.drawString("Total since started: " + isPositive(Calculations.inc_likes_totaled) + Calculations.inc_likes_totaled + " views(Average: " + new DecimalFormat("#######.##").format(Calculations.avg_inc_views) + " per second)", 800/3, 600/3 - 100 + 20);
		
		
		g.setColor(Color.GREEN);
		if(Calculations.likes_inc == 0)
			g.drawString("Likes: " + Calculations.likes_ytr, 800/3, 600/3);
		else
			g.drawString("Likes: " + Calculations.likes_ytr + " (" + isPositive(Calculations.likes_inc) + Calculations.likes_inc + " Likes)", 800/3, 600/3);
		
		g.drawString("Total since started: " + isPositive(Calculations.inc_likes_totaled) + Calculations.inc_likes_totaled + " likes(Average: " + new DecimalFormat("#######.##").format(Calculations.avg_inc_likes) + " per second)", 800/3, 600/3+20);
		
		g.setColor(Color.RED);
		if(Calculations.dislikes_inc == 0)
			g.drawString("Dislikes: " + Calculations.dislikes_ytr, 800/3, 2*600/3);
		else
			g.drawString("Dislikes: " + Calculations.dislikes_ytr + " (" + isPositive(Calculations.dislikes_inc) + Calculations.dislikes_inc + " Dislikes)", 800/3, 2*600/3);
		
		g.drawString("Total since started: " + isPositive(Calculations.inc_dislikes_totaled) + Calculations.inc_dislikes_totaled + " dislikes (Average: " + new DecimalFormat("#######.##").format(Calculations.avg_inc_dislikes) + " per second)", 800/3, 2*600/3+20);
		
		if(Calculations.ratio_total > 1)
			g.drawString("Ratio(Total): " + new DecimalFormat("#######.##").format(Calculations.ratio_total) + " dislikes per like.", 800/3, 600/2 - 10);
		else {
			g.setColor(Color.GREEN);
			g.drawString("Ratio(Total): " + new DecimalFormat("#######.##").format((double) 1 / Calculations.ratio_total) + " likes per dislike.", 800/3, 600/2 - 10);
		}
		if(Calculations.ratio_increase > 1)
			g.drawString("Ratio(Change): " + new DecimalFormat("#######.##").format(Calculations.ratio_increase) + " dislikes gained per like gained", 800 / 3, 600 / 2 + 10);
		else {
			g.setColor(Color.GREEN);
			g.drawString("Ratio(Change): " + new DecimalFormat("#######.##").format((double) 1 / Calculations.ratio_increase) + " likes gained per dislike gained", 800 / 3, 600 / 2 + 10);
		}
		
		g.setColor(Color.GREEN);
		g.drawString("Likes per " + jcb_win.jcb.getSelectedItem() + ": " + new DecimalFormat("#######.##").format(calculateTimewise(jcb_win.jcb.getSelectedIndex(), Calculations.avg_inc_likes)), 800/3, 600-80);
		g.setColor(Color.RED);
		g.drawString("Disikes per " + jcb_win.jcb.getSelectedItem() + ": " + new DecimalFormat("#######.##").format(calculateTimewise(jcb_win.jcb.getSelectedIndex(), Calculations.avg_inc_dislikes)), 800/3, 600-60);
		g.setColor(Color.BLUE);
		g.drawString("Views per " + jcb_win.jcb.getSelectedItem() + ": " + new DecimalFormat("#######.##").format(calculateTimewise(jcb_win.jcb.getSelectedIndex(), Calculations.avg_inc_views)), 800/3, 600 - 100);
		
		g.dispose();
		bs.show();
	}
	
	private double calculateTimewise(int selectedIndex, double in) {
		switch(selectedIndex) {
		case 0:
			return in;
		case 1:
			return in * 60;
		case 2:
			return in * 60 * 60;
		case 3:
			return in * 60 * 60 * 24;
		case 4:
			return in * 60 * 60 * 24 * 7;
		case 5:
			return in * 60 * 60 * 24 * 7 * 4;
		}
		return 0;
	}

	public String isPositive(int i) {
		if(i > 0) return "+";
		return "";
	}
	
}