import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Iterator;
import Car;

public class Parking {
    private int x_size;
    private int y_size;
    private Car goal_car;
    private Car[] carList;
    private boolean[][] parkingMatrix;

    /* http://stackoverflow.com/questions/5849154/can-we-write-our-own-iterator-in-java
     * @desc Iterateur sur les véhicules de la classe pour acceder
     *      au différents véhicules en dehors de la classe. 
     */
    @Override
    public Iterator<Car> iterator () {
        Iterator<Car> it = new Iterator<Car>();

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < carList.length;
        }

        @Override
        public Car next() {
            ++currentIndex;
            return carList[currentIndex];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* @desc Renvoie un la manière dont le parking serait si
     *      la voiture {toMoveCar} était bougée en avant.
     *
     * @param {toMoveCar} : Voiture que l'on va devoir bouger.
     *
     * @return {Parking} : La nouvelle forme du parking.
     */
    public Parking move_forward( Car toMoveCar ) {
        // Vérification que la voiture sait avancer.
        Car newCarPos = toMoveCar.forward();
        for ( int[2] newPosition : toMoveCar ) {
            if ( newPosition[0] > this.x_size
                    || newPosition[1] > this.y_size
                    || this.parkingMatrix[newPosition[0]][newPosition[1]] ) {
                // TODO problème ici car il y aura toujours les positions de la voiture.
                return null;
            }
        }

        // COPY de {carList} mais en remplaçant {toMoveCar}.
        Car[] result = new Car[this.carList.length];

        for ( int i = 0; i < this.carList.length(); ++i ) {
            if ( this.carList[i] == toMoveCar ) {
                result[i] = toMoveCar.forward()
            } else {
                result[i] = carElem.copy();
            }
        }

        return new Parking(this.x_size, this.y_size, result);
    }

    /* @desc Renvoie un la manière dont le parking serait si
     *      la voiture {toMoveCar} était bougée en arrière.
     *
     * @param {toMoveCar} : Voiture que l'on va devoir bouger.
     *
     * @return {Parking} : La nouvelle forme du parking.
     */
    public Parking move_backward( Car toMoveCar ) {
        // TODO la même qu'au dessus.
    }

    public Car set_goal_car (int x_pos, int y_pos) {
        this.goal_car = this.append_car (x_pos, y_pos); 
        // Est-ce qu'aucune copie ne va être fait,
        // est-ce qu'on a les même réfèrence ? 

        return this.goal_car;
    }

    public Car append_car (int x_pos, int y_pos) {
        Car newCar = new Car(x_pos, y_pos);
        this.carList.add( newCar );

        // TODO Prendre en compte que l'on peut avoir plusieurs positions.
        // TODO On peut faire cette opération avant pour attraper 
        //      les éventuels erreurs qu'il peut y avoir si il y a
        //      déjà une voiture à cette position.
        this.parkingMatrix[x_pos][y_pos] = true;

        return newCar;
    }

    Parking (int x_size, int y_size) {
        this.x_size = x_size;
        this.y_size = y_size;

        this.parkingMatrix = new boolean[x_size][y_size];
    }

    Parking (int x_size, int y_size, Car carList) {
        this.x_size = x_size;
        this.y_size = y_size;
        this.carList = carList;

        this.parkingMatrix = new boolean[x_size][y_size];
        for () {
            for () {
                // TODO Ajouter le tout à la matrice.
            }
        }
    }
}
