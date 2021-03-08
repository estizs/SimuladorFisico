package simulator.factories;

import simulator.model.ForceLaws;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
	private static final String _type = "nlug";
	private static final Double _G = 6.67e10-11;
}
