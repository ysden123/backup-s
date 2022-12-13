/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import org.apache.commons.io.FileUtils

import java.io.File
import java.nio.file.{Files, NoSuchFileException, Path, Paths}
import java.text.SimpleDateFormat
import java.util.Calendar
import scala.util.{Failure, Success, Using}

case class VersionService(destinationDirectory: String, maxBackupDirectories: Int) {
  def buildOutputDirectoryName(): String = {
    if (findNumberOfExistingBackups() >= maxBackupDirectories) {
      findOldestBackupDirectory()
        .foreach(p => {
          try {
            println(s"going to delete $p")
            FileUtils.deleteQuietly(p.toFile)
          } catch {
            case exception: Exception => exception.printStackTrace()
          }
        })
    }

    Paths.get(destinationDirectory, buildOutputBackupDirectoryName()).toString
  }

  private def findNumberOfExistingBackups(): Long = {
    Using(Files.list(new File(destinationDirectory).toPath)) {
      list => {
        list.filter(f => Files.isDirectory(f)).count()
      }
    } match {
      case Success(value) => value
      case Failure(exception) => exception match {
        case _: NoSuchFileException => 0
        case x =>
          x.printStackTrace()
          0
      }
    }
  }

  private def findOldestBackupDirectory(): Option[Path] = {
    Using(Files.list(new File(destinationDirectory).toPath)) {
      list => {
        list.filter(f => Files.isDirectory(f)).min(new CreationTimeComparator())
      }
    } match {
      case Success(value) => Some(value.get())
      case Failure(exception) =>
        exception.printStackTrace()
        None
    }
  }

  private def buildOutputBackupDirectoryName(): String = {
    val calendar = Calendar.getInstance()
    val formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
    formatter.format(calendar.getTimeInMillis)
  }
}
