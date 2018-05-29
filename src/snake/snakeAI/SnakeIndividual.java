package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    public int movements;
    public int food;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        movements = original.movements;
        food = original.food;
    }

    @Override
    public double computeFitness() {
        Environment environment = problem.getEnvironment();

        movements = 0;
        food = 0;

        for(int i = 0; i < problem.getNumEvironmentSimulations(); i++)
        {
            environment.initialize(i);
            SnakeAIAgent agent =  (SnakeAIAgent) environment.getAgents().get(0);
            agent.setWeights(genome);
            environment.simulate();

            movements += environment.getMovements();
            food = environment.getFoodsEaten();
        }

        fitness = food * 5 + movements * 2;

        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\nmovements: ");
        sb.append(movements);
        sb.append("\nfood eaten: ");
        sb.append(food);
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        return Double.compare(i.computeFitness(), computeFitness());
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
