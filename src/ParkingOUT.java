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
		BufferedWriter file=this.creating_file();
		try{
			content=this.title();
			file.write(content);
			if (this._chemin[this._chemin.length-1]!=null){
				for (int i=0;i<this._chemin.length;++i){
					content="Mouvement "+(i+1)+":\n";
					content+=this._chemin[i].toString();
					file.write(content);
				}
			}
			else{
				System.out.print("bon");
				content=this._chemin[0].toString();
				file.write(content);
				content="Il n'y a aucune possibilité pour sortir la voiture Goal!.";
			}

			//content=;		
			//System.out.print(content);
			
			
			
			
			file.write(content);
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
