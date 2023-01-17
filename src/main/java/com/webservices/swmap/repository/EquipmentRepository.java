package com.webservices.swmap.repository;

import com.webservices.swmap.model.Equipment;

/**
 * Interface d'accès au Repository de {@link Equipment}.
 */
public interface EquipmentRepository {

	/**
	 * Crée un {@link Equipment}
	 *
	 * @param equipment equipment à créer
	 *
	 * @return la ligne créée si la création s'est correctement déroulée, sinon une exception.
	 */
	Equipment createEquipment(Equipment equipment);

	/**
	 * Modifie un {@link Equipment}
	 *
	 * @param equipment equipment à modifier
	 *
	 * @return la ligne modifiée si la modification s'est correctement déroulée, sinon une exception.
	 */
	Equipment updateEquipment(Equipment equipment);

	/**
	 * Supprime un {@link Equipment}
	 *
	 * @param equipmentId identifiant de l'{@link Equipment} à supprimer
	 *
	 * @return true si l'{@link Equipment} a correctement été supprimé, false sinon
	 */
	boolean deleteEquipment(Long equipmentId);
}
