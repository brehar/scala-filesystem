package commands

import filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"

  def emptyCommand: Command = (state: State) => state.setMessage("")

  def incompleteCommand(name: String): Command = (state: State) => state.setMessage(name + ": incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }
    else if (LS.equals(tokens(0))) new Ls
    else if (PWD.equals(tokens(0))) new Pwd
    else new UnknownCommand
  }
}
