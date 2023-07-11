# introduction

In this project I created an interpreter for the language racket within Java.

The interpreter will only provide the basic functionality used within the language Racket.

It essentially runs a loop:

1. Prompts the user for an input command.
2. Evaluate the input command.
3. Print output
4. Repeat.

Skills associated with this project are:

* Using a language in order to interpret another language.
* Understanding how to implement different syntaxes for a language.
* Implementing nested function calls and interpreting them.

## overview

The structure of the project is as follows:

```text
racket interpreter
|-- README.md                       -- this file
|-- docs                            -- documentation
    |-- interaction.md              -- sample interaction
`-- src                             -- source folder
    |-- types                       -- expression types for racket       
    |-- Frame.java                  -- frame object file
    |-- Interpreter.java            -- interpreter object file 
    |-- InterpreterException.java   -- throw exceptions for interpreter
    |-- Main.java                   -- runs interpreter
    |-- Parser.java                 -- parses string to racket expression
    |-- ParsingException.java       -- throw exceptions for parser
    |-- Utilities.java              -- utility file
```
