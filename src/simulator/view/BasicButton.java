package simulator.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class BasicButton extends JButton implements ActionListener{
	private static final Dimension dim = new Dimension(42, 32);
	
	public BasicButton(String icon){
		super();
		this.setIcon(new ImageIcon(icon));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
	}
}
