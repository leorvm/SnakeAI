package snake.snakeAdhoc;

import snake.*;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {
    // TODO

    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        if(perception.getN().hastFood())
            return Action.NORTH;
        if(perception.getS().hastFood())
            return Action.SOUTH;
        if(perception.getE().hastFood())
            return Action.EAST;
        if(perception.getW().hastFood())
            return Action.WEST;

        //TODO



        return null;
    }

}
