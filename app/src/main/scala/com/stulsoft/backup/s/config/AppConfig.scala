/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.backup.s.config

import org.json4s._
import org.json4s.jackson.JsonMethods._

import java.io.FileNotFoundException
import java.nio.file.NoSuchFileException
import scala.io.Source
import scala.util.{Failure, Success, Using}

case class AppConfig(directories: Set[Directory])

object AppConfig {
  def build(): AppConfig = {
    implicit val formats: DefaultFormats = DefaultFormats
    try {
      val jsonObject: JValue =
        if (System.getProperty("release") == null) {
          println("Getting configuration from the resource")
          parse(Source.fromResource("configuration.json").getLines().mkString)
        } else {
          val configPath = s"${System.getenv("APPDATA")}\\backup-s\\configuration.json"
          println(s"Getting configuration from $configPath")
          Using(Source.fromFile(configPath)) {
            source => parse(source.getLines().mkString)
          } match {
            case Success(jValue) => jValue
            case Failure(exception) => exception match {
              case _ @ (_: NoSuchFileException | _: FileNotFoundException) =>
                println(s"Cannot find $configPath")
                JArray(Nil)
              case x =>
                x.printStackTrace()
                JArray(Nil)
            }
          }
        }
      val directories: Set[Directory] = jsonObject.children.map(directory => directory.extract[Directory]).toSet
      AppConfig(directories)
    } catch {
      case e: Exception =>
        println(s"sError: ${e.getMessage}")
        AppConfig(Set())
    }
  }
}
