package modele;
/**
 * Enum contenant les statuts possibles d'une mission
 */
public enum Statut {

	/** Mission en préparation */
	EN_PREPARATION(1, "En préparation"),
	/** Mission planifiée */
	PLANIFIEE(2, "Planifiée"),
	/** Mission en cours */
	EN_COURS(3, "En cours"),
	/** Mission terminée */
	TERMINEE(4, "Terminée"),
	/** Statut vacances */
	VACANCES(5, "Vacances");

	/** Identifiant du statut */
	private final int idStatut;
	/** Nom  du statut */
	private final String nomStatut;

	/**
	 * Constructeur de l'enum Statut
	 * @param id identifiant du statut
	 * @param nom nom du statut
	 */
	private Statut(int id,String nom) {
		this.idStatut = id;
		this.nomStatut = nom;
	}
	/**
	 * Retourne le nom du statut
	 * @return nom du statut
	 */
	@Override
	public String toString () {
		return this.nomStatut;
	}
	/**
	 * Retourne l'identifiant du statut
	 * @return identifiant du statut
	 */
	public int getIdStatut() {
		return idStatut;
	}
	/**
	 * Retourne le statut correspondant à l'identifiant fourni
	 * @param id identifiant du statut
	 * @return statut correspondant
	 * @throws IllegalArgumentException si aucun statut ne correspond à l'identifiant
	 */
	public static Statut fromId(int id) {
		for (Statut s : Statut.values()) {
			if (s.getIdStatut() == id) {
				return s;
			}
		}
		throw new IllegalArgumentException("Statut inconnu avec id: " + id);
	}
}
