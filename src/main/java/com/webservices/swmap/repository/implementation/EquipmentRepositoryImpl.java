package com.webservices.swmap.repository.implementation;

import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.repository.EquipmentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Provider utilisant une base de donnée comme repository, ce provider manipule la base à l'aide du {@link JdbcTemplate}
 * et de requête SQL décritent dans un script SQL. La classe est annotée @{@link Transactional}(propagation =
 * Propagation.MANDATORY) pour imposer l'execution de ces méthodes dans le contexte d'une transaction déjà existante.
 */
@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class EquipmentRepositoryImpl implements EquipmentRepository {

	//************************ Attributs utilisés dans nos requêtes ************************//

	/** Attributs relatifs à la table Equipment **/

	/** nom de colonne et de parametre pour l'attribut equipment_id */
	public static final String COL_EQUIPMENT_ID = "equipment_id";

	/** nom de colonne et de parametre pour l'attribut horizontal_bar */
	public static final String COL_HORIZONTAL_BAR = "horizontal_bar";

	/** nom de colonne et de parametre pour l'attribut parallel_bar */
	public static final String COL_PARALLEL_BAR = "parallel_bar";

	/** nom de colonne et de parametre pour l'attribut low_parallel_bar */
	public static final String COL_LOW_PARALLEL_BAR = "low_parallel_bar";

	/** nom de colonne et de parametre pour l'attribut espalier */
	public static final String COL_ESPALIER = "espalier";

	/** nom de colonne et de parametre pour l'attribut fixed_rings */
	public static final String COL_FIXED_RINGS = "fixed_rings";

	/** nom de colonne et de parametre pour l'attribut monkey_bridge */
	public static final String COL_MONKEY_BRIDGE = "monkey_bridge";

	//************************ Requêtes MySql ************************//
	/** Logger de la classe */
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String LOG_RESULTAT = "Resultat [{}]";
	/**
	 * Le jdbc template à utiliser pour l'execution des requêtes SQL
	 */
	private final NamedParameterJdbcTemplate jdbcTemplate;
	/**
	 * Récupération de la requête MySQL de création d'un equipment
	 */
	@Value("${swmap.request.equipment.create}")
	private String createEquipmentMySql;
	/**
	 * Récupération de la requête MySQL de modification d'un equipment
	 */
	@Value("${swmap.request.equipment.update}")
	private String updateEquipmentMySql;
	/**
	 * Récupération de la requête MySQL de suppression d'un equipment
	 */
	@Value("${swmap.request.equipment.delete}")
	private String deleteEquipmentMySql;

	/**
	 * Constructeur avec paramètre(s).
	 *
	 * @param jdbcTemplate le template à utiliser
	 */
	@Inject
	public EquipmentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Equipment createEquipment(Equipment equipment) {
		//KeyHolder va nous permettre de garder une trace de l'id
		final KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_HORIZONTAL_BAR, equipment.getHorizontalBar())
		                                                       .addValue(COL_PARALLEL_BAR, equipment.getParallelBar())
		                                                       .addValue(COL_LOW_PARALLEL_BAR, equipment.getLowParallelBar())
		                                                       .addValue(COL_ESPALIER, equipment.getEspalier())
		                                                       .addValue(COL_FIXED_RINGS, equipment.getFixedRings())
		                                                       .addValue(COL_MONKEY_BRIDGE, equipment.getMonkeyBridge());

		String messageInfo = "Creation d'une ligne dans la table Equipment. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la creation d'une ligne dans la table Equipment. \n{}";
		int createEquipment;

		try {
			createEquipment = jdbcTemplate.update(createEquipmentMySql, params, keyHolder, new String[] {COL_EQUIPMENT_ID});
			LOGGER.info(messageInfo, createEquipment);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		Number key = keyHolder.getKey();

		if (key != null) {
			equipment.setEquipmentId(key.longValue());
		}

		return equipment;
	}

	@Override
	public Equipment updateEquipment(Equipment equipment) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_EQUIPMENT_ID, equipment.getEquipmentId())
		                                                       .addValue(COL_HORIZONTAL_BAR, equipment.getHorizontalBar())
		                                                       .addValue(COL_PARALLEL_BAR, equipment.getParallelBar())
		                                                       .addValue(COL_LOW_PARALLEL_BAR, equipment.getLowParallelBar())
		                                                       .addValue(COL_ESPALIER, equipment.getEspalier())
		                                                       .addValue(COL_FIXED_RINGS, equipment.getFixedRings())
		                                                       .addValue(COL_MONKEY_BRIDGE, equipment.getMonkeyBridge());

		String messageInfo = "Modification d'une ligne dans la table Equipment avec l'equipment ID [" + equipment.getEquipmentId() + "] récupéré précédemment. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la mise a jour de l'Equipment avec l'ID [" + equipment.getEquipmentId() + "].\n{}";
		int updateEquipment;

		try {
			updateEquipment = jdbcTemplate.update(updateEquipmentMySql, params);
			LOGGER.info(messageInfo, updateEquipment);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		return equipment;
	}

	@Override
	public boolean deleteEquipment(Long equipmentId) {
		final SqlParameterSource params = new MapSqlParameterSource().addValue(COL_EQUIPMENT_ID, equipmentId);

		String messageInfo = "Suppression de l'equipment avec l'id [" + equipmentId + "]. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder a l'Equipment avec l'id [" + equipmentId + "].";

		int deleteEquipment = jdbcTemplate.update(deleteEquipmentMySql, params);

		if (deleteEquipment == 1) {
			LOGGER.info(messageInfo, deleteEquipment);
			return true;
		} else {
			LOGGER.error(messageError);
			return false;
		}
	}
}
