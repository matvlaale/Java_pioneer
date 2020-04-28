package Lesson_4;

public class Plate {
    int food;

    Plate(int food) {
        this.food = food;
    }

    public boolean outFood(int food) {
        if (this.food >= food) {
            this.food -= food;
            return true;
        }
        return false;
    }

    public void inFood(int food) {
        this.food += food;
    }
}
