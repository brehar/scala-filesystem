package commands

import files.{DirEntry, Directory}
import filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State, entryName: String): DirEntry =
    Directory.empty(state.wd.path, entryName)
}
