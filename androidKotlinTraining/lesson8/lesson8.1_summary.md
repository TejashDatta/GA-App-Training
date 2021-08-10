# 学んだこと
lazy によって初期化
retrofit を用いて api client service の作成
  converter: response を kotlin のオブジェクトに変換します
moshi object を retrofit object の converter と指摘して kotlin で JSON 文字列を kotlin オブジェクトに変換する方法
  json の property を持つデータクラスを作る

# 気づき
MarsProperty というクラス名で property とは クラスが JSON の property を持つことと示すと思っちゃったけど property は不動産の意味合いだ

coroutine を用いた実装は callback object を用いた実装より簡単だ
  何度も引数で Call と Callback の使用を避ける
    従って、割と少ない import (call や callback object の import は不要)
    Call を使えば最終的な return type を一回囲むことになる： eg String -> Call<String>
