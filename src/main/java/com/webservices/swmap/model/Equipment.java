package com.webservices.swmap.model;

/**
 * Modèle métier {@link Equipment}.
 */
public class Equipment {
	private Long equipmentId;
	private int horizontalBar;
	private int parallelBar;
	private int lowParallelBar;
	private int espalier;
	private int fixedRings;
	private int monkeyBridge;

	/**
	 * Constructeur vide pour la désérialisation
	 */
	public Equipment() {
	}

	/**
	 * Constructeur via le Builder
	 *
	 * @param builder builder
	 */
	private Equipment(Builder builder) {
		setEquipmentId(builder.equipmentId);
		setHorizontalBar(builder.horizontalBar);
		setParallelBar(builder.parallelBar);
		setLowParallelBar(builder.lowParallelBar);
		setEspalier(builder.espalier);
		setFixedRings(builder.fixedRings);
		setMonkeyBridge(builder.monkeyBridge);
	}


	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getHorizontalBar() {
		return horizontalBar;
	}

	public void setHorizontalBar(int horizontalBar) {
		this.horizontalBar = horizontalBar;
	}

	public int getParallelBar() {
		return parallelBar;
	}

	public void setParallelBar(int parallelBar) {
		this.parallelBar = parallelBar;
	}

	public int getLowParallelBar() {
		return lowParallelBar;
	}

	public void setLowParallelBar(int lowParallelBar) {
		this.lowParallelBar = lowParallelBar;
	}

	public int getEspalier() {
		return espalier;
	}

	public void setEspalier(int espalier) {
		this.espalier = espalier;
	}

	public int getFixedRings() {
		return fixedRings;
	}

	public void setFixedRings(int fixedRings) {
		this.fixedRings = fixedRings;
	}

	public int getMonkeyBridge() {
		return monkeyBridge;
	}

	public void setMonkeyBridge(int monkeyBridge) {
		this.monkeyBridge = monkeyBridge;
	}

	@Override
	public String toString() {
		return "Equipment{" + "equipmentId=" + equipmentId + ", horizontalBar=" + horizontalBar + ", parallelBar=" + parallelBar + ", lowParallelBar=" + lowParallelBar + ", espalier=" + espalier + ", fixedRings=" + fixedRings + ", monkeyBridge=" + monkeyBridge + '}';
	}

	/**
	 * {@code Equipment} builder static inner class.
	 */
	public static final class Builder {
		private Long equipmentId;
		private int horizontalBar;
		private int parallelBar;
		private int lowParallelBar;
		private int espalier;
		private int fixedRings;
		private int monkeyBridge;

		public Builder() {
		}

		public Builder(Equipment copy) {
			this.equipmentId = copy.getEquipmentId();
			this.horizontalBar = copy.getHorizontalBar();
			this.parallelBar = copy.getParallelBar();
			this.lowParallelBar = copy.getLowParallelBar();
			this.espalier = copy.getEspalier();
			this.fixedRings = copy.getFixedRings();
			this.monkeyBridge = copy.getMonkeyBridge();
		}

		/**
		 * Sets the {@code equipmentId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code equipmentId} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder equipmentId(Long val) {
			equipmentId = val;
			return this;
		}

		/**
		 * Sets the {@code horizontalBar} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code horizontalBar} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder horizontalBar(int val) {
			horizontalBar = val;
			return this;
		}

		/**
		 * Sets the {@code parallelBar} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code parallelBar} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder parallelBar(int val) {
			parallelBar = val;
			return this;
		}

		/**
		 * Sets the {@code lowParallelBar} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code lowParallelBar} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder lowParallelBar(int val) {
			lowParallelBar = val;
			return this;
		}

		/**
		 * Sets the {@code espalier} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code espalier} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder espalier(int val) {
			espalier = val;
			return this;
		}

		/**
		 * Sets the {@code fixedRings} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code fixedRings} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder fixedRings(int val) {
			fixedRings = val;
			return this;
		}

		/**
		 * Sets the {@code monkeyBridge} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code monkeyBridge} to set
		 *
		 * @return a reference to this Builder
		 */
		public Builder monkeyBridge(int val) {
			monkeyBridge = val;
			return this;
		}

		/**
		 * Returns a {@code Equipment} built from the parameters previously set.
		 *
		 * @return a {@code Equipment} built with parameters of this {@code Equipment.Builder}
		 */
		public Equipment build() {
			return new Equipment(this);
		}
	}
}
