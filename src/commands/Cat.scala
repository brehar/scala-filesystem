package commands

import filesystem.State

class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    val dirEntry = wd.findEntry(filename)

    if (dirEntry == null) state.setMessage(filename + ": no such entry!")
    else if (!dirEntry.isFile) state.setMessage(filename + ": is not a file!")
    else state.setMessage(dirEntry.asFile.contents)
  }
}
