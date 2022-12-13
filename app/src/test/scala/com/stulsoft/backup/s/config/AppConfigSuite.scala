/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s.config

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AppConfigSuite extends AnyFunSuite {
  test("AppConfig should return AppConfig") {
    val appConfig = AppConfig.getAppConfig
    assert(appConfig != null)
    val directories = appConfig.getDirectories
    assert(!directories.isEmpty)
    val firstDirectory = directories.get(0)
    assert(firstDirectory.getName.equals("backup-s"))
    val secondDirectory = directories.get(1)
    assert(secondDirectory != null)
    assert(secondDirectory.getDirectoriesToSkip == null)
  }
}
