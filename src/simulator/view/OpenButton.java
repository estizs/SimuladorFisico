package simulator.view;

import java.awt.event.ActionEvent;

import javax.swing.*;

public class OpenButton extends BasicButton{
	public OpenButton() {
		super("resources/icons/open.png");
		this.addActionListener(new OpenButtonListener());
	}
}
