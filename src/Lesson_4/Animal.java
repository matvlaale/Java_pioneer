package Lesson_4;

public class Animal {
    String name;
    String sex;
    float maxRun;
    float maxSwim;
    float maxJump;

    public Animal() {
    }

    public Animal(String name, String sex) {
        this.name = name;
        if (sex.equals("Ж") || sex.equals("Female")) this.sex = "а";
        else this.sex = "";
        Main.newAnimal();
        maxRun = 100;
        maxSwim = 10;
        maxJump = 1;
    }

    public void animalInfo() {
        System.out.println("Животное по имени " + name);
    }

    public void run(float l) {
        if (l <= maxRun) System.out.println(name + " пробежал" + sex + " " + l + " метров");
        else if (maxRun == 0) System.out.println(name + " не умеет бегать");
        else System.out.println(name + " не может пробежать так много");
    }

    public void jump(float l) {
        if (l <= maxJump) System.out.println(name + " прыгнул" + sex + " на " + l + " метров");
        else if (maxJump == 0) System.out.println(name + " не умеет прыгать");
        else System.out.println(name + " не может прыгнуть так высоко");
    }

    public void swim(float l) {
        if (l <= maxSwim) System.out.println(name + " проплыл" + sex + " " + l + " метров");
        else if (maxSwim == 0) System.out.println(name + " не умеет плавать");
        else System.out.println(name + " не может проплыть так много");
    }

    public void setMaxes(double maxRun, double maxJump, double maxSwim) {
        this.maxRun = Math.round(maxRun * 10) / 10f;
        this.maxJump = Math.round(maxJump * 10) / 10f;
        this.maxSwim = Math.round(maxSwim * 10) / 10f;
    }

    public void getMaxes() {
        System.out.println("Бежать до " + maxRun + "м.\nПрыгнуть до " + maxJump + "м.\nПлыть до " + maxSwim + "м.");
    }

    //abstract void voice();
    public void voice() {
        System.out.println("Животное по имени " + name + " издало звук");
    }
}
