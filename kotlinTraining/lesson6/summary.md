基本的なlambda function の実装を前にしましたので今回は同じ基本的なlamba functionの実装を割愛します。

# 学んだこと：
annotations
lambda functions: with, run, apply, let
lambda function の動作について, いつ inline functionを使うべきですか
loop で label
single abstract method の意味, kotlinでそれの使い方

inline function について：
* noinline, crossinline 修飾子の意味と用途
* java byte code に inline kotlin function はどう展開されるか、修飾子によってその展開はどう違うか
* インライン関数の制約:
  * 展開によってコード肥大化
  * 再帰呼び出し禁止
  * public inline function は クラスの private property or function をアクセスできない
* インライン関数の適切な使い場：関数の引数がラムダあるいは関数である場合
* インライン関数を実施してもパーフォマンスがあまり向上しない場合：関数の引数がスカラである場合

# 気づき：
結果的にwith とrun は違いません。run は extension function なのでメソッドチェーンできます。
apply と let の違い： apply は呼び出されたオブジェクト自体を返します, let は呼び出されたオブジェクトのコピーを返します
