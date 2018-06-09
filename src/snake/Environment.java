package snake;

import gui.PanelParameters;
import gui.PanelSimulation;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAI.nn.SnakeAIAgentA;
import snake.snakeAI.nn.SnakeAIAgentB;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.Color;
import java.util.*;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private final List<SnakeAgent> agents;
    private Food food;
    private final int maxIterations;
    private PanelParameters panelParameters;

    private int movements;
    private int foodsEaten;

    private int numInputs;
    private int numOutputs;
    private int numHiddenLayer;

    private boolean alive;

    private int tipoSnake;

  //private LinkedList<Tail> tails;

    public Environment(
            int size,
            int maxIterations,
            int numInputs, int numOutputs, int numHiddenLayer) {
        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.agents = new ArrayList<>();
        this.random = new Random();
        this. movements=0;
        this.foodsEaten = 0;
        this.panelParameters = null;
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayer = numHiddenLayer;
        this.alive = true;
        this.tipoSnake = 0;
    }

    public void setPanelParameters(PanelParameters panelParameters){
        this.panelParameters = panelParameters;
    }

    public void initialize(int seed) {
        random.setSeed(seed);
        cleanEnvironment();
        placeAgents();
        placeFood();
    }

    private void cleanEnvironment() {

        for (SnakeAgent snakeAgent : agents) {
            snakeAgent.getCell().setAgent(null);
            snakeAgent.removeTails();

        }
        this.agents.clear();
        if (food != null) {
            food.getCell().setFood(null);
            food = null;
        }

    }

    private void placeAgents() {
       tipoSnake = panelParameters.getEscolherTipoDeSnake();
       agents.clear();

       switch (tipoSnake) {
            case 0:
                SnakeAdhocAgent snakeAdhocAgent = new SnakeAdhocAgent(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                        , Color.BLACK, this);
                agents.add(snakeAdhocAgent);
                break;
            case 1:
                SnakeRandomAgent snakeRandomAgent = new SnakeRandomAgent(new Cell(random.nextInt(grid.length), random.nextInt(grid.length))
                , Color.GREEN, this);
                agents.add(snakeRandomAgent);

                break;
            case 2: //SnakeAI
                SnakeAIAgent snakeAIAgentA = new SnakeAIAgentA(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                        , numInputs, numHiddenLayer, numOutputs, this);
                agents.add(snakeAIAgentA);
                break;
           case 3: //SnakeAI Homogeneo
               SnakeAIAgent snakeAIAgentCase3 = new SnakeAIAgentA(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                       , numInputs, numHiddenLayer, numOutputs, this);
               agents.add(snakeAIAgentCase3);
               snakeAIAgentCase3 = new SnakeAIAgentA(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                       , numInputs, numHiddenLayer, numOutputs, this);
               agents.add(snakeAIAgentCase3);
               break;
           case 4: //SnakeAI Heterogeneo
               SnakeAIAgent snakeAIAgentCase4A = new SnakeAIAgentA(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                       , numInputs, numHiddenLayer, numOutputs, this);
               agents.add(snakeAIAgentCase4A);
               SnakeAIAgent snakeAIAgentCase4B = new SnakeAIAgentB(grid[random.nextInt(grid.length)][random.nextInt(grid.length)]
                       , numInputs, numHiddenLayer, numOutputs, this);
               agents.add(snakeAIAgentCase4B);
               break;
        }
    }
    public int getTipoSnake() {
        return tipoSnake;
    }

    public int getTipoSnakeParameters() {
        return panelParameters.getEscolherTipoDeSnake();
    }

    public int getFoodAgent0(){ //Necessario para o tipo 4 de snake
        return agents.get(0).getFood();
    }

    public int getFoodAgent1(){ //Necessario para o tipo 4 de snake
        return agents.get(1).getFood();
    }

    public void placeFood() {
        Cell cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        while(cell.hasAgent() || cell.hasTail()) {
            cell = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        }
        food = new Food(cell);
    }

    public void simulate() {
        alive = true;
        movements = 0;
        foodsEaten = 0;
        int i;
        for (i = 0; i < maxIterations && alive; i++) {
            for (SnakeAgent agent: agents ) {
                agent.act(this);
                fireUpdatedEnvironment();
            }
        }
        movements = i;
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

    public void setWeights(double[] genome) {
        for (SnakeAgent agent : agents) {
            ((SnakeAIAgent) agent).setWeights(genome);
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setWeightsCase4(double[] genome) {
        ((SnakeAIAgent) agents.get(0)).setWeights(Arrays.copyOfRange(genome,0,genome.length/2));
        ((SnakeAIAgent) agents.get(1)).setWeights(Arrays.copyOfRange(genome,genome.length/2,genome.length));
    }
}
