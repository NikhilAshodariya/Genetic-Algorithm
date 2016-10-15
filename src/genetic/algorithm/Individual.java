package genetic.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable<Individual>
{

    static final int ENCODING_TYPE = 2;
    static final int NUMBER_OF_BITS = 6;
    private int bits[];

    private Individual(int a[])
    {
        bits = a;
    }

    public Individual()
    {
        bits = new int[NUMBER_OF_BITS];
        for (int i = 0; i < bits.length; i++)
        {
            Random r = new Random();
            int result = r.nextInt(ENCODING_TYPE);// 2 means till 2(exclusive) i.e from 0 to 1
            bits[i] = result;
        }
    }

    public static Individual makeIndividual(int a[])
    {
        Individual temp = new Individual(a);
        return temp;
    }

    public int getBit(int index)
    {
        return bits[index];
    }

    public int[] getIndividualBits()
    {
        return bits;
    }
    

    @Override
    public String toString()
    {
        return "gene = " + Arrays.toString(bits);
    }

    @Override
    public int compareTo(Individual o)
    {
        Arrays.equals(bits, o.bits);
        if (Arrays.equals(bits, o.bits))//Arrays.equals return true if they are same
        {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        Individual other = (Individual) obj;
        if (Arrays.equals(bits, other.bits))//Arrays.equals return true if they are same
        {
            return true;
        }
        return false;
    }
}
