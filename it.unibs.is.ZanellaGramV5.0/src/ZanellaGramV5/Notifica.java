package ZanellaGramV5;

import java.io.Serializable;

import it.unibs.fp.mylib.Data;

public class Notifica implements Serializable{
	
	private Data data;
	private String messaggio;
	private boolean Letta;
	
	public Notifica(Data _data, String _messaggio) {
		data=_data;
		messaggio=_messaggio;
		Letta = false;
	}
	
	public void leggi() {
		Letta = true;
	}

	public Data getData() {
		return data;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public boolean isLetta() {
		return Letta;
	}
	
	public String toString() {
		String s = data.toString() + ": " + messaggio;
		return s;
	}

}
