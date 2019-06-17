
package ZanellaGramV5;
import java.io.Serializable;
import java.lang.reflect.Field;

import it.unibs.fp.mylib.Data;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.Ora;

public class PartitaDiCalcio extends Evento implements Serializable{
	
	public static final String nome = "Partita di Calcio";
	public static final String descrizione = "Evento sportivo concernente il giuoco del pallone";
	
	private static final boolean[] obbligatorietà = {true,true};
	private static final String[] descrizioni = {
			"Genere dei partecipanti",
			"Limiti di età dei partecipanti"
			};

	private String genere;
	private FasciaDiEta fasciaDiEta;
	
	public PartitaDiCalcio(String titolo, int numeroPartecipanti, Data termineIscrizioni, String luogo, Data data,
			Ora ora, Ora durata, double quotaIndividuale, String compresoNellaQuota, Data dataConclusiva,
			Ora oraConclusiva, String note, int tolleranza, Data termineDisiscrizioni, Account propositore, String genere, FasciaDiEta fasciaDiEta) {
		super(titolo, numeroPartecipanti, termineIscrizioni, luogo, data, ora, durata, quotaIndividuale, compresoNellaQuota,
				dataConclusiva, oraConclusiva, note, tolleranza,termineDisiscrizioni, propositore);
		this.genere=genere;
		this.fasciaDiEta=fasciaDiEta;
	}
	
	public PartitaDiCalcio(Object[] campiBase, Object[] campiExtra) {
		super((String)campiBase[0],(int)campiBase[1],(Data)campiBase[2],(String)campiBase[3],(Data)campiBase[4],(Ora)campiBase[5],
				(Ora)campiBase[6],(double)campiBase[7],(String)campiBase[8],(Data)campiBase[9],(Ora)campiBase[10],
				(String)campiBase[11],(Integer)campiBase[12],(Data)campiBase[13], (Account)campiBase[14]);
		genere = (String)campiExtra[0];
		fasciaDiEta = (FasciaDiEta)campiExtra[1];
	}

	public static boolean[] getObbligatorietà() {
		return obbligatorietà;
	}

	public static String[] getDescrizioni() {
		return descrizioni;
	}

	public String getGenere() {
		return genere;
	}

	public FasciaDiEta getFasciaDiEta() {
		return fasciaDiEta;
	}
	
	public static String toStringCategoria() {
		
		StringBuffer s = new StringBuffer(Evento.toStringEvento());
		Field[] fields = PartitaDiCalcio.class.getDeclaredFields();
		
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

	@Override
	public String toString() {
		return CORNICE + "\n" + super.toString() + "\n\tGenere: " + genere + "\n\tFascia di Età: " + fasciaDiEta + "\n\t" + CORNICE;
	}
	
	public String getNome() {
		return nome;
	}

}
