package de.htwg.se.ShoShogi.controller

import de.htwg.se.ShoShogi.model._
import de.htwg.se.ShoShogi.util.Observable

import scala.collection.mutable.ListBuffer
import scala.swing.Publisher

// TODO 1: schauen ob vals und vars aus dem Klassen parameter entfernt werden koennen

//noinspection ScalaStyle
class Controller(private var board: Board, val player_1: Player, val player_2: Player) extends playerRounds with ControllerInterface {
  val playerRounds = playerRounds(board)
  val playerOnesTurn: roundState = new playerOneRound(this)
  val playerTwosTurn: roundState = new playerTwoRound(this)
  var currentState: roundState = playerOnesTurn
  val boardSize = 9
  def getContainer: (List[Piece], List[Piece]) = board.getContainer()
  var state = true

  def createEmptyBoard(): Unit = {
    board = new Board(boardSize, pieceFactory.apply("EmptyPiece", player_1))
    publish(new UpdateAll)
  }

  def createNewBoard(): Unit = {
    board = new Board(boardSize, pieceFactory.apply("EmptyPiece", player_1))

    //Steine fuer Spieler 1
    board = board.replaceCell(0, 0, pieceFactory.apply("Lancer", player_1))
    board = board.replaceCell(1, 0, pieceFactory.apply("Knight", player_1))
    board = board.replaceCell(2, 0, pieceFactory.apply("SilverGeneral", player_1))
    board = board.replaceCell(3, 0, pieceFactory.apply("GoldenGeneral", player_1))
    board = board.replaceCell(4, 0, pieceFactory.apply("King", player_1))
    board = board.replaceCell(5, 0, pieceFactory.apply("GoldenGeneral", player_1))
    board = board.replaceCell(6, 0, pieceFactory.apply("SilverGeneral", player_1))
    board = board.replaceCell(7, 0, pieceFactory.apply("Knight", player_1))
    board = board.replaceCell(8, 0, pieceFactory.apply("Lancer", player_1))
    board = board.replaceCell(7, 1, pieceFactory.apply("Bishop", player_1))
    board = board.replaceCell(1, 1, pieceFactory.apply("Rook", player_1))
    for (i <- 0 to 8) {
      board = board.replaceCell(i, 2, pieceFactory.apply("Pawn", player_1))
    }

    //Steine fuer Spieler 2
    board = board.replaceCell(0, 8, pieceFactory.apply("Lancer", player_2))
    board = board.replaceCell(1, 8, pieceFactory.apply("Knight", player_2))
    board = board.replaceCell(2, 8, pieceFactory.apply("SilverGeneral", player_2))
    board = board.replaceCell(3, 8, pieceFactory.apply("GoldenGeneral", player_2))
    board = board.replaceCell(4, 8, pieceFactory.apply("King", player_2))
    board = board.replaceCell(5, 8, pieceFactory.apply("GoldenGeneral", player_2))
    board = board.replaceCell(6, 8, pieceFactory.apply("SilverGeneral", player_2))
    board = board.replaceCell(7, 8, pieceFactory.apply("Knight", player_2))
    board = board.replaceCell(8, 8, pieceFactory.apply("Lancer", player_2))
    board = board.replaceCell(1, 7, pieceFactory.apply("Bishop", player_2))
    board = board.replaceCell(7, 7, pieceFactory.apply("Rook", player_2))
    for (i <- 0 to 8) {
      board = board.replaceCell(i, 6, pieceFactory.apply("Pawn", player_2))
    }

    publish(new UpdateAll)
    currentState.changeState()
  }

  def boardToString(): String = board.toString

  def boardToArray(): Array[Array[Piece]] = board.toArray

  def possibleMoves(pos: (Int, Int)): List[(Int, Int)] = {
    currentState.possibleMoves(pos)
  }

  def movePiece(currentPos: (Int, Int), destination: (Int, Int)): MoveResult.Value = {
    val result = currentState.movePiece(currentPos, destination)
    currentState.changeState()
    result
  }

  def possibleMovesConqueredPiece(piece: String): List[(Int, Int)] = {
    currentState.possibleMovesConqueredPiece(piece)
  }

  def moveConqueredPiece(pieceAbbreviation: String, destination: (Int, Int)): Boolean = {
    val result = currentState.moveConqueredPiece(pieceAbbreviation, destination)
    currentState.changeState()
    result
  }

  def getPossibleMvConPlayer(piece: String): List[(Int, Int)] = {
    currentState.getPossibleMvConPlayer(piece)
  }

  def promotable(position: (Int, Int)): Boolean = {
    val piece = board.cell(position._1, position._2).getOrElse(return false)
    piece.hasPromotion && ((piece.player == player_1 && position._2 > 5) || (piece.player == player_2 && position._2 < 3))
  }

  def promotePiece(piecePosition: (Int, Int)): Boolean = {
    var piece = board.cell(piecePosition._1, piecePosition._2).getOrElse(return false)
    piece = piece.promotePiece.getOrElse(return false)
    board = board.replaceCell(piecePosition._1, piecePosition._2, piece)
    publish(new UpdateAll)
    true
  }

  def changeState(): Unit = {
    currentState.changeState()
  }
}
