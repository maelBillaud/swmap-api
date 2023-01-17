package com.webservices.swmap.repository;

import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.model.Park;
import com.webservices.swmap.model.Verifier;

import java.util.List;

/**
 * Interface d'accès au Repository de {@link Park}.
 */
public interface ParkRepository {

	/**
	 * Crée un {@link Park}
	 *
	 * @param park {@link Park} à créer
	 *
	 * @return la ligne créée si la création s'est correctement déroulée, sinon une exception.
	 */
	Park createPark(Park park);

	/**
	 * Modifie un {@link Park} et/ou sa ressource {@link Equipment} associée.
	 *
	 * @param park {@link Park} à modifier contenant une ressource {@link Equipment}
	 *
	 * @return les lignes modifiées si la modification s'est correctement déroulée, sinon une exception.
	 */
	Park updatePark(Park park);

	/**
	 * Met à jour le nombre de {@link Verifier} assignés à un {@link Park} après l'ajout d'un {@link Verifier} en base
	 *
	 * @param parkId identifiant du {@link Park} à mettre à jour
	 * @param verifierNumber nombre de personnes ayant vérifié le {@link Park}
	 * @param added nombre de personnes à ajouter en tant que {@link Verifier}
	 * @param modificationAgent utilisateur ayant initié la modification
	 *
	 * @return le nombre de {@link Verifier} mis à jour
	 */
	int updateVerifierNumber(Long parkId, int verifierNumber, int added, String modificationAgent);

	/**
	 * Supprime un {@link Park}.
	 *
	 * @param parkId identifiant du {@link Park} à supprimer
	 *
	 * @return true si le {@link Park} a correctement été supprimé, false sinon
	 */
	boolean deletePark(Long parkId);

	/**
	 * Retourne la liste des {@link Park}
	 *
	 * @return une liste de {@link Park} pouvant être nulle
	 */
	List<Park> getParks();

	/**
	 * Retourne un {@link Park} et sa ressource {@link Equipment} associée.
	 *
	 * @param parkId identifiant du park à retourner
	 *
	 * @return le parc correspondant à l'identifiant ou une erreur
	 */
	Park getParkById(Long parkId);
}
