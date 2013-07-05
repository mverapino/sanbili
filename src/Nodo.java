import java.util.ArrayList;
import java.util.List;


public class Nodo {
	public ArrayList<Registro> setDeRegistros;
	public Nodo[] ramas;
	public boolean[] AtributoOcupado;
	double clase=-1;
	int atributo=-1;
	double entropia;
	public Nodo(ArrayList<Registro> reg) {
		this.setDeRegistros = reg;
		AtributoOcupado = new boolean[Main.threshold.length];
	}

	/**
	 * @param args
	 */
	public void entrenar(ArrayList<Registro> ramas)
	{
		if (ramas.size()==0) return;
		if (ramas.size()==1) this.clase=ramas.get(0).clase;
	}
}
