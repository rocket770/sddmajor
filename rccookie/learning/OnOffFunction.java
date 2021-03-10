package rccookie.learning;

public class OnOffFunction implements NeuronFunction {
    @Override
    public double calculate(double x) {
        return x >= 0 ? 1 : 0;
    }
}
