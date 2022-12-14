/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s

import com.stulsoft.backup.s.config.AppConfig

object App {
  def main(args: Array[String]): Unit = {
    val release = System.getProperty("release") != null
    if (release){
      System.getProperties.put("config.file",s"${System.getenv("APPDATA")}\\backup-s\\application.conf")
    }
    val configFilePath = System.getProperty("config.file")
    println(s"Getting configuration from $configFilePath")
    val appConfig = AppConfig.build()
    val copyDirectories = CopyDirectories(appConfig.directories)
    copyDirectories.makeCopy()
  }
}
