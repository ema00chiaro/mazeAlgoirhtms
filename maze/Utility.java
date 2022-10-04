package maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Utility {
	public static <T> T getRandomElement(Collection<T> c){
		return new ArrayList<>(c).get(new Random().nextInt(c.size()));
	}

	//questa funzione ritorna true con probabilità 1/p
	public static boolean randomBoolean(int p) {
		return new Random().nextInt(p) == 0;
	}

	//ritorna un numero casuale fra 0 e upper bound escluso
	public static int getRandomNumber(int upperBound){
		return new Random().nextInt(upperBound);
	}
}
