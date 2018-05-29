package snake.snakeAdhoc;

import snake.*;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {
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
        else if(s != null && s.hasFood())
            return Action.SOUTH;
        else if(e != null && e.hasFood())
            return Action.EAST;
        else if(w != null && w.hasFood())
            return Action.WEST;

        int snakeLine = this.cell.getLine();
        int snakeColumn = this.cell.getColumn();

        int foodLine = perception.getFood().getLine();
        int foodColumn = perception.getFood().getColumn();

        if(snakeLine < foodLine && s != null &&  !s.hasAgent() && !s.hasTail())
            return Action.SOUTH;
        else if(snakeLine > foodLine && n != null && !n.hasAgent() && !n.hasTail())
            return Action.NORTH;
        else if(snakeColumn <= foodColumn && e != null && !e.hasAgent() && !e.hasTail())
            return Action.EAST;
        else if((snakeColumn >= foodColumn && w != null && !w.hasAgent() && !w.hasTail()))
            return Action.WEST;


        if(n != null && !n.hasTail() && !n.hasAgent())
            return Action.NORTH;
        else if(s != null && !s.hasTail() &&  !s.hasAgent())
            return Action.SOUTH;
        else if(e != null && !e.hasTail() &&  !e.hasAgent())
            return Action.EAST;
        else if(w != null && !w.hasTail() &&  !w.hasAgent())
            return Action.WEST;

        return null;
    }

}
