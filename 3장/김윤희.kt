// 3.1
fun <A> tail(xs: List<A>): List<A> =
    when (xs) {
        is Nil -> throw IllegalStateException() // Nil
        is Cons -> xs.tail
    }

// 3.2
fun <A> setHead(xs: List<A>, x: A): List<A> =
    when (xs) {
        is Nil -> throw IllegalStateException() // Nil
        is Cons -> Cons(x, xs.tail)
    }

// 3.3
fun <A> drop(l: List<A>, n: Int): List<A> {
    if (n == 0) return l

    return when (l) {
        is Nil -> throw IllegalStateException() // Nil
        is Cons -> drop(l.tail, n - 1)
    }
}

// 3.4
fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> {
    return when (l) {
        is Nil -> Nil
        is Cons -> if (f(l.head)) dropWhile(l.tail, f) else l
    }
}

// 3.5
fun <A> init(l: List<A>): List<A> {
    return when (l) {
        is Nil -> throw IllegalStateException()
        is Cons -> if (l.tail == Nil) return Nil else Cons(l.head, init(l.tail))
    }
}

// 3.6
// 즉시 결과를 돌려줄 수 없다. 리스트의 맨 끝까지 모든 원소를 순회하고, 그 후 익명 함수를 적용하면서 한 값으로 축약된다.
// 쇼트 서킷을 제공할 수 있으면 불필요하게 스택에 프레임을 쌓을 필요 없을 가능성이 추가되는 장점이 있을 것 같다.

// 3.7
val z = Nil as List<Int>
val f = { x: Int, y: List<Int> -> Cons(x, y) }

val trace = {
    foldRight(List.of(1, 2, 3), z, f)
    Cons(1, foldRight(List.of(2, 3), z, f))
    Cons(1, Cons(2, foldRight(List.of(3), z, f)))
    Cons(1, Cons(2, Cons(3, foldRight(List.empty(), z, f))))
    Cons(1, Cons(2, Cons(3, Nil)))
}
// 결과: Cons(head=1, tail=Cons(head=2, tail=Cons(head=3, tail=Nil)))
// Cons 생성자를 대치하게 된다.

// 3.8
fun <A> length(xs: List<A>): Int =
    foldRight(xs, 0, { _, y -> 1 + y })

// 3.9
tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
    when (xs) {
        is Nil -> z
        is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
    }
