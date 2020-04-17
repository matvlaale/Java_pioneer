package Lesson_4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static char[][] map;
    public static final int size = 5;
    public static final int dots_to_win = 4;
    public static final char empty = '•';
    public static final char iks = 'X';
    public static final char nol = 'O';
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(iks, true)) {
                System.out.println("Победил человек");
                printMap();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(nol, true)) {
                System.out.println("Победил Искуственный Интеллект");
                printMap();
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }

    public static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = empty;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void humanTurn() {
        int x, y;
        int[] help = aihelp(nol);
        help[0]++;
        help[1]++;
        do {
            System.out.println("Введите координаты в формате X Y");
            System.out.print("Совет ассистента: ");
            if (help[0] != 1 || help[1] != 1) System.out.println(help[0] + " " + help[1]);
            else System.out.println("- -");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = iks;
    }

    public static void aiTurn() {
        int x, y;
        int[] help = aihelp(iks);
        while (!isCellValid(help[0], help[1])) {
            help[0] = rand.nextInt(size);
            help[1] = rand.nextInt(size);
        }
        do {
            x = help[0];
            y = help[1];
            help[0] = rand.nextInt(size);
            help[1] = rand.nextInt(size);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер поставил нолик в " + (x + 1) + " " + (y + 1));
        map[y][x] = nol;
    }

    public static int[] aihelp(char symb) {
        char opsymb = nol;
        if (symb == nol) opsymb = iks;
        int[] help = {0, 0};
        int check = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == empty) {
                    map[i][j] = symb;
                    if (checkWin(symb, false)) {
                        help[0] = j;
                        help[1] = i;
                    }
                    map[i][j] = opsymb;
                    if (checkWin(opsymb, false)) {
                        help[0] = j;
                        help[1] = i;
                        check = 1;
                    }
                    map[i][j] = empty;
                    if (check == 1 && (help[0] != 0 || help[1] != 0)) return help;
                }
            }
        }
        return help;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return false;
        return map[y][x] == empty;
    }

    public static boolean checkWin(char symb, boolean system) {
        char pobed = (symb == nol) ? '@' : '#';
        int[] sum = new int[4];
        int check = 0;
        for (int i = 0; i < size && check == 0; i++) {
            for (int j = 0; j < size && check == 0; j++) {
                for (int k = 0; k < 4; k++) sum[k] = 0;
                for (int k = 0; k < dots_to_win; k++) {
                    if (size - j >= dots_to_win)
                        if (map[i][j + k] == symb) sum[0]++;
                    if (size - i >= dots_to_win)
                        if (map[i + k][j] == symb) sum[1]++;
                    if (size - j >= dots_to_win && size - i >= dots_to_win)
                        if (map[i + k][j + k] == symb) sum[2]++;
                    if (size - j >= dots_to_win && i + 1 >= dots_to_win)
                        if (map[i - k][j + k] == symb) sum[3]++;
                }
                for (int k = 0; k < 4; k++) {
                    if (sum[k] >= dots_to_win) {
                        check = k + 1;
                        break;
                    }
                }
                if (system) for (int k = 0; k < dots_to_win; k++) {
                    if (check - 1 == 0)
                        map[i][j + k] = pobed;
                    else if (check - 1 == 1)
                        map[i + k][j] = pobed;
                    else if (check - 1 == 2)
                        map[i + k][j + k] = pobed;
                    else if (check - 1 == 3)
                        map[i - k][j + k] = pobed;
                }
            }
        }
        return check != 0;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == empty) return false;
            }
        }
        return true;
    }

}
