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
        if(perception.getN().hasFood())
            return Action.NORTH;
        if(perception.getS().hasFood())
            return Action.SOUTH;
        if(perception.getE().hasFood())
            return Action.EAST;
        if(perception.getW().hasFood())
            return Action.WEST;


        //TODO



        return null;
    }

}
