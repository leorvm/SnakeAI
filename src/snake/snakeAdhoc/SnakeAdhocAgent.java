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
        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();

        if(n.hasFood())
            return Action.NORTH;
        if(s.hasFood())
            return Action.SOUTH;
        if(e.hasFood())
            return Action.EAST;
        if(w.hasFood())
            return Action.WEST;

        int snakeLine = this.cell.getLine();
        int snakeColumn = this.cell.getColumn();

        int foodLine = perception.getFood().getLine();
        int foodColumn = perception.getFood().getColumn();

        if(snakeLine < foodLine && !e.hasAgent() && !e.hasTail())
            return Action.EAST;
        else if(snakeLine > foodLine && !w.hasAgent() && !w.hasTail())
            return Action.WEST;
        else if(snakeColumn < foodColumn && !s.hasAgent() && !s.hasTail())
            return Action.SOUTH;
        else if(!n.hasAgent() && !n.hasTail())
            return Action.NORTH;


        return null;
    }

}
