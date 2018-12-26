package commands

import files.{DirEntry, Directory}
import filesystem.State

class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    val root = state.root
    val wd = state.wd

    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    val destinationDirectory = doFindEntry(root, absolutePath)

    if (destinationDirectory == null) state.setMessage(dir + ": no such entry!")
    else if (!destinationDirectory.isDirectory) state.setMessage(dir + ": is not a directory!")
    else State(root, destinationDirectory.asDirectory)
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @scala.annotation.tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry =
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)

        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }

    @scala.annotation.tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) collapseRelativeTokens(path.tail, result)
        else collapseRelativeTokens(path.tail, result.init)
      }
      else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    val newTokens: List[String] = collapseRelativeTokens(tokens, List())

    findEntryHelper(root, newTokens)
  }
}
