package com.webservices.swmap.controller;

import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.model.Park;
import com.webservices.swmap.model.User;
import com.webservices.swmap.model.Verifier;
import com.webservices.swmap.service.ParkService;
import com.webservices.swmap.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller REST de gestion des {@link Park}.
 */
@CrossOrigin
@RestController
@RequestMapping(path=ParkRestController.ROOT_PATH)
public class ParkRestController {
	/**
	 * Chemin de base du controller REST des {@link Park}. ex: /public/rest/parks
	 */
	public static final String ROOT_PARKS = "/parks";
	public static final String ROOT_PATH = "${url.rest.controller.mapping.public}" + ROOT_PARKS;
	public static final String PATH_ID = "/{id}";
	public static final String PATH_VERIFIER = "/verifier";
	public static final String PATH_LOCATION_FILTER = "/location-filter";
	public static final String PARAM_EQUIPMENT_ID = "equipmentId";
	public static final String PARAM_LATITUDE = "latitude";
	public static final String PARAM_LONGITUDE = "longitude";
	public static final String PARAM_DISTANCE = "distance";

	private final ParkService service;

	/**
	 * Constructeur avec paramètre(s) (injection des dépendances)
	 *
	 * @param service le service métier de gestion des Parks
	 */
	public ParkRestController(ParkService service) {
		this.service = service;
	}

	/**
	 * Requête REST POST de création d'une ressource {@link Park} et de sa ressource {@link Equipment} associée. Noter
	 * que si l'id du {@link Park} est fourni il sera ignoré par le service.
	 *
	 * @param park park à créer contenant une ressource {@link Equipment}
	 *
	 * @return la ressource {@link Park} créée (avec son id), un header location permettant
	 * d'accéder à cette ressource et un code 201-CREATED. Ou une erreur 500-INTERNAL_SERVER_ERROR avec un message associé.
	 */
	@PostMapping
	public ResponseEntity createpark(@RequestBody Park park) {
		Park result = service.createPark(Util.setScale(park));

		if (result == null) {
			return ResponseEntity.internalServerError()
			                     .body("Erreur lors de la création du parc.");
		} else {
			URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
			                                          .path(PATH_ID)
			                                          .buildAndExpand(result.getParkId())
			                                          .toUri();

			return ResponseEntity.created(location)
			                     .body(result);
		}
	}

	/**
	 * Requête REST PUT de mise à jour d'une ressource {@link Park} et de sa ressource {@link Equipment} associée.
	 *
	 * @param id identifiant du park a modifier
	 * @param park park à modifier contenant une ressource {@link Equipment}
	 *
	 * @return la ressource {@link Park} modifiée et un code retour 200-OK. Ou un code retour 404-NOT_FOUND
	 */
	@PutMapping(path=PATH_ID)
	public ResponseEntity<Park> updatePark(@PathVariable Long id, @RequestBody Park park) {
		park.setParkId(id);

		Park result = service.updatePark(Util.setScale(park));

		if (result == null) {
			return ResponseEntity.notFound()
			                     .build();
		} else {
			if (result.getEquipment() == null) {
				return ResponseEntity.notFound()
				                     .build();
			}
		}

		return ResponseEntity.status(HttpStatus.OK)
		                     .body(result);
	}

	/**
	 * Requête REST PUT d'association d'un {@link User} à un {@link Park} en créant un {@link Verifier}, puis de mise
	 * à jour du nombre de verifier du {@link Park}
	 *
	 * @param verifier Object contenant les informations du {@link Verifier}
	 *
	 * @return la ressource {@link Park} modifiée et un code retour 200-OK
	 */
	@PutMapping(path=PATH_VERIFIER)
	public ResponseEntity<Park> addParkVerifier(@RequestBody Verifier verifier) {
		Park result = service.addParkVerifier(verifier);

		if (result == null) return ResponseEntity.notFound()
		                                         .build();

		return ResponseEntity.accepted()
		                     .body(result);
	}

	/**
	 * Requête REST DELETE de supression d'une ressource {@link Park} et de sa ressource {@link Equipment} associée.
	 *
	 * @param id identifiant du park à supprimer
	 * @param equipmentId identifiant de l'{@link Equipment} à supprimer
	 *
	 * @return si la ressource {@link Park} et sa ressource {@link Equipment} associée sont supprimée un code retour
	 * 200-OK,
	 * sinon un code retour 404-NOT_FOUND
	 */
	@DeleteMapping(path=PATH_ID)
	public ResponseEntity<Void> deletePark(@PathVariable Long id, @RequestParam(value=PARAM_EQUIPMENT_ID) Long equipmentId) {
		if (service.deletePark(id, equipmentId)) {
			return ResponseEntity.status(HttpStatus.OK)
			                     .build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                     .build();
		}
	}

	/**
	 * Requête REST DELETE de dissociation d'un {@link User} d'un {@link Park} en supprimant un {@link Verifier}, puis
	 * de mise à jour du nombre de verifier de ce {@link Park}
	 *
	 * @param verifier Object contenant les informations du {@link Verifier}
	 *
	 * @return la ressource {@link Park} modifiée et un code retour 200-OK, sinon un code retour 404-NOT_FOUND
	 */
	@DeleteMapping(path=PATH_VERIFIER)
	public ResponseEntity<Park> removeParkVerifier(@RequestBody Verifier verifier) {
		Park result = service.removeParkVerifier(verifier);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.OK)
			                     .body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			                     .build();
		}
	}

	/**
	 * Requête REST GET de recherche de toutes les ressources {@link Park} avec leur {@link Equipment} associé
	 *
	 * @return une liste de {@link Park} avec un code retour 200-OK. Si aucun {@link Park} n'est trouvée, la liste est
	 * simplement vide
	 */
	@GetMapping
	public ResponseEntity<List<Park>> getParks() {
		return ResponseEntity.ok()
		                     .body(service.getParks());
	}

	/**
	 * Requête REST GET de recherche d'une ressource {@link Park} avec ses {@link Equipment} associés par son id
	 *
	 * @param id l'identifiant du {@link Park} à retrouver
	 *
	 * @return la ressource {@link Park} si elle existe dans la base et un code :<br>
	 * <li>200-OK si tous s'est bien passés</li>
	 * <li>404-NOT FOUND si la ressource n'est pas trouvée</li>
	 */
	@GetMapping(path=PATH_ID)
	public ResponseEntity<Park> getParkById(@PathVariable Long id) {
		Park result = service.getParkById(id);

		if (result == null) return ResponseEntity.notFound()
		                                         .build();

		return ResponseEntity.ok()
		                     .body(result);
	}

	/**
	 * Requête REST GET de recherche de toutes les ressources {@link Park} qui sont à moins de <i>{@code distance}</i> km des coordonnées [<i>{@code latitude}</i>,
	 * * <i>{@code longitude}</i>]
	 *
	 * @param latitude Première composante de la coordonnée de référence
	 * @param longitude Seconde composante de la coordonnée de référence
	 * @param distance Distance maximale qui doit séparer un {@link Park} de la coordonnée de référence
	 *
	 * @return une liste de {@link Park} avec un code retour 200-OK. Si aucun {@link Park} n'est trouvée, la liste est
	 * simplement vide
	 */
	@GetMapping(path=PATH_LOCATION_FILTER)
	public ResponseEntity<List<Park>> getParkByLocation(@RequestParam(value=PARAM_LATITUDE) Double latitude, @RequestParam(value=PARAM_LONGITUDE) Double longitude, @RequestParam(value=PARAM_DISTANCE) Double distance) {
		return ResponseEntity.ok()
		                     .body(service.getParkByLocation(latitude, longitude, distance));
	}
}
