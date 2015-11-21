import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import Car;

public class Parking {
    private int x_size;
    private int y_size;
    private Car goal_car;
    private Car[] carList;

    public Car set_goal_car (int x_pos, int y_pos) {
        this.goal_car = this.append_car (x_pos, y_pos); 
        // Est-ce qu'aucune copie ne va être fait,
        // est-ce qu'on a les même réfèrence ? 

        return this.goal_car;
    }

    public Car append_car (int x_pos, int y_pos) {
        Car newCar = new Car(x_pos, y_pos);
        this.carList.add( newCar );
        return newCar;
    }

    Parking (x_size, y_size) {

    }
}
