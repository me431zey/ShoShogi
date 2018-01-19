package de.htwg.se.ShoShogi

import de.htwg.se.ShoShogi.aview.Tui
import de.htwg.se.ShoShogi.aview.gui.SwingGui
import de.htwg.se.ShoShogi.controller.controllerComponent.ControllerInterface
import de.htwg.se.ShoShogi.controller.controllerComponent.controllerBaseImpl.{ Controller, UpdateAll }
import de.htwg.se.ShoShogi.model.boardComponent.boardBaseImpl.Board
import de.htwg.se.ShoShogi.model.pieceComponent.EmptyPiece
import de.htwg.se.ShoShogi.model.playerComponent.Player

import scala.swing.Publisher

object ShoShogi extends Publisher {
  val boardSize = 9
  val controller: ControllerInterface = new Controller(
    new Board(boardSize, new EmptyPiece),
    Player("Player1", true), Player("Player2", false)
  )
  val tui = new Tui(controller)
  //val gui = new SwingGui(controller)
  //listenTo(gui)
  controller.publish(new UpdateAll)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      tui.printInputMenu()
      input = scala.io.StdIn.readLine()
      tui.processInputLine(input)
    } while (input != "q")
    System.exit(0)
  }

  //reactions += { case _ => if (gui == null) System.exit(0) }
}
