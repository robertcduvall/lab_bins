import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 */

/**
 * @author Confidence
 *
 */
public class fitDisksAndPrint {
	
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(51);
		list.add(6);
		
		transform(list, (input) -> (input.stream()).sorted()
												  .collect(Collectors.toList())
												  );
	}
	
	public static void transform(List<Integer> list, 
				Function<List<Integer>, List<Integer>> func)	{
		List<Integer> res = func.apply(list);
		res.forEach(System.out::println);
	}

}

