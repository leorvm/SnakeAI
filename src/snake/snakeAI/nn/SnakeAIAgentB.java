package snake.snakeAI.nn;

import snake.Action;
import snake.Cell;
import snake.Environment;
import snake.Perception;

public class SnakeAIAgentB extends SnakeAIAgent {
    public SnakeAIAgentB(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Environment environment) {
        super(cell, inputLayerSize, hiddenLayerSize, outputLayerSize, environment);
    }

    @Override
    protected Action decide(Perception perception) {
        Cell food = perception.getFood();
        Cell n = perception.getN();
        Cell e = perception.getE();
        Cell s = perception.getS();
        Cell w = perception.getW();

        inputs[1] = n != null && n.hasFood() ? 1 : 0;
        inputs[2] = e != null && e.hasFood() ? 1 : 0;
        inputs[3] = s != null && s.hasFood() ? 1 : 0;
        inputs[4] = w != null && w.hasFood() ? 1 : 0;

        inputs[5] = n != null && !n.hasTail() && !n.hasAgent() ? 1 : 0;
        inputs[6] = e != null && !e.hasTail() && !e.hasAgent() ? 1 : 0;
        inputs[7] = s != null && !s.hasTail() && !s.hasAgent() ? 1 : 0;
        inputs[8] = w != null && !w.hasTail() && !w.hasAgent() ? 1 : 0;


        inputs[9] =  cell.getLine()> food.getLine() ? 1 : 0;
        inputs[10] = cell.getColumn()< food.getColumn() ? 1 : 0;
        inputs[11] = cell.getLine()< food.getLine() ? 1 : 0;
        inputs[12] = cell.getColumn()> food.getColumn() ? 1 : 0;



        forwardPropagation();

        if(output[0]==1)
            return Action.NORTH;
        if(output[1]==1)
            return Action.EAST;
        if(output[2]==1)
            return Action.SOUTH;
        else
            return Action.WEST;
    }
}
