package ga;

public abstract class Individual {
	
	protected double fitness;
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double newFitness) {
		fitness = newFitness;
	}
	public abstract Individual copy();
	public abstract void mutate();
}
