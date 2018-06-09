package snake;

import java.awt.Color;
import java.util.LinkedList;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    private LinkedList<Tail> tailList;
    protected Environment environment;
    private int food;

    public SnakeAgent(Cell cell, Color color, Environment environment) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
       // alive = true;
        this.environment = environment;
        this.tailList = new LinkedList<>();
        food = 0;
    }



    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }

    protected Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell),
                environment.getFoodCell()
        );
    }

    protected void execute(Action action, Environment environment)
    {
        Cell nextCell = null;

        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(cell);
        }

        if (nextCell != null && !nextCell.hasAgent() && !nextCell.hasTail()) {
            if (nextCell.hasFood()) {
                tailList.addFirst(new Tail(cell));
                food++;
                environment.incrementFoodsEaten();
            } else {
                if (!tailList.isEmpty()) {
                    tailList.addFirst(new Tail(cell));
                    tailList.getLast().getCell().setTail(null);
                    tailList.removeLast();
                }
            }
            setCell(nextCell);
        } else {
            environment.setAlive(false);
        }
    }

    public int getFood() {
        return food;
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if (this.cell != null) {
            this.cell.setAgent(null);
        }
        this.cell = newCell;
        if (newCell != null) {
            newCell.setAgent(this);
        }
        if (cell.hasFood()) {
            cell.setFood(null);
            environment.placeFood();
        }
    }    
    
    public Color getColor() {
        return color;
    }

    public void removeTails() {
        for (Tail tail : tailList)
            tail.getCell().setTail(null);
        tailList.clear();

    }
}
