import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.sun.media.jai.iterator.RandomIterCSMDouble;


public class Main {
	public static int[] clases;
	public static double[] threshold;
	public static int minimoEnNodo;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args)  {
		minimoEnNodo= 0;
		ArrayList<Registro>registros = null;
		try{
			registros= leerArchivo(args[0]);
		}
		catch(Exception e){
			System.out.println("No se encontro el archivo");
			return;
		}
		if (registros==null) return;
		Main.setThreshold();
		Main.SetAndCountClasses(registros);
		Collections.shuffle(registros);
		
		Arbol tree = new Arbol(registros);
		
		tree.crearArbol();
		tree.printTree();
		//System.out.println(crossValidation(registros,2));
//		double[] arr = {1.0,1.0,0.0};
//		System.out.println(tree.testRegistro(new Registro(arr,2)));
		
	}
	public static double crossValidation(ArrayList<Registro> registros, int k){
		ArrayList<ArrayList<Registro>>  subsamples= new ArrayList<ArrayList<Registro>>();
		int countPerSample= registros.size()/k;
		int count=0;
		int index=0;
		for (int i = 0;i<k;i++){
			ArrayList<Registro> subsample = new ArrayList<Registro>();
			for(int j = index;j<registros.size()&&count<countPerSample;j++){
				subsample.add(registros.get(j));
				count++;
				index++;
			}
			count=0;
			subsamples.add(subsample);
		}
		double crossValidationResult = 0;
		for(ArrayList<Registro> subsample: subsamples){
			double suma=0;
			Arbol tree = new Arbol(subsample);
			tree.crearArbol();
			for(ArrayList<Registro> testSample: subsamples){
				if(subsample==testSample) continue;
				suma += tree.testTree(testSample);
			}
			crossValidationResult += suma/(double)(k-1);
		}
		return crossValidationResult/k;
		
	}
	private static void SetAndCountClasses(ArrayList<Registro> registros) {
		ArrayList<Integer> clases = new ArrayList<Integer>();
		for(Registro r : registros){
			if (clases.size()==0 ) clases.add(r.clase);
			else
			{
				if(!clases.contains(r.clase)) clases.add(r.clase);
				
			}
		}
		
		Main.clases = new int[clases.size()];
		for(int i =0; i < clases.size();i++){
			Main.clases[i]=clases.get(i);
		}
	}
	public static ArrayList<Registro> leerArchivo(String filename) throws FileNotFoundException{
		// TODO Auto-generated method stub
		File f = new File(filename);
		Scanner scan = new Scanner(f);
		if(!scan.hasNextLine()) return null;
		String primero = scan.nextLine();
		Scanner scanCount = new Scanner(primero);
		int count=0;
		List<Double> primerRegister= new ArrayList<Double>();
		while(scanCount.hasNextDouble()){
			primerRegister.add(scanCount.nextDouble());
			count++;
		}
		scanCount.close();
		
		Main.threshold = new double[count-1];
		double[] registro= new double[count-1];
		for(int i=0; i< count-1 ;i++){
			registro[i] = primerRegister.get(i);
		}
		ArrayList<Registro> registros= new ArrayList<Registro>();
		registros.add(new Registro(registro,(int)(double)primerRegister.get(count-1))); 
		while(scan.hasNextLine()){
			String linea = scan.nextLine();
			
			scanCount= new Scanner(linea);
			int i=0;
			registro= new double[count-1];
			while(scanCount.hasNextDouble()&&i<count-1){
				registro[i]= scanCount.nextDouble();
				i++;
			}
			double clase= scanCount.nextDouble();
			scanCount.close();
			registros.add(new Registro(registro, (int)clase));
		}
		scan.close();
		return registros;
	}
	
	public static void setThresholdMedio(List<Registro> registros){
		double[] min = registros.get(1).reg.clone();
		double[] max = registros.get(1).reg.clone();
		for(Registro r : registros){
			for (int i = 0; i<r.reg.length;i++){
				if(r.reg[i]<min[i]) min[i] = r.reg[i];
				if(r.reg[i]>max[i]) max[i] = r.reg[i];
			}
		}
		for (int i = 0; i< min.length;i++)
		{
			Main.threshold[i]= (min[i]+max[i])/2d;
		}
		
	}
	public static void setThreshold(){
		Random rnd = new Random();
		for (int i = 0; i< threshold.length;i++)
		{
			Main.threshold[i] = rnd.nextDouble();
		}
		
	}
}
