import java.util.ArrayList;
import java.util.List;


public class Arbol {


	Nodo root;
	public Arbol(ArrayList<Registro> registros) {
		root = new Nodo(registros);
	}
	public void crearArbol(){
		crearArbol(root);
	}
	private Nodo crearArbol(Nodo node){
		if (node.setDeRegistros.size()==0) return null;
		
		boolean checkAttributes= false;
		for(int index=0; index<node.AtributoOcupado.length;index++){
			if(!node.AtributoOcupado[index]){
				checkAttributes = true;
				break;
			}
		}
		if(!checkAttributes || node.setDeRegistros.size()<Main.minimoEnNodo){
			int countClases[] = new int[Main.clases.length];
			int	max=0;
			int index=-1;
			for(int i=0; i<countClases.length;i++){	
				countClases[i]=0;
				for(Registro r: node.setDeRegistros){
					if(Main.clases[i]==r.clase)
						countClases[i]++;
				}
				if(max<=countClases[i]) {
					max= countClases[i];
					index=i;
				}
			}
			node.clase=Main.clases[index];
			return node;
		}

		node.entropia = Calcular.entropia(node.setDeRegistros);
		if (node.entropia==0) {
			node.clase=node.setDeRegistros.get(0).clase;
			return node;
		}
		double mejorGain = 0;
		int mejorAtributo = -1;
		for (int i =0; i< Main.threshold.length;i++)
		{
			if(node.AtributoOcupado[i]) continue;
			ArrayList<ArrayList<Registro>> subsets = subsets(node.setDeRegistros,i);
			double Gain = Calcular.Gain(node, subsets);
			if(Gain == 0){
				node.AtributoOcupado[i]=true;
				Gain = Calcular.Gain(node, subsets);
			}
			else if(Gain>mejorGain){
				mejorAtributo=i;
				mejorGain=Gain;
			}
		}
		
		if(mejorAtributo>=0){
			node.AtributoOcupado[mejorAtributo]=true;
			ArrayList<ArrayList<Registro>> subsets = subsets(node.setDeRegistros,mejorAtributo);
			node.ramas = new Nodo[2];;
			for(int i=0;i<subsets.size();i++){
				Nodo n = new Nodo(subsets.get(i));
				node.atributo=mejorAtributo;
				n.AtributoOcupado=node.AtributoOcupado.clone();
				//crearArbol(n);
				node.ramas[i]=crearArbol(n);
			}
			if(node.ramas[0].clase!=-1&&node.ramas[1].clase!=-1&&node.ramas[0].clase==node.ramas[1].clase)
			{
				node.clase = node.ramas[0].clase;
				node.atributo= -1;
				node.ramas=null;
			}
		}
		else{// en el caso raro de que todos los gains sean cero
			int countClases[] = new int[Main.clases.length];
			int	max=0;
			int index=-1;
			for(int i=0; i<countClases.length;i++){	
				countClases[i]=0;
				for(Registro r: node.setDeRegistros){
					if(Main.clases[i]==r.clase)
						countClases[i]++;
				}
				if(max<=countClases[i]) {
					max= countClases[i];
					index=i;
				}
			}
			node.clase=Main.clases[index];
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
	public boolean testRegistro(Registro registro){
		return testRegistro(registro,root);
	}
	private boolean testRegistro(Registro registro, Nodo nodo){

		int atr= nodo.atributo;
		if (atr!=-1)
			if(registro.reg[atr]<Main.threshold[atr])
				return testRegistro(registro, nodo.ramas[0]);
			else
				return testRegistro(registro, nodo.ramas[1]);
		else if(nodo.clase!=-1)
			if(registro.clase ==nodo.clase)
				return true;
			else
				return false;
		Nodo n =nodo.ramas[3];
		return false;
				
				
			
	}
	public double testTree(ArrayList<Registro> testSample){
		int countSuccess=0;
		for (Registro r : testSample){
			if(testRegistro(r)) countSuccess++;
		}
		return countSuccess/(double)testSample.size();
		
	}
	public void printTree(){
		printTree(root,0);
	}
	private void printTree(Nodo n,int level){
		String espacio="";
		for(int i=0;i<level;i++) espacio= espacio+" ";
		System.out.print(espacio);
		if(n.atributo!=-1){
			System.out.println(n.atributo);
			printTree(n.ramas[0],level+1);
			printTree(n.ramas[1],level+1);
			System.out.println(espacio+n.atributo);
		}
		else{
			System.out.println("Clase "+n.clase);
		}
	}
}
