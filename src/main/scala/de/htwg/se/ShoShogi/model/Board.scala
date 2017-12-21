package de.htwg.se.ShoShogi.model


// TODO: Pattern welches die selbe funktion aufruft ein anderen spieler übergibt und so entscheidet aus welchem Container er etwas rausholt
// TODO: Container neue Classe? mit funktionen?

case class Board[Piece](board: Vector[Vector[Piece]], containerPlayer_0: List[Piece], containerPlayer_1: List[Piece]) {
  def this(size: Int, filling: Piece) = this(Vector.tabulate(size, size) { (row, col) => filling }, List.empty[Piece], List.empty[Piece])

  val size: Int = board.size

  def getContainer(): (List[Piece], List[Piece]) = {
    (containerPlayer_0, containerPlayer_1)
  }

  def addToPlayerContainer(player: Player, piece: Piece): Board[Piece] = {
    if (!piece.isInstanceOf[EmptyPiece]) {
      if (player.first) {
        val newCon: List[Piece] = containerPlayer_0 :+ piece
        copy(board, newCon, containerPlayer_1)
      } else {
        val newCon: List[Piece] = containerPlayer_1 :+ piece
        copy(board, containerPlayer_0, newCon)
      }
    } else {
      this
    }
  }

  def removeFromPlayerContainer(player: Player, piece: Piece): Board[Piece] = {
    if (player.first) {
      val newCon: List[Piece] = for { a <- containerPlayer_0 if a != piece } yield {
        a
      }
      copy(board, newCon, containerPlayer_1)
    } else {
      val newCon: List[Piece] = for { a <- containerPlayer_1 if a != piece } yield {
        a
      }
      copy(board, containerPlayer_0, newCon)
    }
  }

  def cell(col: Int, row: Int): Option[Piece] = {
    if (row >= size || col >= size || row < 0 || col < 0) {
      None
    } else {
      Some(board(col)(row))
    }
  }

  def replaceCell(col: Int, row: Int, cell: Piece): Board[Piece] =
    copy(board.updated(col, board(col).updated(row, cell)), containerPlayer_0, containerPlayer_1)

  override def toString: String = {
    var index: Int = 0
    val alphabet = Array[Char]('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i')
    val returnValue = new StringBuilder

    returnValue.append("Captured Player 1: ")
    containerPlayer_0.foreach(x => returnValue.append(x).append("   "))

    returnValue.append("\n   0    1    2    3    4    5    6    7    8 \n \n")

    for (a <- 1 to 19) {
      if (a % 2 == 1) {
        for (b <- 1 to 48) returnValue.append("-")
      } else {
        for (c <- 0 to 8) {
          cell(c, index) match {
            case Some(piece) => returnValue.append(" | " + piece)
            case None =>
          }
        }
        returnValue.append(" | \t" + alphabet(index))
        index += 1
      }
      returnValue.append("\n")
    }

    returnValue.append("Captured Player 2: ")
    containerPlayer_1.foreach(x => returnValue.append(x).append("   "))
    returnValue.append("\n")

    returnValue.toString()
  }
}

