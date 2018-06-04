package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private static final double MOVEMENT_POINTS = 0.0001;
    private static final int FOOD_POINTS = 1;

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
        fitness = original.fitness;
    }

    @Override
    public double computeFitness() {
        Environment environment = problem.getEnvironment();
        fitness = 0;
        movements = 0;
        food = 0;

        for(int i = 0; i < problem.getNumEvironmentSimulations(); i++)
        {
            environment.initialize(i);

            SnakeAIAgent agent =  (SnakeAIAgent) environment.getAgents().get(0);

            agent.setWeights(genome);

            environment.simulate();

            movements += environment.getMovements();
            food += environment.getFoodsEaten();
        }

        fitness = food * FOOD_POINTS + movements * MOVEMENT_POINTS;

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
        sb.append((double)movements/problem.getNumEvironmentSimulations());
        sb.append("\nfood eaten: ");
        sb.append((double)food/problem.getNumEvironmentSimulations());
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
        if (this.getFitness() > i.getFitness())
            return 1;
        if (this.getFitness() < i.getFitness())
            return -1;
        return 0;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
