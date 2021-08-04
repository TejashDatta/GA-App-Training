# 学んだこと：
recycler view のアイテムにclick handler を追加して navigation を実装する方法
  click handler をどのように実装すべき
    click handler 機能を行うため、view holder, view model, adapter, fragment で何を定義と呼び出すべき、要素の連携方法

# 気づき：
Adding click handler to a recycler view item but the implementation is harder than i anticipated due to architectural constraints.
recycler view のアイテムに click handler を追加することは簡単だと予想したけど、実装は architecture 的な考慮があるため以外に紛らわしかった。

SleepNightListener class is only used by ViewHolder in the adapter. So I think it might be better to define it inside the view holder class.
SleepNightListener class は ViewHolder だけに使われるから、ViewHolder クラス内に定義した方が良くないか思った。

It's important to conceptually understand the flow of events and event handling, otherwise it becomes very confusing.
イベントのフローとイベント handling 処理の流れをイメージするのが大事。しないとコードの動作は把握しにくい。
