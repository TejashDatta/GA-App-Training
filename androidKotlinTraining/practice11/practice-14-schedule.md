課題：
記事の取得先を追加する
- 記事一覧に表示する記事をGoogleと 東洋経済オンライン から取得する
- 記事一覧はすべての記事の pubDate の降順で表示する
- 東洋経済オンライン から取得した記事も同様に「フォロー」「最近読んだ記事」に登録されること

タスク：
ニュース元からニュースを取得する api object を作る
- network data object の作成
  - network data object を domain data object へ変換できるようにする
- api object の作成
- api object のテスト
見積もり：5時間

タスク：
ニュース項目の repository は上記の api service を既存の api service のデータとマージするようにする
- pubDate 順にマージする
- rx java で並列処理について勉強する
- テスト
見積もり：5時間

終了日：12月8日
