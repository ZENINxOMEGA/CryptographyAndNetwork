import java.util.*;

public class PlayFairCipher {

    static char[][] matrix = new char[5][5];

    // Create Playfair Matrix
    static void createMatrix(String key) {

        StringBuilder s = new StringBuilder();

        key = key.toUpperCase().replace("J", "I");

        // Add key characters
        for (char ch : key.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z' && s.indexOf(String.valueOf(ch)) == -1) {
                s.append(ch);
            }
        }

        // Add remaining alphabets
        for (char ch = 'A'; ch <= 'Z'; ch++) {

            if (ch == 'J')
                continue;

            if (s.indexOf(String.valueOf(ch)) == -1) {
                s.append(ch);
            }
        }

        // Fill matrix
        int k = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = s.charAt(k++);
            }
        }
    }

    // Display Matrix
    static void displayMatrix() {

        System.out.println("\nPlayfair Matrix:");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Find position of character
    static int[] findPosition(char ch) {

        if (ch == 'J')
            ch = 'I';

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (matrix[i][j] == ch) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    // Prepare plaintext into pairs
    static String prepareText(String text) {

        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        text = text.replace("J", "I");

        StringBuilder result = new StringBuilder();

        int i = 0;

        while (i < text.length()) {

            char first = text.charAt(i);

            if (i + 1 == text.length()) {
                result.append(first);
                result.append('X');
                i++;
            }

            else {
                char second = text.charAt(i + 1);

                if (first == second) {
                    result.append(first);
                    result.append('X');
                    i++;
                }

                else {
                    result.append(first);
                    result.append(second);
                    i += 2;
                }
            }
        }

        return result.toString();
    }

    // Encryption
    static String encrypt(String text) {

        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {

            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            int r1 = p1[0];
            int c1 = p1[1];

            int r2 = p2[0];
            int c2 = p2[1];

            // Same row
            if (r1 == r2) {

                cipher.append(matrix[r1][(c1 + 1) % 5]);
                cipher.append(matrix[r2][(c2 + 1) % 5]);
            }

            // Same column
            else if (c1 == c2) {

                cipher.append(matrix[(r1 + 1) % 5][c1]);
                cipher.append(matrix[(r2 + 1) % 5][c2]);
            }

            // Rectangle
            else {

                cipher.append(matrix[r1][c2]);
                cipher.append(matrix[r2][c1]);
            }
        }

        return cipher.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        System.out.print("Enter Plaintext: ");
        String plaintext = sc.nextLine();

        createMatrix(key);

        displayMatrix();

        String preparedText = prepareText(plaintext);

        System.out.println("\nPrepared Plaintext: " + preparedText);

        String ciphertext = encrypt(preparedText);

        System.out.println("Ciphertext: " + ciphertext);

        sc.close();
    }
}