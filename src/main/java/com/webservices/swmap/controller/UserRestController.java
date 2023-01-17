package com.webservices.swmap.controller;

import com.webservices.swmap.model.User;
import com.webservices.swmap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(path=UserRestController.ROOT_PATH)
public class UserRestController {
	/**
	 * Chemin de base du controller REST des {@link User}.
	 */
	public static final String ROOT_USERS = "/users";
	public static final String ROOT_PATH = "${url.rest.controller.mapping.public}" + ROOT_USERS;
	public static final String PATH_ID = "/{id}";

	private final UserService service;

	/**
	 * Constructeur avec paramètre (injection des dépendances).
	 *
	 * @param service le service métier de gestion des utilisateurs
	 */
	public UserRestController(UserService service) {
		this.service = service;
	}

	/**
	 * Requête REST POST de création d'une ressource {@link User}.
	 * Noter que si l'id du {@link User} est fourni il sera ignoré par le service.
	 *
	 * @param user utilisateur à créer
	 *
	 * @return la ressource {@link User} créée (avec son id), un header location permettant
	 * d'accéder à cette ressource et un code 201-CREATED. Ou une erreur 500-INTERNAL_SERVER_ERROR avec un message
	 * associé.
	 */
	@PostMapping
	public ResponseEntity createUser(@RequestBody User user) {
		User result = service.createUser(user);

		if (result == null) return ResponseEntity.internalServerError()
		                                         .body("Erreur lors de la création de l'utilisateur.");

		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
		                                          .path(PATH_ID)
		                                          .buildAndExpand(result.getUserId())
		                                          .toUri();

		return ResponseEntity.created(location)
		                     .body(result);
	}

	/**
	 * Requête REST UPDATE de mise à jour d'une ressource {@link User}.
	 *
	 * @param id l'identifiant de l'utilisateur à modifier
	 * @param user l'utilisateur avec les champs à mettre à jour
	 *
	 * @return la ressource {@link User} modifiée et un code 202-ACCEPTED. Ou un code retour 404-NOT_FOUND
	 */
	@PutMapping(path=PATH_ID)
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setUserId(id);

		User result = service.updateUser(user);

		if (result == null) return ResponseEntity.notFound()
		                                         .build();

		return ResponseEntity.ok()
		                     .body(result);
	}

	/**
	 * Requête REST GET pour retrouver une ressource {@link User}.
	 *
	 * @param id l'identifiant de l'utilisateur à retrouver
	 *
	 * @return la ressource {@link User} si elle existe dans la base et un code :<br>
	 * <li>200-OK si tous s'est bien passés</li>
	 * <li>404-NOT FOUND si la ressource n'est pas trouvée</li>
	 */
	@GetMapping(path=PATH_ID)
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User result = service.getUserById(id);

		if (result == null) return ResponseEntity.notFound()
		                                         .build();

		return ResponseEntity.ok()
		                     .body(result);
	}

	/**
	 * Requête REST DELETE de suppression d'une ressource {@link User}.
	 *
	 * @param id identifiant de l'utilisateur à supprimer
	 *
	 * @return si la ressource {@link User} est supprimée un code retour 200-OK,
	 * sinon un code retour 404-NOT_FOUND
	 */
	@DeleteMapping(path=PATH_ID)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		if (service.deleteUser(id)) {
			return ResponseEntity.ok()
			                     .build();
		} else {
			return ResponseEntity.notFound()
			                     .build();
		}
	}
}
