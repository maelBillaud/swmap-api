package com.webservices.swmap.repository.implementation;

import com.webservices.swmap.model.Equipment;
import com.webservices.swmap.model.Park;
import com.webservices.swmap.repository.ParkRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Provider utilisant une base de donnée comme repository, ce provider manipule la base à l'aide du {@link JdbcTemplate}
 * et de requête SQL décritent dans un script SQL. La classe est annotée @{@link Transactional}(propagation =
 * Propagation.MANDATORY) pour imposer l'execution de ces méthodes dans le contexte d'une transaction déjà existante.
 */
@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class ParkRepositoryImpl implements ParkRepository {

	//************************ Attributs utilisés dans nos requêtes ************************//
	/* Attributs relatifs à la table Park **/

	/** nom de colonne et de parametre pour l'attribut park_id */
	public static final String COL_PARK_ID = "park_id";

	/** nom de colonne et de parametre pour l'attribut latitude */
	public static final String COL_LATITUDE = "latitude";

	/** nom de colonne et de parametre pour l'attribut longitude */
	public static final String COL_LONGITUDE = "longitude";

	/** nom de colonne et de parametre pour l'attribut country */
	public static final String COL_COUNTRY = "country";

	/** nom de colonne et de parametre pour l'attribut city */
	public static final String COL_CITY = "city";

	/** nom de colonne et de parametre pour l'attribut postcode */
	public static final String COL_POSTCODE = "postcode";

	/** nom de colonne et de parametre pour l'attribut street */
	public static final String COL_STREET = "street";

	/** nom de colonne et de parametre pour l'attribut house_number */
	public static final String COL_HOUSE_NUMBER = "house_number";

	/** nom de colonne et de parametre pour l'attribut is_covered */
	public static final String COL_IS_COVERED = "is_covered";

	/** nom de colonne et de parametre pour l'attribut verifierNumber */
	public static final String COL_VERIFIER_NUMBER = "verifierNumber";

	/** nom de colonne et de parametre pour l'attribut creation_agent */
	public static final String COL_CREATION_AGENT = "creation_agent";

	/** nom de colonne et de parametre pour l'attribut creation_date */
	public static final String COL_CREATION_DATE = "creation_date";

	/** nom de colonne et de parametre pour l'attribut modification_agent */
	public static final String COL_MODIFICATION_AGENT = "modification_agent";

	/** nom de colonne et de parametre pour l'attribut modification_date */
	public static final String COL_MODIFICATION_DATE = "modification_date";

	/* Attributs relatifs à la table Equipment **/

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
	private static final Logger LOGGER = LogManager.getLogger(ParkRepositoryImpl.class);
	private static final String LOG_RESULTAT = "Resultat [{}]";
	/**
	 * Le jdbc template à utiliser pour l'execution des requêtes SQL
	 */
	private final NamedParameterJdbcTemplate jdbcTemplate;
	/**
	 * Récupération de la requête MySQL de création d'un park
	 */
	@Value("${swmap.request.park.create}")
	private String createParkMySql;
	/**
	 * Récupération de la requête MySQL de modification d'un park
	 */
	@Value("${swmap.request.park.update}")
	private String updateParkMySql;
	/**
	 * Récupération de la requête MySQL de mise à jour de l'attribut verifier_number d'un Park
	 */
	@Value("${swmap.request.park.update-verifier-number}")
	private String updateVerifierNumberMySql;
	/**
	 * Récupération de la requête MySQL de suppression d'un park
	 */
	@Value("${swmap.request.park.delete}")
	private String deleteParkMySql;
	/**
	 * Récupération de la requête MySQL de récupération de la liste de tous les Parks
	 */
	@Value("${swmap.request.park.get}")
	private String getParksMySql;
	/**
	 * Récupération de la requête MySQL de récupération d'un Park selon son identifiant
	 */
	@Value("${swmap.request.park.getById}")
	private String getParksByIdMySql;

	/**
	 * Constructeur avec paramètre(s).
	 *
	 * @param jdbcTemplate le template à utiliser
	 */
	@Inject
	public ParkRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Park createPark(Park park) {
		//KeyHolder va nous permettre de garder une trace de l'id
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final LocalDateTime now = LocalDateTime.now();

		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_EQUIPMENT_ID, park.getEquipment()
		                                                                                       .getEquipmentId())
		                                                       .addValue(COL_LATITUDE, park.getLatitude())
		                                                       .addValue(COL_LONGITUDE, park.getLongitude())
		                                                       .addValue(COL_COUNTRY, park.getCountry())
		                                                       .addValue(COL_CITY, park.getCity())
		                                                       .addValue(COL_POSTCODE, park.getPostcode())
		                                                       .addValue(COL_STREET, park.getStreet())
		                                                       .addValue(COL_HOUSE_NUMBER, park.getHouseNumber())
		                                                       .addValue(COL_IS_COVERED, park.getIsCovered())
		                                                       .addValue(COL_CREATION_AGENT, park.getCreationAgent());

		String messageInfo = "Creation d'une ligne dans la table Park. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la creation d'une ligne dans la table Park. \n{}";
		int createPark;

		try {
			createPark = jdbcTemplate.update(createParkMySql, params, keyHolder, new String[] {COL_PARK_ID});
			LOGGER.info(messageInfo, createPark);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		Number key = keyHolder.getKey();

		if (key != null) {
			park.setParkId(key.longValue());
		}
		park.setCreationDate(now);
		park.setModificationAgent(park.getCreationAgent());
		park.setModificationDate(now);

		return park;
	}

	@Override
	public Park updatePark(Park park) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, park.getParkId())
		                                                       .addValue(COL_LATITUDE, park.getLatitude())
		                                                       .addValue(COL_LONGITUDE, park.getLongitude())
		                                                       .addValue(COL_COUNTRY, park.getCountry())
		                                                       .addValue(COL_CITY, park.getCity())
		                                                       .addValue(COL_POSTCODE, park.getPostcode())
		                                                       .addValue(COL_STREET, park.getStreet())
		                                                       .addValue(COL_HOUSE_NUMBER, park.getHouseNumber())
		                                                       .addValue(COL_IS_COVERED, park.getIsCovered())
		                                                       .addValue(COL_MODIFICATION_AGENT, park.getModificationAgent());

		String messageInfo = "Modification d'une ligne dans la table Park avec le park ID [" + park.getParkId() + "]. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la mise a jour du Park avec l'ID [" + park.getParkId() + "].\n{}";
		int updatePark;

		try {
			updatePark = jdbcTemplate.update(updateParkMySql, params);
			LOGGER.info(messageInfo, updatePark);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		park.setModificationDate(LocalDateTime.now());

		return park;
	}

	@Override
	public int updateVerifierNumber(Long parkId, int verifierNumber, int added, String modificationAgent) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, parkId)
		                                                       .addValue(COL_VERIFIER_NUMBER, (verifierNumber + added))
		                                                       .addValue(COL_MODIFICATION_AGENT, modificationAgent);

		String messageInfo = "Met a jour le nombre de verifiers ([" + verifierNumber + "] -> [" + (verifierNumber + added) + "]) du Park ayant pour ID [" + parkId + "]. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la mise a jour du nombre de Verifier pour le Park avec l'ID [" + parkId + "].\n{}";
		int updateVerifierNumber;

		try {
			updateVerifierNumber = jdbcTemplate.update(updateVerifierNumberMySql, params);
			LOGGER.info(messageInfo, updateVerifierNumber);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
		}

		return (verifierNumber + added);
	}

	@Override
	public boolean deletePark(Long parkId) {
		final SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, parkId);

		String messageInfo = "Suppression du park avec l'id [" + parkId + "]. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder au Park avec l'id [" + parkId + "].";

		int deletePark = jdbcTemplate.update(deleteParkMySql, params);

		if (deletePark == 1) {
			LOGGER.info(messageInfo, deletePark);
			return true;
		} else {
			LOGGER.error(messageError);
			return false;
		}
	}

	@Override
	public List<Park> getParks() {
		RowMapper<Park> rowMappergetParks = (ResultSet rs, int rowNumber) -> {
			Equipment resultEquipment = new Equipment.Builder().equipmentId(rs.getLong(COL_EQUIPMENT_ID))
			                                                   .horizontalBar(rs.getInt(COL_HORIZONTAL_BAR))
			                                                   .parallelBar(rs.getInt(COL_HORIZONTAL_BAR))
			                                                   .lowParallelBar(rs.getInt(COL_LOW_PARALLEL_BAR))
			                                                   .espalier(rs.getInt(COL_ESPALIER))
			                                                   .fixedRings(rs.getInt(COL_FIXED_RINGS))
			                                                   .monkeyBridge(rs.getInt(COL_MONKEY_BRIDGE))
			                                                   .build();
			Park resultPark = new Park.Builder().parkId(rs.getLong(COL_PARK_ID))
			                                    .latitude(rs.getObject(COL_LATITUDE, BigDecimal.class))
			                                    .longitude(rs.getObject(COL_LONGITUDE, BigDecimal.class))
			                                    .country(rs.getString(COL_COUNTRY))
			                                    .city(rs.getString(COL_CITY))
			                                    .postcode(rs.getString(COL_POSTCODE))
			                                    .street(rs.getString(COL_STREET))
			                                    .houseNumber(rs.getString(COL_HOUSE_NUMBER))
			                                    .isCovered(rs.getBoolean(COL_IS_COVERED))
			                                    .verifierNumber(rs.getInt(COL_VERIFIER_NUMBER))
			                                    .build();
			resultPark.setEquipment(resultEquipment);

			return resultPark;
		};

		List<Park> result = jdbcTemplate.query(getParksMySql, rowMappergetParks);
		String message = "Recuperation de tous les Parks avec leurs Equipments associes. [{}] Resultats";
		LOGGER.info(message, result.size());
		return result;
	}

	@Override
	public Park getParkById(Long parkId) {
		final SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, parkId);

		RowMapper<Park> rowMapperGetParkById = (ResultSet rs, int rowNumber) -> {
			Equipment resultEquipment = new Equipment.Builder().equipmentId(rs.getLong(COL_EQUIPMENT_ID))
			                                                   .horizontalBar(rs.getInt(COL_HORIZONTAL_BAR))
			                                                   .parallelBar(rs.getInt(COL_HORIZONTAL_BAR))
			                                                   .lowParallelBar(rs.getInt(COL_LOW_PARALLEL_BAR))
			                                                   .espalier(rs.getInt(COL_ESPALIER))
			                                                   .fixedRings(rs.getInt(COL_FIXED_RINGS))
			                                                   .monkeyBridge(rs.getInt(COL_MONKEY_BRIDGE))
			                                                   .build();
			Park resultPark = new Park.Builder().parkId(rs.getLong(COL_PARK_ID))
			                                    .latitude(rs.getObject(COL_LATITUDE, BigDecimal.class))
			                                    .longitude(rs.getObject(COL_LONGITUDE, BigDecimal.class))
			                                    .country(rs.getString(COL_COUNTRY))
			                                    .city(rs.getString(COL_CITY))
			                                    .postcode(rs.getString(COL_POSTCODE))
			                                    .street(rs.getString(COL_STREET))
			                                    .houseNumber(rs.getString(COL_HOUSE_NUMBER))
			                                    .isCovered(rs.getBoolean(COL_IS_COVERED))
			                                    .verifierNumber(rs.getInt(COL_VERIFIER_NUMBER))
			                                    .build();
			resultPark.setEquipment(resultEquipment);

			return resultPark;
		};

		Park result = null;
		String messageInfo = "Recuperation du Park ayant pour parkId [" + parkId + "] avec ses Equipments associes. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder au Park avec l'id [" + parkId + "].";

		try {
			result = jdbcTemplate.queryForObject(getParksByIdMySql, params, rowMapperGetParkById);
			LOGGER.info(messageInfo, result);
			return result;
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(messageError);
			return null;
		}
	}
}
