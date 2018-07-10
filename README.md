# lucene-kuromoji-sample
姓名分割されていない漢字氏名をlucene kuromojiを利用して、分割、フリガナ予測を行います

<br />

# 導入方法
以下の２パターンの利用方法があります。
* 1.Java内に追加する(推奨)
* 2.バッチとして利用する
  * 辞書の読み込みに多少時間がかかるため、多少処理が遅いため、1.での利用をおすすめします。

<br />

# 1.Java内に追加する
## 1-1.前提
* Javaブログラム内で利用

<br />

## 1-2.導入方法
### 1-2-1.maven等のプロジェクト管理ツールを利用している場合
下記をdependencyに追加

```
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>elasticsearch-analysis-kuromoji-neologd</artifactId>
    <version>6.3.1</version>
</dependency>
```

### 1-2-2.maven等のプロジェクト管理ツールを利用していない場合
* [Elasticsearch Analysis Kuromoji Neologd](https://mvnrepository.com/artifact/org.codelibs/elasticsearch-analysis-kuromoji-neologd/6.3.1)から[jar](http://central.maven.org/maven2/org/codelibs/elasticsearch-analysis-kuromoji-neologd/6.3.1/elasticsearch-analysis-kuromoji-neologd-6.3.1.jar)をダウンロード
* 自身のJavaプロジェクトにJarファイルを追加

<br />

## 1-3.導入・実行方法
* 下記[ソース]()を参照
* 実行方法は、[junitテスト]()をご参考

<br />

# 2.バッチとして利用する
## 2-1.前提
* 辞書の読み込みに多少時間がかかるため、多少処理が遅い
* 事前にjavaをインストールしておく

<br />

## 2-2.導入方法
* 以下コマンドを実行する


```
# ソースコードを取得
git clone XX

cd lucene-kuromoji-sample

# jarファイルを作成
mvn clean install

cd target

# jarファイルをテスト実行
java -jar lucene-kuromoji-sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar 山田 太郎
```

<br />

## 2-3.実行方法

```
プログラムから下記コマンドを実行

# 例)rubyの場合
system('java -jar lucene-kuromoji-sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar ${氏名}')                                                                                               
```

