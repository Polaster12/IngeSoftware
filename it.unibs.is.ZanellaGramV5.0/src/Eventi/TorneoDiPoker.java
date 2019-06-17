package Eventi;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

import Account.Account;
import it.unibs.fp.mylib.Data;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.Ora;

public class TorneoDiPoker extends Evento implements Serializable{

	public static final String nome = "Torneo di poker";
	public static final String descrizione = "Torneo concernente il gioco di carte conosciuto come poker";
	
	public static final String[] vociSpeseAggiuntive = { "Cibarie", "Bevande", "Gadget" };
	
	private static final boolean[] obbligatorietà = {true,true,false};
	private static final String[] descrizioni = {
			"Tipologia",
			"Età minima"
			};

	private String tipologia;
	private int etaMinima;
	
	
	public TorneoDiPoker(String titolo, int numeroPartecipanti, Data termineIscrizioni, String luogo, Data data, Ora ora,
			Ora durata, double quotaIndividuale, String compresoNellaQuota, Data dataConclusiva, Ora oraConclusiva,
			String note, Integer tolleranza, Data termineDisiscrizione, Account propositore,String tipologia, int etaMinima,
			Double[] prezziSpeseAggiuntive) {
		super(titolo, numeroPartecipanti, termineIscrizioni, luogo, data, ora, durata, quotaIndividuale, compresoNellaQuota,
				dataConclusiva, oraConclusiva, note, tolleranza, termineDisiscrizione, propositore);
		this.etaMinima=etaMinima;
		this.tipologia=tipologia;
		for(int i=0; i<vociSpeseAggiuntive.length; i++) {
			super.addSpesaAggiuntiva(vociSpeseAggiuntive[i], prezziSpeseAggiuntive[i] );
		}
	}
	
	public TorneoDiPoker(Object[] campiBase, Object[] campiExtra) {
		super((String)campiBase[0],(int)campiBase[1],(Data)campiBase[2],(String)campiBase[3],(Data)campiBase[4],(Ora)campiBase[5],
				(Ora)campiBase[6],(double)campiBase[7],(String)campiBase[8],(Data)campiBase[9],(Ora)campiBase[10],
				(String)campiBase[11],(Integer)campiBase[12],(Data)campiBase[13], (Account)campiBase[14]);
		tipologia = (String)campiExtra[0];
		etaMinima = (int)campiExtra[1];
		ArrayList<Double> prezziSpeseAggiuntive = (ArrayList<Double>)campiExtra[2];
		for(int i=0; i<vociSpeseAggiuntive.length; i++) {
			super.addSpesaAggiuntiva(vociSpeseAggiuntive[i], prezziSpeseAggiuntive.get(i) );
		}
	}
	
	public static boolean[] getObbligatorietà() {
		return obbligatorietà;
	}

	public static String[] getDescrizioni() {
		return descrizioni;
	}
	
	public String getNome() {
		return nome;
	}

	public String getTipologia() {
		return tipologia;
	}

	public int getEtaMinima() {
		return etaMinima;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(CORNICE + "\n" + super.toString() + "\n\tTipologia: " + tipologia + "\n\tEtà minima: " + etaMinima + "\n\t");
		s.append("Le spese aggiuntive sono:");
		super.getSpeseAggiuntive().forEach((str,d) -> {
			s.append("\n\t\t" + str + " : " + d + "0 €");
		});
		s.append("\n\t" + CORNICE);
		return s.toString();
	}
	
	public static String toStringCategoria() {
			
			StringBuffer s = new StringBuffer(Evento.toStringEvento());
			Field[] fields = TorneoDiPoker.class.getDeclaredFields();
			
			for(int i=2;i<fields.length;i++) {
				s.append(fields[i].getName());
				if(getObbligatorietà()[i-2]) {
					s.append("*");
				}
				s.append(": " + getDescrizioni()[i-2]);
				s.append("\n");
			}
			return s.toString();
		}
}
