package simulator.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExitDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExitDialog(JFrame window) {
		super(window, "Exit");
		JPanel panelC = new JPanel();
		panelC.setLayout(new BoxLayout(panelC, BoxLayout.Y_AXIS));
		panelC.add(new JLabel("You're about to exit the application"));
		panelC.add(new JLabel("Exit and close the application?"));
		JPanel panelB = new JPanel();
		panelB.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panelB.add(ok);
		panelB.add(cancel);
		panelC.add(panelB);
		add(panelC);
		setVisible(true);
		setBounds(window.getWidth() / 2 - 200, window.getHeight() / 2 - 50, 400, 100);
	}
}
