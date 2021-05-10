package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	private boolean _showVectors;
	private static final String SHOW_HELP_TEXT = "h: toggle help, v : toggle vectors, +: zoom-in, -: zoom-out, =: fit";
	
	public Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// Add border with title
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Viewer", TitledBorder.LEFT, TitledBorder.TOP));
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		_showVectors = true;
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case '-':
						_scale = _scale * 1.1;
						repaint();
						break;
					case '+':
						_scale = Math.max(1000.0, _scale / 1.1);
						repaint();
						break;
					case '=':
						autoScale();
						repaint();
						break;
					case 'h':
						_showHelp = !_showHelp;
						repaint();
						break;
					case 'v':
						_showVectors = !_showVectors;
						repaint();
						break;
					default:
				}
			}

			public void keyTyped(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			public void mouseClicked(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// use ’gr’ to draw not ’g’ --- it gives nicer results
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		// Draw a cross at center
		drawCross(gr, Color.RED, 5);
		
		// Draw bodies (with vectors if _showVectors is true)
		gr.setColor(Color.BLUE);
		for(Body b : _bodies) {
			int x = _centerX + (int) (b.getPosition().getX() / _scale);
			int y = _centerY - (int) (b.getPosition().getY() / _scale);
			gr.fillOval(x, y, 5, 5);
			gr.drawString(b.getId(), _centerX + (int) (b.getPosition().getX() / _scale) + 5, _centerY - (int) (b.getPosition().getY() / _scale) );
		}
		if(_showVectors)
			for(Body b : _bodies) {
				int x = _centerX + (int) (b.getPosition().getX() / _scale);
				int y = _centerY - (int) (b.getPosition().getY() / _scale);
				drawLineWithArrow(g, x, y, x + (int) (b.getForce().direction().getX() * 20), y - (int) (b.getForce().direction().getY() * 20), 3, 3, Color.GREEN, Color.GREEN);
				drawLineWithArrow(g, x, y, x + (int) (b.getVelocity().direction().getX() * 20), y - (int) (b.getVelocity().direction().getY() * 20), 3, 3, Color.RED, Color.RED);
			}
		gr.setColor(Color.RED);
		// Draw help if _showHelp is true
		if(_showHelp) {
			gr.drawString(SHOW_HELP_TEXT, 10, 30);
			gr.drawString("Scaling ratio: " + _scale, 10, 45);
		}
	}
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}
		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	
	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor, Color arrowColor) {
		
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
		
		x = xm  *cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;
		
		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}
	private void drawCross(Graphics2D gr, Color color, int length) {
		gr.setColor(color);
		gr.drawLine(_centerX, _centerY - length, _centerX, _centerY + length);
		gr.drawLine(_centerX - length, _centerY, _centerX + length, _centerY);
	}
	
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies = bodies;
		autoScale();
		repaint();
	}

	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies = bodies;
		autoScale();
		repaint();
	}

	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		autoScale();
		repaint();
	}
	
	public void onAdvance(List<Body> bodies, double time) {
		repaint();
	}

	public void onDeltaTimeChanged(double dt) {}
	
	public void onForceLawsChanged(String fLawsDesc) {}
}