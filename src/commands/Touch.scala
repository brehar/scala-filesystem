package commands

import files.{DirEntry, File}
import filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = File.empty(state.wd.path, name)
}
