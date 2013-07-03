import java.util.ArrayList;
import java.util.List;


public class Arbol {

	public boolean[] AtributoOcupado;
	Nodo root;
	int clases[];
	public Arbol(ArrayList<Registro> registros) {
		root = new Nodo(registros);
		AtributoOcupado = new boolean[Main.threshold.length];
	}
	public Nodo crearArbol(Nodo node){
		
		node.entropia = Calcular.entropia(root.setDeRegistros);
		if (root.entropia==0) return null;
		double mejorGain = 0;
		int mejorAtributo = -1;
		for (int i =0; i< Main.clases.length;i++)
		{
			if(AtributoOcupado[i]) continue;
			ArrayList<ArrayList<Registro>> subsets = subsets(node.setDeRegistros,i);
			double Gain = Calcular.Gain(node, subsets);
			if(Gain>mejorGain){
				mejorAtributo=i;
				mejorGain=Gain;
			}
		}
		if(mejorAtributo>=0){
			this.AtributoOcupado[mejorAtributo]=true;
			ArrayList<ArrayList<Registro>> subsets = subsets(node.setDeRegistros,mejorAtributo);
			node.ramas = new Nodo[subsets.size()];
			for(int i=0;i<subsets.size();i++){
				Nodo n = new Nodo(subsets.get(i));
				node.ramas[i]=n;
				crearArbol(n);
			}
			
			
		}
		return node;
	}
	public ArrayList<ArrayList<Registro>> subsets(ArrayList<Registro>set,int atributo){
		ArrayList<ArrayList<Registro>> subsets= new ArrayList<ArrayList<Registro>>();
		
			ArrayList<Registro>subset = new ArrayList<Registro>();
			ArrayList<Registro>subset2 = new ArrayList<Registro>();
			for(Registro r : set){
				if(r.reg[atributo]<Main.threshold[atributo]) subset.add(r);
				else subset2.add(r);
			}
			subsets.add(subset);
			subsets.add(subset2);
		
		return subsets;
	}
}
