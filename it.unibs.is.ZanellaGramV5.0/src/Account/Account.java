package Account;

import java.io.Serializable;
import java.util.Vector;

import Eventi.Evento;
import it.unibs.fp.mylib.FasciaDiEta;

public class Account implements Serializable{
	
	private Vector<Evento> eventi;
	private Vector<Notifica> notifiche;
	private Vector<Evento> proposte;

	private String nomignolo;
	private FasciaDiEta fascia;
	private Vector<String> categorieInteresse;
	
	public Account(String n, FasciaDiEta f) {
		eventi = new Vector<Evento>();
		notifiche = new Vector<Notifica>();
		proposte = new Vector<Evento>();
		categorieInteresse = new Vector<String>();
		this.nomignolo=n;
		this.fascia=f;
	}
	
	
	
	public Evento getEvento(int i) {
		return eventi.get(i);
	}
	
	public Evento getProposta(int i) {
		return proposte.get(i);
	}
	
	public Notifica getNotifica(int i) {
		return notifiche.get(i);
	}
	
	public Vector<Notifica> getNotifiche() {
		return notifiche;
	}
	
	public void addEvento(Evento e) {
		eventi.add(e);
	}
	
	public void addProposta(Evento e) {
		proposte.add(e);
	}
	
	public void addNotifica(Notifica n) {
		notifiche.add(n);
	}
	
	public int sizeEventi() {
		return eventi.size();
	}
	
	public int sizeProposte() {
		return proposte.size();
	}
	
	public int sizeNotifiche() {
		return notifiche.size();
	}
	
	public void removeEvento(Evento e) {
		eventi.remove(e);
	}
	
	public void removeProposta(Evento e) {
		proposte.remove(e);
	}
	
	public boolean isPropositore(Evento e) {
		if(proposte.contains(e)) return true;
		else return false;
	}
	
	public String getNomignolo() {
		return nomignolo;
	}

	public void setNomignolo(String nomignolo) {
		this.nomignolo = nomignolo;
	}

	public FasciaDiEta getFascia() {
		return fascia;
	}

	public void setFascia(FasciaDiEta fascia) {
		this.fascia = fascia;
	}

	public Vector<String> getCategorieInteresse() {
		return categorieInteresse;
	}

	public int categorieInteresseSize() {
		return categorieInteresse.size();
	}
	
	public void addCategorieInteresse(String s) {
		categorieInteresse.addElement(s);
	}
	
	public void removeCategorieInteresse(String s) {
		categorieInteresse.remove(s);
	}
	
	public Vector<Notifica> getNotificheNonLette(){
		Vector<Notifica> nonLette = new Vector<>();
		
		for(int i=0; i<sizeNotifiche();i++) {
			if (!notifiche.get(i).isLetta()) {
				nonLette.add(notifiche.get(i));
			}
		}
		
		return nonLette;
	}
	
	public void leggiNotificheNonLette() {
		
		for(int i=0; i<sizeNotifiche();i++) {
			if (!notifiche.get(i).isLetta()) {
				notifiche.get(i).leggi();
			}
		}
		
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Nomignolo: " + nomignolo + "\n");
		s.append("Fascia Di Età: ");
		if (fascia==null) s.append("non specificato");
		else s.append(fascia);
		s.append("\nCategorie di interesse:\n");
		for(String str: categorieInteresse) {
			s.append("\t- " + str );
		}
		return s.toString();
	}


}
