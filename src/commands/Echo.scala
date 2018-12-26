package commands

import files.{Directory, File}
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

  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)

      if (dirEntry == null) currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if (dirEntry.isDirectory) currentDirectory
      else {
        if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
      }
    }
    else {
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)

      if (newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)
    }
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if (filename.contains(Directory.SEPARATOR))
      state.setMessage(filename + " must not contain directory separators!")
    else if (checkIllegal(filename)) state.setMessage(filename + ": illegal entry name!")
    else {
      val newRoot: Directory =
        getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)

      if (newRoot == state.root) state.setMessage(filename + ": no such file!")
      else State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  def checkIllegal(name: String): Boolean = name.contains(".")
}
