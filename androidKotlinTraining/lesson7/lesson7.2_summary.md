# 学んだこと：
DiffUtil の役割、ItemCallback と使い方
  * DiffUtil は list がどう違ってるか計算する (要素の追加、削除、変化を認識する)
  * ItemCallback で Item の内容とidentity を比較する方法を定義する
list adapter を継承して使い方
  * list adapter 使えば簡単に DiffUtil を使える

binding adapter の定義と使い方
  * transformations のようにデータをビューで表示するために変化できる

# 気づき：
binding utils ファイル内で binding utils クラスは空だったから削除した

transformations, livedata with viewmodel binding と binding adapter の実装を比較してみた。
どの場合にどれがもっと適切でしょうか？
binding adapterの使用の方は間接的な感じがする。
