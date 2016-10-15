package genetic.algorithm;

import java.util.Random;

public class Conversion
{

    public static int binaryToDecimal(String binary)
    {
        return Integer.parseInt(binary, 2);
    }

    public static int binaryToDecimal(int a[])
    {
        char temp[] = new char[a.length];
        for (int i = 0; i < a.length; i++)
        {
            temp[i] = Character.forDigit(a[i], 2);
        }
        return binaryToDecimal(new String(temp));
    }

    public static String toBinaryString(int a[])
    {
        char temp[] = new char[a.length];
        for (int i = 0; i < a.length; i++)
        {
            if (a[i] == 0)
            {
                temp[i] = '0';
            }
            else
            {
                temp[i] = '1';
            }
        }
        return (new String(temp));
    }
    
}
