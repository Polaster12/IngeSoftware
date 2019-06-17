package ZanellaGramV5;

import java.util.Vector;

import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenu0 {
	
	private static final String titolo0 = "ZanellaGram";
	private static final String[] voci0 = {"Accedi","Crea Nuovo Account"};
	private static MyMenu menu0 = new MyMenu(titolo0,voci0,false);
	
	public static void menu0() {
		int i;
		i = menu0.scegli();
		switch(i) {
			case 1:accedi();
				break;
			case 2:nuovoAccount();
				break;
		}
	}
	
	private static void nuovoAccount() {
		String n;
		boolean valido = true;
		n = InputDati.leggiStringaNonVuota("\nInserisci il Nomignolo* --> ");
		
		for(int i=0; i<Main.getContainer().sizeAccount(); i++) {
			if(n.equals(Main.getContainer().getAccounts().get(i).getNomignolo())) {
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
			Main.setMioAccount(a);
			Main.getContainer().addAccount(Main.getMioAccount());
			
			menuInteressi();
		}
		else menu0();
		
	}
	
public static void menuInteressi() {
		
		String titolo = "Seleziona la categoria alla quale sei interessato/a";
		int i;
		
		do {
			
			Vector<String> altreCategorie = new Vector<>();
			
			for(String s:Container.categorie) {
				if (!Main.getMioAccount().getCategorieInteresse().contains(s))
					altreCategorie.add(s);
			}
			
			String[] voci = new String[altreCategorie.size()];
			
			for(int j=0;j<altreCategorie.size();j++) {
				voci[j] = altreCategorie.get(j);
			}
			
			MyMenu menu = new MyMenu(titolo,voci,true);
			i = menu.scegli()-1;
			if (i>=0){
				Main.getMioAccount().addCategorieInteresse(altreCategorie.get(i));
				System.out.println("\nCategoria aggiunta ai tuoi interessi");
			}
		} while(i>=0);
	}

	private static void accedi() {
		String s=InputDati.leggiStringaNonVuota("\nUsername: ");
		boolean trovato=false;
		for(int i=0; i<Main.getContainer().sizeAccount(); i++) {
			if(s.equals(Main.getContainer().getAccounts().get(i).getNomignolo())) {
				Main.setMioAccount(Main.getContainer().getAccounts().get(i));
				trovato=true;
			}
		}
		if(!trovato) {
			System.out.println("\nUsername non valido");
			menu0();
		}
	}
	
	

}
