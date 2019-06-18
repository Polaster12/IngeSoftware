package Controller;

import java.util.Vector;

import Account.Notifica;
import DBMS.Dati;
import Eventi.Evento;
import Eventi.Stati;
import View.UtilView;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuProfilo {
	
	private static final String titolo4 = "Profilo";
	private static final String[] voci4 = {"Nuove Notifiche","Tutte le Notifiche","Eventi a cui sono iscritto", "Eventi proposti da me", "Dati Personali"};
	private static MyMenu menuProfilo = new MyMenu(titolo4,voci4,true);
	
	private static final String titolo7 = "Che azione vuoi compiere su questo evento?";
	private static final String[] voci7 = {"Ritira","Pubblica"};
	private static MyMenu menuAzioneEvento = new MyMenu(titolo7,voci7,true);
	
	public static void menuProfilo(Dati dati) {
		int i;
		do{
			i = menuProfilo.scegli();
			switch(i) {
				case 1:
					vediNuoveNotifiche(dati);
					break;
				case 2:
					vediTutteNotifiche(dati);
					break;
				case 3:visualizzaEventiIscritto(dati);
					break;
				case 4:visualizzaEventiProposti(dati);
					break;
				case 5:modificaProfilo(dati);
					break;
			}
		}while(i!=0);
		
	}
	
	private static void visualizzaEventiIscritto(Dati dati) {
		
		String[] voci = new String[dati.getMioAccount().sizeEventi()];
		
		for(int j=0;j<dati.getMioAccount().sizeEventi();j++) {
			voci[j] =dati.getMioAccount().getEvento(j).toString();
		}
		
		MyMenu menu = new MyMenu("Sei iscritto ai seguenti eventi, digita il numero per disiscriverti",voci,true);
		int i = menu.scegli()-1;
		if (i>=0 && dati.getMioAccount().getEvento(i).getTermineDisiscrizioni().compareTo(dati.getDataOdierna())<=0 ){
			dati.getMioAccount().getEvento(i).removeAmmontari(dati.getMioAccount().getEvento(i).PartecipantiIndexOf(dati.getMioAccount()));
			dati.getMioAccount().getEvento(i).removePartecipante(dati.getMioAccount());
			dati.getMioAccount().removeEvento(dati.getMioAccount().getEvento(i));
			UtilView.visualizzaMessaggio("Disiscrizione eseguita con successo");
		}
		else {
			if(i>=0) UtilView.visualizzaMessaggio("Non puoi disiscriverti perchè passato il termine");
		}
	}
	
	private static void visualizzaEventiProposti(Dati dati) {
		
		String[] voci = new String[dati.getMioAccount().sizeEventi()];

		for(int j=0;j<dati.getMioAccount().sizeProposte();j++) {
			voci[j] = dati.getMioAccount().getProposta(j).toString();
		}
		
		MyMenu menu = new MyMenu("Hai proposto i seguenti eventi, digita il numero per ritirare una proposta",voci,true);
		int i = menu.scegli()-1;
		
		if (i>=0){
			menuAzioneEvento(dati.getMioAccount().getProposta(i),dati);
		}
		
		
		
	}
	
	private static void menuAzioneEvento(Evento e,Dati dati) {
		int i;
		do{
			i = menuAzioneEvento.scegli();
			switch(i) {
				case 1: ritiraEvento(e,dati);
					break;
				case 2: pubblicaEvento(e,dati);
					break;
			}
		}while(i!=0);
	}

	private static void pubblicaEvento(Evento e,Dati dati) {
		if (e.isPubblicabile(dati)){
			UtilView.visualizzaMessaggio("L'evento non può essere pubblicato perchè già pubblicato o è scaduto il termine iscrizioni");
		}
		else {
			e.setStato(Stati.aperto);
			UtilView.visualizzaMessaggio("La proposta è stata pubblicata correttamente!");
		}
	}

	private static void ritiraEvento(Evento e,Dati dati) {
		if (e.isRitirabile(dati)) {
			UtilView.visualizzaMessaggio("Non è possibile ritirare questo evento perchè è passata la data di termine disiscrizioni");
		}
		else {
			String messaggio = "L'evento "; 
			if (e.getTitolo() != null)
				messaggio = messaggio + e.getTitolo();
			else messaggio = messaggio + e.getNome();
			Notifica n = new Notifica(dati.getDataOdierna(),messaggio + ", previsto per il giorno " + e.getData() + ", è stato ritirato dall'organizzatore");
			e.sendNotifiche(n);
			dati.getContainer().getPartite().remove(e);
			e.eventoRitirato(dati.getDataOdierna());
			dati.getMioAccount().removeProposta(e);
			UtilView.visualizzaMessaggio("La proposta è stata ritirata correttamente");
		}
	}

	private static void vediNuoveNotifiche(Dati dati) {
		Vector<Notifica> nonLette = dati.getMioAccount().getNotificheNonLette(); 
		UtilView.visualizzaNotifiche(nonLette);
		dati.getMioAccount().leggiNotificheNonLette();
	}

	private static void vediTutteNotifiche(Dati dati) {
		UtilView.visualizzaNotifiche(dati.getMioAccount().getNotifiche());
	}
	
	private static void modificaProfilo(Dati dati) {
		UtilView.visualizzaMessaggio(dati.getMioAccount().toString() + "\n");
		UtilMenuModProfilo.menuModificaProfilo(dati);
	}

}
