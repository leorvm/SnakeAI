package snake;

import gui.PanelParameters;
import gui.PanelSimulation;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private final List<SnakeAgent> agents;
    private Food food;
    private final int maxIterations;
    private PanelParameters panelParameters;

    private int movements;
    private int foodsEaten;

//private LinkedList<Tail> tails;

    public Environment(
            int size,
            int maxIterations) {

        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.agents = new ArrayList<>();
        this.random = new Random();



        //this.tails = new LinkedList<>();
    }

    public void setPanelParameters(PanelParameters panelParameters){
        this.panelParameters = panelParameters;
    }

    public void initialize(int seed) {
        random.setSeed(seed);

        placeAgents();
        placeFood();
    }

    private void placeAgents() {

        int teste = panelParameters.getEscolherTipoDeSnake();

        switch (teste) {
            case 0:
                SnakeAdhocAgent snakeAdhocAgent = new SnakeAdhocAgent(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                        , Color.BLACK);
                agents.add(snakeAdhocAgent);
                break;
            case 1:
                SnakeRandomAgent snakeRandomAgent = new SnakeRandomAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length))
                        , Color.GREEN);
                agents.add(snakeRandomAgent);
                break;
            case 2:
                SnakeAIAgent snakeAIAgent = new SnakeAIAgent(new Cell(random.nextInt(grid.length),
                        random.nextInt(grid.length)), 5, 5, 5);
                agents.add(snakeAIAgent);
                break;
        }
    }

    public void placeFood() {
        Cell cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        while(cell.hasAgent() || cell.hasTail()) {
            cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        }
        food = new Food(cell);
    }

    public void simulate() {
        movements = 0;
        foodsEaten = 0;
        for (int i = 0; i < maxIterations; i++) {
            for (SnakeAgent agent: agents ) {
                movements += 1;
                agent.act(this);
                fireUpdatedEnvironment();
                if(!agent.isAlive())
                    return;
            }
        }
    }

    public int getSize() {
        return grid.length;
    }

        public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }
/*
    public void addTail(Tail tail) {
        tails.add(tail);
        tail.getCell().setTail(tail);
    }
*/
    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getFoodCell() {
        return food.getCell();
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public List<SnakeAgent> getAgents() {
        return agents;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public void incrementFoodsEaten() {
        foodsEaten += 1;
    }

    public int getMovements() {
        return movements;
    }

    public int getFoodsEaten() {
        return foodsEaten;
    }
}
