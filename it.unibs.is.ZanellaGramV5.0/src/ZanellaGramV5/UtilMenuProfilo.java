package ZanellaGramV5;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuProfilo {
	
	private static final String titolo4 = "Profilo";
	private static final String[] voci4 = {"Nuove Notifiche","Tutte le Notifiche","Eventi a cui sono iscritto", "Eventi proposti da me", "Dati Personali"};
	private static MyMenu menuProfilo = new MyMenu(titolo4,voci4,true);
	
	private static final String titolo7 = "Che azione vuoi compiere su questo evento?";
	private static final String[] voci7 = {"Ritira","Pubblica"};
	private static MyMenu menuAzioneEvento = new MyMenu(titolo7,voci7,true);
	
	public static void menuProfilo() {
		int i;
		do{
			i = menuProfilo.scegli();
			switch(i) {
				case 1:
					vediNuoveNotifiche();
					break;
				case 2:
					vediTutteNotifiche();
					break;
				case 3:visualizzaEventiIscritto();
					break;
				case 4:visualizzaEventiProposti();
					break;
				case 5:modificaProfilo();
					break;
			}
		}while(i!=0);
		
	}
	
	private static void visualizzaEventiIscritto() {
		
		String[] voci = new String[Main.getMioAccount().sizeEventi()];
		
		for(int j=0;j<Main.getMioAccount().sizeEventi();j++) {
			voci[j] =Main.getMioAccount().getEvento(j).toString();
		}
		
		MyMenu menu = new MyMenu("Sei iscritto ai seguenti eventi, digita il numero per disiscriverti",voci,true);
		int i = menu.scegli()-1;
		if (i>=0 && Main.getMioAccount().getEvento(i).getTermineDisiscrizioni().compareTo(Main.getDataOdierna())<=0 ){
			Main.getMioAccount().getEvento(i).removeAmmontari(Main.getMioAccount().getEvento(i).PartecipantiIndexOf(Main.getMioAccount()));
			Main.getMioAccount().getEvento(i).removePartecipante(Main.getMioAccount());
			Main.getMioAccount().removeEvento(Main.getMioAccount().getEvento(i));
			System.out.println("\nDisiscrizione eseguita con successo");
		}
		else {
			if(i>=0) System.out.println("\nNon puoi disiscriverti perchè passato il termine");
		}
	}
	
	private static void visualizzaEventiProposti() {
		
		String[] voci = new String[Main.getMioAccount().sizeEventi()];

		for(int j=0;j<Main.getMioAccount().sizeProposte();j++) {
			voci[j] = Main.getMioAccount().getProposta(j).toString();
		}
		
		MyMenu menu = new MyMenu("Hai proposto i seguenti eventi, digita il numero per ritirare una proposta",voci,true);
		int i = menu.scegli()-1;
		
		if (i>=0){
			menuAzioneEvento(Main.getMioAccount().getProposta(i));
		}
		
		
		
	}
	
	private static void menuAzioneEvento(Evento e) {
		int i;
		do{
			i = menuAzioneEvento.scegli();
			switch(i) {
				case 1: ritiraEvento(e);
					break;
				case 2: pubblicaEvento(e);
					break;
			}
		}while(i!=0);
	}

	private static void pubblicaEvento(Evento e) {
		if (e.isPubblicato() || e.getTermineIscrizioni().compareTo(Main.getDataOdierna())>0){
			System.out.println("\nL'evento non può essere pubblicato perchè già pubblicato o è scaduto il termine iscrizioni");
		}
		else {
			e.setStato(Stati.aperto);
			System.out.println("\nLa proposta è stata pubblicata correttamente!");
		}
	}

	private static void ritiraEvento(Evento e) {
		if (Main.getDataOdierna().compareTo(e.getTermineDisiscrizioni())<0) {
			System.out.println("\nNon è possibile ritirare questo evento perchè è passata la data di termine disiscrizioni");
		}
		else {
			String messaggio = "L'evento "; 
			if (e.getTitolo() != null)
				messaggio = messaggio + e.getTitolo();
			else messaggio = messaggio + e.getNome();
			Notifica n = new Notifica(Main.getDataOdierna(),messaggio + ", previsto per il giorno " + e.getData() + ", è stato ritirato dall'organizzatore");
			e.sendNotifiche(n);
			Main.getContainer().getPartite().remove(e);
			e.eventoRitirato(Main.getDataOdierna());
			Main.getMioAccount().removeProposta(e);
			System.out.println("\nLa proposta è stata ritirata correttamente");
		}
	}	

	private static void vediNuoveNotifiche() {
	
		System.out.println(BelleStringhe.incornicia("notifiche: "));
		for(int i=0; i<Main.getMioAccount().sizeNotifiche();i++) {
			if (!Main.getMioAccount().getNotifica(i).isLetta()) {
				System.out.println("\n" + Main.getMioAccount().getNotifica(i).toString());
				Main.getMioAccount().getNotifica(i).leggi();
			}
		}
		
	}

	private static void vediTutteNotifiche() {
		
		System.out.println(BelleStringhe.incornicia("notifiche: "));
		for(int i=0; i<Main.getMioAccount().sizeNotifiche();i++) {
				System.out.println("\n" + Main.getMioAccount().getNotifica(i).toString());
		}
	}
	
	private static void modificaProfilo() {
		System.out.println("\n" + Main.getMioAccount().toString() + "\n");
		UtilMenuModProfilo.menuModificaProfilo();
	}

}
