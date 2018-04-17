package snake;

import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private final List<SnakeAgent> agents;
    private Food food;
    private final int maxIterations;

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
    }

    public void initialize(int seed) {
        random.setSeed(seed);

        placeAgents();
        placeFood();
    }

    // TODO MODIFY TO PLACE ADHOC OR AI SNAKE AGENTS
    private void placeAgents() {
        //SnakeRandomAgent snakeRandomAgent = new SnakeRandomAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length)), Color.GREEN);
        //agents.add(snakeRandomAgent);
        SnakeAdhocAgent snakeAdhocAgent = new SnakeAdhocAgent(grid[random.nextInt(grid.length)][random.nextInt(grid.length)], Color.BLACK);
        agents.add(snakeAdhocAgent);
    }

    private void placeFood() {
        // TODO
        if(food == null)
            food = new Food(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]);
    }

    public void simulate() {
        // TODO

        fireUpdatedEnvironment();
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

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
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
}
