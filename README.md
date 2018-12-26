# Scala Filesystem

This is an application designed to simulate an interactive Unix-type filesystem, written in Scala.

### Data Structures

The filesystem is composed of a series of immutable data structures called __DirEntry__, which can either be a *File* or a *Directory*.

### State

The application transforms an immutable series of DirEntry data structures into a (potentially) new series of DirEntry data structures through the use of __Commands__.

### Supported Commands

1. mkdir \[argument\]: adds a *Directory* to the filesystem.
2. touch \[argument\]: adds a *File* to the filesystem.
3. ls: lists the files and directories in the current working directory.
4. pwd: prints out the current working directory.
5. cd \[argument\]: changes the current directory to the specified working directory.
    - Absolute and relative paths are both supported.
    - The relative token '.' (indicating the current directory) is supported.
    - The relative token '..' (indicating the parent directory) is supported.
6. rm \[argument\]: removes the specified DirEntry from the filesystem.
7. echo \[(optional) contents\] \[(optional) operator\] \[(optional) filename\]:
    - Without an operator specified, the echo command will simply repeat back the contents.
    - If the ">" operator is specified, contents will be written to the indicated file, which will be created as necessary.
    - If the ">>" operator is specified, contents will be appended to the indicated file, which will be created if it does not already exist.
8. cat \[argument\]: prints out the contents of the specified file.
