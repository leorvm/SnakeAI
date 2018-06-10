package snake.snakeAI;

import snake.Environment;
import snake.SnakeAgent;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

import java.util.List;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private static final double MOVEMENT_POINTS = 0.001;
    private static final int FOOD_POINTS = 5;

    public int movements;
    public int food;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        this.movements = original.movements;
        this.food = original.food;
        this.fitness = original.fitness;
    }

    @Override
    public double computeFitness() {
        Environment environment = problem.getEnvironment();
        fitness = 0;
        movements = 0;
        food = 0;

        int food0 = 0;
        int food1 = 0;

        for(int i = 0; i < problem.getNumEvironmentSimulations(); i++)
        {
            environment.initialize(i);

            if(environment.getTipoSnake() == 4) //Genomas diferentes para cobras heterogeneas
                environment.setWeightsCase4(genome);
            else
                environment.setWeights(genome);

            environment.simulate();

            if (environment.getTipoSnake() == 4) //2 snakes Heterogeneo
            {
                food0 += environment.getFoodAgent0();
                food1 += environment.getFoodAgent1();
            }

            movements += environment.getMovements();
            food += environment.getFoodsEaten();
        }

        if (environment.getTipoSnake() == 4) //2 snakes Heterogeneo
        {
            int penalty = food0 - food1 > 2 ? food0 - food1
                    : food1 - food0 > 2 ? food1 - food0 : 0;

            fitness = food * FOOD_POINTS + movements * MOVEMENT_POINTS - penalty;
        } else
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

    @Override
    public String writeResults(){
        StringBuilder sb = new StringBuilder();
        sb.append("\tfitness: ");
        sb.append(fitness);
        sb.append("\tmovements: ");
        sb.append((double)movements/problem.getNumEvironmentSimulations());
        sb.append("\tfood eaten: ");
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
