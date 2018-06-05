package snake.snakeRandom;

import org.omg.IOP.ENCODING_CDR_ENCAPS;
import snake.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeRandomAgent extends SnakeAgent {

    private ArrayList<Character> pickRandomPosition ;
    private Random random ;

    public SnakeRandomAgent(Cell cell, Color color, Environment environment) {
        super(cell, color, environment);
        pickRandomPosition = new ArrayList<>(0);
        random = new Random();
    }

    @Override
    protected Action decide(Perception perception) {


        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();

        if (s != null && !s.hasAgent() && !s.hasTail()) {
            pickRandomPosition.add('s');
        }
        if (n != null && !n.hasAgent() && !n.hasTail()) {
            pickRandomPosition.add('n');
        }
        if (e != null && !e.hasAgent() && !e.hasTail()) {
            pickRandomPosition.add('e');
        }
        if (w != null && !w.hasAgent() && !w.hasTail()) {
            pickRandomPosition.add('w');
        }

        if (pickRandomPosition.isEmpty()) {
            environment.setAlive(false);
            return null;
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

        environment.setAlive(false);
        return null;
    }
}
