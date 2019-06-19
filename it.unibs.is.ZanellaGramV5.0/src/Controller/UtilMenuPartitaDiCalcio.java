package Controller;

import java.util.Vector;

import DBMS.Dati;
import Eventi.PartitaDiCalcio;
import Eventi.Stati;
import View.UtilView;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuPartitaDiCalcio {
	
	private static final String titolo3 = "Partita Di Calcio";
	private static final String[] voci3 = {"Descrizione categoria","Crea Evento","Partecipa Evento"};
	private static MyMenu menuPartitaDiCalcio = new MyMenu(titolo3,voci3,true);
	
	public static void menuPartitaDiCalcio(Dati dati) {
		int i;
		do{
			i = menuPartitaDiCalcio.scegli();
			switch(i) {
				case 1:UtilView.visualizzaMessaggio(PartitaDiCalcio.toStringCategoria());
					break;
				case 2:creaPartitaDiCalcio(dati);
					break;
				case 3:visualizzaPartitaDiCalcio(dati);
					break;
			}
		}while(i!=0);
	}

	private static void visualizzaPartitaDiCalcio(Dati dati) {
		Vector<PartitaDiCalcio> partiteAperte = dati.partiteAperte();
		
		String[] voci = new String[partiteAperte.size()];
		
		for(int j=0;j<partiteAperte.size();j++) {
			voci[j] = partiteAperte.get(j).toString();
		}
		
		
		if(partiteAperte.size()!=0) {
			MyMenu menu = new MyMenu("Partite Disponibili",voci,true);
			int i = menu.scegli()-1;
			if (i>=0 && !partiteAperte.get(i).controlloIscrizione(dati.getMioAccount())){
				partiteAperte.get(i).addPartecipante(dati.getMioAccount());
				dati.getMioAccount().addEvento(partiteAperte.get(i));
				UtilView.visualizzaMessaggio("Iscrizione completata con successo");
			}
			else {
				if (i>=0 && partiteAperte.get(i).controlloIscrizione(dati.getMioAccount())) {
					UtilView.visualizzaMessaggio("Sei gia iscritto!!");
				}
			}
		}
		else {
			UtilView.visualizzaMessaggio("Non ci sono Partite di Calcio disponibili");
			menuPartitaDiCalcio(dati);
		}
		
	}
	
private static void creaPartitaDiCalcio(Dati dati) {

		
		Object[] valoriBase = UtilView.compilazioneBaseView(dati);
		valoriBase[14] = dati.getMioAccount();
		
		Object[] valoriExtra = UtilView.compilazionePartitaCalcioView();
		
		if (PartitaDiCalcio.controlloObbligatorietàPartitaDiCalcio(valoriBase, valoriExtra)) {
			PartitaDiCalcio p = new PartitaDiCalcio(valoriBase, valoriExtra);
			UtilView.visualizzaMessaggio("Evento creato con successo!");	
			p.addPartecipante(dati.getMioAccount());
			dati.getMioAccount().addProposta(p);
			pubblicazionePartitaDiCalcio(p,dati);
		}else {
			UtilView.visualizzaMessaggio("Alcuni dei campi obbligatori non sono stati compilati!");
		}
}

private static void pubblicazionePartitaDiCalcio(PartitaDiCalcio p,Dati dati) {
			if(UtilView.ask("Vuoi pubblicarlo adesso?(E' possibile la pubblicazione in un secondo momento)-->")){
				p.setStato(Stati.aperto);
				UtilInviti.notificaInteressati(p,dati);
				UtilInviti.inviti(p,dati);
			}			
			dati.getContainer().getPartite().add(p);
		}

}
