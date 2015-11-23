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
    private void _generate_parkings(Parking parking_conf) {
        // TODO Savoir quoi faire avec tout les parkings généré.
        for ( Car specific_car : parking_conf ) {
            // On avance la voiture tant que c'est possible.
            Parking tmp_parking_conf = parking_conf.move_forward( specific_car );
            while ( tmp_parking_conf != null ) {
                this._generate_parkings( tmp_parking_conf );
                tmp_parking_conf = parking_conf.move_forward( specific_car );
            }

            // On recule la voiture tant que c'est possible.
            tmp_parking_conf = parking_conf.move_backward( specific_car );
            while ( tmp_parking_conf != null ) {
                this._generate_parkings( parking_conf.move_backward(specic_car) )
                tmp_parking_conf = parking_conf.move_backward( specific_car );
            }
        }
    }

    public static void main (String[] args) {
        if ( args.length > 1) {
            ParkingIN parsedParking = new ParkingIN(args[1]);
            baseParking = parsedParking.parseParking();

            ParkingGraph result_graph = _generate_parkings(baseParking);
        } else {
            // print usage.
        }
    }
}
