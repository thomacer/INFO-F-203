import Parking;
import ParkingIN;
import Car;

public class RushHour {
    // private Car[] carList;
    private Parking baseParking;

    /* @desc Génère récursivement toutes les configurations 
     *      possible de parking.
     *
     * @param {parking_conf} : Un objet parking qui contient 
     *      la configuration d'un parking (position voiture
     *      + taille du parking).
     */
    private _generate_parkings(Parking parking_conf) {

    }

    public static void main (String[] args) {
        if ( args.length > 1) {
            ParkingIN parsedParking = new ParkingIN(args[1]);
            baseParking = parsedParking.parseParking();

            ParkingGraph result_graph = _generate_parkings(parking_conf);
        } else {
            // print usage.
        }
    }
}
