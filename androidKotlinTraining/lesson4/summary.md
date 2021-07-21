# 学んだこと
* Log, Timber, Logcat の使い方:
  * Timber vs Log: Timber でクラス名は自動的にタグ名となります
* Application class について：
  * Application is a base class that contains global application state for your entire app
  * Application class は、アプリ全体のグローバルアプリケーション状態を含む基本クラスです。
  * custom Application classの定義方法
* lifecycle の概念:
  * activity lifecycle
  * fragment lifecycle
  * visible lifecycle
  * interactive lifecycle

* activity lifecycle callback methods:
  * onCreate: when activity is created and data is initialized
  * onRestart: called before onStart
  * onStart: when activity becomes visible
  * onResume: when activity gets focus
  * onPause: when activity loses focus
  * onStop: when activity loses visibility
  * onDestroy: when activity is killed and memory cleanup  is done

  * onCreate: activity が作成され、該当のデータは初期化される際
  * onRestart: restart の際、onStart の前に呼び出される
  * onStart: activity は可視になる際
  * onResume: activity は focus を与えられる際
  * onPause: activity は focus を失う際
  * onStop: activity は不可視になる際
  * onDestroy: activity が終了され、メモリのクリーンアップが行われた際
  
* fragment lifecycle methods:
  * https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging#5 
  * 上にまとまれていてるので、またまとめていない

# 気付き
* android system は background 実行して資源を使ってるアプリを終了してメモリを取り戻すため、
stop のlifecycle method で自分の手で実行をやめるべきです
