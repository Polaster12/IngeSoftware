package Util;

import java.util.Vector;

import Account.Account;
import Main.Container;
import Main.Dati;
import Main.Main;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuAccesso {
	
	private static final String titolo0 = "ZanellaGram";
	private static final String[] voci0 = {"Accedi","Crea Nuovo Account"};
	private static MyMenu menu0 = new MyMenu(titolo0,voci0,false);
	
	public static void menuAccesso(Dati dati) {
		int i;
		i = menu0.scegli();
		switch(i) {
			case 1:accedi(dati);
				break;
			case 2:nuovoAccount(dati);
				break;
		}
	}
	
	private static void nuovoAccount(Dati dati) {
		String n;
		boolean valido = true;
		n = InputDati.leggiStringaNonVuota("\nInserisci il Nomignolo* --> ");
		
		for(int i=0; i<dati.getContainer().sizeAccount(); i++) {
			if(n.equals(dati.getContainer().getAccounts().get(i).getNomignolo())) {
				valido=false;
				System.out.println("\nNomignolo già in uso");
			}else valido = true;
		}
		
		if(valido) {
			FasciaDiEta f = null;
			System.out.println("Fascia di età: ");
			Integer min = InputDati.leggiInteroNull("Età minima --> ");
			Integer max = InputDati.leggiInteroNull("Età massima --> ");
			if (min != null && max != null) f = new FasciaDiEta(min, max);
			
			Account a = new Account(n,f);
			dati.setMioAccount(a);
			dati.getContainer().addAccount(dati.getMioAccount());
			
			menuInteressi(dati);
		}
		else menuAccesso(dati);
		
	}
	
public static void menuInteressi(Dati dati) {
		
		String titolo = "Seleziona la categoria alla quale sei interessato/a";
		int i;
		
		do {
			
			Vector<String> altreCategorie = new Vector<>();
			
			for(String s:Container.categorie) {
				if (!dati.getMioAccount().getCategorieInteresse().contains(s))
					altreCategorie.add(s);
			}
			
			String[] voci = new String[altreCategorie.size()];
			
			for(int j=0;j<altreCategorie.size();j++) {
				voci[j] = altreCategorie.get(j);
			}
			
			MyMenu menu = new MyMenu(titolo,voci,true);
			i = menu.scegli()-1;
			if (i>=0){
				dati.getMioAccount().addCategorieInteresse(altreCategorie.get(i));
				System.out.println("\nCategoria aggiunta ai tuoi interessi");
			}
		} while(i>=0);
	}

	private static void accedi(Dati dati) {
		String s=InputDati.leggiStringaNonVuota("\nUsername: ");
		boolean trovato=false;
		for(int i=0; i<dati.getContainer().sizeAccount(); i++) {
			if(s.equals(dati.getContainer().getAccounts().get(i).getNomignolo())) {
				dati.setMioAccount(dati.getContainer().getAccounts().get(i));
				trovato=true;
			}
		}
		if(!trovato) {
			System.out.println("\nUsername non valido");
			menuAccesso(dati);
		}
	}
	
	

}
