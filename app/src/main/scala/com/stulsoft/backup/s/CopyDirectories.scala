/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import com.stulsoft.backup.s.config.Directory
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.time.DurationFormatUtils

import java.io.File

case class CopyDirectories(directories: Set[Directory]) {
  def makeCopy(): Unit = {
    directories.foreach(directory => {
      val versionService = VersionService(directory.getDestination, directory.getMaxBackupDirectories)
      val odn = versionService.buildOutputDirectoryName()
      println()
      println(s"Copying ${directory.getName}")
      println(s"   source      : ${directory.getSource}")
      println(s"   destination : ${directory.getDestination}")
      val start = System.currentTimeMillis()
      val directoryFilter = new DirectoryFilter(directory.getDirectoriesToSkip)
      try {
        FileUtils.deleteDirectory(new File(odn))
        FileUtils.copyDirectory(new File(directory.getSource), new File(odn), directoryFilter, false)
      } catch {
        case exception: Exception => exception.printStackTrace()
      }
      val duration = DurationFormatUtils.formatDuration(System.currentTimeMillis() - start,
      "HH:mm:ss,SSS")
      println()
      println(s"${directory.getName} copied in $duration")
      println(s"Handled ${directoryFilter.getHandledDirectories} directories, ${directoryFilter.getHandledFiles} files.")
      println(s"Skipped ${directoryFilter.getSkippedDirectories} directories.")
    })
  }
}
