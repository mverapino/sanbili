import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

	public Main() {
		
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File f = new File(args[0]);
		Scanner scan = new Scanner(f);
		if(!scan.hasNextLine()) return;
		String primero = scan.nextLine();
		Scanner scanCount = new Scanner(primero);
		int count=0;
		List<Double> primerRegister= new ArrayList<Double>();
		while(scanCount.hasNextDouble()){
			primerRegister.add(scanCount.nextDouble());
			count++;
		}
		scanCount.close();
		double[] registro= new double[count-1];
		for(int i=0; i< count-1 ;i++){
			registro[i] = primerRegister.get(i);
		}
		List<Registro> registros= new ArrayList<Registro>();
		registros.add(new Registro(registro,primerRegister.get(count-1))); 
		while(scan.hasNextLine()){
			String linea = scan.nextLine();
			
			scanCount= new Scanner(linea);
			int i=0;
			while(scanCount.hasNextDouble()&&i<count-1){
				registro[i]= scanCount.nextDouble();
				i++;
			}
			double clase= scanCount.nextDouble();
			scanCount.close();
			registros.add(new Registro(registro, clase));
		}
		scan.close();
		
		
		
	}

}
