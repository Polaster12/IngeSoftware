package Controller;

import java.util.Vector;

import Account.Account;
import Account.Notifica;
import DBMS.Dati;
import Eventi.Evento;
import View.UtilView;

public class UtilInviti {

	public static void inviti(Evento e,Dati dati) {
		if (!dati.listaInvitabili(e).isEmpty()) {
			if (UtilView.ask("Desideri invitare delle persone che in passato hanno partecipato"
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
	
	public static void invita(Evento e,Dati dati) {
		Vector<Account> invitabili = dati.listaInvitabili(e);
		
		for (int i=0; i< invitabili.size(); i++) {
			UtilView.visualizzaMessaggio((i+1) + "\t" + invitabili.get(i).getNomignolo());
		}
		
		UtilView.visualizzaMessaggio("");
		
		int i;
		Vector<Account> invitati = new Vector<>();
		do {
			i = UtilView.chiediIntero("Inserire il numero di chi vuoi invitare (0 per uscire) --> ", 0, invitabili.size())-1;
			if (!invitati.contains(invitabili.get(i))) {
				invitati.add(invitabili.get(i));
			}
		} while(i>=0);
		
		Notifica invito = new Notifica(dati.getDataOdierna(), "Sei stato invitato da " + e.getPropositore().getNomignolo() + " al suo evento " + e.getTitolo());
		for(Account a:invitati) {
			a.addNotifica(invito);
		}
		UtilView.visualizzaMessaggio("Inviti spediti con successo");
		
	}

}
