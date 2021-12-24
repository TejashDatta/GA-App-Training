課題：
- ドロワーの下に “記事取得先を追加する” を追加する(UIはSlackを参考にする)
- “記事取得先を追加する“をタップで「記事取得先を登録」画面へ遷移する
- 「記事取得先を登録」画面
  - 画面の構成
    - ヘッダー
      - 戻るボタン
      - 画面名
      - 保存ボタン
    - 入力項目
      - サイト名（必須項目、30字以内）
      - URL（必須項目、200字以内、URL形式であること）
  - 動作
    - 入力項目の検証に失敗する場合、保存ボタンが押せない
    - 保存ボタンをタップで、内容を保存し、画面を閉じる
- ドロワーの項目
  - Google News
  - 東洋経済
  - 追加したサイトの“サイト名” を追加した順で表示する
    - 追加したサイト名をタップすると東洋経済同様の画面に遷移すること
- その他
  - サイトの並び替えや削除、編集はこの課題では一旦なしでOK

タスク：add source の画面を作る
基本的に activity, fragment, layout, presenter, contract を実装する
見積もり：2時間

タスク：edit text の使い方とバリデーションがある実装例を勉強します
見積もり：2時間

タスク：add source 画面で edit text とそのバリデーションを導入する
保存ボタンがバリデーション状態によって有効
見積もり：5時間

タスク：news source を shared preferences で保存するオブジェクトを作る、
れを news repository から使えるようにする
見積もり：5時間

タスク：add source 画面が source を保存するようにする
見積もり：2時間

タスク：任意のURLへリクエストできる retrofit api object を作る
見積もり：4時間

タスク：news repository から news source によってニュース取得できるようにする
見積もり：5時間

タスク：news index presenter は初期化したときで記述した news source から取得するようにする
見積もり：3時間

タスク：navigation drawer で recycler view の使い方について勉強する
見積もり：4時間

タスク：navigation drawer で recycler view を使って news source ごとの項目を表示する
見積もり：5時間

タスク：navigation drawer の news source 項目をタップしたら該当の画面へ遷移するようにする
見積もり：5時間

バッファ：3日
終了日：1月13日
