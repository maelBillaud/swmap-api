package com.webservices.swmap.model;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modèle métier {@link Park}.
 */
public class Park {
	private Long parkId;
	private Equipment equipment;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@Size(max=50, message="Le nom du pays ne doit pas dépasser plus que 50 caractères")
	private String country;
	@Size(max=100, message="Le nom de la ville ne doit pas dépasser plus que 100 caractères")
	private String city;
	@Size(max=50, message="Le nom de code postal ne doit pas dépasser plus que 50 caractères")
	private String postcode;
	@Size(max=250, message="Le nom de rue ne doit pas dépasser plus que 250 caractères")
	private String street;
	@Size(max=10, message="Le numéro de rue ne doit pas dépasser plus que 10 caractères")
	private String houseNumber;
	private Boolean isCovered;
	private Integer verifierNumber;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String creationAgent;
	private LocalDateTime creationDate;
	@Size(max=50, message="Le nom d'agent ne doit pas dépasser plus que 50 caractères")
	private String modificationAgent;
	private LocalDateTime modificationDate;


	/**
	 * Constructeur vide pour la désérialisation
	 */
	public Park() {
	}

	/**
	 * Constructeur via le Builder
	 *
	 * @param builder builder
	 */
	private Park(Builder builder) {
		setParkId(builder.parkId);
		setEquipment(builder.equipment);
		setLatitude(builder.latitude);
		setLongitude(builder.longitude);
		setCountry(builder.country);
		setCity(builder.city);
		setPostcode(builder.postcode);
		setStreet(builder.street);
		setHouseNumber(builder.houseNumber);
		isCovered = builder.isCovered;
		setVerifierNumber(builder.verifierNumber);
		setCreationAgent(builder.creationAgent);
		setCreationDate(builder.creationDate);
		setModificationAgent(builder.modificationAgent);
		setModificationDate(builder.modificationDate);
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Boolean getIsCovered() {
		return isCovered;
	}

	public void setIsCovered(Boolean covered) {
		isCovered = covered;
	}

	public Integer getVerifierNumber() {
		return verifierNumber;
	}

	public void setVerifierNumber(Integer verifierNumber) {
		this.verifierNumber = verifierNumber;
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

	@Override
	public String toString() {
		return "Park{" + "parkId=" + parkId + ", equipment=" + equipment + ", latitude=" + latitude + ", longitude=" + longitude + ", country='" + country + '\'' + ", city='" + city + '\'' + ", postcode='" + postcode + '\'' + ", street='" + street + '\'' + ", houseNumber=" + houseNumber + ", isCovered=" + isCovered + ", verifierNumber=" + verifierNumber + ", creationAgent='" + creationAgent + '\'' + ", creationDate=" + creationDate + ", modificationAgent='" + modificationAgent + '\'' + ", modificationDate=" + modificationDate + '}';
	}

	/**
	 * {@code Park} builder static inner class.
	 */
	public static final class Builder {
		private Long parkId;
		private Equipment equipment;
		private BigDecimal latitude;
		private BigDecimal longitude;
		private String country;
		private String city;
		private String postcode;
		private String street;
		private String houseNumber;
		private Boolean isCovered;
		private Integer verifierNumber;
		private @Size(max=50) String creationAgent;
		private LocalDateTime creationDate;
		private @Size(max=50) String modificationAgent;
		private LocalDateTime modificationDate;

		public Builder() {
		}

		public Builder(Park copy) {
			this.parkId = copy.getParkId();
			this.equipment = copy.getEquipment();
			this.latitude = copy.getLatitude();
			this.longitude = copy.getLongitude();
			this.country = copy.getCountry();
			this.city = copy.getCity();
			this.postcode = copy.getPostcode();
			this.street = copy.getStreet();
			this.houseNumber = copy.getHouseNumber();
			this.isCovered = copy.getIsCovered();
			this.verifierNumber = copy.getVerifierNumber();
			this.creationAgent = copy.getCreationAgent();
			this.creationDate = copy.getCreationDate();
			this.modificationAgent = copy.getModificationAgent();
			this.modificationDate = copy.getModificationDate();
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
		 * Sets the {@code equipment} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code equipment} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder equipment(Equipment val) {
			equipment = val;
			return this;
		}

		/**
		 * Sets the {@code latitude} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code latitude} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder latitude(BigDecimal val) {
			latitude = val;
			return this;
		}

		/**
		 * Sets the {@code longitude} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code longitude} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder longitude(BigDecimal val) {
			longitude = val;
			return this;
		}

		/**
		 * Sets the {@code country} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code country} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder country(String val) {
			country = val;
			return this;
		}

		/**
		 * Sets the {@code city} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code city} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder city(String val) {
			city = val;
			return this;
		}

		/**
		 * Sets the {@code postcode} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code postcode} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder postcode(String val) {
			postcode = val;
			return this;
		}

		/**
		 * Sets the {@code street} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code street} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder street(String val) {
			street = val;
			return this;
		}

		/**
		 * Sets the {@code houseNumber} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code houseNumber} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder houseNumber(String val) {
			houseNumber = val;
			return this;
		}

		/**
		 * Sets the {@code isCovered} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code isCovered} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder isCovered(Boolean val) {
			isCovered = val;
			return this;
		}

		/**
		 * Sets the {@code verifierNumber} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code verifierNumber} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder verifierNumber(Integer val) {
			verifierNumber = val;
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
		 * Returns a {@code Park} built from the parameters previously set.
		 *
		 * @return a {@code Park} built with parameters of this {@code Park.Builder}
		 */
		public Park build() {
			return new Park(this);
		}
	}
}