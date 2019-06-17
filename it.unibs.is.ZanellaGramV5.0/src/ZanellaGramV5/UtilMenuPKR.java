package ZanellaGramV5;

import java.util.ArrayList;
import java.util.Vector;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuPKR {
	
	private static final String titolo6 = "Torneo di Poker";
	private static final String[] voci3 = {"Descrizione categoria","Crea Evento","Partecipa Evento"};
	private static MyMenu menuPoker = new MyMenu(titolo6,voci3,true);
	
	public static void menuPoker() {
		int i;
		do{
			i = menuPoker.scegli();
			switch(i) {
				case 1:System.out.println(TorneoDiPoker.toStringCategoria());
					break;
				case 2:creaTorneoDiPoker();
					break;
				case 3:visualizzaTorneiDiPoker();
					break;
			}
		}while(i!=0);
	}

	private static void creaTorneoDiPoker() {
		
		Object[] valoriBase = new Object[15];
		Object[] valoriExtra = new Object[3];
		
		UtilInviti.compilazioneBase(valoriBase);
		
		valoriExtra[0] = InputDati.leggiStringaNull("Tipologia* --> ");
		valoriExtra[1] = InputDati.leggiInteroNull("Eta minima dei partecipanti* -->");
		
		ArrayList<Double> speseAggiuntive = new ArrayList<>();
		System.out.println("Inserisci le spese aggiuntive:");
		for(int i=0;i<TorneoDiPoker.vociSpeseAggiuntive.length;i++) {
			speseAggiuntive.add(InputDati.leggiDoubleNull(TorneoDiPoker.vociSpeseAggiuntive[i] + " -->"));
		}
		valoriExtra[2] = speseAggiuntive;
		
		if (controlloObbligatoriet‡TorneoDiPoker(valoriBase, valoriExtra)) {
			TorneoDiPoker p = new TorneoDiPoker(valoriBase, valoriExtra);
			System.out.println("\nEvento creato con successo");
			if(InputDati.yesOrNo("\nVuoi pubblicarlo adesso?(E' possibile la pubblicazione in un secondo momento)-->")){
				p.setStato(Stati.aperto);
				UtilInviti.notificaInteressati(p);
				UtilInviti.inviti(p);
			}
			p.addPartecipante(Main.getMioAccount());
			Main.getMioAccount().addProposta(p);
			Main.getContainer().getTornei().add(p);
		} else {
			System.out.println("\nAlcuni dei campi obbligatori non sono stati compilati!");
		}
		
	}
	
	private static boolean controlloObbligatoriet‡TorneoDiPoker(Object[] valoriBase, Object[] valoriExtra) {
		boolean result = true;
		
		for (int i=0; i<valoriBase.length-1; i++) {
			if (Evento.getObbligatoriet‡()[i] && valoriBase[i]==null) result=false;
		}
		for (int i=0; i<valoriExtra.length; i++) {
			if (TorneoDiPoker.getObbligatoriet‡()[i] && valoriExtra[i]==null) result=false;
		}
		return result;
}

	public static void visualizzaTorneiDiPoker() {
		Vector<TorneoDiPoker> aperti = torneiDiPokerAperti();
		
		String[] voci = new String[aperti.size()];
		
		for(int j=0;j<aperti.size();j++) {
			voci[j] = aperti.get(j).toString();
		}
		
		if(aperti.size()!=0) {
			MyMenu menu = new MyMenu("Tornei di Poker Disponibili",voci,true);
			int i = menu.scegli()-1;
			if (i>=0 && !aperti.get(i).controlloIscrizione(Main.getMioAccount())){
				aperti.get(i).addPartecipante(Main.getMioAccount());
				aperti.get(i).addAmmontare(aperti.get(i).getQuotaIndividuale());
				scegliSpeseAggiuntive(aperti.get(i));
				Main.getMioAccount().addEvento(aperti.get(i));
				System.out.println("\nIscrizione completata con successo");
			}
			else {
				if (i>=0 && aperti.get(i).controlloIscrizione(Main.getMioAccount())) {
					System.out.println("\nSei gia iscritto!!");
				}
			}
		}
		else {
			System.out.println("\nNon ci sono tornei aperti disponibili");
			menuPoker();
		}
}

	private static void scegliSpeseAggiuntive(TorneoDiPoker t) {
		int i;
		t.getSpeseAggiuntive().forEach((s,d) -> {
			if (InputDati.yesOrNo("Vuoi sostenere la spesa " + s + " (" + d +"0 Ä)")) {
				t.sumAmmontari(Main.getMioAccount(), d);
			}	
		});
		
		System.out.println();
		
		i=0;
		Vector<Account> invitati = new Vector<>();
		do {
			i = InputDati.leggiIntero("Inserire il numero delle spese che vuoi sostenere (0 per uscire) --> ", 0, t.getSpeseAggiuntive().size()-1);
			
		} while(i>=0);
	}

	private static Vector<TorneoDiPoker> torneiDiPokerAperti() {
		Vector<TorneoDiPoker> aperti = new Vector<>();
		
		for(TorneoDiPoker f:Main.getContainer().getTornei()) {
			if (f.isAperto())
				aperti.add(f);
		}
		
		return aperti;
	}

}
