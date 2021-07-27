# 学んだこと
model-view-view model (MVVM) architecture
  * model： アプリのデータとビジネスロジックを管理する。例：データベースやAPIから情報を取得するクラス。
  * view： データの表示を管理する。例：UI controller, XML layout
  * view-model: view に与えて表示するデータを決定する。表示のためにビューに与えるデータを管理する。
UI controller: 
  * Activity, fragment など
  * business logic を含めない
  * 表示のロジックを担う
view model
  * データ（インプットなど）変換を行う
  * UI controller を参照すべきじゃない
view model factory:
  * view model を初期化する、既存の view model object を戻す

`java.lang.Class.isAssignableFrom()`
  * The `java.lang.Class.isAssignableFrom()` determines if the class or interface represented by the Class object is either the same as, or is a superclass or superinterface of, the class or interface represented by the specified Class parameter.
  * `java.lang.Class.isAssignableFrom()` を使ってクラスは引数のクラスと同じか、親クラスか認識できます。

`viewModel = ViewModelProvider(this).get(GameViewModel::class.java)` で `::class.java` とはどういう意味ですか？
`::class` 
  * class reference operator 
  * used to obtain the statically known reference to the class at runtime
  * 実行時にクラスへの静的に既知の参照を取得するために使用されます
  * returns reference type of KClass
`::class.java` -> 
  * returns reference type of Java

`viewModel = ViewModelProvider(this).get(GameViewModel::class.java)` で何でクラス参照はjava の型です？
要すると、何で `viewModel = ViewModelProvider(this).get(GameViewModel::class)` にしない？
AndroidSDKが処理するときにClassオブジェクトの期待値は、kotlinのclassオブジェクトではなくて、javaのclassオブジェクトを期待します。

# 気付き
view model factory の create method は一見複雑に見えますが、分解すれば理解できます。
