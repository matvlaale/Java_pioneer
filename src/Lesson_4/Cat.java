package Lesson_4;

public class Cat {
    String sex;
    String name;
    String color;
    int appetite;
    boolean isFed;

    public Cat(String name, String sex, String color, int appetite) {
        this.name = name;
        this.color = color;
        this.appetite = appetite;
        if (sex.equals("Ж")) this.sex = "а";
        else this.sex = "";
        normColor();
        isFed = false;
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

    public void eat(Plate plate) {
        if (isFed) System.out.println(name + " сыт" + sex);
        else if (plate.outFood(appetite)) {
            isFed = true;
            System.out.println(name + " поел" + sex);
        } else System.out.println(name + " не может" + " поесть");
    }

    public void voice() {
        System.out.println(name + " мяукнул" + sex);
    }

    public String getName() {
        return name;
    }

    public boolean getisFed() {
        return isFed;
    }
}
