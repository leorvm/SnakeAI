package snake;

import java.awt.Color;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }

    protected Perception buildPerception(Environment environment) {
        // TODO

        return null;
    }

    protected void execute(Action action, Environment environment)
    {
        // TODO
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){this.cell.setAgent(null);}
        this.cell = newCell;
        if(newCell != null){newCell.setAgent(this);}
    }    
    
    public Color getColor() {
        return color;
    }
}
