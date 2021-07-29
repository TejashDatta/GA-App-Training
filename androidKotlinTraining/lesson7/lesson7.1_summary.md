# 学んだこと：
RecyclerView の特定：
  * 表示されるアイテムだけを作る
  * viewholder を recycle するから効率高い
layout manager:
  * recycler view で表示の形を決める (grid, list, etc)
adapter:
  * recycler view と view model を連携する
view holder:
  * view を recycler view で使えるように機能追加する

# 気づき：
RecyclerView の実装は、adapter, viewholder などの多くの部分で構成されている。だから実装は大きくて複数みえる。
大きな概念を理解しようとするときは、それを分割して、最初に各構成概念を個別に理解することが重要だと改めて実感した。

チュートリアルで onCreateViewHolder は onBindViewHolder の後に実装されるけど、adapter コード内に、onCreateViewHolder を onBindViewHolder の上に置く方が適切だと感じた。
