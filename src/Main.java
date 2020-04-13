import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int N = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] mas = new int[N];

        System.out.println("5. Нахождение максимума и минимума");
        System.out.println("Введите " + N + " элементов массива: ");
        for (int i = 0; i < N; i++)
            mas[i] = scanner.nextInt();
        maxmin(mas);

        System.out.println("6. Нахождение равных сумм частей");
        System.out.println("Введите " + N + " элементов массива: ");
        for (int i = 0; i < N; i++)
            mas[i] = scanner.nextInt();
        System.out.println(palindrom(mas) + "\n");

        System.out.println("7. Сдвиг элементов");
        System.out.println("Введите " + N + " элементов массива: ");
        for (int i = 0; i < N; i++)
            mas[i] = scanner.nextInt();
        System.out.println("Введите количество позиций для сдвига: ");
        int n = scanner.nextInt();
        push(mas, n);
    }

    public static void maxmin(int[] mas) {
        int maxi = mas[0];
        int mini = mas[0];
        for (int i = 1; i < N; i++) {
            if (mas[i] > maxi) maxi = mas[i];
            if (mas[i] < mini) mini = mas[i];
        }
        System.out.println("Максимальный элемент = " + maxi
                + "\nМинимальный элемент = " + mini + "\n");
    }

    public static boolean palindrom(int[] mas) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < N; i++)
            sum1 += mas[i];
        for (int i = 0; i < N; i++) {
            sum1 -= mas[i];
            sum2 += mas[i];
            if (sum1 == sum2) return true;
        }
        return false;
    }

    public static void push(int[] mas, int n) {
        int zap = 0;
        if (n < 0) n = N + (n % N);
        n %= N;
        for (int i = 0; i < n; i++) {
            zap = mas[N - 1];
            for (int j = N - 1; j > 0; j--) {
                mas[j] = mas[j - 1];
            }
            mas[0] = zap;
        }
        for (int i = 0; i < N; i++)
            System.out.print(mas[i] + "  ");
    }
}