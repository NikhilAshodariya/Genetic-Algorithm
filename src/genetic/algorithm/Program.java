package genetic.algorithm;

import java.io.*;

public class Program
{

    public static void main(String[] args) throws IOException
    {
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        int mutationRate = 0;
        float crossoverRatio = 0;
        boolean haveSolution;
        int populationSize;

        GeneticAlgorithm ga = null;

        System.out.println("Enter population size");
        populationSize = Integer.parseInt(kin.readLine());

        System.out.println("do system have solution y-yes and n-no ");
        if (kin.readLine().equalsIgnoreCase("y"))
        {
            haveSolution = true;
        }
        else
        {
            haveSolution = false;
        }

        System.out.println("Want to enter mutation rate y-yes and n-no ");
        if (kin.readLine().equalsIgnoreCase("y"))
        {
            System.out.println("Enter mutation rate ");
            mutationRate = Integer.parseInt(kin.readLine());
            ga = new GeneticAlgorithm(mutationRate, populationSize, haveSolution);
        }
        
        System.out.println("Want to enter crossoverRatio rate y-yes and n-no ");
        if (kin.readLine().equalsIgnoreCase("y"))
        {
            System.out.println("Enter ratio for crossover ");
            crossoverRatio = Float.parseFloat(kin.readLine());
            if (mutationRate != 0)
            {
                ga = new GeneticAlgorithm(mutationRate, crossoverRatio, populationSize, haveSolution);
            }
            else
            {
                ga = new GeneticAlgorithm(crossoverRatio, populationSize, haveSolution);
            }
        }

        if (mutationRate == 0 && crossoverRatio == 0)
        {
            ga = new GeneticAlgorithm(populationSize, haveSolution);
        }

        ga.calculate();
    }
}
