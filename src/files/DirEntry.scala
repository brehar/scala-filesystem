package files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory

  def asFile: File

  def isDirectory: Boolean

  def isFile: Boolean

  def getType: String
}
