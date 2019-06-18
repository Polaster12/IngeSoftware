package Controller;

import java.util.Vector;

import DBMS.Dati;
import Eventi.Stati;
import Eventi.TorneoDiPoker;
import View.UtilView;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuTorneoPoker {
	
	private static final String titolo6 = "Torneo di Poker";
	private static final String[] voci3 = {"Descrizione categoria","Crea Evento","Partecipa Evento"};
	private static MyMenu menuPoker = new MyMenu(titolo6,voci3,true);
	
	public static void menuPoker(Dati dati) {
		int i;
		do{
			i = menuPoker.scegli();
			switch(i) {
				case 1:UtilView.visualizzaMessaggio(TorneoDiPoker.toStringCategoria());
					break;
				case 2:creaTorneoDiPoker(dati);
					break;
				case 3:visualizzaTorneiDiPoker(dati);
					break;
			}
		}while(i!=0);
	}

	private static void creaTorneoDiPoker(Dati dati) {
		
		Object[] valoriBase = UtilView.compilazioneBaseView(dati);
		valoriBase[14] = dati.getMioAccount();
		
		Object[] valoriExtra = UtilView.compilazioneTorneoDiPokerView();
		
		if (TorneoDiPoker.controlloObbligatorietàTorneoDiPoker(valoriBase, valoriExtra)) {
			TorneoDiPoker p = new TorneoDiPoker(valoriBase, valoriExtra);
			UtilView.visualizzaMessaggio("\nEvento creato con successo");
			p.addPartecipante(dati.getMioAccount());
			dati.getMioAccount().addProposta(p);
			pubblicazioneTorneoDiPoker(p,dati);
		} else {
			UtilView.visualizzaMessaggio("\nAlcuni dei campi obbligatori non sono stati compilati!");
		}
		
	}
	private static void pubblicazioneTorneoDiPoker(TorneoDiPoker p,Dati dati) {
		if(UtilView.ask("\nVuoi pubblicarlo adesso?(E' possibile la pubblicazione in un secondo momento)-->")){
			p.setStato(Stati.aperto);
			UtilInviti.notificaInteressati(p,dati);
			UtilInviti.inviti(p,dati);
			dati.getContainer().getTornei().add(p);
		}
	}
	
	public static void visualizzaTorneiDiPoker(Dati dati) {
		Vector<TorneoDiPoker> aperti = dati.torneiDiPokerAperti();
		
		String[] voci = new String[aperti.size()];
		
		for(int j=0;j<aperti.size();j++) {
			voci[j] = aperti.get(j).toString();
		}
		
		if(aperti.size()!=0) {
			MyMenu menu = new MyMenu("Tornei di Poker Disponibili",voci,true);
			int i = menu.scegli()-1;
			if (i>=0 && !aperti.get(i).controlloIscrizione(dati.getMioAccount())){
				aperti.get(i).addPartecipante(dati.getMioAccount());
				aperti.get(i).addAmmontare(aperti.get(i).getQuotaIndividuale());
				scegliSpeseAggiuntive(aperti.get(i),dati);
				dati.getMioAccount().addEvento(aperti.get(i));
				UtilView.visualizzaMessaggio("\nIscrizione completata con successo");
			}
			else {
				if (i>=0 && aperti.get(i).controlloIscrizione(dati.getMioAccount())) {
					UtilView.visualizzaMessaggio("\nSei gia iscritto!!");
				}
			}
		}
		else {
			UtilView.visualizzaMessaggio("\nNon ci sono tornei aperti disponibili");
			menuPoker(dati);
		}
}

	private static void scegliSpeseAggiuntive(TorneoDiPoker t,Dati dati) {
		
		t.getSpeseAggiuntive().forEach((s,d) -> {
			if (UtilView.ask("Vuoi sostenere la spesa " + s + " (" + d +"0 €)?")) {
				t.sumAmmontari(dati.getMioAccount(), d);
			}	
		});
//		int i;
//		UtilView.visualizzaMessaggio("");
//		i=0;
//		do {
//			i = InputDati.leggiIntero("Inserire il numero delle spese che vuoi sostenere (0 per uscire) --> ", 0, t.getSpeseAggiuntive().size()-1);	
//		} while(i>=0);
	}

}
