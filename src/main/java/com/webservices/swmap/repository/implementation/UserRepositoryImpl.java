package com.webservices.swmap.repository.implementation;

import com.webservices.swmap.enums.Level;
import com.webservices.swmap.enums.Role;
import com.webservices.swmap.model.User;
import com.webservices.swmap.repository.UserRepository;
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
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Provider utilisant une base de donnée comme repository, ce provider manipule la base à l'aide du {@link JdbcTemplate}
 * et de requête SQL décrite dans un script SQL. La classe est annotée @{@link Transactional}(propagation =
 * Propagation.MANDATORY) pour imposer l'exécution de ces méthodes dans le contexte d'une transaction déjà existante.
 */
@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class UserRepositoryImpl implements UserRepository {

	//************************ Attributs utilisés dans nos requêtes ************************//
	/* Attributs relatifs à la table User */

	/** nom de colonne et de paramètre pour l'attribut user_id */
	public static final String COL_USER_ID = "user_id";

	/** nom de colonne et de paramètre pour l'attribut name */
	public static final String COL_NAME = "name";

	/** nom de colonne et de paramètre pour l'attribut username */
	public static final String COL_USERNAME = "username";

	/** nom de colonne et de paramètre pour l'attribut birth_date */
	public static final String COL_BIRTH_DATE = "birth_date";

	/** nom de colonne et de paramètre pour l'attribut level */
	public static final String COL_LEVEL = "level";

	/** nom de colonne et de paramètre pour l'attribut mail */
	public static final String COL_MAIL = "mail";

	/** nom de colonne et de paramètre pour l'attribut password */
	public static final String COL_PASSWORD = "password";

	/** nom de colonne et de paramètre pour l'attribut creation_agent */
	public static final String COL_CREATION_AGENT = "creation_agent";

	/** nom de colonne et de paramètre pour l'attribut creation_date */
	public static final String COL_CREATION_DATE = "creation_date";

	/** nom de colonne et de paramètre pour l'attribut modification_agent */
	public static final String COL_MODIFICATION_AGENT = "modification_agent";

	/** nom de colonne et de paramètre pour l'attribut modification_date */
	public static final String COL_MODIFICATION_DATE = "modification_date";

	/** nom de colonne et de paramètre pour l'attribut role */
	public static final String COL_ROLE = "role";

	//************************ Requêtes MySql ************************//
	/** Logger de la classe */
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String LOG_RESULTAT = "Resultat [{}]";
	/**
	 * Le jdbc modèle à utiliser pour l'exécution des requêtes SQL
	 */
	private final NamedParameterJdbcTemplate jdbcTemplate;
	/**
	 * Récupération de la requête MySQL de création d'un utilisateur.
	 */
	@Value("${swmap.request.user.create}")
	private String createUserMySql;
	/**
	 * Récupération de la requête MySQL de modification d'un utilisateur.
	 */
	@Value("${swmap.request.user.update}")
	private String updateUserMySql;
	/**
	 * Récupération de la requête MySQL de sélection d'un utilisateur.
	 */
	@Value("${swmap.request.user.getUserById}")
	private String getUserByIdMySql;
	/**
	 * Récupération de la requête MySQL de suppression d'un utilisateur.
	 */
	@Value("${swmap.request.user.delete}")
	private String deleteUserMySql;

	/**
	 * Constructeur avec paramètre(s).
	 *
	 * @param jdbcTemplate le modèle à utiliser
	 */
	@Inject
	public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User createUser(User user) {
		// Nous permet de garder une trace de l'id
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final LocalDateTime now = LocalDateTime.now();

		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(COL_NAME, user.getName())
				.addValue(COL_USERNAME, user.getUsername())
				.addValue(COL_BIRTH_DATE, user.getBirthDate())
				.addValue(COL_MAIL, user.getMail())
				.addValue(COL_LEVEL, user.getLevel())
				.addValue(COL_PASSWORD, user.getPassword())
				.addValue(COL_CREATION_AGENT, user.getCreationAgent())
				.addValue(COL_ROLE, user.getRole());

		String messageInfo = "Creation d'une ligne dans la table User. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la creation d'une ligne dans la table User. \n{}";
		int createUser;

		try {
			createUser = jdbcTemplate.update(createUserMySql, params, keyHolder, new String[] {COL_USER_ID});
			LOGGER.info(messageInfo, createUser);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		Number key = keyHolder.getKey();

		if (key != null) {
			user.setUserId(key.longValue());
		}
		user.setCreationDate(now);
		user.setModificationAgent(user.getCreationAgent());
		user.setModificationDate(now);

		return user;
	}

	@Override
	public User updateUser(User user) {
		final LocalDateTime now = LocalDateTime.now();

		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(COL_USER_ID, user.getUserId())
				.addValue(COL_NAME, user.getName())
				.addValue(COL_USERNAME, user.getUsername())
				.addValue(COL_BIRTH_DATE, user.getBirthDate())
				.addValue(COL_LEVEL, user.getLevel())
				.addValue(COL_MAIL, user.getMail())
				.addValue(COL_PASSWORD, user.getPassword())
				.addValue(COL_MODIFICATION_AGENT, user.getModificationAgent())
				.addValue(COL_ROLE, user.getRole());

		String messageInfo = "Modification d'une ligne dans la table User avec le user ID [" + user.getUserId() + "]. " + LOG_RESULTAT;
		String messageError = "Erreur lors de la mise a jour du User avec l'ID [" + user.getUserId() + "].\n{}";
		int updateUser;

		try {
			updateUser = jdbcTemplate.update(updateUserMySql, params);
			LOGGER.info(messageInfo, updateUser);
		} catch (DataAccessException e) {
			LOGGER.error(messageError, e.toString());
			return null;
		}

		user.setModificationDate(now);

		return user;
	}

	@Override
	public User getUserById(Long userId) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_USER_ID, userId);

		RowMapper<User> userRowMapper = (ResultSet rs, int rowNumber) -> new User.Builder()
				.userId(rs.getLong(COL_USER_ID))
				.name(rs.getString(COL_NAME))
				.username(rs.getString(COL_USERNAME))
				.birthDate(rs.getObject(COL_BIRTH_DATE, LocalDate.class))
				.level(Level.valueOf(rs.getString(COL_LEVEL)))
				.mail(rs.getString(COL_MAIL))
				.password(rs.getString(COL_PASSWORD))
				.role(Role.valueOf(rs.getString(COL_ROLE)))
				.build();

		User user = null;
		String messageInfo = "Recuperation de l'utilisateur ayant pour userId [" + userId + "]. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder a l'utilisateur avec l'id [" + userId + "].";

		try {
			user = jdbcTemplate.queryForObject(getUserByIdMySql, params, userRowMapper);
			LOGGER.info(messageInfo, user);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error(messageError);
		}

		return user;
	}

	@Override
	public boolean deleteUser(Long userId) {
		SqlParameterSource params = new MapSqlParameterSource().addValue(COL_USER_ID, userId);

		String messageInfo = "Suppression de l'utilisateur avec l'id [" + userId + "]. " + LOG_RESULTAT;
		String messageError = "Impossible d'acceder au User avec l'id [" + userId + "].";

		int deleteUser = jdbcTemplate.update(deleteUserMySql, params);

		if (deleteUser == 1) {
			LOGGER.info(messageInfo, deleteUser);
			return true;
		} else {
			LOGGER.error(messageError);
			return false;
		}
	}
}
