import java.util.Scanner;
public class MultiplicativeCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            // Encryption
            if (choice == 1) {

                System.out.print("Enter plain text: ");
                String pt = sc.nextLine();

                System.out.print("Enter key (1-26): ");
                int k = sc.nextInt();

                if (k < 1 || k > 26) {
                    System.out.println("Invalid key!");
                    continue;
                }

                String ct = "";

                for (int i = 0; i < pt.length(); i++) {
                    char ch = pt.charAt(i);

                    if (ch >= 'a' && ch <= 'z') {
                        int p = ch - 'a';
                        int c = (p * k) % 26;

                        ct = ct + (char)(c + 'A');

                    } else if (ch == ' ') {
                        ct = ct + ' ';

                    } else {
                        System.out.println("Invalid plain text!");
                        ct = "";
                        break;
                    }
                }

                if (!ct.equals("")) {
                    System.out.println("Cipher Text: " + ct);
                }
            }

            // Decryption
            else if (choice == 2) {
                System.out.print("Enter cipher text: ");
                String ct = sc.nextLine();

                System.out.print("Enter key (1-26): ");
                int k = sc.nextInt();

                if (k < 1 || k > 26) {
                    System.out.println("Invalid key!");
                    continue;
                }

                // Find K inverse
                int inverse = -1;

                for (int i = 1; i < 26; i++) {
                    if ((k * i) % 26 == 1) {
                        inverse = i;
                        break;
                    }
                }

                if (inverse == -1) {
                    System.out.println("K inverse does not exist!");
                    System.out.println("Please use a valid key.");
                    continue;
                }

                System.out.println("K inverse: " + inverse);

                String pt = "";

                for (int i = 0; i < ct.length(); i++) {
                    char ch = ct.charAt(i);

                    if (ch >= 'A' && ch <= 'Z') {
                        int c = ch - 'A';
                        int p = (c * inverse) % 26;

                        pt = pt + (char)(p + 'a');

                    } else if (ch == ' ') {
                        pt = pt + ' ';

                    } else {
                        System.out.println("Invalid cipher text!");
                        pt = "";
                        break;
                    }
                }

                if (!pt.equals("")) {
                    System.out.println("Plain Text: " + pt);
                }
            }

            // Exit
            else if (choice == 3) {
                System.out.println("Program exited.");
                break;
            }

            else {
                System.out.println("Invalid choice!");
            }
        }
    }
}