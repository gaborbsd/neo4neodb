package com.github.neo4neodb.domain;

public enum MagnitudeBand {
	U(0.36, 1810), B(0.44, 4260), V(0.55, 3640), R(0.64, 3080), I(0.79, 2550), J(
			1.26, 1600), H(1.60, 1080), K(2.22, 670), g(0.52, 3730), r(0.67,
			4490), i(0.79, 4760), z(0.91, 4810);

	private double lambda;
	private int flux;

	private MagnitudeBand(double lambda, int flux) {
		this.lambda = lambda;
		this.flux = flux;
	}

	public double getLambda() {
		return lambda;
	}

	public int getFlux() {
		return flux;
	};
}
