/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import com.stulsoft.backup.s.config.AppConfig

object App {
  def main(args: Array[String]): Unit = {
    val appConfig = AppConfig.build()
    println("Processing following configuration:")
    println(appConfig)
    val copyDirectories = CopyDirectories(appConfig.directories)
    copyDirectories.makeCopy()
  }
}
