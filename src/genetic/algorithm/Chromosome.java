package genetic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome
{

    private int unChangedCount = 0;
    private Individual[] population;

    public Chromosome(int populationSize)
    {
        assert (populationSize > 0 && populationSize <= 64);

        population = new Individual[populationSize];
    }

    public void initalizePopulation()
    {
        /*
         * all the below things is done so that the individual is not repeated
         */
        int temp = population.length;
        for (int i = 0, j = 0; i < temp;)
        {
            Individual populationTemp = new Individual();
            for (j = 0; j < i; j++)
            {
                if (population[j].equals(populationTemp))
                {
                    break;
                }
            }
            if (i == j)
            {
                population[i] = populationTemp;
                i++;
            }

        }
    }

    public boolean checkIfExist(Individual a)
    {
        for (int i = 0; i < population.length; i++)
        {
            population[i].equals(a);
            if (population[i].equals(a))
            {
                return true;
            }
        }
        return false;
    }

    public void addIndividuals(Individual individual1, Individual individual2 , ArrayList<Integer>searchSpaceOver)
    {
        sortPopulation();
        Individual p1 = population[population.length - 1];//this would be the largest
        Individual p2 = population[population.length - 2];//this would be the next largest value

        int valueP1 = Conversion.binaryToDecimal(p1.getIndividualBits());
        int valueP2 = Conversion.binaryToDecimal(p2.getIndividualBits());
        int valueI1 = Conversion.binaryToDecimal(individual1.getIndividualBits());
        int valueI2 = Conversion.binaryToDecimal(individual2.getIndividualBits());

        int p1FitnessValue = GeneticAlgorithm.fitnessFunction(valueP1);
        int p2FitnessValue = GeneticAlgorithm.fitnessFunction(valueP2);
        int i1FitnessValue = GeneticAlgorithm.fitnessFunction(valueI1);
        int i2FitnessValue = GeneticAlgorithm.fitnessFunction(valueI2);

        if (i1FitnessValue > p1FitnessValue && i1FitnessValue > p2FitnessValue)
        {
            if (i2FitnessValue > p1FitnessValue && i2FitnessValue > p2FitnessValue )
            {
                unChangedCount++;
                return;
            }
        }
        if (i1FitnessValue < p1FitnessValue || i1FitnessValue < p2FitnessValue && !searchSpaceOver.contains(valueI1))
        {
            if (i1FitnessValue < p1FitnessValue)
            {
                //replace p1 with i1
                if (!checkIfExist(individual1) )
                {
                    population[population.length - 1] = individual1;
                }
            }
            else //replace p2 with i2
            {
                if (!checkIfExist(individual1))
                {
                    population[population.length - 2] = individual1;
                }
            }
        }
        sortPopulation();
        p1 = population[population.length - 1];//this would be the largest
        p2 = population[population.length - 2];//this would be the next largest value

        valueP1 = Conversion.binaryToDecimal(p1.getIndividualBits());
        valueP2 = Conversion.binaryToDecimal(p2.getIndividualBits());

        p1FitnessValue = GeneticAlgorithm.fitnessFunction(valueP1);
        p2FitnessValue = GeneticAlgorithm.fitnessFunction(valueP2);

        if (i2FitnessValue < p1FitnessValue || i2FitnessValue < p2FitnessValue && !searchSpaceOver.contains(valueI2))
        {
            if (i2FitnessValue < p1FitnessValue)
            {
                //replace p1 with i1
                if (!checkIfExist(individual2))
                {
                    population[population.length - 1] = individual2;
                }
            }
            else //replace p2 with i2
            {
                if (!checkIfExist(individual2))
                {
                    population[population.length - 2] = individual2;
                }
            }
        }
    }

    public Individual[] getIndividuals(int numberOfIndividual)
    {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        Individual t[] = new Individual[numberOfIndividual];
        for (int i = 0; i < numberOfIndividual;)
        {
            Random r = new Random();
            int result = r.nextInt(population.length);
            if (!temp.contains(result))
            {
                temp.add(result);
                i++;
            }
        }
        for (int i = 0; i < temp.size(); i++)
        {
            t[i] = population[temp.get(i)];
        }
        return t;
    }

    public void sortPopulation()
    {
        Individual temp;
        for (int i = 0; i < population.length; i++)
        {
            for (int j = 1; j < (population.length - i); j++)
            {
                int temp1 = GeneticAlgorithm.fitnessFunction(Conversion.binaryToDecimal(population[j - 1].getIndividualBits()));
                int temp2 = GeneticAlgorithm.fitnessFunction(Conversion.binaryToDecimal(population[j].getIndividualBits()));
                if (temp1 > temp2)
                {
                    temp = population[j - 1];
                    population[j - 1] = population[j];
                    population[j] = temp;
                }

            }
        }
    }

    public Individual checkSolution(int solution)
    {
        for (int i = 0; i < population.length; i++)
        {
            int temp = GeneticAlgorithm.fitnessFunction(Conversion.binaryToDecimal(population[i].getIndividualBits()));
            if (solution == temp)
            {
                System.out.println("solution = " + population[i]);
                return population[i];
            }
        }
        return null;
    }

    public Individual getMinimum()
    {
        Individual t = population[0];
        int min = Conversion.binaryToDecimal(population[0].getIndividualBits());
        for (int i = 1; i < population.length; i++)
        {
            int temp = Conversion.binaryToDecimal(population[i].getIndividualBits());
            if (min > temp)
            {
                min = temp;
                t = population[i];
            }
        }
        return t;
    }

    public Individual[] getPopulation()
    {
        return population;
    }

    public int getUnChangedCount()
    {
        return unChangedCount;
    }

    public void setUnChangedCount(int unChangedCount)
    {
        this.unChangedCount = unChangedCount;
    }

    public int getPopulationNumber()
    {
        return population.length;
    }

    public Individual getIndividual(int index)
    {
        return population[index];
    }

    @Override
    public String toString()
    {
        return makeString();
    }

    private String makeString()
    {
        String temp = new String();
        for (int i = 0; i < population.length; i++)
        {
            temp += (" i = " + i + " === " + population[i] + "\n");
        }
        temp += "passover \n";
        return temp;
    }
}
