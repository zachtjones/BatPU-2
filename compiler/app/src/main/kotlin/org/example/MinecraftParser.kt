package org.example

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

interface Literal

data class CharacterLiteral(val character: Char): Literal
data class NumberLiteral(val number: Int): Literal

interface Statement

data class FunctionCallStatement(
    val functionName: String,
    val args: List<Literal>
): Statement
object CommentStatement: Statement

data class FunctionDeclaration(val name: String, val body: List<Statement>)

object MinecraftParser : Grammar<List<FunctionDeclaration>>() {
    private val funToken by literalToken("fun")
//    private val ifToken by literalToken("if")
//    private val elseToken by literalToken("else")
//    private val whileToken by literalToken("while")
    private val openBraceToken by literalToken("{")
    private val closeBraceToken by literalToken("}")
    private val openParenthesisToken by literalToken("(")
    private val closeParenthesisToken by literalToken(")")
    private val commaToken by literalToken(",")


    private val characterLiteralToken by regexToken("'.'")
    private val numberLiteralToken by regexToken("[0-9]+")
    private val identifierToken by regexToken("[_A-Za-z][_A-Za-z0-9]*")
    private val spacesToken by regexToken("[ \n\r\t]+")
    private val commentToken by regexToken("//.*\n")

    private val characterParser: Parser<CharacterLiteral> by characterLiteralToken.map { CharacterLiteral(it.text[1]) }
    private val numberParser: Parser<NumberLiteral> by numberLiteralToken.map { NumberLiteral(it.text.toInt()) }
    private val literalParser: Parser<Literal> by characterParser or numberParser

    private val functionCallExpression: Parser<FunctionCallStatement> by identifierToken and
            skip(openParenthesisToken) and
            // TODO - support expression args, not just literals
            separatedTerms(literalParser, commaToken, acceptZero = true) and
            skip(closeParenthesisToken) use {
                FunctionCallStatement(t1.text, t2)
            }

    private val commentParser: Parser<CommentStatement> by commentToken map { CommentStatement }

    // starting simple with just function calls and comments
    private val statement: Parser<Statement> by functionCallExpression or
            commentParser


    private val funParser: Parser<FunctionDeclaration> by skip(funToken) and skip(spacesToken) and identifierToken and
            // TODO arguments - right now only () is supported
            skip(openParenthesisToken) and skip(closeParenthesisToken) and
            skip(zeroOrMore(spacesToken)) and skip(openBraceToken) and
            skip(spacesToken) and separatedTerms(statement, spacesToken) and
            skip(spacesToken) and skip(closeBraceToken) use {
                FunctionDeclaration(t1.text, t2)
            }



    override val rootParser: Parser<List<FunctionDeclaration>> by separatedTerms(funParser, spacesToken or commentToken)
}