import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class ParkingOUT {
	private String _filename;
	private Parking[] _chemin;
	
	ParkingOUT(Parking[] chemin){
		this._filename=("Solution.txt");
		this._chemin=chemin.clone();
	}
	
	private BufferedWriter creating_file(){
		BufferedWriter bw=null;
		try{
			File file=new File(this._filename);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			bw=new BufferedWriter(fw);
		}
		catch(IOException exc){
		System.out.print("Cannot create file!");
		}
		return bw;
	}
	
	public void printing(){
		String content;
		String[] mouvement;
		BufferedWriter file=this.creating_file();
		try{
			content=this.title();
			file.write(content);
			if (this._chemin[this._chemin.length-1]!=null){
				mouvement=new String[this._chemin[0].get_numCar()];
				for (int k=0;k<this._chemin[0].get_numCar();++k){
					mouvement[k]="";
				}
				for (int i=0;i<this._chemin.length;++i){ //this._chemin.length
					content="Mouvement "+(i+1)+":\n";
					// System.out.print(this._chemin[i].get(1).get_x_pos());
					//System.out.print(this._chemin[i].get(1).get_y_pos());
					content+=this._chemin[i].toString();
					file.write(content);
					for (Car c:this._chemin[i]){
						content="Deplacement Voiture "+c.get_num()+": ";
						mouvement[c.get_num()-1]+="-> [";
						if (c.get_num()==1){
							content="Deplacement Goal: ";
						}
						for (int[] pos:c){
							mouvement[c.get_num()-1]+="("+pos[0]+","+pos[1]+")";
							//content+="("+pos[0]+","+pos[1]+")";
						}
						mouvement[c.get_num()-1]+="]";
						content+=mouvement[c.get_num()-1];
						content+="\n";
						file.write(content);
					}
					file.write("\n");
				}
			}
			else{
				content=this._chemin[0].toString();
				content+="Il n'y a aucune possibilité pour sortir la voiture Goal!.\n";
				file.write(content);
				for (Car c:this._chemin[0]){
					content="Deplacement Voiture "+c.get_num()+": ";
					if (c.get_num()==1){
						content="Deplacement Goal: ";
					}
					for (int[] pos:c){
						content+="("+pos[0]+","+pos[1]+")";
					}
					content+="\n";
					file.write(content);
				}
				file.write("\n");;
			}
			
			file.close();
		}
		catch(IOException e){
			System.out.print("Cannot close file!");
		}
	}
	private String title(){
		String res="Solution finale :\n";
		return res;
	}
	private String nbr_chemin(boolean reussi){
		String res="Il n'y a pas moyen de sortir du parking.";
		if (reussi){
			//res="Une façon de sortir du parking en "+(this._chemin.lenght()-1)+"a été trouvée.";
		}
		return res;
	}
}
