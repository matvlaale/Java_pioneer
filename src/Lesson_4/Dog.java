package Lesson_4;

public class Dog extends Animal {
    String color;

    public Dog(String name, String sex, String color) {
        super(name, sex);
        this.color = color;
        normColor();
        Main.newDog();
        maxRun = 500;
        maxJump = 0.5f;
        maxSwim = 10;
    }

    public void normColor() {
        StringBuilder colorBuild = new StringBuilder(color);
        colorBuild.replace(colorBuild.length() - 2, colorBuild.length(), "");
        if (this.sex.equals("а")) this.color = colorBuild + "ая";
        else this.color = colorBuild + "ый";
    }

    public void dogInfo() {
        if (sex.equals("а")) System.out.println(color + " собака по имени " + name);
        else System.out.println(color + " пёс по имени " + name);
    }

    @Override
    public void voice() {
        System.out.println(name + " гавкнул" + sex);
    }
}
