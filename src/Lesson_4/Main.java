package Lesson_4;


import java.util.Random;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        Plate plate = new Plate(0);
        Cat[] cat = new Cat[5];
        String[] names = genNames(5);
        String[] colors = {"Серый", "Коричневый", "Красный", "Золотой", "Белый"};
        String[] sexes = {"М", "Ж"};
        for (int i = 0; i < 5; i++) {
            cat[i] = new Cat(names[i], sexes[rand.nextInt(sexes.length)], colors[rand.nextInt(colors.length)], rand.nextInt(20));
        }

        int check = 1;
        while (check != 0) {
            check = 0;
            plate.inFood(20);
            for (int i = 0; i < 5; i++) {
                cat[i].eat(plate);
                if (!cat[i].getisFed()) check = 1;
            }
            System.out.println();
        }
        System.out.println("Все кошаки сытые");
    }

    public static String[] genNames(int n) {
        String[] particles = {"Га", "Та", "Ва", "Ря", "Фе", "Ри", "Ста", "Зла", "Ти", "Ца", "Ко", "Ну"};
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            StringBuilder newName = new StringBuilder();
            int numParts = rand.nextInt(3) + 2;
            for (int j = 0; j < numParts; j++) {
                String newParticle = particles[rand.nextInt(particles.length)];
                if (j != 0) newParticle = newParticle.toLowerCase();
                newName.append(newParticle);
            }
            names[i] = newName.toString();
        }
        return names;
    }
}

