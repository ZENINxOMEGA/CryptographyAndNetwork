import java.util.Scanner;

public class HillCipher {

    static Scanner sc = new Scanner(System.in);

  
    static int determinant(int[][] k) {
        return k[0][0] * (k[1][1] * k[2][2] - k[1][2] * k[2][1])
                - k[0][1] * (k[1][0] * k[2][2] - k[1][2] * k[2][0])
                + k[0][2] * (k[1][0] * k[2][1] - k[1][1] * k[2][0]);
    }


    static int modInverse(int n) {
        n = n % 26;

        if (n < 0) {
            n += 26;
        }

        for (int i = 1; i < 26; i++) {
            if ((n * i) % 26 == 1) {
                return i;
            }
        }

        return -1;
    }

    static int[][] inverseMatrix(int[][] key) {

        int det = determinant(key);
        det = det % 26;

        if (det < 0) {
            det += 26;
        }

        int detInverse = modInverse(det);

        if (detInverse == -1) {
            return null;
        }

        int[][] adj = new int[3][3];

   

        adj[0][0] = key[1][1] * key[2][2]
                - key[1][2] * key[2][1];

        adj[0][1] = key[0][2] * key[2][1]
                - key[0][1] * key[2][2];

        adj[0][2] = key[0][1] * key[1][2]
                - key[0][2] * key[1][1];

        adj[1][0] = key[1][2] * key[2][0]
                - key[1][0] * key[2][2];

        adj[1][1] = key[0][0] * key[2][2]
                - key[0][2] * key[2][0];

        adj[1][2] = key[0][2] * key[1][0]
                - key[0][0] * key[1][2];

        adj[2][0] = key[1][0] * key[2][1]
                - key[1][1] * key[2][0];

        adj[2][1] = key[0][1] * key[2][0]
                - key[0][0] * key[2][1];

        adj[2][2] = key[0][0] * key[1][1]
                - key[0][1] * key[1][0];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                adj[i][j] = (adj[i][j] * detInverse) % 26;

                if (adj[i][j] < 0) {
                    adj[i][j] += 26;
                }
            }
        }

        return adj;
    }


    static boolean validText(String text) {

        if (text.length() == 0) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {

            if (!Character.isLetter(text.charAt(i))) {
                return false;
            }
        }

        return true;
    }

   
    static String encrypt(String text, int[][] key) {

        text = text.toUpperCase();

        while (text.length() % 3 != 0) {
            text += "X";
        }

        String cipher = "";

        for (int i = 0; i < text.length(); i += 3) {

            int x1 = text.charAt(i) - 'A';
            int x2 = text.charAt(i + 1) - 'A';
            int x3 = text.charAt(i + 2) - 'A';

            int y1 = (key[0][0] * x1
                    + key[0][1] * x2
                    + key[0][2] * x3) % 26;

            int y2 = (key[1][0] * x1
                    + key[1][1] * x2
                    + key[1][2] * x3) % 26;

            int y3 = (key[2][0] * x1
                    + key[2][1] * x2
                    + key[2][2] * x3) % 26;

            cipher += (char) (y1 + 'A');
            cipher += (char) (y2 + 'A');
            cipher += (char) (y3 + 'A');
        }

        return cipher;
    }

  
    static String decrypt(String text, int[][] inverse) {

        text = text.toUpperCase();

        String plain = "";

        for (int i = 0; i < text.length(); i += 3) {

            int y1 = text.charAt(i) - 'A';
            int y2 = text.charAt(i + 1) - 'A';
            int y3 = text.charAt(i + 2) - 'A';

            int x1 = (inverse[0][0] * y1
                    + inverse[0][1] * y2
                    + inverse[0][2] * y3) % 26;

            int x2 = (inverse[1][0] * y1
                    + inverse[1][1] * y2
                    + inverse[1][2] * y3) % 26;

            int x3 = (inverse[2][0] * y1
                    + inverse[2][1] * y2
                    + inverse[2][2] * y3) % 26;

            plain += (char) (x1 + 'A');
            plain += (char) (x2 + 'A');
            plain += (char) (x3 + 'A');
        }

        return plain;
    }

    public static void main(String[] args) {

        int[][] key = new int[3][3];
        int[][] inverse;

        System.out.println("===== 3 x 3 HILL CIPHER =====");

        while (true) {

            System.out.println("\nEnter the 3 x 3 Key Matrix:");

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    while (true) {

                        System.out.print("key[" + i + "][" + j + "] = ");

                        if (sc.hasNextInt()) {

                            int value = sc.nextInt();

                            if (value >= 0 && value <= 25) {
                                key[i][j] = value;
                                break;
                            } else {
                                System.out.println(
                                        "Invalid input! Enter value between 0 and 25.");
                            }

                        } else {
                            System.out.println("Invalid input! Enter a number.");
                            sc.next();
                        }
                    }
                }
            }

            inverse = inverseMatrix(key);

            if (inverse != null) {
                break;
            }

            System.out.println("\nInvalid Key Matrix!");
            System.out.println(
                    "This matrix does not have an inverse modulo 26.");
            System.out.println("Enter another matrix.");
        }

        sc.nextLine();

       
        while (true) {

            System.out.println("\n======================");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");
            System.out.println("======================");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

      
            if (choice.equals("1")) {

                String plainText;

                while (true) {

                    System.out.print("\nEnter Plain Text: ");
                    plainText = sc.nextLine();

                    if (validText(plainText)) {
                        break;
                    }

                    System.out.println("Invalid input!");
                    System.out.println("Enter alphabets only.");
                }

                String cipherText = encrypt(plainText, key);

                System.out.println("Cipher Text: " + cipherText);
            }

            else if (choice.equals("2")) {

                String cipherText;

                while (true) {

                    System.out.print("\nEnter Cipher Text: ");
                    cipherText = sc.nextLine();

                    if (!validText(cipherText)) {

                        System.out.println("Invalid input!");
                        System.out.println("Enter alphabets only.");
                        continue;
                    }

                    if (cipherText.length() % 3 != 0) {

                        System.out.println("Invalid input!");
                        System.out.println(
                                "Cipher text length must be a multiple of 3.");
                        continue;
                    }

                    break;
                }

                String plainText = decrypt(cipherText, inverse);

                System.out.println("Plain Text: " + plainText);
            }

            else if (choice.equals("3")) {

                System.out.println("\nProgram ended.");
                break;
            }

            else {

                System.out.println(
                        "Invalid choice! Enter 1, 2 or 3.");
            }
        }

        sc.close();
    }
}