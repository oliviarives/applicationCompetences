package modele;

public enum Service {
	INDUSTRIEL ("Industriel"),
	LOGISTIQUE ("Logistique");

	private final String nom; 
	
	private Service(String nom) {
		this.nom = nom;
	}
	
	@Override 
	public String toString() {
		return this.nom;
	}
}
