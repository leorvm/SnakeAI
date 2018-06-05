package snake.snakeAI.nn;

import snake.*;

import java.awt.Color;
import java.util.Random;

public abstract class SnakeAIAgent extends SnakeAgent {
   
    final protected int inputLayerSize;
    final protected int hiddenLayerSize;
    final protected int outputLayerSize;

    /**
     * Network inputs array.
     */
    final protected int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final protected double[][] w1;
    /**
     * Output layer weights.
     */
    final protected double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final protected double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final protected int[] output;

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
    protected void forwardPropagation() {
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
}
