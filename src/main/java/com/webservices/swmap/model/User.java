package com.webservices.swmap.model;

import com.webservices.swmap.enums.Level;
import com.webservices.swmap.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modèle métier {@link User}
 */
public class User {
	private Long userId;
	@Size(max=100, message="Le nom ne doit pas dépasser plus que 100 caractères")
	private String name;
	@Size(max=50, message="Le nom d'utilisateur ne doit pas dépasser plus que 50 caractères")
	private String username;
	@Past(message="Veuillez ajouter une date de naissance valide")
	private LocalDate birthDate;
	private Level level;
	@Size(max=100, message="Le mail ne doit pas dépasser plus que 100 caractères")
	@Email(message="Veuillez ajouter un mail valide", regexp=".+[@].+[\\.].+")
	@NotNull
	private String mail;
	private String password;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String creationAgent;
	private LocalDateTime creationDate;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String modificationAgent;
	private LocalDateTime modificationDate;
	private Role role;

	/**
	 * Constructeur vide pour la désérialisation
	 */
	public User() {
	}

	private User(Builder builder) {
		setUserId(builder.userId);
		setName(builder.name);
		setUsername(builder.username);
		setBirthDate(builder.birthDate);
		setLevel(builder.level);
		setMail(builder.mail);
		setPassword(builder.password);
		setCreationAgent(builder.creationAgent);
		setCreationDate(builder.creationDate);
		setModificationAgent(builder.modificationAgent);
		setModificationDate(builder.modificationDate);
		setRole(builder.role);
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreationAgent() {
		return creationAgent;
	}

	public void setCreationAgent(String creationAgent) {
		this.creationAgent = creationAgent;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationAgent() {
		return modificationAgent;
	}

	public void setModificationAgent(String modificationAgent) {
		this.modificationAgent = modificationAgent;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", birthDate=" + birthDate +
				", level=" + level +
				", mail='" + mail + '\'' +
				", password='" + password + '\'' +
				", creationAgent='" + creationAgent + '\'' +
				", creationDate=" + creationDate +
				", modificationAgent='" + modificationAgent + '\'' +
				", modificationDate=" + modificationDate +
				", role=" + role +
				'}';
	}


	/**
	 * {@code User} builder static inner class.
	 */
	public static final class Builder {
		private Long userId;
		private @Size(max = 100, message = "Le nom ne doit pas dépasser plus que 100 caractères") String name;
		private @Size(max = 50, message = "Le nom d'utilisateur ne doit pas dépasser plus que 50 caractères") String username;
		private @Past(message = "Veuillez ajouter une date de naissance valide") LocalDate birthDate;
		private Level level;
		private @Size(max = 100, message = "Le mail ne doit pas dépasser plus que 100 caractères") @Email(message = "Veuillez ajouter un mail valide", regexp = ".+[@].+[\\.].+") @NotNull String mail;
		private String password;
		private @Size(max = 50, message = "Le nom d'agent ne doit pas dépasser plus que 50 caractères") String creationAgent;
		private LocalDateTime creationDate;
		private @Size(max = 50, message = "Le nom d'agent ne doit pas dépasser plus que 50 caractères") String modificationAgent;
		private LocalDateTime modificationDate;
		private Role role;

		public Builder() {
		}

		public Builder(User copy) {
			this.userId = copy.getUserId();
			this.name = copy.getName();
			this.username = copy.getUsername();
			this.birthDate = copy.getBirthDate();
			this.level = copy.getLevel();
			this.mail = copy.getMail();
			this.password = copy.getPassword();
			this.creationAgent = copy.getCreationAgent();
			this.creationDate = copy.getCreationDate();
			this.modificationAgent = copy.getModificationAgent();
			this.modificationDate = copy.getModificationDate();
			this.role = copy.getRole();
		}

		/**
		 * Sets the {@code userId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code userId} to set
		 * @return a reference to this Builder
		 */
		public Builder userId(Long val) {
			userId = val;
			return this;
		}

		/**
		 * Sets the {@code name} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code name} to set
		 * @return a reference to this Builder
		 */
		public Builder name(@Size(max = 100, message = "Le nom ne doit pas dépasser plus que 100 caractères") String val) {
			name = val;
			return this;
		}

		/**
		 * Sets the {@code username} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code username} to set
		 * @return a reference to this Builder
		 */
		public Builder username(@Size(max = 50, message = "Le nom d'utilisateur ne doit pas dépasser plus que 50 caractères") String val) {
			username = val;
			return this;
		}

		/**
		 * Sets the {@code birthDate} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code birthDate} to set
		 * @return a reference to this Builder
		 */
		public Builder birthDate(@Past(message = "Veuillez ajouter une date de naissance valide") LocalDate val) {
			birthDate = val;
			return this;
		}

		/**
		 * Sets the {@code level} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code level} to set
		 * @return a reference to this Builder
		 */
		public Builder level(Level val) {
			level = val;
			return this;
		}

		/**
		 * Sets the {@code mail} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code mail} to set
		 * @return a reference to this Builder
		 */
		public Builder mail(@Size(max = 100, message = "Le mail ne doit pas dépasser plus que 100 caractères") @Email(message = "Veuillez ajouter un mail valide", regexp = ".+[@].+[\\.].+") @NotNull String val) {
			mail = val;
			return this;
		}

		/**
		 * Sets the {@code password} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code password} to set
		 * @return a reference to this Builder
		 */
		public Builder password(String val) {
			password = val;
			return this;
		}

		/**
		 * Sets the {@code creationAgent} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code creationAgent} to set
		 * @return a reference to this Builder
		 */
		public Builder creationAgent(@Size(max = 50, message = "Le nom d'agent ne doit pas dépasser plus que 50 caractères") String val) {
			creationAgent = val;
			return this;
		}

		/**
		 * Sets the {@code creationDate} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code creationDate} to set
		 * @return a reference to this Builder
		 */
		public Builder creationDate(LocalDateTime val) {
			creationDate = val;
			return this;
		}

		/**
		 * Sets the {@code modificationAgent} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code modificationAgent} to set
		 * @return a reference to this Builder
		 */
		public Builder modificationAgent(@Size(max = 50, message = "Le nom d'agent ne doit pas dépasser plus que 50 caractères") String val) {
			modificationAgent = val;
			return this;
		}

		/**
		 * Sets the {@code modificationDate} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code modificationDate} to set
		 * @return a reference to this Builder
		 */
		public Builder modificationDate(LocalDateTime val) {
			modificationDate = val;
			return this;
		}

		/**
		 * Sets the {@code role} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code role} to set
		 * @return a reference to this Builder
		 */
		public Builder role(Role val) {
			role = val;
			return this;
		}

		/**
		 * Returns a {@code User} built from the parameters previously set.
		 *
		 * @return a {@code User} built with parameters of this {@code User.Builder}
		 */
		public User build() {
			return new User(this);
		}
	}
}
