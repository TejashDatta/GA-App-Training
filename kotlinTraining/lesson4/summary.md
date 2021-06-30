# 学んだこと：
class 定義、constructor定義, getter, setter,
class visibility modifiers (private, public, etc), class inheritanceの仕方
abstract class, interface の定義と使い方, interface delegation
singeleton class, data class, enum class の定義

# 気づき：
abstract class vs interface:
* 抽象クラス内に変数定義可能です（インタフェースはメソッドのみ）
*抽象クラスはconstuctorを持てる
* 抽象クラスは一つだけ継承できる

abstract class vs interface 使い分け：
* 中にあるほとんどのメソッドに実装がない(少ないメソッドのみがデファルト実装がある）場合はインターフェイスを使用します
* ほとんどのメソッドと変数が定義されているがクラスを完了できない場合に抽象クラスを使用します
