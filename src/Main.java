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
            if (checkWin(iks)) {
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
            if (checkWin(nol)) {
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
        int[] sum = new int[6];
        int[] sumo = new int[4];
        for (int i = 0; i < 6; i++) {
            sum[i] = 0;
            if (i < 4) sumo[i] = 0;
        }
        int[] help = {0, 0};

        int sumx;
        for (int i = 0; i < size; i++) {

            //horizontal
            sumx = 0;
            for (int j = 0; j < size; j++) {
                if (map[i][j] == symb) sumx++;
            }
            if (sumx >= 3 && map[i][1] != opsymb && map[i][2] != opsymb && map[i][3] != opsymb) {
                if (map[i][0] == empty) {
                    help[0] = 0;
                    help[1] = i;
                }
                if (map[i][size - 1] == empty) {
                    help[0] = size - 1;
                    help[1] = i;
                }
                for (int j = 1; j < 4; j++)
                    if (map[i][j] == empty) {
                        help[0] = j;
                        help[1] = i;
                    }
            }

            //vertical
            sumx = 0;
            for (int j = 0; j < size; j++) {
                if (map[j][i] == symb) sumx++;
            }
            if (sumx >= 3 && map[1][i] != opsymb && map[2][i] != opsymb && map[3][i] != opsymb) {
                if (map[0][i] == empty) {
                    help[0] = i;
                    help[1] = 0;
                }
                if (map[size - 1][i] == empty) {
                    help[0] = i;
                    help[1] = size - 1;
                }
                for (int j = 1; j < 4; j++)
                    if (map[j][i] == empty) {
                        help[0] = i;
                        help[1] = j;
                    }
            }
        }

        //complex diagonal
        for (int i = 1; i < size; i++) {
            //straight
            if (map[i][i - 1] == symb) sum[1]++;
            if (map[i - 1][i] == symb) sum[2]++;
            if (map[i][size - i] == symb) sum[3]++;
            if (map[i - 1][size - i - 1] == symb) sum[4]++;
            //opposite
            if (map[i][i - 1] == opsymb) sumo[0]++;
            if (map[i - 1][i] == opsymb) sumo[1]++;
            if (map[i][size - i] == opsymb) sumo[2]++;
            if (map[i - 1][size - i - 1] == opsymb) sumo[3]++;
        }
        for (int i = 1; i < size; i++) {
            //straight
            if (sum[1] >= 3 && map[i][i - 1] == empty) {
                help[0] = i - 1;
                help[1] = i;
            }
            if (sum[2] >= 3 && map[i - 1][i] == empty) {
                help[0] = i;
                help[1] = i - 1;
            }
            if (sum[3] >= 3 && map[i][size - i] == empty) {
                help[0] = size - i;
                help[1] = i;
            }
            if (sum[4] >= 3 && map[i - 1][size - i - 1] == empty) {
                help[0] = size - i - 1;
                help[1] = i - 1;
            }
            //opposite
            if (sumo[0] >= 3 && map[i][i - 1] == empty) {
                help[0] = i - 1;
                help[1] = i;
            }
            if (sumo[1] >= 3 && map[i - 1][i] == empty) {
                help[0] = i;
                help[1] = i - 1;
            }
            if (sumo[2] >= 3 && map[i][size - i] == empty) {
                help[0] = size - i;
                help[1] = i;
            }
            if (sumo[3] >= 3 && map[i - 1][size - i - 1] == empty) {
                help[0] = size - i - 1;
                help[1] = i - 1;
            }
        }
        if (help[0] != 0 || help[1] != 0) return help;

        //simple diagonal
        for (int i = 0; i < size; i++) {
            if (map[i][i] == symb) sum[0]++;
            if (map[size - i - 1][i] == symb) sum[5]++;
        }
        if (sum[0] >= 3 && map[1][1] != opsymb && map[2][2] != opsymb && map[3][3] != opsymb) {
            if (map[1][1] == empty) {
                help[0] = 1;
                help[1] = 1;
            } else if (map[2][2] == empty) {
                help[0] = 2;
                help[1] = 2;
            } else if (map[3][3] == empty) {
                help[0] = 3;
                help[1] = 3;
            } else {
                help[0] = 4;
                help[1] = 4;
            }
        }
        if (sum[5] >= 3 && map[3][1] != opsymb && map[2][2] != opsymb && map[1][3] != opsymb) {
            if (map[3][1] == empty) {
                help[0] = 1;
                help[1] = 3;
            } else if (map[2][2] == empty) {
                help[0] = 2;
                help[1] = 2;
            } else if (map[1][3] == empty) {
                help[0] = 3;
                help[1] = 1;
            } else if (map[0][4] == empty) {
                help[0] = 4;
                help[1] = 0;
            } else {
                help[0] = 0;
                help[1] = 4;
            }
        }
        return help;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return false;
        return map[y][x] == empty;
    }

    public static boolean checkWin(char symb) {
        int check_lines = 8;
        char pobed = (symb == nol) ? '@' : '#';
        int[] sum = new int[check_lines];
        int[] check = new int[check_lines];
        for (int j = 0; j < check_lines; j++) {
            sum[j] = 0;
            check[j] = 1;
        }
        for (int i = 0; i < size; i++) {
            //simple lines
            for (int j = 0; j < 2; j++) {
                sum[j] = 0;
                check[j] = 1;
            }
            for (int j = 0; j < size; j++) {
                if (map[j][i] == symb && (check[0] == 1 || j == 1)) {
                    sum[0]++;
                    check[0] = 1;
                } else check[0] = 0;
                if (map[i][j] == symb && (check[1] == 1 || j == 1)) {
                    sum[1]++;
                    check[1] = 1;
                } else check[1] = 0;
            }
            if (sum[0] >= 4) {
                for (int j = 0; j < size; j++)
                    if (map[j][i] == symb) map[j][i] = pobed;
                return true;
            }
            if (sum[1] >= 4) {
                for (int j = 0; j < size; j++)
                    if (map[i][j] == symb) map[i][j] = pobed;
                return true;
            }

            //simple diagonal
            if (map[i][i] == symb && (check[2] == 1 || i == 1)) {
                sum[2]++;
                check[2] = 1;
            } else check[2] = 0;
            if (map[size - i - 1][i] == symb && (check[3] == 1 || i == 1)) {
                sum[3]++;
                check[3] = 1;
            } else check[3] = 0;

            //complex diagonal
            if (i > 0) {
                if (map[i][i - 1] == symb) sum[4]++;
                if (map[i - 1][i] == symb) sum[5]++;
                if (map[i][size - i] == symb) sum[6]++;
                if (map[i - 1][size - i - 1] == symb) sum[7]++;
            }
        }
        if (sum[2] >= 4) {
            for (int i = 0; i < size; i++)
                if (map[i][i] == symb) map[i][i] = pobed;
            return true;
        }
        if (sum[3] >= 4) {
            for (int i = 0; i < size; i++)
                if (map[size - i - 1][i] == symb) map[size - i - 1][i] = pobed;
            return true;
        }
        if (sum[4] >= 4) {
            for (int i = 1; i < size; i++)
                if (map[i][i - 1] == symb) map[i][i - 1] = pobed;
            return true;
        }
        if (sum[5] >= 4) {
            for (int i = 1; i < size; i++)
                if (map[i - 1][i] == symb) map[i - 1][i] = pobed;
            return true;
        }
        if (sum[6] >= 4) {
            for (int i = 1; i < size; i++)
                if (map[i][size - i] == symb) map[i][size - i] = pobed;
            return true;
        }
        if (sum[7] >= 4) {
            for (int i = 1; i < size; i++)
                if (map[i - 1][size - i - 1] == symb) map[i - 1][size - i - 1] = pobed;
            return true;
        }
        return false;
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
