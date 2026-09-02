// a should be co prime with 26
// b should be in the range of 1 to 26  a = 5 b = 7
//encryption: (a * x + b) mod 26  // (x->PT)
//decryption: a^-1 * (y - b) mod 26  //(y->CT)

// find the encyption:
// To perform encryption using the Affine Cipher, you can use the formula:
// E(x) = (a * x + b) mod 26

// find the decryption:
// To perform decryption using the Affine Cipher, you can use the formula:
// D(y) = a^-1 * (y - b) mod 26


// 1 Encryption -> enter PT only small letter
//             -> list of coprime(26)
//             -> select a
//             -> enter b (1-26)
//             -> CT
// 2 Decryption -> enter CT only capital letter
//             -> enter a 
//             -> print a^-1
//             -> enter b
//             -> PT
// 3 exit






import java.util.*;

public class AffineCipher {

    // Find multiplicative inverse of a modulo 26
    static int findInverse(int a) {
        for (int i = 1; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                return i;
            }
        }
        return -1;
    }

    // Encryption
    static String encrypt(String pt, int a, int b) {

        StringBuilder ct = new StringBuilder();

        for (char ch : pt.toCharArray()) {

            int x = ch - 'a';

            int y = (a * x + b) % 26;

            ct.append((char) ('A' + y));
        }

        return ct.toString();
    }

    // Decryption
    static String decrypt(String ct, int a, int b) {

        int inverse = findInverse(a);

        System.out.println("a^-1 = " + inverse);

        StringBuilder pt = new StringBuilder();

        for (char ch : ct.toCharArray()) {

            int y = ch - 'A';

            int x = (inverse * (y - b)) % 26;

            // Java negative modulo ko handle karne ke liye
            if (x < 0) {
                x += 26;
            }

            pt.append((char) ('a' + x));
        }

        return pt.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // ---------------- ENCRYPTION ----------------

            if (choice == 1) {

                System.out.print("Enter Plaintext (small letters only): ");
                String pt = sc.nextLine();

                System.out.println("Coprime numbers with 26:");

                for (int i = 1; i <= 26; i++) {
                    if (gcd(i, 26) == 1) {
                        System.out.print(i + " ");
                    }
                }

                System.out.println();

                System.out.print("Select a: ");
                int a = sc.nextInt();

                if (gcd(a, 26) != 1) {
                    System.out.println("Invalid a! It must be coprime with 26.");
                    continue;
                }

                System.out.print("Enter b (1-26): ");
                int b = sc.nextInt();

                String ct = encrypt(pt, a, b);

                System.out.println("Ciphertext = " + ct);
            }

            // ---------------- DECRYPTION ----------------

            else if (choice == 2) {

                System.out.print("Enter Ciphertext (capital letters only): ");
                String ct = sc.nextLine();

                System.out.print("Enter a: ");
                int a = sc.nextInt();

                if (gcd(a, 26) != 1) {
                    System.out.println("Invalid a! It must be coprime with 26.");
                    continue;
                }

                int inverse = findInverse(a);

                System.out.println("a^-1 = " + inverse);

                System.out.print("Enter b: ");
                int b = sc.nextInt();

                String pt = decrypt(ct, a, b);

                System.out.println("Plaintext = " + pt);
            }

            // ---------------- EXIT ----------------

            else if (choice == 3) {

                System.out.println("Program exited.");
                break;

            } else {

                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    // GCD
    static int gcd(int a, int b) {

        while (b != 0) {

            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}