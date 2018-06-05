package snake.snakeAI.nn;

import snake.*;

import java.awt.Color;
import java.util.Random;

public class SnakeAIAgent extends SnakeAgent {
   
    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;

    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private int[] output;

    public SnakeAIAgent(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize,
            Environment environment) {
        super(cell, Color.BLUE, environment);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];
    }

    /**
     * Initializes the network's weights
     * 
     * @param weights vector of weights comming from the individual.
     */
    public void setWeights(double[] weights) {
        int w = 0;

        for (int i = 0; i < inputLayerSize; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                w1[i][j] = weights[w++];

            }
        }
        for (int i = 0; i < hiddenLayerSize + 1; i++) {
            for (int j = 0; j < outputLayerSize; j++) {
                w2[i][j] = weights[w++];

            }
        }
    }
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    private void forwardPropagation() {
        double sum;
        double maxValue = Double.MIN_VALUE;
        int posM = 0;
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = 1 / (1 + Math.pow(Math.E, -sum));
        }
        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i]=0;
            if (sum > maxValue) {
                maxValue = sum;
                posM=i;
            }
        }
        output[posM] = 1;
    }

    @Override
    protected Action decide(Perception perception) {
        Cell food = perception.getFood();
        Cell n = perception.getN();
        Cell e = perception.getE();
        Cell s = perception.getS();
        Cell w = perception.getW();

        inputs[0] = n != null && n.hasFood() ? 1 : 0;
        inputs[1] = e != null && e.hasFood() ? 1 : 0;
        inputs[2] = s != null && s.hasFood() ? 1 : 0;
        inputs[3] = w != null && w.hasFood() ? 1 : 0;

        inputs[4] = n != null && !n.hasTail() && !n.hasAgent() ? 1 : 0;
        inputs[5] = e != null && !e.hasTail() && !e.hasAgent() ? 1 : 0;
        inputs[6] = s != null && !s.hasTail() && !s.hasAgent() ? 1 : 0;
        inputs[7] = w != null && !w.hasTail() && !w.hasAgent() ? 1 : 0;


        inputs[9] =  cell.getLine()> food.getLine() ? 1 : 0;
        inputs[10] = cell.getColumn()< food.getColumn() ? 1 : 0;
        inputs[11] = cell.getLine()< food.getLine() ? 1 : 0;
        inputs[12] = cell.getColumn()> food.getColumn() ? 1 : 0;



        forwardPropagation();

        if(output[0]==1)
            return Action.NORTH;
        if(output[1]==1)
            return Action.EAST;
        if(output[2]==1)
            return Action.SOUTH;
        else
            return Action.WEST;
    }
}
