package com.webservices.swmap.model;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Modèle métier {@link Verifier}.
 */
public class Verifier {
	private Long verifierId;
	private Long parkId;
	private Long userId;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String creationAgent;
	private LocalDateTime creationDate;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String modificationAgent;
	private LocalDateTime modificationDate;

	/**
	 * Constructeur vide pour la désérialisation
	 */
	public Verifier() {
	}

	/**
	 * Constructeur via le Builder
	 *
	 * @param builder builder
	 */
	private Verifier(Builder builder) {
		setVerifierId(builder.verifierId);
		setParkId(builder.parkId);
		setUserId(builder.userId);
		setCreationAgent(builder.creationAgent);
		setCreationDate(builder.creationDate);
		setModificationAgent(builder.modificationAgent);
		setModificationDate(builder.modificationDate);
	}

	public Long getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(Long verifierId) {
		this.verifierId = verifierId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	/**
	 * {@code Verifier} builder static inner class.
	 */
	public static final class Builder {
		private Long verifierId;
		private Long parkId;
		private Long userId;
		private @Size(max=50) String creationAgent;
		private LocalDateTime creationDate;
		private @Size(max=50) String modificationAgent;
		private LocalDateTime modificationDate;

		public Builder() {
		}

		public Builder(Verifier copy) {
			this.verifierId = copy.getVerifierId();
			this.parkId = copy.getParkId();
			this.userId = copy.getUserId();
			this.creationAgent = copy.getCreationAgent();
			this.creationDate = copy.getCreationDate();
			this.modificationAgent = copy.getModificationAgent();
			this.modificationDate = copy.getModificationDate();
		}

		/**
		 * Sets the {@code verifierId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code verifierId} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder verifierId(Long val) {
			verifierId = val;
			return this;
		}

		/**
		 * Sets the {@code parkId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code parkId} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder parkId(Long val) {
			parkId = val;
			return this;
		}

		/**
		 * Sets the {@code userId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code userId} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder userId(Long val) {
			userId = val;
			return this;
		}

		/**
		 * Sets the {@code creationAgent} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code creationAgent} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder creationAgent(@Size(max=50) String val) {
			creationAgent = val;
			return this;
		}

		/**
		 * Sets the {@code creationDate} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code creationDate} to set
		 *
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
		 *
		 * @return a reference to this Builder
		 */
		public Builder modificationAgent(@Size(max=50) String val) {
			modificationAgent = val;
			return this;
		}

		/**
		 * Sets the {@code modificationDate} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code modificationDate} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder modificationDate(LocalDateTime val) {
			modificationDate = val;
			return this;
		}

		/**
		 * Returns a {@code Verifier} built from the parameters previously set.
		 *
		 * @return a {@code Verifier} built with parameters of this {@code Verifier.Builder}
		 */
		public Verifier build() {
			return new Verifier(this);
		}
	}
}