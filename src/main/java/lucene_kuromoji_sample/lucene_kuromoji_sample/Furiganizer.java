package lucene_kuromoji_sample.lucene_kuromoji_sample;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.dict.UserDictionary;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.tokenattributes.ReadingAttribute;

/**
 * 姓名分割クラス
 */
public class Furiganizer {
	private static final char[] c = { '\u3000' };
	private static final String WSPACE = new String(c); // 全角スペース

	/**
	 * バッチとして実行する場合に使用する
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1 && 2 <= args.length) {
			System.out.println("error:引数を1つ以上2以下で指定して下さい");
			System.exit(1);
		}

		try {
			System.out.println(furiganize(String.join(" ", args)));
		} catch (IOException e) {
			System.out.println("error:例外が発生しました");
			System.exit(1);
		}
	}

	/**
	 * 姓名分割されていない漢字氏名をkuromojiを使用して分割、予測したフリガナを返却する
	 * 
	 * @param name
	 * @param userDictonary：ユーザ辞書
	 * @return
	 * @throws IOException
	 */
	public static String furiganize(String name, String userDictonary) throws IOException {
		if (isEmpty(name))
			return "";

		// 返却する漢字氏名・フリガナ氏名
		StringBuilder kanjiName = new StringBuilder();
		StringBuilder kanaName = new StringBuilder();

		// ユーザ辞書を定義
		InputStreamReader isr = getResource(userDictonary);
		UserDictionary dict = (isr != null) ? UserDictionary.open(isr) : null;

		try (JapaneseTokenizer tokenizer = new JapaneseTokenizer(dict, false, JapaneseTokenizer.Mode.NORMAL)) {

			tokenizer.setReader(new StringReader(name));

			ReadingAttribute readingAttribute = tokenizer.addAttribute(ReadingAttribute.class);
			CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);
			tokenizer.reset();
			while (tokenizer.incrementToken()) {
				String kanji = charTermAttribute.toString();

				// 半角・全角スペース・*はskip
				if (isEmpty(kanji) || kanji.equals("*")) {
					continue;
				}

				kanjiName.append(kanji + " ");

				String kana = readingAttribute.getReading();
				if (kana == null) {
					kana = charTermAttribute.toString();
				}
				kanaName.append(kana + " ");
			}
			kanjiName.setLength(kanjiName.length() - 1);
			kanaName.setLength(kanaName.length() - 1);

			return kanjiName.append(",").append(kanaName).toString();
		}
	}

	/**
	 * 姓名分割されていない漢字氏名をkuromojiを使用して分割、予測したフリガナを返却する
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static String furiganize(String name) throws IOException {
		return furiganize(name, null);
	}

	private static InputStreamReader getResource(String userDictonary) {
		if (userDictonary == null)
			return null;

		ClassLoader classLoader = Furiganizer.class.getClassLoader();
		return new InputStreamReader(classLoader.getResourceAsStream(userDictonary));
	}

	/**
	 * null、半角または全角スペースか判定する
	 * 
	 * @param value
	 * @return
	 */
	private static boolean isEmpty(String value) {
		return (value == null || value.trim().length() == 0 || value.replaceAll(WSPACE, "").length() == 0);
	}
}
