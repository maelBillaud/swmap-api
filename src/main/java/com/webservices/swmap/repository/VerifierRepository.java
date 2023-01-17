package com.webservices.swmap.repository;

import com.webservices.swmap.model.Verifier;

/**
 * Interface d'accès au Repository de {@link Verifier}.
 */

public interface VerifierRepository {

	/**
	 * Crée un {@link Verifier}
	 *
	 * @param verifier {@link Verifier} à créer
	 *
	 * @return la ligne créée si la création s'est correctement déroulée, sinon une exception.
	 */
	Verifier createVerifier(Verifier verifier);

	/**
	 * Supprime un {@link Verifier}
	 *
	 * @param verifier {@link Verifier} à supprimer
	 *
	 * @return true si le {@link Verifier} a correctement été supprimé, false sinon
	 */
	boolean deleteVerifier(Verifier verifier);
}
