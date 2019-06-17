package ZanellaGramV5;

import java.io.Serializable;
import java.util.Vector;

public class Container implements Serializable {
	

	public static final String[] categorie = {PartitaDiCalcio.nome,TorneoDiPoker.nome};

	private Vector<Account> accounts;
	private Vector<PartitaDiCalcio> partite;
	private Vector<TorneoDiPoker> tornei;
	
	public Container (Vector<Account> a, Vector<PartitaDiCalcio> p, Vector<TorneoDiPoker> t) {
		accounts = a;
		partite = p;
		tornei = t;
	}

	public Vector<Account> getAccounts() {
		return accounts;
	}

	public Vector<PartitaDiCalcio> getPartite() {
		return partite;
	}
	
	public Vector<TorneoDiPoker> getTornei() {
		return tornei;
	}

	public void setPartite(Vector<PartitaDiCalcio> partite) {
		this.partite = partite;
	}
	
	public void setTornei(Vector<TorneoDiPoker> feste) {
		this.tornei = feste;
	}
	
	public int sizeAccount() {
		return accounts.size();
	}
	
	public void addAccount(Account a) {
		accounts.add(a);
	}
	
	public int sizeCategorie() {
		return categorie.length;
	}
	
}
