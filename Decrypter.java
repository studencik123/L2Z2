import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Decrypter
{
	/*
	 * wiadomosc = BENVOLIO I do but keep the peace. Put up thy sword, Or manage it to part these men with me
	 * 
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		int numberOfCiphertexts = 19;
		String ciphertext[] = new String[numberOfCiphertexts];
		String toDecrypt = "10001011 00111101 11111011 01110111 11110000 11000011 00001010 10010100 11100100 10101100 11101110 10100000 01010100 01000000 00110101 11000101 00011100 00001111 01101010 11100001 11001110 10011011 11111011 11101100 00110011 00101101 01000001 00010101 01001000 01001011 00000010 00000000 01001001 10110101 10001100 10110000 10010101 00001010 10000011 00100000 00111110 10011111 00111000 10100110 10100001 01000100 11000110 01110100 00011000 00101001 11011111 01011001 00111001 11101011 01000000 01010101 00101101 00011011 01111101 11101110 01001000 11011010 10011000 00111110 11100111 10010010 01011001 10111010 10011110 00001001 00000011 01011000 11001111 10101101 01010110 00111110 00101011 11101110 10100110 01001100 01100101 11110011 01100110 11010111 10010010 11010011 00001100 10011010 01011010 00000110 00011000 11101111 00001110 11011000 01010010 00100000 00101100 00110001 ";
		ArrayList<ArrayList<Integer>> possibleKeys = new ArrayList<ArrayList<Integer>>();

		/*
		 * Wczytywanie kryptogramow
		 */
		for (int i = 0; i < numberOfCiphertexts; i++)
		{
			String fileName = "ciphertext" + (i + 1) + ".txt";
			File file = new File(fileName);
			Scanner in = new Scanner(file);
			do {
				ciphertext[i] = in.nextLine();
			} while (in.hasNextLine());
			ciphertext[i] = ciphertext[i].replace(" ", "");
		}
		toDecrypt = toDecrypt.replace(" ", "");
		
		System.out.println("length = " + toDecrypt.length() / 8);
		
		//Wyszukiwanie znakow mozliwych w kluczu na poszczegolnych pozycjach	
		for (int k = 0; k < toDecrypt.length() / 8; k++)
		{
			possibleKeys.add(new ArrayList<Integer>());
			for (int i = 0; i < 255; i++)
			{
				int counter = 0; //W ilu kryptogramach znajduje sie znak ktory moze byc kluczem
				for (int lista = 0; lista < numberOfCiphertexts; lista++)
				{
					int a = 0;
					try
					{
						a = i ^ Integer.parseInt(ciphertext[lista].substring(k * 8, (k * 8) + 8), 2);
					}
					catch(StringIndexOutOfBoundsException e)
					{
						
					}
					
					if (isViableChar(a))
					{
						counter++;
					}
					else
					{
						break;
					}

				}		
				if (counter == numberOfCiphertexts)
				{			
					possibleKeys.get(k).add(i);
				}
			}
		}
		
		int k = 0;
		double  temp = 0;
		double  komb = 1;
		for (int d = 0; d < toDecrypt.length() / 8; d++)
		{
			temp = 0;
			for (int i = 0; i < possibleKeys.get(k).size(); i++)
			{
				int a = Integer.parseInt(toDecrypt.substring(d * 8, (d * 8) + 8), 2)
						^ (int) possibleKeys.get(k).get(i);
				
				if (isViableChar(a))
				{
					possibleKeys.get(k).set(i,a);
					System.out.print("|" + (char) a + "|");
					temp++;
				}
			}
			
			System.out.println();
			//System.out.println(komb *= temp);
			k++;
		}
	}

	public static boolean isViableChar(int character)
	{
		if (44 <= character && character <= 59)
		{
			return true;
		}
		else if (63 <= character && character <= 90)
		{
			return true;
		}
		else if (97 <= character && character <= 122)
		{
			return true;
		}
		else if (32 <= character && character <= 34)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}