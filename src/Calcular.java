import java.util.ArrayList;
import java.util.List;


public class Calcular {
	public static double entropia(List<Registro> set) {
		if (set.size()==0) return 0;
		double entropia=0;	
		for (int i = 0; i < Main.clases.length; i++){
			int cont=0;
			for(int j=0; j < set.size();j++){
				Registro reg = set.get(j);
				if(reg.clase==i) cont++;
			}

			double p = cont/(double)set.size();
			if(cont > 0) {
				entropia = entropia - p * (Math.log(p) / Math.log(2));
			}
		}
		return entropia;
	}
	public static double Gain (Nodo raiz, ArrayList<ArrayList<Registro>> subsets){
		double sum=0;
		for(ArrayList<Registro> subset: subsets){
			double entropia = entropia(subset);
			sum = sum+ (subset.size()/(double)raiz.setDeRegistros.size()*entropia(subset));
		}
		return raiz.entropia-sum;
	}
}
