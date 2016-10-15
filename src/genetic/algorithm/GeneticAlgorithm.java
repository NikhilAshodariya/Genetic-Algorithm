package genetic.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm
{

    private final int NUMBER_OF_BIT_CHANGE=2;
    private final int searchSpaceNumber = 64;
    private ArrayList<Integer> searchSpaceOver = new ArrayList<>();
    private int populationSize = 6;
    private int mutationRate = 2;
    private float crossoverRatio = (float) 0.5;
    private Integer solution = null;
    private Chromosome cs;

    private GeneticAlgorithm(int populationSize)
    {
        this.populationSize = populationSize;
        cs = new Chromosome(this.populationSize);
        cs.initalizePopulation();
    }

    public GeneticAlgorithm(int populationSize, boolean haveSolution) throws IOException
    {
        this(populationSize);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        if (haveSolution)
        {
            System.out.println("what is solution ");
            solution = Integer.parseInt(kin.readLine());
        }
    }

    public GeneticAlgorithm(int mutationRate, int populationSize, boolean haveSolution) throws IOException
    {
        this(populationSize);
        this.mutationRate = mutationRate;
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        if (haveSolution)
        {
            System.out.println("what is solution ");
            solution = Integer.parseInt(kin.readLine());
        }
    }

    public GeneticAlgorithm(float crossoverRatio, int populationSize, boolean haveSolution) throws IOException
    {
        this(populationSize);
        this.crossoverRatio = crossoverRatio;
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        if (haveSolution)
        {
            System.out.println("what is solution ");
            solution = Integer.parseInt(kin.readLine());
        }
    }

    public GeneticAlgorithm(int mutationRate, float crossoverRatio, int populationSize, boolean haveSolution) throws IOException
    {
        this(populationSize);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        if (haveSolution)
        {
            System.out.println("what is solution ");
            solution = Integer.parseInt(kin.readLine());
        }
    }

    public boolean solutionFound()
    {
        if (solution != null)
        {
            Individual foundsolution = cs.checkSolution(solution);
            if (foundsolution == null)
            {
                return false;
            }
            return true;

        }
        else
        {
            boolean temp = isSearchSpaceFinished();
            System.out.println("tmep = " + temp);
            return temp;
        }
    }

    public boolean isSearchSpaceFinished()
    {
        for (int i = 0; i < cs.getPopulationNumber(); i++)
        {
            Individual temp = cs.getIndividual(i);
            int value = Conversion.binaryToDecimal(temp.getIndividualBits());
            if (!searchSpaceOver.contains(value))
            {
                searchSpaceOver.add(value);
            }
        }
        System.out.println("SearchSpacesize = " + searchSpaceOver.size());
        if (searchSpaceOver.size() == searchSpaceNumber)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void calculate()
    {
        for (int i = 1; !solutionFound(); i++)
        {
            System.out.println("pass " + i);
            System.out.println(cs);
            selection();
        }
        System.out.println("solution = ");
        System.out.println(cs.getMinimum());
    }

    public void selection()
    {
        Individual[] individuals = cs.getIndividuals(2);
        Individual parentOne = individuals[0];
        Individual parentTwo = individuals[1];

        ArrayList<Integer> temp1 = new ArrayList<Integer>(Individual.NUMBER_OF_BITS);
        ArrayList<Integer> temp2 = new ArrayList<Integer>(Individual.NUMBER_OF_BITS);
        crossover(temp1, temp2, parentOne, parentTwo);

    }

    public void crossover(ArrayList<Integer> temp1, ArrayList<Integer> temp2, Individual parentOne, Individual parentTwo)
    {
        int parentOneGeneNumber = numberOfParentOneGene(crossoverRatio);
        int parentTwoGeneNumber = Individual.NUMBER_OF_BITS - parentOneGeneNumber;
        for (int i = 0; i < parentOneGeneNumber; i++)
        {
            temp1.add(parentOne.getBit(i));
            temp2.add(parentOne.getBit(Individual.NUMBER_OF_BITS + i - parentOneGeneNumber));
        }

        for (int i = 0; i < parentTwoGeneNumber; i++)
        {
            temp1.add(parentTwo.getBit(parentOneGeneNumber + i));
            temp2.add(parentTwo.getBit(i));
        }

        int genes1[] = new int[Individual.NUMBER_OF_BITS];
        int genes2[] = new int[Individual.NUMBER_OF_BITS];
        for (int i = 0; i < Individual.NUMBER_OF_BITS; i++)
        {
            genes1[i] = temp1.get(i);
            genes2[i] = temp2.get(i);
        }
        Individual individual1 = Individual.makeIndividual(genes1);
        Individual individual2 = Individual.makeIndividual(genes2);

        cs.addIndividuals(individual1, individual2,searchSpaceOver);
        if (cs.getUnChangedCount() >= mutationRate)
        {
            cs.setUnChangedCount(0);
            mutation();
        }

    }

    public void mutation()
    {
        Individual[] individuals = cs.getIndividuals(2);
        Individual parentOne = individuals[0];
        Individual parentTwo = individuals[1];

        int[] temp1 = parentOne.getIndividualBits();
        int[] temp2 = parentTwo.getIndividualBits();

        for (int i = 0; i < NUMBER_OF_BIT_CHANGE; i++)
        {

            Random r = new Random();
            int result = r.nextInt(temp1.length);
            if (temp1[result] == 0)
            {
                temp1[result] = 1;
            }
            else
            {
                temp1[result] = 0;
            }
            result = r.nextInt(temp2.length);
            if (temp2[result] == 0)
            {
                temp2[result] = 1;
            }
            else
            {
                temp2[result] = 0;
            }
        }
        Individual t1 = Individual.makeIndividual(temp1);
        Individual t2 = Individual.makeIndividual(temp2);
        cs.addIndividuals(t1, t2,searchSpaceOver);
    }

    public int numberOfParentOneGene(float a)
    {
        int temp = (int) (a * Individual.NUMBER_OF_BITS);
        return temp;
    }

    public static int fitnessFunction(int x)
    {
        return (x * x + 3 * x - 5);
    }
}
