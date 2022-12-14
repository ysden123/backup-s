/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s.config

import com.typesafe.config.ConfigFactory

import scala.jdk.CollectionConverters._

case class AppConfig(directories: Set[Directory])

object AppConfig {
  def build(): AppConfig = {
    val appConfigRow = ConfigFactory.load()
    val directoriesRow = appConfigRow.getConfigList("directories").asScala
    val directories = directoriesRow.map(config => {
      val name = config.getString("name")
      val source = config.getString("source")
      val destination = config.getString("destination")
      val maxBackupDirectories = config.getInt("maxBackupDirectories")
      val directoriesToSkip = if (config.hasPath("directoriesToSkip")) {
        Some(config.getStringList("directoriesToSkip").asScala.toSet)
      } else {
        None
      }
      Directory(name, source, destination, maxBackupDirectories, directoriesToSkip)
    }).toSet
    AppConfig(directories)
  }
}
