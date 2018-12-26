package files

import filesystem.FilesystemException

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {

  def asDirectory: Directory = throw new FilesystemException("A file cannot be converted to a directory!")

  def asFile: File = this

  def isDirectory: Boolean = false

  def isFile: Boolean = true

  def getType: String = "File"

  def setContents(newContents: String): File = new File(parentPath, name, newContents)

  def appendContents(newContents: String): File = setContents(contents + "\n" + newContents)
}

object File {
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
