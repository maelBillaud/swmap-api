package com.webservices.swmap.util;

import com.webservices.swmap.model.Park;

import java.math.RoundingMode;

public class Util {

	/**
	 * Applique un arrondi de 5 chiffres après la virgule aux latitude et longitude du {@link Park} donné en paramètre.
	 * Cela implique une précision des coordonnées au mètre près (qui sont renvoyés à une précision de 10^(-8)mm par
	 * l'ihm)
	 *
	 * @param park {@link Park} dont les valeurs sont à arrondir
	 *
	 * @return un {@link Park} avec des valeurs arrondies
	 */
	public static Park setScale(Park park) {
		park.setLatitude(park.getLatitude()
		                     .setScale(5, RoundingMode.HALF_UP));
		park.setLongitude(park.getLongitude()
		                      .setScale(5, RoundingMode.HALF_UP));
		return park;
	}

	/**
	 * Calcul la distance entre 2 points définis par leur latitude et longitude grâce à la formule de Haversine
	 *
	 * @param lat1 Première composante du premier point
	 * @param long1 Seconde composante du premier point
	 * @param lat2 Première composante du second point
	 * @param long2 Seconde composante du second point
	 *
	 * @return une distance en km
	 */
	public static long distanceBetweenCoordinates(Double lat1, Double long1, Double lat2, Double long2) {
		double theta = long1 - long2;
		double distance = 60 * 1.1515 * (180 / Math.PI) * Math.acos(Math.sin(lat1 * (Math.PI / 180)) * Math.sin(lat2 * (Math.PI / 180)) + Math.cos(lat1 * (Math.PI / 180)) * Math.cos(lat2 * (Math.PI / 180)) * Math.cos(theta * (Math.PI / 180)));
		return Math.round(distance * 1.609344);
	}
}
