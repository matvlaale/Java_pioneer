package Lesson_4;


import java.util.Random;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    public static int catsCount = 0;
    public static int dogsCount = 0;
    public static int animalsCount = 0;

    public static void main(String[] args) {
        Animal seal = new Animal("Маша", "Ж");
        seal.setMaxes(100, 0, 300);
        seal.animalInfo();
        seal.voice();
        seal.run(100);
        seal.jump(1);
        seal.swim(200);
        System.out.println();

        Animal[] cat = new Cat[5];
        String[] names = {"Агата", "Аферистка", "Варя", "Златица", "Карина"};
        String[] colors = {"Серый", "Коричневый", "Красный", "Золотой", "Белый"};
        for (int i = 0; i < 5; i++) {
            cat[i] = new Cat(names[(int) (Math.random() * names.length)], "Ж", colors[(int) (Math.random() * colors.length)]);
            cat[i].setMaxes(Math.random() * 300 + 100, Math.random() * 2 + 1, Math.random() * 0.5);
        }
        int n = (int) (Math.random() * 4);
        ((Cat) cat[n]).catInfo();
        cat[n].getMaxes();
        cat[n].voice();
        cat[n].run(150);
        cat[n].jump(1);
        cat[n].swim(10);
        System.out.println();

        Dog dog = new Dog("Мухтар", "м", "Коричневый");
        dog.dogInfo();
        dog.voice();
        dog.run(300);
        dog.jump(2);
        dog.swim(10);
        System.out.println();
        
        System.out.println("Всего котов: " + catsCount + "\nВсего собак: " + dogsCount + "\nИтого животных: " + animalsCount);
    }

    public static void newCat() {
        catsCount++;
    }

    public static void newDog() {
        dogsCount++;
    }

    public static void newAnimal() {
        animalsCount++;
    }
}

