package Util;

import java.util.Vector;

import Account.Account;
import Account.Notifica;
import Eventi.Evento;
import Main.Dati;
import Main.Main;
import it.unibs.fp.mylib.Data;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.Ora;

public class UtilInviti {
	
	public static void compilazioneBase(Object[] valoriBase, Dati dati) {
		valoriBase[0] = InputDati.leggiStringaNull("\nTitolo --> ");
		valoriBase[1] = InputDati.leggiInteroNull("Partecipanti necessari* --> ");
		valoriBase[2]=InputDati.leggiDataNull("Data termine iscrizioni*:", dati.getDataOdierna());
		valoriBase[3] = InputDati.leggiStringaNull("Luogo* --> ");
		valoriBase[4]=InputDati.leggiDataNull("Data inizio evento*:", (Data)valoriBase[2]);
		System.out.println("Ora inizio evento*:");
		Integer giorno = InputDati.leggiInteroNull("Ora --> ", 0, 23);
		Integer mese = InputDati.leggiInteroNull("Minuti --> ", 0, 59);
		if (giorno != null && mese != null) valoriBase[5] = new Ora(giorno, mese);
		else valoriBase[5]=null;
		System.out.println("Durata: ");
		giorno = InputDati.leggiInteroConMinimoNull("Ora --> ", 0);
		mese = InputDati.leggiInteroNull("Minuti --> ", 0, 59);
		if (giorno != null && mese != null) valoriBase[6] = new Ora(giorno, mese);
		else valoriBase[6]=null;
		valoriBase[7] = InputDati.leggiDoubleNull("Quota di partecipazione (in Euro €)* --> ");
		valoriBase[8] = InputDati.leggiStringaNull("Compreso in quota --> ");
		valoriBase[9]=InputDati.leggiDataNull("Data conclusiva:", (Data)valoriBase[4]);
		System.out.println("Ora conclusiva:");
		giorno = InputDati.leggiInteroNull("Ora --> ", 0, 23);
		mese = InputDati.leggiInteroNull("Minuti --> ", 0, 59);
		if (giorno != null && mese != null) valoriBase[10] = new Ora(giorno, mese);
		else valoriBase[10]=null;
		valoriBase[11] = InputDati.leggiStringaNull("Note --> ");
		valoriBase[12] = InputDati.leggiInteroNull("Tolleranza Numero di Partecipanti --> ");
		valoriBase[13]=InputDati.leggiDataNull("Data termine disicrizioni:", dati.getDataOdierna());
		valoriBase[14] = dati.getMioAccount();
	}
	
	public static void inviti(Evento e,Dati dati) {
		if (!listaInvitabili(e,dati).isEmpty()) {
			if (InputDati.yesOrNo("Desideri invitare delle persone che in passato hanno partecipato"
					+ " a tuoi eventi di questo tipo?")) invita(e,dati);
		}
	}
	
	public static void notificaInteressati(Evento e,Dati dati) {
		Notifica n = new Notifica(dati.getDataOdierna(),"Un nuovo evento del tipo " + "\" " + e.getNome() + " \"" + " è stato proposto, per partecipare visualizza la tua bacheca." );
		for(Account a:dati.getContainer().getAccounts()) {
			if(a.getCategorieInteresse().contains(e.getNome()) && !a.isPropositore(e)) {
				a.addNotifica(n);
			}
		}
	}
	
	public static Vector<Account> listaInvitabili(Evento e,Dati dati) {
		Vector<Account> invitabili = new Vector<>();
		for(Account a:dati.getContainer().getAccounts()) {
			for(int i=0; i<a.sizeEventi(); i++) {
				if(a.getEvento(i).getNome().equals(e.getNome()) && e.getPropositore().isPropositore(a.getEvento(i)) &&
						!a.equals(e.getPropositore())) {
					invitabili.add(a);
				}
			}
		}
		return invitabili;
	}
	
	public static void invita(Evento e,Dati dati) {
		Vector<Account> invitabili = listaInvitabili(e,dati);
		
		for (int i=0; i< invitabili.size(); i++) {
			System.out.println((i+1) + "\t" + invitabili.get(i).getNomignolo());
		}
		
		System.out.println();
		
		int i;
		Vector<Account> invitati = new Vector<>();
		do {
			i = InputDati.leggiIntero("Inserire il numero di chi vuoi invitare (0 per uscire) --> ", 0, invitabili.size())-1;
			if (!invitati.contains(invitabili.get(i))) {
				invitati.add(invitabili.get(i));
			}
		} while(i>=0);
		
		Notifica invito = new Notifica(dati.getDataOdierna(), "Sei stato invitato da " + e.getPropositore().getNomignolo() + " al suo evento " + e.getTitolo());
		for(Account a:invitati) {
			a.addNotifica(invito);
		}
		System.out.println("Inviti spediti con successo");
		
	}

}
