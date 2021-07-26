# 学んだこと
* LiveData, MutableLiveData の特定の使い方
  * observable: LiveData に observer を設定して、LiveData の変化に応じて対応ができる
  * lifecycle aware: LiveData を observe すれば、observer は lifecycle owner と紐付いて、その lifecycle owner の状態は active じゃないと更新しない
* observer の使い方
* viewLifeCycleOwner
  * fragment の view lifecycle を track する
  * view の更新を行う observer は fragment が不可視の状態で動作すべきじゃないので observer の owner として viewLifeCycleOwner を使う
* backing property の必要、使い方
* Observer pattern の意味
  * event 変数を用いて、実装方法
* viewmodel を bind して、xml で click listener を設定

# 気付き
基本的に LiveData と Observer の使用は簡単そう。ですが、Observer の使用に関して、lifecycle owner のことは細かいので気を使う必要があります。

MutableLiveData 変数は値が変更可能ですが、var ではなく、val で定義します。なぜなら MutableLiveData の変数は MutableLiveData object 参照を持っています。変数を var で定義すれば参照を変えられる危険があります。
