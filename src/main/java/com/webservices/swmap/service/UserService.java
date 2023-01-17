package com.webservices.swmap.service;

import com.webservices.swmap.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Interface d'appel du service {@link User}.
 */
@Validated
public interface UserService {
	/**
	 * Crée un {@link User}.
	 *
	 * @param user utilisateur à créer
	 *
	 * @return la ligne crée si la création s'est correctement déroulée, sinon une exception.
	 */
	User createUser(@NotNull @Valid User user);

	/**
	 * Modifie un {@link User}.
	 *
	 * @param user utilisateur à modifier
	 *
	 * @return la ligne modifiée si la modification s'est correctement déroulée, sinon une exception.
	 */
	User updateUser(@NotNull @Valid User user);

	/**
	 * Retourne un {@link User}.
	 *
	 * @param userId l'identifiant de l'utilisateur
	 *
	 * @return la ligne correspondante à l'identifiant fourni.
	 */
	User getUserById(@NotNull Long userId);

	/**
	 * Supprime un {@link User}.
	 *
	 * @param userId l'identifiant de l'utilisateur
	 *
	 * @return true si le {@link User} a correctement été supprimé, false sinon.
	 */
	boolean deleteUser(@NotNull Long userId);
}
