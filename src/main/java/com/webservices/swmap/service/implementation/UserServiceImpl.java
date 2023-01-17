package com.webservices.swmap.service.implementation;

import com.webservices.swmap.enums.Level;
import com.webservices.swmap.model.User;
import com.webservices.swmap.repository.UserRepository;
import com.webservices.swmap.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	/**
	 * Constructeur avec paramètre (injection des dépendances).
	 *
	 * @param repository Le repository de persistence des {@link User}
	 */
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User createUser(User user) {
		// On définit l'attribut level à BEGINNER pour retourner une valeur cohérente
		// (voir README pour plus de détails)
		user.setLevel(Level.BEGINNER);
		return repository.createUser(user);
	}

	@Override
	public User updateUser(User user) {
		return repository.updateUser(user);
	}

	@Override
	public User getUserById(Long userId) {
		return repository.getUserById(userId);
	}

	@Override
	public boolean deleteUser(Long userId) {
		return repository.deleteUser(userId);
	}
}
