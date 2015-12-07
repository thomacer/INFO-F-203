import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.UnsupportedOperationException;

public class RushHour {
    /* @desc Place dans la liste.
     *
     * @param {newParking} : Nouveau parking à placer dans le graphe.
     *
     * @return {boolean} true si ajouté dans la liste sinon false (déjà dans la liste).
     */
    static private boolean _put_in_list (Parking newParking, ArrayList<Parking> parkingList) {
        int i = 0;
        boolean result;
        // Recherche pour savoir si il y a déjà un {newParking} dans la liste.
        while ( (i < parkingList.size()) && !(parkingList.get(i).equals(newParking)) ) {
            ++i;
        }
        if (i == parkingList.size()) {
            // Si il n'est pas dans la liste.
            parkingList.add(newParking);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /* @desc Crée la liste des parking emprunté une fois que l'algorithme
     *  de recherche du plus court chemin est finis.
     *
     * @param {parkingList} Liste des parking générés.
     * @param {countNode} Distance par apport au parking base pour chaques noeuds.
     * @param {nodePath} Liste des parent pour chaques noeuds.
     * @param {currentNode} Index du noeud gagnant sur lequel l'algorithme s'est arrêté.
     *
     * @return {Parking[]} Liste des parkings dans l'ordre des mouvements effectué.
     */
    static private Parking[] _create_path (ArrayList<Parking> parkingList,
                                           ArrayList<Integer> countNode,
                                           ArrayList<Integer> nodePath,
                                           int currentNode)
    {
        int resultSize = countNode.get(currentNode);
        Parking[] result = new Parking[resultSize + 1]; // Ajouter 1 car le count commence à 0.

        // Création de la liste résultat, contenant le trajet dans l'ordre.
        for (int i = resultSize; i >= 0; --i) {
            result[i] = parkingList.get(currentNode);
            currentNode = nodePath.get(currentNode);
        }

        return result;
    }

    /* @desc Crée la liste des parking emprunté une fois que l'algorithme
     *  de recherche du plus court chemin est finis.
     *
     * @param {queue} Queue pour les parkings que l'on doit encore traiter.
     * @param {parkingList} Liste des parking générés.
     * @param {nodePath} Liste des parent pour chaques noeuds.
     * @param {countNode} Distance par apport au parking base pour chaques noeuds.
     * @param {newParkingConf} Nouveau parking a éventuellement rajouter dans les listes.
     * @param {currentNode} Index du noeud gagnant sur lequel l'algorithme s'est arrêté.
     *
     * @return {Parking[]} Liste des parkings dans l'ordre des mouvements effectué.
     */
    static private void _update_lists(LinkedList<Integer> queue,
                                      ArrayList<Parking> parkingList,
                                      ArrayList<Integer> nodePath,
                                      ArrayList<Integer> countNode,
                                      Parking newParkingConf,
                                      int currentNode)
    {
        if (newParkingConf != null) {
            if ( _put_in_list(newParkingConf, parkingList) ) {
                queue.add( parkingList.size() - 1 );
                nodePath.add(currentNode);
                countNode.add( countNode.get(currentNode) + 1 );
            }
        }
    }

    /* @desc Parcour en breadth first du graph pour trouver le chemin le plus
     *      court pour accêder à la sortie.
     *
     * @param {baseParking} : Configuration du parking au départ.
     *
     * @return {Parking[]} La trajet à travers les différentes configurations
     *      de parking pour arriver à la sortie.
     */
    static public Parking[] find_shortest_path (Parking baseParking) {
        // Va stocker l'antécédent à chaques noeuds traité.
        // À chaque "case" de ce tableau il va être stocké
        // l'index de son antécédent.
        ArrayList<Integer> nodePath = new ArrayList<Integer>();
        nodePath.add(0);

        // Va stocker la distance par apport à la base.
        ArrayList<Integer> countNode = new ArrayList<Integer>();
        countNode.add(0);

        // Stock les noeuds à traiter.
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);

        ArrayList<Parking> parkingList = new ArrayList<Parking>();
        parkingList.add(baseParking);

        int currentNode = 0;

        while ( !(parkingList.get(currentNode).is_won()) ) {
            Parking currentParking = parkingList.get(currentNode);
            for ( Car aCar : currentParking ) {
                Parking newParkingConf = currentParking.move_forward(aCar);
                _update_lists(queue, parkingList, nodePath, countNode, newParkingConf, currentNode);
                    
                newParkingConf = currentParking.move_backward(aCar);
                _update_lists(queue, parkingList, nodePath, countNode, newParkingConf, currentNode);
            }
            
            if ( queue.size() > 0 ) {
                currentNode = queue.poll();
            } else {
                // Si on est arrivé au dernier noeud et qu'on a toujours 
                // pas trouvé de chemin gagnant.
                // TODO Throw une erreur plutôt
                // Parking[] result = new Parking[2];
                // result[0] = baseParking;
                // result[1] = null;
                // return result;
                throw new UnsupportedOperationException();
            }
        }

        return _create_path(parkingList, countNode, nodePath, currentNode);
    }

    public static void main (String[] args) {
        if ( args.length > 0) {
            RushHour main = new RushHour();

            ParkingIN parsedParking = new ParkingIN(args[0]);
            Parking baseParking = parsedParking.parse_input_file();

            Parking[] result = {};
            try {
                result = main.find_shortest_path(baseParking);
                ParkingOUT.printing(result);

                for (int i = 0; i < result.length; ++i) {
                    System.out.println(result[i]);
                }


            } catch(UnsupportedOperationException e) {
                ParkingOUT.print_no_result (baseParking);
            }


        } else {
            System.out.println("Veuillez passer le fichier en argument.");
        }
    }
}
