# Compiler

Rather than writing all code in assembly, here's a handy compiler that will generate the assembly for you, and feed
that into the assembler.

Code that is written will assemble to this [ISA](https://docs.google.com/spreadsheets/d/1Bj3wHV-JifR2vP4HRYoCWrdXYp3sGMG0Q58Nm56W4aI/edit?gid=0#gid=0).

This language is a much simplified version of kotlin/java

## Language specification

### Variables:
* You can use any standard names for variables (a-z)(A-Za-z0-9)+
* Variables are all 8-bit unsigned integers 

### Mathematical operations:

These are based on the ones supported in the ISA

* Add: '+'
* Subtract: '-'
* Bitwise nor (not or): '~', 'nor'
* Bitwise and: '&', 'and'
* Bitwise xor: '^', 'xor'
* Bitwise right shift: '>>1'
  * you can only shift right by one, so you must use the '>>1' symbol

Also supports:
* Increment: `++` works as a postfix (i++)
* Decrement: `--` works as a postfix (i--)

### Keywords:

These are words reserved in the language you can't use as an identifier

* `fun` - function
* `if` - if statement
* `else` - else statement
* `while` - while loop

### Assignment expressions:

For now, only simple expressions are supported.

Constants declaration - before any functions in the file - global scope.
* `val x = 1`

Variable declaration - have to be in a function
* `var y = 2`
* `var z = `some expression

All variables are 8 bits. 

0 will be false, and any non-zero number will be true.

### Math expressions:

You can write any variables but only one operation per line.

So for example `var a = b + 5`

### Built-in functions:

All these functions start with `_` to indicate they are
defined by the language specification.

Rather than working directly with memory, you can use
these functions:

Screen:
* `_bufferPixel(x, y)` - draws pixel x,y to the screen buffer (turns it on)
* `_clearPixel(x, y)` - clears pixel x,y to the screen buffer (turns it off)
* `_getPixel(x, y)` - returns the state of pixel x,y: 0 (off) or 1 (on)
* `_drawBuffer()` - updates the screen to reflect the state of the buffer
* `_clearBuffer()` - sets all pixels in the buffer to 0. Does not clear the screen.

Character display: (10 characters)
* `_appendCharacter(x)` - append character x to the buffer
* `_writeCharacterBuffer()` - writes the characters in the buffer to the display
* `_clearCharacterBuffer()` - clears the character buffer. Does not clear the screen

Characters are defined as:
* 0 - SPACE (blank) ` `
* 1 - 26 - `A` - `Z`
* 27 - `.`
* 28 - `!`
* 29 - `?`

Characters can be represented using single quotes. Lowercase letters will also work but will show up in caps.

example: `'A'` and `'a'` will both show "A"

Number display: 
* `_showNumber(x)` - shows the number passed in on the display
* `_clearNumber(x)` - clears the number on the display (turns all number lamps off)
* `_signedMode()` - sets the number display to render in 2's complement (-128 to 127)
* `_unsignedMode()` - sets the number display to render as unsigned number (0 to 255)

Input:
* `_random()` - returns a random 8-bit number
* `_controller()` - returns the state of the controller
  * Bit 7 - Start
  * Bit 6 - Select
  * Bit 5 - A button
  * Bit 4 - B button
  * Bit 3 - Up button pressed
  * Bit 2 - Right button pressed
  * Bit 1 - Down button pressed
  * Bit 0 - Left button pressed

### Program defined functions:

Must use the characters (a-zA-Z0-9), starting with a character.

You can only use the same name once.

You must define a function called `main` that takes in no parameters.
* this is where your program will start.

Functions will implicitly return 0 if you don't have a return value.

Examples:
* `fun hi(a, b, c) { ... }`

Your functions can be in any order.


## Output of the compiler

Output will be the assembly code

The following is the calling convention

r0 - zero register
r1 - argument 1
r2 - argument 2
r3 - argument 3

r4-r9 - scratch registers - modified when calling other functions

r10-r14 - persisted registers - not modified when calling other functions

r15 - return value

Memory usage:
* 0 - persist r10
* 1 - persist r11
* 2 - persist r12
* 3 - persist r13
* 4 - persist r14

