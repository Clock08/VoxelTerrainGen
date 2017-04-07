package federation.terrain;

import java.util.Random;

public class Terrain {
	
	private static final int CONTINENT_OCTAVES = 4;
	private static final double CONTINENT_FREQUENCY = 1;
	private static final double CONTINENT_AMPLITUDE = 2;
	private static final double CONTINENT_LACUNARITY = 2;
	private static final double CONTINENT_GAIN = 0.5;
	
	private long seed;
	private long continentSeed;
	private long roughSeed;
	//private long featureSeed;
	private long fineSeed;
	//private long detailSeed;
	
	//private long temperatureSeed;
	//private long moistureSeed;
	
	public Terrain(long seed) {
		this.seed = seed;
		Random random = new Random(seed);
		continentSeed = random.nextLong();
		roughSeed = random.nextLong();
		//featureSeed = random.nextLong();
		fineSeed = random.nextLong();
		//detailSeed = random.nextLong();
	}
	
	public int getHeight(int x, int z) {
		float continentHeight = 0;
		float rough = 0;
		float fine = 0;
		
		double amplitude = CONTINENT_AMPLITUDE;
		double frequency = CONTINENT_FREQUENCY;
		for (int i = 0; i < CONTINENT_OCTAVES; i++) {
			continentHeight += amplitude * Noise.noise(x * frequency, z * frequency);
			frequency *= CONTINENT_LACUNARITY;
			amplitude *= CONTINENT_GAIN;
		}
		
		return (int) ((continentHeight) * 64);
	}
	
	public boolean isAir(int x, int y, int z) {
		if (y > getHeight(x, z)) return true;
		return false;
	}
}
