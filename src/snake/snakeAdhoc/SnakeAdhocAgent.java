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

        if(n != null && n.hasFood())
            return Action.NORTH;
        if(s != null && s.hasFood())
            return Action.SOUTH;
        if(e != null && e.hasFood())
            return Action.EAST;
        if(w != null && w.hasFood())
            return Action.WEST;

        int snakeLine = this.cell.getLine();
        int snakeColumn = this.cell.getColumn();

        int foodLine = perception.getFood().getLine();
        int foodColumn = perception.getFood().getColumn();

        if(snakeLine < foodLine && s != null &&  !s.hasAgent() && !s.hasTail())
            return Action.SOUTH;
        else if(snakeLine > foodLine && n != null && !n.hasAgent() && !n.hasTail())
            return Action.NORTH;
        else if(snakeColumn < foodColumn && e != null && !e.hasAgent() && !e.hasTail())
            return Action.EAST;
        else if(w != null && !w.hasAgent() && !w.hasTail())
            return Action.WEST;


        return null;
    }

}
