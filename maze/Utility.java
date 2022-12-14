package maze;

import java.util.Collection;
import java.util.Random;

public class Utility {
	private static Random rand = new Random();

	public static <T> T getRandomElement(Collection<T> c){
		return c.stream()
				.skip(getRandomNumber(c.size()))
				.findFirst()
				.get();
	}

	//questa funzione ritorna true con probabilit√† 1/p
	public static boolean randomBoolean(int p) {
		return rand.nextInt(p) == 0;
	}

	//ritorna un numero casuale fra 0 e upper bound escluso
	public static int getRandomNumber(int upperBound){
		return rand.nextInt(upperBound);
	}
}
