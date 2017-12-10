package VignèreCipher;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @version 1.0 - 12/9/17
 * @author Mike Upton
 */
public class Cipher
{
    private static char[] alphabet = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q','r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static ArrayList<Character> cipherShifter = new ArrayList<>(26);
    private static String message;
    private static String key;
    public static void main(String[] args)
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter message to encrypt: ");
        message = kb.nextLine();
        System.out.print("Enter the key: ");
        key = kb.next();
        message = decrypt(message, key);

        System.out.println("EncryptedText: " + message);
    }

    public static void generateKeyList()
    {
        int index = 0;
        for(char c : alphabet)
        {
            cipherShifter.add(index, c);
            index++;
        }
    }

    public static char shiftedLetter(char c, char key)
    {
        generateKeyList();
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
        {
            int startIndex = cipherShifter.indexOf(c);
            int indexShift = cipherShifter.indexOf(key);
            int newIndex = startIndex + indexShift;
            if(newIndex < 26)
                return cipherShifter.get(newIndex);
            else
                return cipherShifter.get(newIndex-26);
        }
        else
            return c;
    }
    public static char deshiftedLetter(char c, char key)
    {
        generateKeyList();
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
        {
            int startIndex = cipherShifter.indexOf(c);
            int indexShift = cipherShifter.indexOf(key);
            int newIndex = startIndex - indexShift;
            if(newIndex < 0)
                newIndex = (26-indexShift) + startIndex;
            return cipherShifter.get(newIndex);
        }
        else
            return c;
    }

    public static String encrypt(String str, String key)
    {
        int keyCount = 0;
        String encrypted = "";
        for(int i = 0; i < str.length(); i++)
        {
            if(keyCount == key.length())
                keyCount = 0;
            char c = str.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            {
                encrypted += shiftedLetter(str.toLowerCase().charAt(i), key.toLowerCase().charAt(keyCount));
                keyCount++;
            }
            else
                encrypted += " ";
        }
        return encrypted;
    }

    public static String decrypt(String str, String key)
    {
        int keyCount = 0;
        String decrypted = "";
        for(int i = 0; i < str.length(); i++)
        {
            if(keyCount == key.length())
                keyCount = 0;
            if(str.charAt(i) == ' ')
                decrypted += " ";
            else
            {
                decrypted += deshiftedLetter(str.toLowerCase().charAt(i), key.toLowerCase().charAt(keyCount));
                keyCount++;
            }
        }
        return decrypted;
    }

}
