# 学んだこと
Glide library を用いてインタネットから画像をダウンロードして表示する
インタネットから画像をダウンロードして表示するのに色々な処理が必要：
  ダウンロード、ストレージ、decompression, batch image download

# 気づき
binding adapter:
  tutorial の 実装で binding adapter 関数で let で引数を確認したけど、早期リータンの方が簡潔だからリファクタした
  binding adapter の新しい定義方に会った：
    第一目の引数はViewで、第二目の引数はxmlで受け取った値だ。
    前のbinding adapter の実装では関数はViewのextension関数として定義されていた

