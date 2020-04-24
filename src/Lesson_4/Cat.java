package Lesson_4;

public class Cat extends Animal {
    String color;

    public Cat(String name, String sex, String color) {
        super(name, sex);
        this.color = color;
        normColor();
        Main.newCat();
        maxRun = 200;
        maxJump = 2;
        maxSwim = 0;
    }

    public void normColor() {
        StringBuilder colorBuild = new StringBuilder(color);
        colorBuild.replace(colorBuild.length() - 2, colorBuild.length(), "");
        if (this.sex.equals("а")) this.color = colorBuild + "ая";
        else this.color = colorBuild + "ый";
    }

    public void catInfo() {
        if (sex.equals("а")) System.out.println(color + " кошка по имени " + name);
        else System.out.println(color + " кот по имени " + name);
    }

    @Override
    public void voice() {
        System.out.println(name + " мяукнул" + sex);
    }
}
