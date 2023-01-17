package com.webservices.swmap.repository.implementation;

import com.webservices.swmap.model.Verifier;
import com.webservices.swmap.repository.VerifierRepository;
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
import java.time.LocalDateTime;

/**
 * Provider utilisant une base de donnée comme repository, ce provider manipule la base à l'aide du {@link JdbcTemplate}
 * et de requête SQL décritent dans un script SQL. La classe est annotée @{@link Transactional}(propagation =
 * Propagation.MANDATORY) pour imposer l'execution de ces méthodes dans le contexte d'une transaction déjà existante.
 */
@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class VerifierRepositoryImpl implements VerifierRepository {

	//************************ Attributs utilisés dans nos requêtes ************************//

	/* Attributs relatifs à la table Verifier **/
	/** nom de colonne et de parametre pour l'attribut verifier_id */
	public static final String COL_VERIFIER_ID = "verifier_id";

	/** nom de colonne et de parametre pour l'attribut park_id (clé étrangère de la table Park) */
	public static final String COL_PARK_ID = "park_id";

	/** nom de colonne et de parametre pour l'attribut equipment_id (clé étrangère de la table User) */
	public static final String COL_USER_ID = "user_id";

	/** nom de colonne et de parametre pour l'attribut creation_agent */
	public static final String COL_CREATION_AGENT = "creation_agent";

	/** nom de colonne et de parametre pour l'attribut creation_date */
	public static final String COL_CREATION_DATE = "creation_date";

	//************************ Requêtes MySql ************************//
	/** Logger de la classe */
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String LOG_RESULTAT = "Resultat [{}]";
	/**
	 * Le jdbc template à utiliser pour l'execution des requêtes SQL
	 */
	private final NamedParameterJdbcTemplate jdbcTemplate;
	/**
	 * Récupération de la requête MySQL de création d'un Verifier
	 */
	@Value("${swmap.request.verifier.create}")
	private String createVerifierMySql;
	/**
	 * Récupération de la requête MySQL de suppression d'un Verifier
	 */
	@Value("${swmap.request.verifier.delete}")
	private String deleteVerifierMySql;

	/**
	 * Constructeur avec paramètre(s).
	 *
	 * @param jdbcTemplate le template à utiliser
	 */
	@Inject
	public VerifierRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Verifier createVerifier(Verifier verifier) {
		//KeyHolder va nous permettre de garder une trace de l'id
		final KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, verifier.getParkId())
		                                                       .addValue(COL_USER_ID, verifier.getUserId())
		                                                       .addValue(COL_CREATION_AGENT, verifier.getCreationAgent());

		String messageInfo = "Creation d'une ligne dans la table Verifier. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la creation d'une ligne dans la table Verifier. \n{}";
		int createVerifier;

		try {
			createVerifier = jdbcTemplate.update(createVerifierMySql, params, keyHolder, new String[] {COL_VERIFIER_ID});
			LOGGER.info(messageInfo, createVerifier);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		Number key = keyHolder.getKey();

		if (key != null) {
			verifier.setVerifierId(key.longValue());
		}
		verifier.setCreationDate(LocalDateTime.now());

		return verifier;
	}

	@Override
	public boolean deleteVerifier(Verifier verifier) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_PARK_ID, verifier.getParkId())
		                                                       .addValue(COL_USER_ID, verifier.getUserId());

		String messageInfo = "Suppression du verifier avec la cle park_id[" + verifier.getParkId() + "] et user_id[" + verifier.getUserId() + "]. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder au verifier avec la clé park_id[" + verifier.getParkId() + "] et user_id[" + verifier.getUserId() + "].";

		int deleteVerifier = jdbcTemplate.update(deleteVerifierMySql, params);

		if (deleteVerifier == 1) {
			LOGGER.info(messageInfo, deleteVerifier);
			return true;
		} else {
			LOGGER.error(messageError);
			return false;
		}
	}
}
