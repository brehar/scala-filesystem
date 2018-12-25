package commands

import filesystem.State

class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
    if (args.isEmpty) state
    else if (args.tail.isEmpty) state.setMessage(args.head)
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContent(args, args.length - 2)

      if (">>".equals(operator)) doEcho(state, contents, filename, append = true)
      else if (">".equals(operator)) doEcho(state, contents, filename, append = false)
      else state.setMessage(createContent(args, args.length))
    }
  }

  def createContent(args: Array[String], topIndex: Int): String = {
    @scala.annotation.tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      val spaceIfNecessary: String =
        if (currentIndex == 0) ""
        else " "

      if (currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator + spaceIfNecessary + args(currentIndex))
    }

    createContentHelper(0, "")
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = ???
}
