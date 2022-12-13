/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import com.stulsoft.backup.s.config.AppConfig

import scala.jdk.CollectionConverters.CollectionHasAsScala

object App {
  def main(args: Array[String]): Unit = {
    val appConfig = AppConfig.getAppConfig
    val sss = appConfig.getDirectories.asScala.toSet
    val copyDirectories = CopyDirectories(sss)
    copyDirectories.makeCopy()
  }
}
