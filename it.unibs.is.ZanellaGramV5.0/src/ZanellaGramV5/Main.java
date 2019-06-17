package ZanellaGramV5;

import java.util.Date;
import java.util.GregorianCalendar;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import it.unibs.fp.mylib.*;

public class Main {
	
	
	private static Account mioAccount; 
	
	private static Data dataOdierna;
	
	private static Container container;
	
	private static File f;
	
	
	public static void main(String[] args) {
		caricamento();
		
		aggiornaData();
		System.out.println(dataOdierna);
		
		UtilMenu0.menu0();
		
		UtilMenu1.menu1();

		salvataggio();
	}
	
	
	
	public static void aggiornaData() {
		GregorianCalendar d = new GregorianCalendar();
		dataOdierna = new Data(d.get(Calendar.DAY_OF_MONTH),d.get(Calendar.MONTH)+1,d.get(Calendar.YEAR));
		for(PartitaDiCalcio c: container.getPartite()) {
				c.cambioStato(dataOdierna);
		}
		
		for(TorneoDiPoker f: container.getTornei()) {
				f.cambioStato(dataOdierna);
		}
	}
	
	private static void caricamento() {
		f = new File("ZanellaGram.dat");
		container = (Container)ServizioFile.caricaSingoloOggetto(f);
		if (!ServizioFile.successo) container = new Container(new Vector<Account>(),new Vector<PartitaDiCalcio>(), new Vector<TorneoDiPoker>());
	}

	private static void salvataggio() {
		ServizioFile.salvaSingoloOggetto(f, container);
		System.out.println("\nSalvataggio effetuato correttamente");
	}

	public static Container getContainer() {
		return container;
	}

	public static Data getDataOdierna() {
		return dataOdierna;
	}



	public static Account getMioAccount() {
		return mioAccount;
	}

	public static void setMioAccount(Account a) {
		mioAccount = a;
	}
	
}
