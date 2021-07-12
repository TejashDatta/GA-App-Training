# 学んだこと：
* scrollview
* layout editor
* style: padding, margin, font, stylesheet

# 気づき：
* layout editor の design view で \n が含まれてる文字列を表示したら改行じゃなく、\n をそのまま表示する bug した。最初は design view で \n を改行として表示しないと思ってしまいましたので今後こんな bug があると覚えます。
* image view を追加した後、layout editor の design view で表示されていたが、アプリを実行したら表示されなかった。理由はimage view で選択した写真はtools namespaceのものでした。今後写真が現れないとnamespace を確認します。
