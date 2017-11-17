package de.htwg.se.ShoShogi

import de.htwg.se.ShoShogi.aview.Tui
import de.htwg.se.ShoShogi.controller.Controller
import de.htwg.se.ShoShogi.model.{ Board, EmptyPiece, Player }

object ShoShogi {
  val boardSize = 9
  val controller = new Controller(new Board(boardSize, new EmptyPiece), Player("Player1", true), Player("Player2", false))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      tui.printInputMenu()
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
