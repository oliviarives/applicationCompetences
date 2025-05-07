package modele;

public enum Statut {

	EN_PREPARATION(1,"En préparation"),
	PLANIFIEE(2,"Planifiée"),
	EN_COURS(3,"En cours"),
	TERMINEE(4,"Terminée"),
	VACANCES(5,"Vacances");

	private final int idStatut;
	private final String nomStatut;
	
	private Statut(int id,String nom) {
		this.idStatut = id;
		this.nomStatut = nom;
	}
	
	@Override
	public String toString () {
		return this.nomStatut;
	}

	public int getIdStatut() {
		return idStatut;
	}

	public static Statut fromId(int id) {
		for (Statut s : Statut.values()) {
			if (s.getIdStatut() == id) {
				return s;
			}
		}
		throw new IllegalArgumentException("Statut inconnu avec id: " + id);
	}


}
