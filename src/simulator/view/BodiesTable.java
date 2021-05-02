package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	public BodiesTable(Controller ctrl){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));
		// Crear una instancia BodiesTableModel (se le pasa a la JTable)
		BodiesTableModel model = new BodiesTableModel(ctrl);
		JTable bodiesTable = new JTable(model);
		// A�adir JTable a this con un JScrollPane
		JScrollPane pane = new JScrollPane(bodiesTable);
		this.add(pane);
	}
}
