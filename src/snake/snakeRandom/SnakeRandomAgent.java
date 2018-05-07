package snake.snakeRandom;

import snake.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeRandomAgent extends SnakeAgent {
    // TODO

    private ArrayList<Character> pickRandomPosition = new ArrayList<>();
    private Random random = new Random();

    public SnakeRandomAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {



        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();

        if(s != null &&  !s.hasAgent() && !s.hasTail()) {
            pickRandomPosition.add('s');
        }
        if(n != null && !n.hasAgent() && !n.hasTail() ) {
            pickRandomPosition.add('n');
        }
        if( e != null && !e.hasAgent() && !e.hasTail() ) {
            pickRandomPosition.add('e');
        }
        if(w != null && !w.hasAgent() && !w.hasTail() ) {
            pickRandomPosition.add('w');
        }


        char randomDirection = pickRandomPosition.get(random.nextInt(pickRandomPosition.size()));
        pickRandomPosition.clear();

        if (randomDirection == 'n'){
            return Action.NORTH;

        }else if (randomDirection == 's'){
            return Action.SOUTH;

        }else if(randomDirection == 'e'){
            return Action.EAST;

        }else if(randomDirection == 'w'){
            return Action.WEST;
        }

        return null;
    }
}
