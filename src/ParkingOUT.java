import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class ParkingOUT {
    static private BufferedWriter creating_file(){
        String filename = "Solution.txt";
		BufferedWriter bw=null;
		try{
			File file=new File(filename);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			bw=new BufferedWriter(fw);
		} catch(IOException exc){
            System.out.print("Cannot create file!");
		}
		return bw;
	}

	static public void printing(Parking[] resultPath){
		String content;
		String[] mouvement;
		BufferedWriter file=creating_file();
		try{
			content=title();
			file.write(content);

            mouvement=new String[resultPath[0].get_numCar()];
            for (int k = 0; k < resultPath[0].get_numCar(); ++k){
                mouvement[k]="";
            }
            for (int i = 0; i < resultPath.length; ++i){
                content="Mouvement "+(i+1)+":\n";
                content+=resultPath[i].toString();
                file.write(content);
                for (Car c : resultPath[i]){
                    content="Deplacement Voiture "+c.get_num()+": ";
                    mouvement[c.get_num()-1]+="-> [";
                    if (c.get_num()==1){
                        content="Deplacement Goal: ";
                    }
                    for (int[] pos:c){
                        mouvement[c.get_num()-1]+="("+pos[0]+","+pos[1]+")";
                    }
                    mouvement[c.get_num()-1]+="]";
                    content+=mouvement[c.get_num()-1];
                    content+="\n";
                    file.write(content);
                }
                file.write("\n");
            }

            file.close();
        } catch(IOException e){
            System.out.print("Cannot close file!");
        }
    }

    static public void print_no_result (Parking baseParking) {
        BufferedWriter file=creating_file();
        String content=title();

        try {
            content = baseParking.toString();
            content += "Il n'y a aucune possibilité pour sortir la voiture Goal!.\n";
            file.write(content);
            for (Car c : baseParking){
                content="Deplacement Voiture "+c.get_num()+": ";
                if (c.get_num()==1){
                    content = "Deplacement Goal: ";
                }
                for (int[] pos : c){
                    content+="("+pos[0]+","+pos[1]+")";
                }
                content+="\n";
                file.write(content);
            }
            file.write("\n");
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static private String title(){
        String res="Solution finale :\n";
        return res;
    }

    static private String nbr_chemin(boolean reussi){
		String res="Il n'y a pas moyen de sortir du parking.";
		if (reussi){
			//res="Une façon de sortir du parking en "+(this._chemin.lenght()-1)+"a été trouvée.";
		}
		return res;
	}
}
