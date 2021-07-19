# 学んだこと：
* Safe Args gradle plugin と NavDirection クラスを使って、activity の間にtype safe & null safe ように引数を受け渡す
* getString で string resource にデータを埋め込む
* intents について：
  * 作り方と使い方
  * implicit と explicit intent はどう違う
  * ACTION でどんな activityが欲しいを指定する
  * EXTRA でシェアする情報を追加する
  * resolveActivity と package manager を使って intent を扱うactivityがあるか確認する

# 気づき：
intent の使用は細かさそうので将来に使えば例を参照する必要があると思います。
fromBundle, requireArguments, getString, resolveActivity は紛らわしかったが、docs でこれらのメソッドを調べたら疑問を答えられました。
