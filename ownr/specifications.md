# 模倣OWNRアプリの仕様：

## アプリ概要

What: OWNRアプリの一部の機能（ログイン、物件一覧、物件詳細）を模倣したアプリ。

Why: ダッタが模倣のアプリの開発を通してOWNRの仕様と使用されている技術を習得するため。

When: 一旦決めた機能を3月31日まで開発を完了する。

Who: ダッタテジャシュが練習のために開発する。app team のメンバーがアプリを確認します。
    ユーザーはダッタテジャシュとapp team の他のメンバーです。

Where: アプリは公開されない。github 上投稿されたソースファイルから app team のメンバーがアプリを起動できる。

How: android アプリを開発する。

## 画面

- スプラッシュ
- ログイン
- 物件一覧
- 物件詳細

## 画面の詳細

- スプラッシュ
  - OWNR のロゴを表示する
  - ユーザーがログインしたかどうかによって処理分岐する
    - 非ログイン状態であれば: ログイン画面へ遷移
    - ログイン状態であれば： 物件一覧へ遷移

- ログイン
  - customer ID とパスワードの記入項目

- 物件一覧
  - ユーザーが所有する物件の項目を並べる
  - 項目をタップすれば物件詳細へ遷移する
  - 物件項目の情報
    - 背景で物件の写真
    - 物件名
    - purchase price
    - yield
    - rent
    - tenanted / vacant
  - ログアウト・ボタン
    - タップすればログアウトして、ログイン画面へ遷移する

- 物件詳細
  - 上に物件の写真
    - 右下で写真数
  - 物件名
  - 物件住所
  - purchase price
  - rent / yield
  - タブ
    - management situation
      - notice of owned property
      - rental information
        - current rental status
        - rent / management fee
        - contract term
        - deposit / key money
    - property summary
     - propery info
      - purchase price
      - date completed
      - layout
      - private-use area
      - structure / size
      - nearest railway / subway station
    - taxes
      - fixed asset tax
      - urban planning tax
      - real estate acquisition tax
    - rental property management agreement
      - property management company
      - management format
      - management fee
      - contract period
      - automatic renewal
      - rent guarantee period during vacancy
      - guarantee fee
    - building management
      - propery management company
      - propery management fee
      - reserve fund for repairs
      - contact
    - mortgage
      - financial institution of mortgage
      - mortgage execution date
      - interest rate / type
      - period
      - repayment method
      - total borrowing
      - monthly repayment
    - documents
      - タブだけを実装する
