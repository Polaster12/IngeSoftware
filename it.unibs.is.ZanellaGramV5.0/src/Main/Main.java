package Main;

import java.util.GregorianCalendar;
import java.io.File;
import java.util.Calendar;
import java.util.Vector;

import Account.Account;
import Controller.UtilMenuAccesso;
import Controller.UtilMenuHome;
import DBMS.Container;
import DBMS.Dati;
import Eventi.PartitaDiCalcio;
import Eventi.TorneoDiPoker;
import it.unibs.fp.mylib.*;

public class Main {
	
	private static File f;
	
	
	public static void main(String[] args) {
		Dati dati = new Dati();
		
		caricamento(dati);
		aggiornaData(dati);
		
		UtilMenuAccesso.menuAccesso(dati);
		
		UtilMenuHome.menuHome(dati);

		salvataggio(dati);
	}
	
	
	
	public static void aggiornaData(Dati dati) {
		GregorianCalendar d = new GregorianCalendar();
		dati.setDataOdierna(new Data(d.get(Calendar.DAY_OF_MONTH),d.get(Calendar.MONTH)+1,d.get(Calendar.YEAR)));
		for(PartitaDiCalcio c: dati.getContainer().getPartite()) {
				c.cambioStato(dati.getDataOdierna());
		}
		
		for(TorneoDiPoker f: dati.getContainer().getTornei()) {
				f.cambioStato(dati.getDataOdierna());
		}
	}
	
	private static void caricamento(Dati dati) {
		f = new File("ZanellaGram.dat");
		Container container = (Container)ServizioFile.caricaSingoloOggetto(f);
		if (!ServizioFile.successo) container = new Container(new Vector<Account>(),new Vector<PartitaDiCalcio>(), 
				new Vector<TorneoDiPoker>());
		dati.setContainer(container);
	}

	private static void salvataggio(Dati dati) {
		ServizioFile.salvaSingoloOggetto(f, dati.getContainer());
		System.out.println("\nSalvataggio effetuato correttamente");
	}

	
	
}
