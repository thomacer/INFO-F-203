import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class ParkingOUT {
    static private final String title = "Solution finale :\n";
    static private final String filename = "Solution.txt";

    static private BufferedWriter creating_file(){
        BufferedWriter bw = null;
        try{
            File file = new File(filename);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
        } catch(IOException exc){
            System.out.print("Cannot create file!");
        }
        return bw;
    }

    /* @desc Imprime dans la console les déplacements fait par les voitures pour
     *      arriver à la solution.
     *
     * @param {resultPath} : Liste des parkings qui sont utiles pour la réponse.
     */
    static public void printToConsole(Parking[] resultPath){
        for (int carNum = 0; carNum < resultPath[0].get_number_of_car(); ++carNum) {
            // Pour printer comme dans l'énoncé il faut faire une voiture à la fois
            // et rechercher dans les solutions quand est-ce que celle-ci est bougé.

            // Cas de base.
            System.out.println("\nDéplacement éffectués par la voiture " + carNum + ":");
            System.out.println("1. " + resultPath[0].get(carNum));
            int count = 2;
            for (int j = 1; j < resultPath.length; ++j) {
                if (resultPath[j].get_moved_car().get_num() == carNum) {
                    System.out.println(count + ". " + resultPath[j].get_moved_car());
                    ++count;
                }
                
            }
        }
    }

    static public void printing(Parking[] resultPath){
        ParkingOUT.printToConsole(resultPath);
        String content;
        String[] mouvement;

        content = title;
        content += resultPath[resultPath.length - 1].toString();
        content += "Une façon de sortir du Parking en " + (resultPath.length - 1)
            + " mouvement a été trouvée.\n\n";
        // (resultPath.length - 1) est utilisé car le cas de base n'est pas considéré
        // comme un mouvement.

        int precNum = -1;
        for (int i = 1; i < resultPath.length; ++i){
            Car movedCar = resultPath[i].get_moved_car();
            int newCarNum = movedCar.get_num();
            if (newCarNum != precNum) {
                content += "\nDéplacements car " + newCarNum + ":\n";
                content +=  movedCar.posPreviousToString() + movedCar.posToString();
            } else {
                content += " -> " + movedCar.posToString();
            }
            precNum = newCarNum;
        }

        BufferedWriter file=creating_file();
        try{
            file.write(content);
            file.close();
        } catch(IOException e){
            System.out.print("Cannot close file!");
        }
    }

    static public void print_no_result (Parking baseParking) {
        BufferedWriter file=creating_file();
        String content=title;

        try {
            content = baseParking.toString();
            content += "Il n'y a aucune possibilité pour sortir la voiture Goal!.\n";

            for (Car c : baseParking){
                if (c.is_goal()){
                    content += "Deplacement Goal: ";
                } else {
                    content += "Deplacement Voiture " + c.get_num() + ": ";
                }

                for (int[] pos : c){
                    content += "("+pos[0]+","+pos[1]+")";
                }
                content+="\n";
            }
            content += "\n";

            file.write(content);

            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
