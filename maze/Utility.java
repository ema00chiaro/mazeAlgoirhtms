package maze;

import java.util.List;
import java.util.Random;

public class Utility {
	public static <T> T getRandomElement(List<T> list){
		return list.get(new Random().nextInt(list.size()));
	}

	//questa funzione ritorna true con probabilità 1/p
	public static boolean randomBoolean(int p) {
		return new Random().nextInt(p) == 0;
	}
}
