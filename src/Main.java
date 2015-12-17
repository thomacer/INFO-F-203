import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.UnsupportedOperationException;

public class Main {
    /* @desc Place dans la liste.
     *
     * @param {newParking} : Nouveau parking à placer dans le graphe.
     *
     * @return {boolean} true si ajouté dans la liste sinon false (déjà dans la liste).
     */
    static private int _put_in_list (Parking newParking, ArrayList<Parking> parkingList) {
        int i = 0;
        // Recherche pour savoir si il y a déjà un {newParking} dans la liste.
        while ( (i < parkingList.size()) && !(parkingList.get(i).equals(newParking)) ) {
            ++i;
        }
        if (i == parkingList.size()) {
            // Si il n'est pas dans la liste.
            parkingList.add(newParking);
        } else {
            i = 0;
        }
        return i;
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
    static private int _update_lists(LinkedList<Integer> queue,
                                     ArrayList<Parking> parkingList,
                                     ArrayList<Integer> nodePath,
                                     ArrayList<Integer> countNode,
                                     Parking newParkingConf,
                                     int currentNode)
    {
        int result = 0;
        if (newParkingConf != null) {
            result = _put_in_list(newParkingConf, parkingList);
            if (result > 0) {
                queue.add( parkingList.size() - 1 );
                nodePath.add(currentNode);
                countNode.add( countNode.get(currentNode) + 1 );
            }
        } 
        return result;
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

        // Va servir dans le cas où il n'y a pas de solution
        // Stock à chaque fois la position la plus lointaine que la 
        // voiture goal a atteind.
        int bestParkingConf = 0;

        while ( !(parkingList.get(currentNode).is_won()) ) {
            Parking currentParking = parkingList.get(currentNode);
            for ( Car aCar : currentParking ) {
                Parking newParkingConf = currentParking.move_forward(aCar);
                int newPos = _update_lists(queue, parkingList, nodePath, countNode, newParkingConf, currentNode);
                if (newPos > 0 && aCar.is_goal()) {
                    bestParkingConf = newPos;
                }
                    
                newParkingConf = currentParking.move_backward(aCar);
                _update_lists(queue, parkingList, nodePath, countNode, newParkingConf, currentNode);
            }
            
            if ( queue.size() > 0 ) {
                currentNode = queue.poll();
            } else {
                // Si on est arrivé au dernier noeud et qu'on a toujours 
                // pas trouvé de chemin gagnant.
                throw new NoResultFoundError(_create_path(parkingList, countNode, nodePath, bestParkingConf));
            }
        }

        return _create_path(parkingList, countNode, nodePath, currentNode);
    }

    public static void main (String[] args) {
        if ( args.length > 0) {
            Parking baseParking = ParkingIN.parse_input_file(args[0]);
            ParkingOUT.print_init(baseParking);
            Parking[] result = {};
            try {
                result = Main.find_shortest_path(baseParking);
                ParkingOUT.print_win(result);
            } catch(NoResultFoundError e) {
                ParkingOUT.print_no_result( e.get_best() );
            }
        } else {
            System.out.println("Veuillez passer le fichier en argument.");
        }
    }
}
