package Controller;

import java.util.Vector;

import Account.Account;
import DBMS.Dati;
import View.UtilView;
import it.unibs.fp.mylib.FasciaDiEta;
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
		n = UtilView.chiediStringaNonVuota("Inserisci il Nomignolo*");
		
		for(int i=0; i<dati.getContainer().sizeAccount(); i++) {
			if(n.equals(dati.getContainer().getAccounts().get(i).getNomignolo())) {
				valido=false;
				UtilView.visualizzaMessaggio("Nomignolo già in uso");
			}else valido = true;
		}
		
		if(valido) {
			FasciaDiEta f = UtilView.chiediFasciaDiEta("Fascia di età: ");
			Account a = new Account(n,f);
			dati.setMioAccount(a);
			dati.getContainer().addAccount(dati.getMioAccount());
			
			aggiungiInteressi(dati);
		}
		else menuAccesso(dati);
		
	}
	
	public static void aggiungiInteressi(Dati dati) {
		
		String titolo = "Seleziona la categoria alla quale sei interessato/a";
		int i;
		
		do {
			Vector<String> altreCategorie = dati.altreCategorie();
			
			String[] voci = new String[altreCategorie.size()];
			
			for(int j=0;j<altreCategorie.size();j++) {
				voci[j] = altreCategorie.get(j);
			}
			
			MyMenu menu = new MyMenu(titolo,voci,true);
			i = menu.scegli()-1;
			if (i>=0){
				dati.getMioAccount().addCategorieInteresse(altreCategorie.get(i));
				UtilView.visualizzaMessaggio("\nCategoria aggiunta ai tuoi interessi");
			}
		} while(i>=0);
	}

	private static void accedi(Dati dati) {
		String s=UtilView.chiediStringaNonVuota("Username");
		Account a = dati.cercaAccount(s);
		if(a==null) {
			UtilView.visualizzaMessaggio("Username non valido");
			menuAccesso(dati);
		} else {
			dati.setMioAccount(a);
		}
	}
}
