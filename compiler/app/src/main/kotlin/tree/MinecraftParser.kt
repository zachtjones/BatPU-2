package tree

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

object MinecraftParser : Grammar<Program>() {
    private val funToken by literalToken("fun")

    //    private val ifToken by literalToken("if")
//    private val elseToken by literalToken("else")
//    private val whileToken by literalToken("while")
    private val openBraceToken by literalToken("{")
    private val closeBraceToken by literalToken("}")
    private val openParenthesisToken by literalToken("(")
    private val closeParenthesisToken by literalToken(")")
    private val commaToken by literalToken(",")
    private val varToken by literalToken("var")
    private val equalsToken by literalToken("=")
    private val whileToken by literalToken("while")

    private val greaterThanOrEqual by literalToken(">=")
    private val lessThan by literalToken("<")
    private val isEqual by literalToken("==")
    private val notEqual by literalToken("!=")

    private val decrement by literalToken("--")
    private val increment by literalToken("++")

    private val characterLiteralToken by regexToken("'.'")
    private val numberLiteralToken by regexToken("[0-9]+")
    private val identifierToken by regexToken("[_A-Za-z][_A-Za-z0-9]*")
    private val spacesToken by regexToken("[ \n\r\t]+", ignore = true)
    private val commentToken by regexToken("//.*\n?", ignore = true)

    // ignored
    private val commentParser: Parser<CommentStatement> by commentToken map { CommentStatement }

    // simple parsers
    private val characterParser: Parser<CharacterLiteral> by characterLiteralToken.map { CharacterLiteral(it.text[1]) }
    private val numberParser: Parser<NumberLiteral> by numberLiteralToken.map { NumberLiteral(it.text.toInt()) }
    private val literalParser: Parser<Literal> by characterParser or numberParser

    // simple parsers with variables
    private val readVariable: Parser<ReadVariable> by identifierToken map {
        ReadVariable(it.text)
    }

    private val variableDeclarationAndAssignmentExpression: Parser<VariableDeclarationAndAssignment> by skip(varToken) and
            identifierToken and skip(equalsToken) and literalParser use {
        VariableDeclarationAndAssignment(variableName = t1.text, value = t2)
    }

    private val incrementStatement: Parser<IncrementStatement> by identifierToken and -increment map {
        IncrementStatement(it.text)
    }

    private val decrementStatement: Parser<DecrementStatement> by identifierToken and -decrement map {
        DecrementStatement(it.text)
    }

    // control flow: functions / loops / blocks
    private val functionCallExpression: Parser<FunctionCallStatement> by identifierToken and
            skip(openParenthesisToken) and
            // TODO - support expression args, not just literals
            separatedTerms(parser(this::expression), commaToken, acceptZero = true) and
            skip(closeParenthesisToken) use {
        FunctionCallStatement(t1.text, t2)
    }

    /** Expressions that return one of the 4 conditions supported by the ISA */
    private val booleanExpression: Parser<ComparisonExpression> by identifierToken and
            (greaterThanOrEqual or lessThan or isEqual or notEqual) and
            literalParser use {
        ComparisonExpression(t1.text, t2.text, t3)
    }

    /** General expressions that deal with 8-bit values */
    private val expression: Parser<Expression> by literalParser or readVariable

    // starting simple with just function calls and comments
    private val statement: Parser<Statement> by functionCallExpression or
            commentParser or
            variableDeclarationAndAssignmentExpression or
            incrementStatement or
            decrementStatement or
            parser(this::whileStatement)

    private val whileStatement: Parser<WhileStatement> by -whileToken and -openParenthesisToken and
            booleanExpression and -closeParenthesisToken and -openBraceToken and
            separatedTerms(statement, spacesToken) and -closeBraceToken use  {
        WhileStatement(expression = t1, body = t2)
    }

    private val funParser: Parser<FunctionDeclaration> by -funToken and -spacesToken and identifierToken and
            // TODO arguments - right now only () is supported
            -openParenthesisToken and -closeParenthesisToken and
            -openBraceToken and
            separatedTerms(statement, spacesToken) and
            -closeBraceToken use {
        FunctionDeclaration(t1.text, t2)
    }


    override val rootParser: Parser<Program> by separatedTerms(funParser, spacesToken or commentToken) use {
        Program(functions = this)
    }
}