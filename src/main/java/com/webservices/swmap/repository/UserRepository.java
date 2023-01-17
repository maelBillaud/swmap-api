package com.webservices.swmap.repository;

import com.webservices.swmap.model.User;

/**
 * Interface d'accès au Repository de {@link User}.
 */
public interface UserRepository {

	/**
	 * Crée un {@link User}.
	 *
	 * @param user utilisateur à créer
	 *
	 * @return la ligne créée si la création s'est correctement déroulée, sinon une exception.
	 */
	User createUser(User user);

	/**
	 * Modifie un {@link User}.
	 *
	 * @param user utilisateur à modifier
	 *
	 * @return la ligne modifiée si la création s'est correctement déroulée, sinon une exception.
	 */
	User updateUser(User user);

	/**
	 * Trouve un {@link User} depuis son ID.
	 *
	 * @param userId l'identifiant de l'utilisateur à retrouver
	 *
	 * @return la ligne correspondante à l'identifiant dans la base si elle existe, sinon une exception.
	 */
	User getUserById(Long userId);

	/**
	 * Supprime un {@link User}.
	 *
	 * @param userId l'identifiant de l'utilisateur à supprimer
	 *
	 * @return true si le {@link User} a correctement été supprimé, false sinon.
	 */
	boolean deleteUser(Long userId);
}
