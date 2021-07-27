# 学んだこと
* view model を bind すれば、view は UI controller を通さずに直接に view model のデータと関数を使える
  * listener bindings: onClick などのイベントで lambda 関数で view model の関数を呼び出す
  * UI controller で observer を使わずに livedata をビューで参照する
  * livedata を string formatting 対応する

# 気付き
binding の lifecycleOwner の設定を忘れてはいけない。
