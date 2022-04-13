import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MainClass {

	public static void main(String[] args) throws Exception
	{	
		HashMap<String, Integer> words = new HashMap<String, Integer>();

		// Pass url through getWords Method and retrieve word count
		getWords("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm", words);

		Map<String, Integer>sortedwords = sortByValue(words);

		for (Entry<String, Integer> word : sortedwords.entrySet()) 
		{	
			System.out.println("Word: " + word.getKey() + " \t\tTimes Counted: "+ word.getValue());
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Method to turn website contents into String and count words
	static Map getWords(String url, Map<String, Integer> words) throws Exception
	{
		URL website = new URL(url);
		Scanner scan = new Scanner(website.openStream());
		StringBuffer sb = new StringBuffer();
		
		while (scan.hasNext()) {
			sb.append(scan.next());
			sb.append(System.lineSeparator());
		}
		
		
		String res = sb.toString().replaceAll("<[^>]*>", " ").replaceAll("mdash", "\n").replaceAll("\\-", "\n");
		res = res.substring(res.indexOf("The", 1550)-1);
		res = res.substring(0,res.indexOf("!", 6500)+1);
		res = res.replaceAll("\\p{Punct}", "");
		res = res.replaceAll("\\W", "\n");
		res = res.replaceAll("\\s+","\n");
		res = res.trim();
		
		Scanner wc = new Scanner(res);
		
		while (wc.hasNext())
		{
			String word = wc.nextLine();
			word = word.toLowerCase(); 
			// Frequency count variable
			Integer count = words.get(word);

			// If the same word is repeating
			if (count != null) 
			{
				count++;
			}
			else
				// If word never occurred after occurring once, set count as unity
				count = 1;
			words.put(word, count);
		}
		wc.close();
		scan.close();
		return words;
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Method to sort hashmap by values and limit hashmap output to only 20
    public static Map<String, Integer> sortByValue(HashMap<String, Integer> wordsort)
    {	
    	Map<String, Integer> res = wordsort.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    	
    	return res;		
    }
}
