package CaesarShiftCipher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Encrypts and Decrypts messages using the Caesar Shift Cipher
 * Essentially a Caesar shift is when you take the alphabet (ABC...Z) and shift it by an integer key
 * between 1 and 26(Although the key can be any integer -> There are only 26 possibilities) resulting in a
 * shifted version of the alphabet
 *
 * Example:
 *  Message: "Hello World!"
 *  Key: 1 -> "ifmmp xpsme!"
 *  Key: 26 -> "hello world!"
 *  Key: 52 -> "hello world!
 *
 * @version 1.0 - 12/9/17
 * @author Mike Upton
 */
public class Cipher
{
    private static String message;
    private static int key;
    private static char[] alphabet = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                                                    'o', 'p', 'q','r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static char[] shifted;
    final private static String dictionaryFilePath = "src/CaesarShiftCipher/Dictionary.txt";
    private static Set<String> dictionary;

    public static void main(String[] args)
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter message to encrypt: ");
        message = kb.nextLine();
        System.out.print("Enter the key: ");
        key = kb.nextInt();
        message = encrypt(message, key);

        System.out.println("EncryptedText: " + message);
        int i = 0;
        for(String str : breakCipher(message))
        {
            if(i < 9)
                System.out.println(str);
            else
                System.out.println(str);
            i++;
        }
    }

    /**
     * Encrypts the String str with integer key between 0 and highest possible integer
     * Although key may be > 26 there are only 26 possibilities for the shift cipher
     * @param str
     * @param key
     * @return Encrypted String str
     */
    public static String encrypt(String str, int key)
    {
        //Populating shifted array of characters in alphabet
        shifted = new char[26];
        for(int i = 0; i < shifted.length; i++)
        {
            if(key < 26)
            {
                shifted[i] = alphabet[key];
                key++;
            }
            else
            {
                key = key - (26 * (key/26));
                shifted[i] = alphabet[key];
                key++;
            }
        }

        //Mapping connection between A-Z alphabet and new shifted alphabet for encryption
        HashMap<Character, Character> cipher = new HashMap<>();
        for(int i = 0; i < alphabet.length; i++)
        {
            cipher.put(alphabet[i], shifted[i]);
        }

        //Creating encrypted string message using cipher HashMap
        String encryptedText = "";
        for(int i = 0; i < str.length(); i++)
        {
            if(str.toLowerCase().charAt(i) == ' ')
            {
                encryptedText += " ";
            }
            else if((str.toLowerCase().charAt(i) >= 'a' && str.toLowerCase().charAt(i) <= 'z') || (str.toLowerCase().charAt(i) >= 'A' && str.toLowerCase().charAt(i) <= 'Z') )
            {
                encryptedText += cipher.get(str.toLowerCase().charAt(i));
            }
            else
            {
                encryptedText += str.charAt(i);
            }
        }
        return encryptedText;
    }

    /**
     * Decrypts the String str with integer key between 0 and highest possible integer
     * @param str
     * @param key
     * @return Decrypted String str
     */
    public static String decrypt(String str, int key)
    {
        //Populating shifted array of characters in alphabet
        shifted = new char[26];
        for(int i = 0; i < shifted.length; i++)
        {
            int index = key + i;
            if(index < 26)
                shifted[i] = alphabet[index];
            else
            {
                index = index % 26;
                shifted[i] = alphabet[index];
            }
        }

        //Mapping connection between A-Z alphabet and new shifted alphabet for decryption
        HashMap<Character, Character> decipher = new HashMap<>();
        for(int i = 0; i < alphabet.length; i++)
        {
            decipher.put(shifted[i], alphabet[i]);
        }

        //Creating decrypted string message using decipher HashMap
        String encryptedText = "";
        for(int i = 0; i < str.length(); i++)
        {
            if (str.toLowerCase().charAt(i) == ' ')
                encryptedText += " ";
            else if((str.toLowerCase().charAt(i) >= 'a' && str.toLowerCase().charAt(i) <= 'z') || (str.toLowerCase().charAt(i) >= 'A' && str.toLowerCase().charAt(i) <= 'Z') )
                encryptedText += decipher.get(str.toLowerCase().charAt(i));
            else
                encryptedText += str.charAt(i);
        }
        return encryptedText;
    }

    private static Set<String> getDictionary ()
    {
        if (dictionary != null)
            return dictionary;
        Scanner file = null;
        try
        {
            file = new Scanner(new File(dictionaryFilePath));
            dictionary = new HashSet<>();
            // For each word in the input
            while (file.hasNext())
            {
                // Convert the word to lower case, trim it and insert into the set
                dictionary.add(file.next().trim().toLowerCase());
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Cannot find dictionary file");
        }
        finally
        {
            file.close();
        }
        return dictionary;
    }

    //count number of words found in dictionary
    public static int evaluateMetric(String input)
    {
        //split String by space, punctuation
        String[] splitWords = input.split("[\\p{Punct}\\s]+");
        int match = 0;

        for (String s: splitWords)
        {
            if (getDictionary().contains(s))
            {
                match++;
            }
        }
        return match;
    }
    /**
     * Takes input of message as String str, and returns an array with all possible Caesar shifts
     * @param str
     * @return String array of all 26 possible shifted results
     */
    public static ArrayList<String> breakCipher(String str)
    {
        //Creating, populating and returning array of all possible String messages from encrypted String str
        ArrayList<String> possibilities = new ArrayList<>();
        for(int i = 1; i < 27; i++)
        {
            if(evaluateMetric(decrypt(str, i)) > 0)
                possibilities.add("Key " + i + ": " + decrypt(str, i));
        }
        return possibilities;
    }
}
