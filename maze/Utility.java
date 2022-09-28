package maze;

import java.util.List;
import java.util.Random;

public class Utility {
	public static <T> T getRandomElement(List<T> list){
		return list.get(new Random().nextInt(list.size()));
	}
}
