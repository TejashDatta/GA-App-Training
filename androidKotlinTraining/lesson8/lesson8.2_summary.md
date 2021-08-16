# 学んだこと
Glide library を用いてインターネットから画像をダウンロードして表示する
インターネットから画像をダウンロードして表示するのに色々な処理が必要：
  ダウンロード、ストレージ、decompression, 複数のイメージの同時表示

# 気づき
binding adapter:
  tutorial の 実装で binding adapter 関数で let で引数を確認したけど、早期リータンの方が簡潔だからリファクタした
  binding adapter の新しい定義方法に会った：
    第一目の引数はViewで、第二目の引数はxmlで受け取った値だ。
    前のbinding adapter の実装では関数はViewのextension関数として定義されていた
    binding adapter の使用は fragment で observer を設定するより簡単だと感じた

インターネットから情報を取得するとき、色々な状態（ダウンロード中、済、失敗など）があるからすべてに対応する必要がある
