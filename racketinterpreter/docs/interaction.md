# sample interaction

```bash
mohidtanveer@mohids-MacBook-Pro racketinterpreter % javac src/Main.java src/Interpreter.java
mohidtanveer@mohids-MacBook-Pro racketinterpreter % java src.Main
Welcome to JRacket!  Type "end" to stop the interpreter.
>>> (define x 3)
      Evaluating: (define x 3)
==> done

>>> x
      Evaluating: x
==> 3
>>> (define z '(1 2 3))
      Evaluating: (define z (quote (1 2 3)))
      Evaluating: (quote (1 2 3))
==> done

>>> z
      Evaluating: z
==> (1 2 3)
>>> (define y z)
      Evaluating: (define y z)
      Evaluating: z
==> done

>>> y
      Evaluating: y
==> (1 2 3)
>>> (+ 3 3)
      Evaluating: (+ 3 3)
      Evaluating: +
==> 6
>>> (define z 42)
      Evaluating: (define z 42)
==> done

>>> (define q 5)
      Evaluating: (define q 5)
==> done

>>> (* (- 2 q) z)
      Evaluating: (* (- 2 q) z)
      Evaluating: *
      Evaluating: (- 2 q)
      Evaluating: -
      Evaluating: q
      Evaluating: z
==> -126
>>> (cons 1 '())
      Evaluating: (cons 1 (quote ()))
      Evaluating: cons
      Evaluating: (quote ())
==> (1)
>>> (define L (cons 1 '(2 3)))
      Evaluating: (define L (cons 1 (quote (2 3))))
      Evaluating: (cons 1 (quote (2 3)))
      Evaluating: cons
      Evaluating: (quote (2 3))
==> done

>>> L
      Evaluating: L
==> (1 2 3)
>>> l
      Evaluating: l
src.InterpreterException: Variable not defined:l
>>> (= 3 4)
      Evaluating: (= 3 4)
      Evaluating: =
==> #f
>>> (cons (car L) L)
      Evaluating: (cons (car L) L)
      Evaluating: cons
      Evaluating: (car L)
      Evaluating: car
      Evaluating: L
      Evaluating: L
==> (1 1 2 3)
>>> (if (= 3 4) 1 2)
      Evaluating: (if (= 3 4) 1 2)
      Evaluating: (= 3 4)
      Evaluating: =
==> 2
>>> (if (= 3 3) 1 2)
      Evaluating: (if (= 3 3) 1 2)
      Evaluating: (= 3 3)
      Evaluating: =
==> 1
>>> (if (equal? '(1 2) '(1 2)) (cons 'a '(b)) 'c)           
      Evaluating: (if (equal? (quote (1 2)) (quote (1 2))) (cons (quote a) (quote (b))) (quote c))
      Evaluating: (equal? (quote (1 2)) (quote (1 2)))
      Evaluating: equal?
      Evaluating: (quote (1 2))
      Evaluating: (quote (1 2))
      Evaluating: (cons (quote a) (quote (b)))
      Evaluating: cons
      Evaluating: (quote a)
      Evaluating: (quote (b))
==> (a b)
>>> (lambda (x y) (+ x y))
      Evaluating: (lambda (x y) (+ x y))
==> #<function:anonymous>
>>> (lambda () 'p)
      Evaluating: (lambda () (quote p))
==> #<function:anonymous>
>>> (lambda (lst) (cons 1 lst))
      Evaluating: (lambda (lst) (cons 1 lst))
==> #<function:anonymous>
>>> (define add1 (lambda (x) (+ x 1)))
      Evaluating: (define add1 (lambda (x) (+ x 1)))
      Evaluating: (lambda (x) (+ x 1))
==> done

>>> (add1 5)
      Evaluating: (add1 5)
      Evaluating: add1
      Applying: [Func:add1 [x] (+ x 1) jracket.Frame:{L=(1 2 3), q=5, x=3, y=(1 2 3), z=42, add1=#<function:add1>}]
      Evaluating: (+ x 1)
      Evaluating: +
      Evaluating: x
==> 6
>>> (define make-adder (lambda (x) (lambda (y) (+ x y))))
      Evaluating: (define make-adder (lambda (x) (lambda (y) (+ x y))))
      Evaluating: (lambda (x) (lambda (y) (+ x y)))
==> done

>>> (define add2 (make-adder 2))
      Evaluating: (define add2 (make-adder 2))
      Evaluating: (make-adder 2)
      Evaluating: make-adder
      Applying: [Func:make-adder [x] (lambda (y) (+ x y)) jracket.Frame:{L=(1 2 3), q=5, x=3, y=(1 2 3), make-adder=#<function:make-adder>, z=42, add1=#<function:add1>}]
      Evaluating: (lambda (y) (+ x y))
==> done

>>> (add2 19)
      Evaluating: (add2 19)
      Evaluating: add2
      Applying: [Func:add2 [y] (+ x y) jracket.Frame:{x=2}]
      Evaluating: (+ x y)
      Evaluating: +
      Evaluating: x
      Evaluating: y
==> 21
>>> (define fact (lambda (n) (if (= n 0) 1 (* n (fact (- n 1))))))
      Evaluating: (define fact (lambda (n) (if (= n 0) 1 (* n (fact (- n 1))))))
      Evaluating: (lambda (n) (if (= n 0) 1 (* n (fact (- n 1)))))
==> done

>>> (fact 3)
      Evaluating: (fact 3)
      Evaluating: fact
      Applying: [Func:fact [n] (if (= n 0) 1 (* n (fact (- n 1)))) jracket.Frame:{fact=#<function:fact>, L=(1 2 3), q=5, x=3, y=(1 2 3), make-adder=#<function:make-adder>, z=42, add2=#<function:add2>, add1=#<function:add1>}]
      Evaluating: (if (= n 0) 1 (* n (fact (- n 1))))
      Evaluating: (= n 0)
      Evaluating: =
      Evaluating: n
      Evaluating: (* n (fact (- n 1)))
      Evaluating: *
      Evaluating: n
      Evaluating: (fact (- n 1))
      Evaluating: fact
      Evaluating: (- n 1)
      Evaluating: -
      Evaluating: n
      Applying: [Func:fact [n] (if (= n 0) 1 (* n (fact (- n 1)))) jracket.Frame:{fact=#<function:fact>, L=(1 2 3), q=5, x=3, y=(1 2 3), make-adder=#<function:make-adder>, z=42, add2=#<function:add2>, add1=#<function:add1>}]
      Evaluating: (if (= n 0) 1 (* n (fact (- n 1))))
      Evaluating: (= n 0)
      Evaluating: =
      Evaluating: n
      Evaluating: (* n (fact (- n 1)))
      Evaluating: *
      Evaluating: n
      Evaluating: (fact (- n 1))
      Evaluating: fact
      Evaluating: (- n 1)
      Evaluating: -
      Evaluating: n
      Applying: [Func:fact [n] (if (= n 0) 1 (* n (fact (- n 1)))) jracket.Frame:{fact=#<function:fact>, L=(1 2 3), q=5, x=3, y=(1 2 3), make-adder=#<function:make-adder>, z=42, add2=#<function:add2>, add1=#<function:add1>}]
      Evaluating: (if (= n 0) 1 (* n (fact (- n 1))))
      Evaluating: (= n 0)
      Evaluating: =
      Evaluating: n
      Evaluating: (* n (fact (- n 1)))
      Evaluating: *
      Evaluating: n
      Evaluating: (fact (- n 1))
      Evaluating: fact
      Evaluating: (- n 1)
      Evaluating: -
      Evaluating: n
      Applying: [Func:fact [n] (if (= n 0) 1 (* n (fact (- n 1)))) jracket.Frame:{fact=#<function:fact>, L=(1 2 3), q=5, x=3, y=(1 2 3), make-adder=#<function:make-adder>, z=42, add2=#<function:add2>, add1=#<function:add1>}]
      Evaluating: (if (= n 0) 1 (* n (fact (- n 1))))
      Evaluating: (= n 0)
      Evaluating: =
      Evaluating: n
==> 6
>>> end

mohidtanveer@mohids-MacBook-Pro racketinterpreter % 
```
