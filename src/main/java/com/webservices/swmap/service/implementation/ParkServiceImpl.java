package com.webservices.swmap.service.implementation;

import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.model.Park;
import com.webservices.swmap.model.Verifier;
import com.webservices.swmap.repository.EquipmentRepository;
import com.webservices.swmap.repository.ParkRepository;
import com.webservices.swmap.repository.VerifierRepository;
import com.webservices.swmap.service.ParkService;
import com.webservices.swmap.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implémentation de l'interface {@link Park}. Partie métier de l'application, c'est ici que vont se retrouver les traitements sur les données.
 */
@Service
@Transactional
public class ParkServiceImpl implements ParkService {

	private final ParkRepository repository;
	private final EquipmentRepository equipmentRepository;

	private final VerifierRepository verifierRepository;

	/**
	 * Constructeur avec paramètre(s) (injection des dépendances)
	 *
	 * @param repository Le repository de persistence des {@link Park}
	 * @param equipmentRepository Le repository de persistence des {@link Equipment}
	 * @param verifierRepository Le repository de persistence des {@link Verifier}
	 */
	@Inject
	public ParkServiceImpl(ParkRepository repository, EquipmentRepository equipmentRepository, VerifierRepository verifierRepository) {
		this.repository = repository;
		this.equipmentRepository = equipmentRepository;
		this.verifierRepository = verifierRepository;
	}

	@Override
	public Park createPark(Park park) {
		Equipment equipment = equipmentRepository.createEquipment(park.getEquipment());
		if (equipment == null) return null;

		park.setEquipment(equipment);
		Park result = repository.createPark(park);
		//Si la création d'un Equipment réussi mais que celle d'un Park échoue, on risque d'avoir en base un
		// equipment_id lié à aucun Park.
		if (result == null) equipmentRepository.deleteEquipment(equipment.getEquipmentId());
		return result;
	}

	@Override
	public Park updatePark(Park park) {
		equipmentRepository.updateEquipment(park.getEquipment());
		return repository.updatePark(park);
	}

	@Override
	public Park addParkVerifier(Verifier verifier) {
		//Récupération du park pour avoir accès à son nombre de verifier actuel et s'assurer de l'existence du parkId
		Park park = repository.getParkById(verifier.getParkId());

		if (park == null) return null;

		//Ajout du verifier au park
		Verifier createdVerifier = verifierRepository.createVerifier(verifier);

		if (createdVerifier == null) return null;

		//Mise à jour du nombre de verifier du park
		park.setVerifierNumber(repository.updateVerifierNumber(verifier.getParkId(), park.getVerifierNumber(), 1, verifier.getCreationAgent()));

		return park;
	}

	@Override
	public boolean deletePark(Long parkId, Long equipmentId) {
		return (repository.deletePark(parkId) && equipmentRepository.deleteEquipment(equipmentId));
	}

	@Override
	public Park removeParkVerifier(Verifier verifier) {
		//Récupération du park pour avoir accès à son nombre de verifier actuel et s'assurer de l'existence du parkId
		Park park = repository.getParkById(verifier.getParkId());

		if (park == null) return null;

		//Suppression du Verifier
		if (! verifierRepository.deleteVerifier(verifier)) return null;

		//Mise à jour du nombre de verifier du park
		park.setVerifierNumber(repository.updateVerifierNumber(verifier.getParkId(), park.getVerifierNumber(), - 1, verifier.getModificationAgent()));

		return park;
	}

	@Override
	public List<Park> getParks() {
		return repository.getParks();
	}

	@Override
	public Park getParkById(Long parkId) {
		return repository.getParkById(parkId);
	}

	@Override
	public List<Park> getParkByLocation(Double latitude, Double longitude, Double distance) {
		List<Park> result = repository.getParks();
		long roundedDistance = Math.round(distance);

		//Variable qui va nous servir d'index
		int i = 0;
		for (int j = 0; j < result.size(); j++) {
			if (Util.distanceBetweenCoordinates(latitude, longitude, result.get(i)
			                                                               .getLatitude()
			                                                               .doubleValue(), result.get(i)
			                                                                                     .getLongitude()
			                                                                                     .doubleValue()) > roundedDistance) {
				result.remove(i);
				//Lorsqu'on supprime une ligne du tableau, ses index sont remis à jour. Comme on a une ligne de
				// moins, il faut que notre index soit, lui aussi, mis à jour.
				i--;
			}
			i++;
		}

		return result;
	}
}
