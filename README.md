# lucene-kuromoji-sample
姓名分割されていない漢字氏名を、lucene kuromojiを利用して分割し、フリガナ予測を行います。

<br />

## 導入方法
以下の２パターンの利用方法があります。
* 1.Java内に追加する(推奨)
* 2.バッチとして利用する

<br />

## 1.Java内に追加する
### 1-1.前提
* Javaブログラム内で利用

<br />

### 1-2.導入方法
#### 1-2-1.elasticsearch-analysis-kuromoji-neologdを導入
**mavenを利用している場合**
下記をdependencyに追加

```
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>elasticsearch-analysis-kuromoji-neologd</artifactId>
    <version>6.3.1</version>
</dependency>
```

<br />

#### 1-2-2.サンプルコードを参考にロジックを追加
* [Furiganizer.java](https://github.com/itoshige/lucene-kuromoji-sample/blob/master/src/main/java/lucene_kuromoji_sample/lucene_kuromoji_sample/Furiganizer.java)の```public static String furiganize(String name, String userDictonary)```を参照に、ロジックを追加
* ※実行方法は、[junitテスト](https://github.com/itoshige/lucene-kuromoji-sample/blob/master/src/test/java/lucene_kuromoji_sample/lucene_kuromoji_sample/FuriganizerTest.java)をご参考
* ※サンプルコードでは、漢字・フリガナの間に「,」が入り、姓と名の間に半角スペースを入れています。適宜ご修正ください。

<br />

## 2.バッチとして利用する
### 2-1.前提
* 辞書の読み込みに多少時間がかかるため、多少処理が遅い
* 事前にjavaをインストールしておく

<br />

### 2-2.導入方法
* 以下コマンドを実行する


```
# ソースコードを取得
git clone https://github.com/itoshige/lucene-kuromoji-sample.git

cd lucene-kuromoji-sample

# jarファイルを作成
mvn clean install -DskipTests=true

cd target

# jarファイルをテスト実行
java -jar lucene-kuromoji-sample-0.0.1-jar-with-dependencies.jar 橘高司
-------------
橘 高司,タチバナ タカシ
※サンプルコードでは、漢字・フリガナの間に「,」が入り、姓と名の間に半角スペースを入れています。適宜ご修正ください。
-------------
```

<br />

### 2-3.実行方法

```
プログラムから下記コマンドを実行

# 例)rubyの場合
system('java -jar lucene-kuromoji-sample-0.0.1-jar-with-dependencies.jar ${氏名}')                                                                                               
```

<br />

## ユーザ辞書の追加方法
* 任意のユーザ辞書を追加することが可能できます。
* ただし、ユーザ辞書が大きくなるほど、処理速度が遅くなります。

### ユーザ辞書の作成方法
下記のような定義ファイルを作成（userdict.csvを参考）

```
達規,達規,タツノリ,カスタム名詞
秀城,秀城,ヒデキ,カスタム名詞
嘉規,嘉規,ヨシキ,カスタム名詞
```

### 追加方法

下記、```JapaneseTokenizer```の第一引数に、UserDictionaryで定義したユーザ辞書を設定


```
JapaneseTokenizer tokenizer = new JapaneseTokenizer(dict, false, JapaneseTokenizer.Mode.NORMAL)
```

<br />

## 備考
##### ご利用について
* 当サンプルはいかなる保証もおこなっておりません。
* 当サンプルをご利用時は、利用者の責任においてご利用ください。

##### テストデータ
精度確認用に下記ツールを利用して、テストデータを生成しました。
* [疑似個人情報生成 - 生成条件入力](https://hogehoge.tk/personal/generator/?)
* [すごい名前生成器](https://namegen.jp/)

## License
* MIT
