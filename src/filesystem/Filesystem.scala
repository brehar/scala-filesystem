package filesystem

import commands.Command
import files.Directory

object Filesystem extends App {
  val root = Directory.ROOT
  val initialState = State(root, root)

  initialState.show()

  io.Source.stdin.getLines().foldLeft(initialState)((currentState, newLine) => {
    val newState = Command.from(newLine).apply(currentState)

    newState.show()
    newState
  })
}
