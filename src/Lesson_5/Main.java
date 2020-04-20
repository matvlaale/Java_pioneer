package Lesson_5;


import java.util.Random;
import java.util.Scanner;


class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        int age, money;
        String name, pos, email, phone;
        System.out.println("Введите ФИО: ");
        name = scanner.nextLine();
        System.out.println("Введите должность: ");
        pos = scanner.nextLine();
        System.out.println("Введите зарплату и возраст: ");
        money = scanner.nextInt();
        age = scanner.nextInt();
        System.out.println("Введите почту: ");
        email = scanner.next();
        System.out.println("Введите номер телефона: ");
        phone = scanner.next();
        Employee director = new Employee(name, age, pos, email, phone, money);
        director.normPhone();
        director.setCur("£");
        director.info();

        Employee[] workers = new Employee[5];
        workers[0] = new Employee("Zhan Xio", 48, "Токарь", "zhanxio@cheery.cn", "+86(10)6764-5489", 22000);
        workers[1] = new Employee("Kinchimo Xio", 42, "Главный инженер", "kinchimoxio@cheery.cn", "+86(10)6764-5479", 38000);
        workers[2] = new Employee("Aleksa Min", 32, "Секретарь", "aleksamin@cheery.cn", "+86(10)2624-5372", 20000);
        workers[3] = new Employee("Sasha Zeleni", 41, "Менеджер", "sashazeleni@cheery.cn", "+86(10)1220-1584", 40000);
        workers[4] = new Employee("Alejandro Rojas", 37, "Глава отдела продаж", "alejandrorojas@cheery.cn", "+86(10)1919-9192", 55000);
        for (int i = 0; i < 5; i++) {
            workers[i].setCur("¥");
            if (workers[i].getAge() >= 40) workers[i].info();
        }
    }
}

class Employee {

    private String cur;
    private String name;
    private String position;
    private String email;
    private String phone;
    private int money;
    private int age;

    public Employee(String name, int age, String position, String email, String phone, int money) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.money = money;
        this.cur = "₽";
    }

    public void normPhone() {
        StringBuilder ph = new StringBuilder(phone);
        if (ph.charAt(0) != '+') {
            ph.replace(0, 1, "+7");
        }
        if (ph.charAt(2) != '(') {
            ph.insert(2, '(');
            ph.insert(6, ')');
        }
        if (ph.charAt(10) != '-') {
            ph.insert(10, '-');
            ph.insert(13, '-');
        }
        this.phone = ph.toString();
    }

    public void info() {
        System.out.println(position + "\n" + name + ", " + age + "\nЗарплата: " + cur + money + "\nПочта: " + email + "\nТелефон: " + phone + "\n");
    }

    public int getAge() {
        return age;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }
}