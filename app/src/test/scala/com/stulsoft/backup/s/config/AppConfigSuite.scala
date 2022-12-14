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
    val appConfig = AppConfig.build()
    assert(appConfig != null)
    val directories = appConfig.directories
    assert(directories.nonEmpty)
    val firstDirectory = directories.head
    assert(firstDirectory.name == "backup-s")
    val secondDirectory = directories.last
    assert(secondDirectory != null)
    assert(secondDirectory.directoriesToSkip.isEmpty)
  }
}
