import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = -8255319694373975038L;
	public JComboBox<String> jcb;
	
		public Window(int w, int h, String title, Component comp, JComboBox<String> jcb) {
			super(title);
			
			this.jcb = jcb;
			this.setSize(w, h);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			this.add(comp);
			
			this.setVisible(true);
			this.pack();
		}
		
		public void stop() {
			this.dispose();
		}
}
