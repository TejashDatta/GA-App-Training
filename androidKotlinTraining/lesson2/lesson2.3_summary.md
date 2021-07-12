# 学んだこと：
constraint layout の使い方:
  * chain
  * baseline constraint

# 気づき：
* 前にDrawableRes annotation を使ったので、R.id ための同様な annotation があるかもしれないと思って、@IdRes という annotation を探しだした。
* constraint layout を使えば、View 間の依存関係に注意しなきゃなりませんでしょう。
* XML で多くの property names はこのように `layout_constraintBottom_toBottomOf` camel case と snake case を混ぜてる。キャメルケースが一部に使用され、スネークケースが他の部分に使用されている理由はありますか。
