# Reverse Polish Notation Converter

This program is designed to convert infix expressions to **Reverse Polish Notation (RPN)** and vice versa. It processes expressions received from the standard input, which includes a number of operations to perform and strings representing individual expressions.

Each expression that is inputted begins with an identifier of either `INF:` or `ONP:` (RPN), denoting the initial format of the expression. Depending on this, the program performs the appropriate conversion. For expressions in infix notation, it converts them to RPN and for expressions in RPN, it converts them to infix notation.

## Usage and Error Handling

In use, the program checks whether a string is a valid expression before proceeding. If the expression is valid, conversion is carried out and the result is printed to the standard output. However, if the expression is not valid, the program returns an "error".

## Implementation

The program implements two converter classes: `InfToOnpConverter` and `OnpToInfConverter`. Each of these classes contains methods for conversion between the different formats along with methods to verify the correctness of any given expression.

The aim of this functionality is to provide developers and mathematicians with a versatile tool that can be used to visualize and verify the transformation of different mathematical and logical expressions.
