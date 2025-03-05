package modele;

public enum Statut {

	EN_PREPARATION("En préparation"),
	PLANIFIEE("Planifiée"),
	EN_COURS("En cours"),
	TERMINEE("Terminée");
	
	private final String nom; 
	
	private Statut(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString () {
		return this.nom;
	}
	
}
