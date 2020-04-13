import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int N = 5;
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static void main(String[] args) {
        String[] words = {"apple", "orange", "lemon", "banana", "apricot",
                "avocado", "broccoli", "carrot", "cherry", "garlic", "grape",
                "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive",
                "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        play(words);
    }

    public static void play(String[] words) {
        int n = Math.abs(random.nextInt()) % 25;
        String answer = "";
        String mask_string = "###############";
        System.out.println("Отгадайте слово: ");
        while (!answer.equals(words[n])) {
            char[] mask = mask_string.toCharArray();
            answer = scanner.next();
            if (!answer.equals(words[n])) {
                int en = Math.min(answer.length(), words[n].length());
                for (int i = 0; i < en; i++) {
                    if (answer.charAt(i) == words[n].charAt(i))
                        mask[i] = answer.charAt(i);
                }
                for (int i = 0; i < 15; i++) System.out.print(mask[i]);
                System.out.println();
            }
        }
        System.out.println("Верно");
    }
}
