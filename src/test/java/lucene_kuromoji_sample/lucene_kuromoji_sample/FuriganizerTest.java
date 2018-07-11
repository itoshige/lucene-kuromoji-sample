package lucene_kuromoji_sample.lucene_kuromoji_sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class FuriganizerTest
{
	@Test
	public void ひらがな変換の精度確認_漢字スペースあり_1件() throws IOException {
		String expectedKanji = "万行 達規";
		String kanjiKana = Furiganizer.furiganize(expectedKanji);
		System.out.println(kanjiKana);
	}
	
	@Test
	public void ひらがな変換の精度確認_漢字スペースあり_1件_ユーザ辞書() throws IOException {
		String expectedKanji = "万行 達規";
		String kanjiKana = Furiganizer.furiganize(expectedKanji, "userdict.csv");
		System.out.println(kanjiKana);
	}

	
	@Test
	public void ひらがな変換の精度確認_漢字スペースあり() throws IOException {		
		displayCorrectCount("kanji2kana-wspace.csv");
	}
	
	@Test
	public void ひらがな変換の精度確認_漢字スペースなし() throws IOException {
		displayCorrectCount("kanji2kana-wospace.csv");		
	}
	
	@Test
	public void ひらがな変換の精度確認_漢字スペースあり_珍しい名前() throws IOException {
		displayCorrectCount("kanji2kana-wspace-rarename.csv");
	}

	@Test
	public void ひらがな変換の精度確認_漢字スペースあり_珍しい名前_ユーザ辞書() throws IOException {
		displayCorrectCount("kanji2kana-wspace-rarename.csv", "userdict.csv");
	}
	
	/**
	 * 正解率を表示する
	 * 
	 * @param testNameFile
	 * @throws IOException
	 */
	private void displayCorrectCount(String testNameFile, String userDictFile) throws IOException {
		Map<String, String> testNames = getTestNames(testNameFile);
		Set<String> kanjiName = testNames.keySet();

		int correct = 0;
		
		for (Iterator<String> n = kanjiName.iterator(); n.hasNext();)
		{
			 String expectedKanji = n.next();
			 String kanjiKana = Furiganizer.furiganize(expectedKanji, userDictFile);
			 String[] name = kanjiKana.split(",");
			 
			 String expectedkana = testNames.get(expectedKanji);
			 String kuromojikanji = name[0];
			 String kuromojikana = name[1];
			 
			 System.out.print("kanji:" + expectedKanji + "	expected:" + expectedkana + "	kuromojikanji:" + kuromojikanji + "	kuromojikana:" + kuromojikana);
			 if(kuromojikana.equals(expectedkana)) {
				 correct++;
				 System.out.print("	○");
			 }
			 System.out.println("");
		}
		
		System.out.println("正解率:" + (double)correct * 100 / kanjiName.size() + "	正解数:" + correct + "	母数:" + kanjiName.size());
	}
	
	private void displayCorrectCount(String testNameFile) throws IOException {
		displayCorrectCount(testNameFile, null);
	}

	/**
	 * kuromoji精度確認用のテストデータを取得する
	 * 
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("finally")
	private Map<String, String> getTestNames(String filename) {
		Map<String, String> testNames = new HashMap<String, String>();
		
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(filename).getFile());
			InputStreamReader isr  = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(isr); 
			   
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",", 0);
				testNames.put(data[0], data[1]);
			}
			br.close();		
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			return testNames;
		}
	}
}
