package Util;

import java.util.Vector;

import Eventi.Evento;
import Eventi.PartitaDiCalcio;
import Main.Dati;
import Main.Main;
import Main.Stati;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;
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
				case 1:System.out.println(PartitaDiCalcio.toStringCategoria());
					break;
				case 2:creaPartitaDiCalcio(dati);
					break;
				case 3:visualizzaPartitaDiCalcio(dati);
					break;
			}
		}while(i!=0);
	}

	private static void visualizzaPartitaDiCalcio(Dati dati) {
		Vector<PartitaDiCalcio> partiteAperte = partiteAperte(dati);
		
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
				System.out.println("\nIscrizione completata con successo");
			}
			else {
				if (i>=0 && partiteAperte.get(i).controlloIscrizione(dati.getMioAccount())) {
					System.out.println("\nSei gia iscritto!!");
				}
			}
		}
		else {
			System.out.println("\nNon ci sono Partite di Calcio disponibili");
			menuPartitaDiCalcio(dati);
		}
		
	}
	
private static void creaPartitaDiCalcio(Dati dati) {
		
		Object[] valoriBase = new Object[15];
		Object[] valoriExtra = new Object[2];
		
		UtilInviti.compilazioneBase(valoriBase,dati);
		
		valoriExtra[0] = InputDati.leggiStringaNull("Genere* --> ");
		System.out.println("Fascia di et‡*: ");
		Integer min = InputDati.leggiInteroNull("Et‡ minima --> ");
		Integer max = InputDati.leggiInteroNull("Et‡ massima --> ");
		if (min != null && max != null) valoriExtra[1] = new FasciaDiEta(min, max);
		else valoriExtra[1]=null;
		
		if (controlloObbligatoriet‡PartitaDiCalcio(valoriBase, valoriExtra)) {
			PartitaDiCalcio p = new PartitaDiCalcio(valoriBase, valoriExtra);
			System.out.println("\nEvento creato con successo");
			if(InputDati.yesOrNo("\nVuoi pubblicarlo adesso?(E' possibile la pubblicazione in un secondo momento)-->")){
				p.setStato(Stati.aperto);
				UtilInviti.notificaInteressati(p,dati);
				UtilInviti.inviti(p,dati);
			}
			p.addPartecipante(dati.getMioAccount());
			dati.getMioAccount().addProposta(p);
			dati.getContainer().getPartite().add(p);
		} else {
			System.out.println("\nAlcuni dei campi obbligatori non sono stati compilati!");
		}
		
	}

	private static boolean controlloObbligatoriet‡PartitaDiCalcio(Object[] valoriBase, Object[] valoriExtra) {
		boolean result = true;
		
		for (int i=0; i<valoriBase.length-1; i++) {
			if (Evento.getObbligatoriet‡()[i] && valoriBase[i]==null) result=false;
		}
		for (int i=0; i<valoriExtra.length; i++) {
			if (PartitaDiCalcio.getObbligatoriet‡()[i] && valoriExtra[i]==null) result=false;
		}
		return result;
	}
	
	public static Vector<PartitaDiCalcio> partiteAperte(Dati dati){
		Vector<PartitaDiCalcio> partiteAperte = new Vector<>();
			
		for(PartitaDiCalcio p:dati.getContainer().getPartite()) {
			if (p.isAperto())
				partiteAperte.add(p);
		}
		
		return partiteAperte;
	}

}
