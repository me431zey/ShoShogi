package de.htwg.se.ShoShogi.controller

import de.htwg.se.ShoShogi.model._
import de.htwg.se.ShoShogi.util.Observable

import scala.collection.mutable.ListBuffer

//noinspection ScalaStyle
class Controller(var board: Board[Piece], val player_1: Player, val player_2: Player) extends Observable {
  val boardSize = 9

  def createEmptyBoard(): Unit = {
    board = new Board[Piece](boardSize, new EmptyPiece)
    notifyObservers
  }

  def createNewBoard(): Unit = {
    board = new Board[Piece](boardSize, new EmptyPiece)

    //Steine fuer Spieler 1
    board = board.replaceCell(0, 0, Lancer(player_1))
    board = board.replaceCell(1, 0, Knight(player_1))
    board = board.replaceCell(2, 0, SilverGeneral(player_1))
    board = board.replaceCell(3, 0, GoldenGeneral(player_1))
    board = board.replaceCell(4, 0, King(player_1))
    board = board.replaceCell(5, 0, GoldenGeneral(player_1))
    board = board.replaceCell(6, 0, SilverGeneral(player_1))
    board = board.replaceCell(7, 0, Knight(player_1))
    board = board.replaceCell(8, 0, Lancer(player_1))
    board = board.replaceCell(1, 1, Bishop(player_1))
    board = board.replaceCell(7, 1, Rook(player_1))
    for (i <- 0 to 8) {
      board = board.replaceCell(i, 2, Pawn(player_1))
    }

    //Steine fuer Spieler 2
    board = board.replaceCell(0, 8, Lancer(player_2))
    board = board.replaceCell(1, 8, Knight(player_2))
    board = board.replaceCell(2, 8, SilverGeneral(player_2))
    board = board.replaceCell(3, 8, GoldenGeneral(player_2))
    board = board.replaceCell(4, 8, King(player_2))
    board = board.replaceCell(5, 8, GoldenGeneral(player_2))
    board = board.replaceCell(6, 8, SilverGeneral(player_2))
    board = board.replaceCell(7, 8, Knight(player_2))
    board = board.replaceCell(8, 8, Lancer(player_2))
    board = board.replaceCell(1, 7, Bishop(player_2))
    board = board.replaceCell(7, 7, Rook(player_2))
    for (i <- 0 to 8) {
      board = board.replaceCell(i, 6, Pawn(player_2))
    }

    notifyObservers
  }

  def boardToString(): String = board.toString

  def possibleMoves(pos: (Int, Int)): List[(Int, Int)] = {
    var moveList = ListBuffer[(Int, Int)]()

    board.cell(pos._1, pos._2) match {
      case Some(piece) => {
        for ((s, o) <- piece.getMoveSet(pos._1, pos._2)) {
          board.cell(s, o) match {
            case Some(pieceDestiCell) => {
              if (pieceDestiCell.player != piece.player) {
                moveList.+=((s, o))
              }
            }
            case None =>
          }
        }
      }
      case None =>
    }

    moveList.toList
  }
}
