package xogame;


import sun.rmi.runtime.Log;

import java.util.Random;
import java.util.Scanner;

public class Logic {
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    public static String[] valueColors = {"Серый", "Оранжевый", "Зелёный", "Синий", "Фиолетовый", "Красный"};
    public static String[] propertyColors = {"Чёрный", "Красный", "Жёлтый", "Оранжевый", "Фиолетовый", "Зелёный", "Голубой", "Синий", "Коричневый", "Водопроводный"};
    public static String[] types = {"Деньги", "Собственность", "Рента", "Действие", "Недвижимость"};

    public static String[] actionTypes = {"Выкрасть здание", "Забрать 2М", "Забрать 5М"};
    public static int[] actionValues = {3, 2, 3};
    public static String[] actionDescriptions = {"Заберите любое здание у игрока (кроме комплекта)", "Заберите 2М", "Заберите 5М"};

    public static void main() {
        Player[] players = new Player[2];
        players[0] = new Player();
        players[1] = new Player();
        Card newCard;

        int turn = 1;
        for (int i = 0; i < 5; i++) {
            newCard = genCard();
            players[0].addCard(newCard);
            newCard = genCard();
            players[1].addCard(newCard);
        }
        while (checkWin(players) == -1) {
            turn = (turn + 1) % 2;
            newCard = genCard();
            players[turn].addCard(newCard);
            newCard = genCard();
            players[turn].addCard(newCard);

            System.out.println("ИГРОК " + (turn + 1));

            int next = -1;
            int turnCount = 1;
            while (next != 0 && turnCount <= 3) {
                players[turn].infoOpen();
                System.out.println();
                players[turn].infoClose();
                System.out.println("Ход №" + turnCount++ + "\nВоспользуетесь картой? Пишите её номер или 0, если нет: ");
                next = scanner.nextInt();
                if (players[turn].getNumCards() < next) {
                    System.out.println("Такого номера нет. Напишите другой: ");
                    next = scanner.nextInt();
                } else if (next != 0){
                    useCard(players[turn], players[(turn + 1) % 2], next - 1);
                    if(turnCount > 3) next = 0;
                }
            }
            while(players[turn].getNumCards() > 7){
                System.out.println("Осталось слишком много карт (" + "Нужно выкинуть " + (players[turn].getNumCards() - 7) + ")");
                players[turn].infoClose();
                System.out.println("Введите номер карты");
                int number = scanner.nextInt();
                players[turn].removeCard(number, "");
            }
        }
        int winner = checkWin(players);
        System.out.println("Игрок №" + winner + " победил!");
    }

    public static int checkWin(Player[] players) {
        if (players[0].getNumPropertySet() == 3 || players[0].isWinner()) return 1;
        if (players[1].getNumPropertySet() == 3 || players[1].isWinner()) return 2;
        return -1;
    }

    public static int numOfElement(String[] array, String elem) {
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(elem)) return i;
        return -1;
    }

    public static void useCard(Player player1, Player player2, int number) {
        if (player1.getPack()[number].getType().equals("Рента") || player1.getPack()[number].getType().equals("Забрать 2М") || player1.getPack()[number].getType().equals("Забрать 5М")) {
            int money;
            if (player1.getPack()[number].getType().equals("Рента")) {
                String color = "test";
                while(!player1.getPack()[number].getFirst().equals(color) && !player1.getPack()[number].getSecond().equals(color)) {
                    System.out.println("За какой цвет хотите взять ренту?");
                    color = scanner.next();
                }
                money = player1.getRents()[numOfElement(propertyColors, color)];
            } else {
                if (player1.getPack()[number].getType().equals("Забрать 2М")) money = 2;
                else money = 5;
            }
            System.out.println("Другой игрок должен заплатить " + money + "М");
            player1.addMoney(new Card("Деньги", "", money));
            int more = player2.removeMoney(money);
            if (more == 0) System.out.println("Другой игрок заплатил из банка");
            else {
                System.out.println("Денег в банке не хватило, чем будете расплачиваться?");
                while (more > 0 && player2.getNumCards() != 0) {
                    player2.infoClose();
                    System.out.println("Введите номер карты (осталось: " + more + "М):");
                    int number2 = scanner.nextInt();
                    if (player2.getPack()[number2 - 1] != null) {
                        more -= player2.getPack()[number2 - 1].getValue();
                        player2.removeCard(number2, "");
                    }
                }
                if(more > 0 && player2.getNumCards() == 0) player1.setWinner();
                else System.out.println("Долг выплачен");
            }
        } else if (player1.getPack()[number].getType().equals("Выкрасть здание")) {
            player2.infoOpen();
            System.out.println("Выберите цвет собственности: ");
            String color = scanner.next();
            if(player2.removeCard(0, color))
                player1.addProperty(new Card("Собственность", "Здание", 1, color));
        }
        player1.removeCard(number + 1, "");
    }

    public static Card genCard() {
        int typeNum = rand.nextInt(4);
        String name = "";
        int value = 1;
        Card newCard;

        if (typeNum == 0) {
            value = rand.nextInt(6) + 1;
            if (value == 6) value = 10;
            newCard = new Card(types[typeNum], name, value);
        } else if (typeNum == 1) {
            int colorNum = rand.nextInt(10);
            name = "Здание";
            newCard = new Card(types[typeNum], name, value, propertyColors[colorNum]);
        } else if (typeNum == 2) {
            int colorNum1 = rand.nextInt(10);
            int colorNum2 = rand.nextInt(10);
            newCard = new Rent(propertyColors[colorNum1], propertyColors[colorNum2]);
        } else if (typeNum == 3) {
            int actionNum = rand.nextInt(actionTypes.length);
            newCard = new Action(actionTypes[actionNum], actionValues[actionNum]);
        } else newCard = new Card();
        return newCard;
    }
}

class Player {
    int numCards;
    int numPropertySets;
    int money;
    int[] rents;
    Card[][] properties;
    Card[] pack;
    boolean winner;

    public Player() {
        this.numCards = 0;
        this.numPropertySets = 0;
        properties = new Card[10][4];
        rents = new int[10];
        pack = new Card[12];
        money = 0;
        winner = false;
    }

    public void addProperty(Card newCard) {
        int number = Logic.numOfElement(Logic.propertyColors, newCard.getColor());
        for (int i = 0; i < 4; i++) {
            if (properties[number][i] == null) {
                properties[number][i] = newCard;
                break;
            }
        }
        checkProperties();
    }

    public boolean removeProperty(String color) {
        int number = Logic.numOfElement(Logic.propertyColors, color);
        for (int i = 0; i < 4; i++) {
            if (number >= 7 && i >= 2
                    || number <= 6 && number >= 1 && i >= 3) {
                System.out.println("Нельзя забирать здания из комплекта!");
                return false;
            }
            if (properties[number][i] == null) {
                properties[number][i - 1] = null;
                checkProperties();
                return true;
            }
        }
        return false;
    }

    public void addMoney(Card newCard) {
        money += newCard.getValue();
    }

    public int removeMoney(int amount) {
        if (amount <= this.money) {
            money -= amount;
            return 0;
        } else {
            amount -= this.money;
            this.money = 0;
            return amount;
        }
    }

    public void addCard(Card newCard) {
        if (newCard.getType().equals("Собственность")) addProperty(newCard);
        else if (newCard.getType().equals("Деньги")) addMoney(newCard);
        else {
            numCards++;
            pack[numCards - 1] = newCard;
        }
    }

    public boolean removeCard(int number, String color) {
        if ((!color.equals("")) && rents[Logic.numOfElement(Logic.propertyColors, color)] != 0)
            return removeProperty(color);
        else if (!color.equals("")) System.out.println("Такой собственности нет");
        else if (pack[number - 1] != null) {
            numCards--;
            number--;
            int count = 0;
            for (int i = 0; i < 12; i++) {
                if (pack[i] != null) count = i;
                if (i == number) pack[i] = null;
            }
            if (number != count) {
                pack[number] = pack[count];
                pack[count] = null;
            }
            return true;
        } else System.out.println("Такой карты нет");
        return false;
    }

    public void infoOpen() {
        System.out.println("Денег в банке: " + money + "М");
        System.out.println("Всего комплектов собственности " + numPropertySets);
        for (int i = 0; i < 10; i++) {
            if (properties[i][0] != null) {
                System.out.print(Logic.propertyColors[i] + ": ");
                for (int j = 0; j < 4; j++) {
                    if (properties[i][j] != null) System.out.print(properties[i][j].name + ", ");
                    else break;
                }
                System.out.println("рента = " + rents[i]);
            }
        }
    }

    public void infoClose() {
        System.out.println("Карты игрока:");
        int count = 1;
        for (int i = 0; i < 12; i++)
            if (pack[i] != null) {
                System.out.print((count++) + " ");
                pack[i].info();
            }
    }

    public void checkProperties() {
        numPropertySets = 0;
        int[] numProp = new int[10];
        for (int i = 0; i < 10; i++) {
            numProp[i] = 0;
            for (int j = 0; j < 4; j++) {
                if (properties[i][j] != null) numProp[i]++;
                else break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if (i >= 7 && numProp[i] >= 2
                    || i == 0 && numProp[i] >= 4
                    || i <= 6 && i >= 1 && numProp[i] >= 3) numPropertySets++;
            rents[i] = numProp[i];
        }
    }

    public void setWinner(){
        winner = true;
    }

    public int getNumPropertySet() {
        return numPropertySets;
    }

    public int getNumCards() {
        return numCards;
    }

    public Card[] getPack() {
        return pack;
    }

    public int[] getRents() {
        return rents;
    }

    public boolean isWinner(){
        return winner;
    }
}

class Card {
    String type;
    String name;
    String color;
    String description;
    int value;

    public Card() {
        this.type = "";
        this.name = "";
        this.value = 1;
        this.color = "Серый";
        this.description = "";
    }

    public Card(String type, String name, int value) {
        this.type = type;
        this.name = name;
        this.value = value;
        if (value != 10) this.color = Logic.valueColors[value - 1];
        else this.color = Logic.valueColors[5];
        this.description = "";
    }

    public Card(String type, String name, int value, String color) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.color = color;
        this.description = "";
    }

    public void info() {
        System.out.println(name + ", " + color + ", " + value);
        System.out.println(description);
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getFirst() {
        return "";
    }

    public String getSecond() {
        return "";
    }
}

class Rent extends Card {
    String first;
    String second;
    int numOfProperties;

    Rent(String first, String second) {
        super();
        this.type = "Рента";
        this.name = "Рента";
        this.description = "Возьмите ренту за " + first + " цвет или " + second;

        this.first = first;
        this.second = second;
        this.numOfProperties = 2;
    }

    Rent() {
        super();
        this.type = "Рента";
        this.value = 3;
        this.color = "Зелёный";

        this.first = "";
        this.second = "";
        this.numOfProperties = 10;
    }
    @Override
    public String getFirst(){ return first; }
    @Override
    public String getSecond(){ return second; }
}

class Action extends Card {
    String actionType;

    Action(String actionType, int value) {
        super("Действие", actionType, value);
        int numberAction = Logic.numOfElement(Logic.actionTypes, actionType);
        this.description = Logic.actionDescriptions[numberAction];
        this.actionType = actionType;
    }

    @Override
    public String getType() {
        return actionType;
    }
}