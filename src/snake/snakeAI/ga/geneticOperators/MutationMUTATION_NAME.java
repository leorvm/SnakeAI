package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

//PLEASE, MODIFY THE CLASS NAME
public class MutationMUTATION_NAME <I extends RealVectorIndividual> extends Mutation<I> {

    //double upper, lower;
   
    public MutationMUTATION_NAME(double probability,/* double upper, double lower*/ /*TODO?*/) {
        super(probability);
        // TODO
        //this.upper = upper;
        //this.lower = lower;
    }

    @Override
    public void run(I ind) {
        // TODO

        for (int i = 0; i < ind.getNumGenes(); i++) {
            if(GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, GeneticAlgorithm.random.nextDouble());
            }
        }
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + TODO?*/;
    }
}