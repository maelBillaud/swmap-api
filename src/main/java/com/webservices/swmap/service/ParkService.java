package com.webservices.swmap.service;


import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.model.Park;
import com.webservices.swmap.model.User;
import com.webservices.swmap.model.Verifier;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Interface d'appel du service {@link Park}.
 */
@Validated
public interface ParkService {

	/**
	 * Crée un {@link Park} et sa ressource {@link Equipment} associée.
	 *
	 * @param park park à créer contenant une ressource {@link Equipment}
	 *
	 * @return les lignes créées si la création s'est correctement déroulée, sinon une exception.
	 */
	Park createPark(@NotNull @Valid Park park);

	/**
	 * Modifie un {@link Park} et/ou sa ressource {@link Equipment} associée.
	 *
	 * @param park park à modifier contenant une ressource {@link Equipment}
	 *
	 * @return les lignes modifiées si la modification s'est correctement déroulée, sinon une exception.
	 */
	Park updatePark(@NotNull @Valid Park park);

	/**
	 * Associe un {@link User} à un {@link Park} en créant un {@link Verifier}, puis met à jour le nombre de verifier
	 * du {@link Park}
	 *
	 * @param verifier Object contenant les informations du {@link Verifier}
	 *
	 * @return le {@link Park} auquel l'utilisateur a été associé avec ses données à jour, sinon une exception
	 */
	Park addParkVerifier(@NotNull @Valid Verifier verifier);

	/**
	 * Supprime un {@link Park} et sa ressource {@link Equipment} associée.
	 *
	 * @param parkId identifiant du park à supprimer
	 * @param equipmentId identifiant de l'{@link Equipment} à supprimer
	 *
	 * @return true si le {@link Park} a correctement été supprimé, false sinon
	 */
	boolean deletePark(@NotNull Long parkId, @NotNull Long equipmentId);

	/**
	 * Dissocie un {@link User} d'un {@link Park} en supprimant un {@link Verifier}, puis met à jour le nombre de
	 * verifier de ce {@link Park}
	 *
	 * @param verifier Object contenant les informations du {@link Verifier}
	 *
	 * @return le {@link Park} auquel l'utilisateur a été dissocié avec ses données à jour, sinon une exception
	 */
	Park removeParkVerifier(@NotNull @Valid Verifier verifier);

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
	Park getParkById(@NotNull Long parkId);

	/**
	 * Retourne la liste des {@link Park} qui sont à moins de <i>{@code distance}</i> km des coordonnées [<i>{@code latitude}</i>,
	 * <i>{@code longitude}</i>]
	 *
	 * @param latitude Première composante de la coordonnée de référence
	 * @param longitude Seconde composante de la coordonnée de référence
	 * @param distance Distance maximale qui doit séparer un {@link Park} de la coordonnée de référence
	 *
	 * @return une liste de {@link Park} pouvant être nulle
	 */
	List<Park> getParkByLocation(@NotNull Double latitude, @NotNull Double longitude, @NotNull Double distance);
}