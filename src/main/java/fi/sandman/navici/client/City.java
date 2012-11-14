package fi.sandman.navici.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Available cities, their display and url names and WGS84 coordinates to map
 * center point.
 * 
 * @author Jouni Latvatalo <jouni.latvatalo@gmail.com>
 * 
 */
public enum City {
	JYVASKYLA("Jyväskylä", "jyvaskyla", 62.24263, 25.74719),

	KUOPIO("Kuopio", "kuopio", 62.89040, 27.68717),

	HYVINKAA("Hyvinkää", "hyvinkaa", 60.63462, 24.85514),

	HAMEENLINNA("Hämeenlinna", "hameenlinna", 60.99683, 24.46339),

	IISALMI("Iisalmi", "iisalmi", 63.56096, 27.21023),

	KAJAANI("Kajaani", "kajaani", 64.22503, 27.73257),

	KOTKA("Kotkat", "kotka", 60.46326, 26.93988),

	KOUVOLA("Kouvola", "kouvola", 60.87042, 26.70242),

	LAHTI("Lahti", "lahti", 60.97958, 25.65897),

	LANSIUUSIMAA("Länsi-Uusimaa", "lansiuusimaa", 60.24715, 24.07660),

	MIKKELI("Mikkeli", "mikkeli", 61.68784, 27.27339),

	PORI("Pori", "pori", 61.47996, 21.81586),

	PORVOO("Porvoo", "porvoo", 60.39605, 25.66818),

	RAUMA("Rauma", "rauma", 61.12560, 21.51989),

	RIIHIMAKI("Riihimäki", "riihimaki", 60.73926, 24.76786),

	ROVANIEMI("Rovaniemi", "rovaniemi", 66.49880, 25.71143),

	SALO("Salo", "salo", 60.39282, 23.12995),

	SAVONLINNA("Savonlinna", "savonlinna", 61.86816, 28.89915),

	SEINAJOKI("Seinäjoki", "seinajoki", 62.78390, 22.83942),

	TAMPERE("Tampere", "tampere", 61.49796, 23.76587),

	TURKU("Turku", "turku", 60.45074, 22.26458),

	VARKAUS("Varkaus", "varkaus", 62.32096, 27.86779);

	private static List<City> cities = new ArrayList<City>() {

		private static final long serialVersionUID = 1L;

		{
			add(HYVINKAA);
			add(HAMEENLINNA);
			add(IISALMI);
			add(JYVASKYLA);
			add(KAJAANI);
			add(KOUVOLA);
			add(KUOPIO);
			add(LAHTI);
			add(LANSIUUSIMAA);
			add(MIKKELI);
			add(PORI);
			add(PORVOO);
			add(RAUMA);
			add(RIIHIMAKI);
			add(ROVANIEMI);
			add(SALO);
			add(SAVONLINNA);
			add(SEINAJOKI);
			add(TAMPERE);
			add(TURKU);
			add(VARKAUS);
		}
	};
	private String displayName;
	private String propertiesName;
	private double latitude;
	private double longitude;

	City(String displayName, String propertiesName, double latitude,
			double longitude) {
		this.displayName = displayName;
		this.propertiesName = propertiesName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public static List<City> getCities() {
		return cities;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
