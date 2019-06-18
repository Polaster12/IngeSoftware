package Eventi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import Account.Account;
import Account.Notifica;
import DBMS.Dati;

import java.io.*;
import it.unibs.fp.mylib.Data;
import it.unibs.fp.mylib.Ora;

public class Evento implements Serializable {

	private static final boolean[] obbligatorietà = {false,true,true,true,true,true,false,true,false,false,false,false,false,false};
	private static final String[] descrizioni = {
			"Nome di fantasia da dare all'evento",
			"Numero di persone da coinvolgere nell’evento",
			"Data di chiusura delle iscrizioni",
			"Indirizzo di svolgimento dell'evento o luogo di ritrovo",
			"Data di inizio",
			"Orario di ritrovo",
			"Durata approssimativa dell'evento",
			"Spese individuali da sostenere",
			"Servizi compresi nella quota",
			"Data di conclusione dell'evento",
			"Ora di conclusione dell'evento",
			"Altri dettagli",
			"Numero di iscritti in più tollerabili",
			"Ultima data utile per la disiscrizione"
	};	
	
	public static final String CORNICE = "--------------------------------------------------------------------------------";
	
	private String titolo;
	private int numeroPartecipanti;
	private Data termineIscrizioni;
	private String luogo;
	private Data data;
	private Ora ora;	
	private Ora durata;
	private double quotaIndividuale;
	private String compresoNellaQuota;
	private Data dataConclusiva;
	private Ora oraConclusiva;
	private String note;
	private int tolleranza;
	private Data termineDisiscrizione;
	
	private Vector<Account> partecipanti;
	private Vector<Double> ammontari;
	
	private Stati stato;
	
	private Account propositore;
	
	private HashMap<String, Double> speseAggiuntive;
	
	public static final String nome = null;
	public static final String descrizione = null;
	
	public Evento(String titolo, int numeroPartecipanti, Data termineIscrizioni, String luogo, Data data, Ora ora, Ora durata, double quotaIndividuale, String compresoNellaQuota, Data dataConclusiva, Ora oraConclusiva, String note, Integer tolleranza, Data termineDisiscrizione, Account propositore) {
		this.titolo = titolo;
		this.numeroPartecipanti = numeroPartecipanti;
		this.termineIscrizioni = termineIscrizioni;
		this.luogo = luogo;
		this.data = data;
		this.ora = ora;	
		this.durata = durata;
		this.quotaIndividuale = quotaIndividuale;
		this.compresoNellaQuota = compresoNellaQuota;
		if (dataConclusiva != null) this.dataConclusiva = dataConclusiva;
		else this.dataConclusiva=data;
		this.oraConclusiva = oraConclusiva;
		this.note = note;
		if (tolleranza != null) this.tolleranza = tolleranza;
		else this.tolleranza=0;
		if (termineDisiscrizione != null && termineDisiscrizione.compareTo(termineIscrizioni)>=0) this.termineDisiscrizione = termineDisiscrizione;
		else this.termineDisiscrizione=termineIscrizioni;
		partecipanti = new Vector<Account>();
		ammontari = new Vector<>();
		stato = Stati.aperto;
		this.propositore=propositore;
		this.speseAggiuntive= new HashMap<>();
	}
	
	public static boolean[] getObbligatorietà() {
		return obbligatorietà;
	}
	
	public static String[] getDescrizioni() {
		return descrizioni;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}
	
	public Data getTermineIscrizioni() {
		return termineIscrizioni;
	}
	
	public String getLuogo() {
		return luogo;
	}
	
	public Data getData() {
		return data;
	}
	
	public Ora getOra() {
		return ora;
	}
	
	public Ora getDurata() {
		return durata;
	}
	
	public double getQuotaIndividuale() {
		return quotaIndividuale;
	}
	
	public String getCompresoNellaQuota() {
		return compresoNellaQuota;
	}
	
	public Data getDataConclusiva() {
		return dataConclusiva;
	}
	
	public Ora getOraConclusiva() {
		return oraConclusiva;
	}
	
	public String getNote() {
		return note;
	}
	
	public int getTolleranza() {
		return tolleranza;
	}
	
	public Data getTermineDisiscrizioni() {
		return termineDisiscrizione;
	}
	
	public Stati getStato() {
		return stato;
	}
	
	public Account getPropositore() {
		return propositore;
	}
	
	public boolean isAperto() {
		if (stato == Stati.aperto)
			return true;
		return false;
	}
	
	public int numeroIscritti() {
		return partecipanti.size();
	}
	
	public boolean controlloIscrizione(Account a) {
		
		for(int i=0;i<partecipanti.size();i++) {
			if (partecipanti.get(i).equals(a)) return true;
		}
		return false;
	}
	
	public void addPartecipante(Account a) {
		if (partecipanti.size()<numeroPartecipanti+tolleranza)
			partecipanti.add(a);
		else {
			System.out.println("Attenzione!!!!! Numero massimo partecipanti raggiunto \nIscrizione non effettuata");
		}
	}
	
	public void removePartecipante(Account o) {
		partecipanti.remove(o);
		if(numeroPartecipanti + tolleranza>partecipanti.size()) stato = Stati.aperto;
	}
	
	public void sendNotifiche(Notifica n) {
		for(Account a:partecipanti) {
			a.addNotifica(n);
		}
	}
	
	public void sendConferma() {
		StringBuffer str = new StringBuffer();
		str.append("L'evento " + this.titolo + " si terrà il giorno " + this.data + " alle ore " + this.ora + 
				" in/a " + this.luogo + ".\nSi ricorda che la quota di partecipazione è di ");
		for(int i=0;i<partecipanti.size();i++) {
			String s = str.toString() + ammontari.get(i) + "0 €";
			Notifica n = new Notifica(termineIscrizioni.giornoSuccessivo(),s);
			partecipanti.get(i).addNotifica(n);
		}
		
	}
	
	public static String toStringEvento() {
		StringBuffer s = new StringBuffer();
		Field[] fields = Evento.class.getDeclaredFields();
		s.append("* --> campo obbligatorio \n\n");
		for(int i=2;i<fields.length;i++) {
			s.append(fields[i].getName());
			
				if(Evento.getObbligatorietà()[i-2]) {
					s.append("*");
				}
				s.append(": " + Evento.getDescrizioni()[i-2]);
				s.append("\n");
		}
		return s.toString();
	}

	public String toString() {
		return "\tTitolo: " + titolo + "\n\tNumero di Partecipanti: " + numeroPartecipanti + "\n\tTermine delle Iscrizioni: "
				+ termineIscrizioni + "\n\tLuogo: " + luogo + "\n\tData: " + data + "\n\tOra: " + ora + "\n\tDurata: " + durata
				+ "\n\tQuota Individuale: " + quotaIndividuale + "0 €\n\tCompreso Nella Quota: " + compresoNellaQuota
				+ "\n\tData Conclusiva: " + dataConclusiva + "\n\tOra Conclusiva: " + oraConclusiva + "\n\tNote: " + note;
	}
	
	public void cambioStato(Data data) {
		Stati statoFuturo = null;
		switch(this.stato) {
			case aperto:if(data.compareTo(termineIscrizioni)<0) {
							if (this.numeroIscritti()<this.numeroPartecipanti) {
								statoFuturo = Stati.fallito;
								String messaggio = "L'evento "; 
								if (this.titolo!=null)
									messaggio = messaggio + this.titolo;
								else messaggio = messaggio + this.getNome();
								Notifica n = new Notifica(termineIscrizioni.giornoSuccessivo(),messaggio + ", previsto per il giorno " + this.data + ", è fallito a causa dell'insufficienza dei partecipanti");
								sendNotifiche(n);
							}
							else {
								statoFuturo = Stati.notificato;
								String messaggio = "L'evento "; 
								if (this.titolo!=null)
									messaggio = messaggio + this.titolo;
								else messaggio = messaggio + this.getNome();
								for (int i=0; i<this.numeroIscritti(); i++) {
									Notifica n = new Notifica(termineIscrizioni.giornoSuccessivo(), messaggio + 
											" si terrà il giorno " + this.data + " alle ore " + this.ora + " in/a " + 
											this.luogo + ".\nSi ricorda che la tua spesa complessiva è " + 
											this.getAmmontare(i) + "0 €");
									partecipanti.get(i).addNotifica(n);
								}
							}
						} 
						else {
							if (data.compareTo(termineDisiscrizione)<0 && this.numeroIscritti()==this.numeroPartecipanti+this.tolleranza) {
								statoFuturo = Stati.chiuso;
							}
							else statoFuturo = Stati.aperto;
						}
				break;
			case chiuso:if(data.compareTo(termineIscrizioni)<0) {
							statoFuturo = Stati.notificato;
							String messaggio = "L'evento "; 
							if (this.titolo!=null)
								messaggio = messaggio + this.titolo;
							else messaggio = messaggio + this.getNome();
							for (int i=0; i<this.numeroIscritti(); i++) {
								Notifica n = new Notifica(termineIscrizioni.giornoSuccessivo(), messaggio + 
										" si terrà il giorno " + this.data + " alle ore " + this.ora + " in/a " + 
										this.luogo + ".\nSi ricorda che la tua spesa complessiva è " + 
										this.getAmmontare(i) + "0 €");
								partecipanti.get(i).addNotifica(n);
							}
						} 
						else statoFuturo=Stati.chiuso;
						
						if(data.compareTo(dataConclusiva)<0) {
							statoFuturo = Stati.concluso;
							String messaggio = "L'evento "; 
							if (this.titolo!=null)
								messaggio = messaggio + this.titolo;
							else messaggio = messaggio + this.getNome();
							Notifica n = new Notifica(dataConclusiva.giornoSuccessivo(), messaggio + ", previsto per il giorno " + this.data + " si è concluso con successo");
							sendNotifiche(n);
						}
				break;
			case notificato: if(data.compareTo(dataConclusiva)<0) {
								statoFuturo = Stati.concluso;
								String messaggio = "L'evento "; 
								if (this.titolo!=null)
									messaggio = messaggio + this.titolo;
								else messaggio = messaggio + this.getNome();
								Notifica n = new Notifica(dataConclusiva.giornoSuccessivo(), messaggio + ", previsto per il giorno " + this.data + " si è concluso con successo");
								sendNotifiche(n);
							} else statoFuturo=Stati.notificato;
					break;
			case concluso: statoFuturo=Stati.concluso;
					break;
			case fallito: statoFuturo=Stati.fallito;
					break;
			case ritirato: statoFuturo=Stati.ritirato;
					break;
			case nonpubblico: statoFuturo=Stati.nonpubblico;
					break;
		}
		this.stato= statoFuturo;	
	}
	
	public void eventoRitirato(Data d) {
		for(int i=0; i<partecipanti.size(); i++) {
			partecipanti.get(i).removeEvento(this);
		}
		sendNotifiche(new Notifica(d, "L'evento " + this.titolo + " previsto per il giorno " + this.data + " alle ore " + this.ora + " in/a " + this.luogo + " è stato ritirato dal propositore."));
		stato = Stati.ritirato;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void addSpesaAggiuntiva(String s, Double d) {
		speseAggiuntive.put(s, d);
	}
	
	public boolean isEmptySpeseAggiuntive() {
		return speseAggiuntive.isEmpty();
	}
	
	public HashMap<String, Double> getSpeseAggiuntive() {
		return speseAggiuntive;
	}
	
	public Double getAmmontare(int i) {
		return ammontari.get(i);
	}
	
	public void addAmmontare(Double v) {
		ammontari.add(v);
	}
	
	public void sumAmmontari(Account a, Double v) {
		int i=partecipanti.indexOf(a);
		ammontari.set(i, ammontari.get(i) + v);
	}
	
	public void removeAmmontari(int i) {
		ammontari.remove(i);
	}

	public int PartecipantiIndexOf(Account a) {
		return partecipanti.indexOf(a);
	}

	public void setStato(Stati s) {
		stato = s;
	}

	public boolean isPubblicato() {
		if(stato == Stati.nonpubblico) return false;
		return true;
	}

	public boolean isPubblicabile(Dati dati) {
		return (isPubblicato() || termineIscrizioni.compareTo(dati.getDataOdierna())>0);
	}

	public boolean isRitirabile(Dati dati) {
		return dati.getDataOdierna().compareTo(termineDisiscrizione)<0;
	}

}
