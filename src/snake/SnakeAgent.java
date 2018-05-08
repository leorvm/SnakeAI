package snake;

import java.awt.Color;
import java.util.LinkedList;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    private LinkedList<Cell> tailList;
    boolean alive;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
        alive = true;
        tailList = new LinkedList<>();
    }

    public boolean isAlive() {
        return alive;
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
            setCell(nextCell, environment);
        } else {
            alive = false;
            System.out.println("dead snake");
        }
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell, Environment environment) {
        if (this.cell != null) {
            this.cell.setAgent(null);
        }

        Cell aux = this.cell;
        this.cell = newCell;



        if(newCell.hasFood()) {
            newCell.setFood(null);
            environment.placeFood();
            //aux.setTail(new Tail(null));


            tailList.add(0, aux);
            aux.setTail(new Tail(null));
            //environment.addTail(new Tail(aux));

        } else {
            if (!tailList.isEmpty()) {
                tailList.getLast().setTail(null);
                tailList.removeLast();
                tailList.add(0, aux);
                aux.setTail(new Tail(null));
            }
        }


        if (newCell != null) {
            newCell.setAgent(this);
        }
    }    
    
    public Color getColor() {
        return color;
    }
}
