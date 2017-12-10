# Caesar Shift Cipher:
- This primitive type of cipher can be used to very simply encrypt a message with a number key between 1 and 26.
- It works by taking the message and shifting each letter to the right by the number of the key
- Example: Hello World!
    - Key: 1 -> "ifmmp xpsme!"
    - Key: 26 -> "hello world!"
    - Key: 52 -> "hello world!

# Vignère Cipher:
- A Vignère cipher is a slightly more complicated cipher with the main difference being that the key is a word.
- For each letter in the alphabet, there is an assigned index from 0-25, using the key one can determine the amount that they need to shift each letter of the message to encrypt or decrypt it
- The best way to understand how the cipher works is through the table below:

| A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10| 11| 12| 13| 14| 15| 16| 17| 18| 19| 20| 21| 22| 23| 24| 25|

- So for example the phrase "Hello", encrypted with the key "World" would be "lqual" 
| Message | H | E | L | L | O |
|:--|---|---|---|---|---|
| Index | 7 | 4 | 11| 11| 14|
| Key | W | O | R | L | D |
| Index | 22| 14| 17| 11| 3 |
