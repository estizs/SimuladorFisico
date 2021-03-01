package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {
	private Double lossFactor;
	private Double lossFrequency;
	private Double c;
	
	public MassLosingBody(String id, Vector2D v, Vector2D p, Double m, Double lossFactor, Double lossFrequency) {
		super(id, v, p, m);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		c = 0.0;
	}
	
	void move(Double t) {
		super.move(t);
		c += t;
		if (c >= lossFrequency) {
			m *= (1 - lossFactor);
			c = 0.0;
		}
	}
}
