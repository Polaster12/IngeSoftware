package View;

import java.util.ArrayList;
import java.util.Vector;

import Account.Notifica;
import DBMS.Dati;
import Eventi.TorneoDiPoker;
import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.Data;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;

public class UtilView {
	
	public static int chiediIntero(String messaggio) {
		return InputDati.leggiIntero(messaggio);
	}
	
	public static int chiediIntero(String messaggio,int min,int max) {
		return InputDati.leggiIntero(messaggio,min,max);
	}
	
	public static boolean ask(String domanda) {
		return InputDati.yesOrNo(domanda);
	}
	
	public static void visualizzaMessaggio(String messaggio) {
		System.out.println("\n" + messaggio);
	}
	
	public static String chiediStringaNonVuota(String messaggio) {
		return InputDati.leggiStringaNonVuota("\n" + messaggio + " --> ");
	}
	
	public static FasciaDiEta chiediFasciaDiEta(String messaggio) {
		return InputDati.leggiFasciaEtaNull(messaggio);
	}

	public static Object[] compilazioneBaseView(Dati dati) {
		Object[] valoriBase = new Object[15];
		valoriBase[0] = InputDati.leggiStringaNull("\nTitolo --> ");
		valoriBase[1] = InputDati.leggiInteroNull("Partecipanti necessari* --> ");
		valoriBase[2] = InputDati.leggiDataNull("Data termine iscrizioni*:", dati.getDataOdierna());
		valoriBase[3] = InputDati.leggiStringaNull("Luogo* --> ");
		valoriBase[4] = InputDati.leggiDataNull("Data inizio evento*:", (Data)valoriBase[2]);
		valoriBase[5] = InputDati.leggiOraNull("Ora inizio evento*:");
		valoriBase[6] = InputDati.leggiOraNull("Durata: ");
		valoriBase[7] = InputDati.leggiDoubleNull("Quota di partecipazione (in Euro €)* --> ");
		valoriBase[8] = InputDati.leggiStringaNull("Compreso in quota --> ");
		valoriBase[9] = InputDati.leggiDataNull("Data conclusiva:", (Data)valoriBase[4]);
		valoriBase[10] = InputDati.leggiOraNull("Ora conclusiva:");
		valoriBase[11] = InputDati.leggiStringaNull("Note --> ");
		valoriBase[12] = InputDati.leggiInteroNull("Tolleranza Numero di Partecipanti --> ");
		valoriBase[13] = InputDati.leggiDataNull("Data termine disicrizioni:", dati.getDataOdierna());
		return valoriBase;
	}
	
	public static Object[] compilazionePartitaCalcioView() {
		Object[] valoriExtra = new Object[2];
		valoriExtra[0] = InputDati.leggiStringaNull("Genere* --> ");
		valoriExtra[1] = InputDati.leggiFasciaEtaNull("Fascia di età*: ");
		return valoriExtra;
	}
	
	public static Object[] compilazioneTorneoDiPokerView() {
		
		Object[] valoriExtra = new Object[3];
		valoriExtra[0] = InputDati.leggiStringaNull("Tipologia* --> ");
		valoriExtra[1] = InputDati.leggiInteroNull("Eta minima dei partecipanti* -->");
		
		ArrayList<Double> speseAggiuntive = new ArrayList<>();
		System.out.println("Inserisci le spese aggiuntive:");
		for(int i=0;i<TorneoDiPoker.vociSpeseAggiuntive.length;i++) {
			speseAggiuntive.add(InputDati.leggiDoubleNull(TorneoDiPoker.vociSpeseAggiuntive[i] + " -->"));
		}
		valoriExtra[2] = speseAggiuntive;
		
		return valoriExtra;
	}

	public static void visualizzaNotifiche(Vector<Notifica> nonLette) {
		System.out.println(BelleStringhe.incornicia("notifiche: "));
		for(Notifica n:nonLette) {
				System.out.println("\n" +n.toString());
		}
	}
}
