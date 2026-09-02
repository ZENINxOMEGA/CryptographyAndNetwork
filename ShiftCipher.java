import java.util.Scanner;
public class ShiftCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter shift: ");
        int shift = sc.nextInt();

        String result = "";

        for(int i=0; i<text.length(); i++) {
            char ch = text.charAt(i);

            if(ch >= 'a' && ch <= 'z') {
                if(choice == 1) {
                    ch = (char)(ch + shift);

                    if(ch > 'z')
                        ch = (char)(ch - 26);
                }
                else if(choice == 2) {
                    ch = (char)(ch - shift);

                    if(ch < 'a')
                        ch = (char)(ch + 26);
                }
            }
            result = result + ch;
        }

        if(choice == 1)
            System.out.println("Cipher Text: " + result);

        else if(choice == 2)
            System.out.println("Plain Text: " + result);

        else
            System.out.println("Invalid Choice!");
    }
}


//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.print("Enter cipher text: ");
//         String text = sc.nextLine();

//         for(int shift = 0; shift < 26; shift++) {
//             String plain = "";

//             for(int i=0; i<text.length(); i++) {
//                 char ch = text.charAt(i);

//                 if(ch >= 'a' && ch <= 'z') {
//                     ch = (char)(ch - shift);

//                     if(ch < 'a')
//                         ch = (char)(ch + 26);
//                 }
//                 plain = plain + ch;
//             }
//             System.out.println("Shift " + shift + ": " + plain);
//         }
//     }
// }