package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;

public class FLSelection extends JDialog {
	private static final long serialVersionUID = 1L;
	// El modelo de la tabla de parámetros de las ForceLaws
	private ForceLawsTableModel valuesModel;
	// La tabla de las ForceLaws
	private JTable values;
	// El comboBox
	private JComboBox<String> cbo;
	// Controller
	private Controller ctrl;
	// La lista de columnas para la tabla
	private static final String[] columnNames = {
			"Key", "Value", "Description"
	};
	
	public FLSelection(JFrame window, Controller ctrl) {
		super(window, "Force Laws Selection");
		this.ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		// Panel principal
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new BorderLayout());
		// Texto descriptivo
		JTextArea desc = new JTextArea(
                "Select a force law and provide values for the parametes in the Value column (default values are used for parametes with no value).");
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setOpaque(false);
		selectionPanel.add(desc, BorderLayout.PAGE_START);
		// Panel para el comboBox y la tabla
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		// Panel de selección fl
		JPanel selection = new JPanel();
		selection.setLayout(new FlowLayout());
		// JComboBox
		cbo = initComboBox();
		// Tabla con valores
		valuesModel = new ForceLawsTableModel(getRowData(getSelectedForceLaw((String) cbo.getSelectedItem())), columnNames);
		values = new JTable(valuesModel);
		center.add(new JScrollPane(values));
		// Label auxiliar
		selection.add(new JLabel("Force Laws: "));
		selection.add(cbo);
		center.add(selection);
		selectionPanel.add(center, BorderLayout.CENTER);
		// Panel de botones
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttons.add(initCancelButton());
		buttons.add(initOKButton());
		selectionPanel.add(buttons, BorderLayout.PAGE_END);
		add(selectionPanel);
		hideFlSelection();
		// Visibilidad y tamaño del cuádro de diálogo
		setSize(600, 250);
	}
	
	private JButton initOKButton() {
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Actualizar las ForceLaws del controller:
					JSONObject newFl = descToJSONObject(cbo.getSelectedIndex());
					JSONObject data = new JSONObject();
					for (int i = 0; i < values.getRowCount(); i++)
						if (!((String) values.getValueAt(i, 1)).equals("")) {
							if ((String) values.getValueAt(i, 0) != "c") 
								data.put((String) values.getValueAt(i, 0), Double.parseDouble((String) values.getValueAt(i, 1)));
							else {
								JSONArray c = new JSONArray(new JSONTokener((String) values.getValueAt(i, 1)));
								data.put((String) values.getValueAt(i, 0), c);
							}
						}
					newFl.put("data", data);
					ctrl.setForceLawsInfo(newFl);
					hideFlSelection();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "El valor introducido para alguno de los parámetros no es válido");
				}
			}
		});
		return ok;
	}
	
	private JSONObject descToJSONObject(int index) {
		List<JSONObject> l = ctrl.getForceLawsInfo();
		return l.get(index);
	}
	
	private JButton initCancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideFlSelection();
			}
		});
		return cancel;
	}
	
	private JComboBox<String> initComboBox() {
		JComboBox<String> comboBox = new JComboBox<String>(getForceLawsVector());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valuesModel.update(getRowData(getSelectedForceLaw((String) cbo.getSelectedItem())));
			}
		});
		return comboBox;
	}
	
	private Vector<String> getForceLawsVector() {
		List<JSONObject> infoFl = ctrl.getForceLawsInfo();
		Vector<String> flVector = new Vector<String>();
		for (JSONObject jo: infoFl)
			flVector.addElement((String) jo.get("desc"));
		return flVector;
	}
	
	private String[][] getRowData(JSONObject selectedItem) {
		JSONObject info = selectedItem.getJSONObject("data");
		String[][] data = new String[info.keySet().size()][3];
		int i = 0;
		for(String key : info.keySet()) {
			data[i][0] = key;
			data[i][1] = "";
			data[i][2] = info.getString(key);
			++i;
		}
		return data;
	}
	
	private JSONObject getSelectedForceLaw(String selectedItem) {
		List<JSONObject> ja = ctrl.getForceLawsInfo();
		for(JSONObject jo: ja)
			if(selectedItem.equals(jo.getString("desc")))
				return jo;
		return null;
	}
	
	public void showFlSelection() {
		setVisible(true);
	}
	
	public void hideFlSelection() {
		setVisible(false);
	}
}
