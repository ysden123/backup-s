/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import com.stulsoft.backup.s.config.Directory
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.time.DurationFormatUtils

import java.io.File
import scala.jdk.CollectionConverters._

case class CopyDirectories(directories: Set[Directory]) {
  def makeCopy(): Unit = {
    directories.foreach(directory => {
      val versionService = VersionService(directory.destination, directory.maxBackupDirectories)
      val odn = versionService.buildOutputDirectoryName()
      println()
      println(s"Copying ${directory.name}")
      println(s"   source      : ${directory.source}")
      println(s"   destination : ${directory.destination}")
      val start = System.currentTimeMillis()
      val directoryFilter = directory.directoriesToSkip match {
        case Some(set) => new DirectoryFilter(set.asJava)
        case _ => new DirectoryFilter(null)
      }
      try {
        FileUtils.deleteDirectory(new File(odn))
        FileUtils.copyDirectory(new File(directory.source), new File(odn), directoryFilter, false)
      } catch {
        case exception: Exception => exception.printStackTrace()
      }
      val duration = DurationFormatUtils.formatDuration(System.currentTimeMillis() - start,
      "HH:mm:ss,SSS")
      println()
      println(s"${directory.name} copied in $duration")
      println(s"Handled ${directoryFilter.getHandledDirectories} directories, ${directoryFilter.getHandledFiles} files.")
      println(s"Skipped ${directoryFilter.getSkippedDirectories} directories.")
    })
  }
}
