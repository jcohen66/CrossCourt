name := "CrossCourt"

version := "1.0"

scalaVersion := "2.11.8"


sbtVersion := "0.13.5"

// resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= Seq(
//  "org.scala-lang" % "scala-reflect" % "2.11.8",
//  "org.scala-lang" % "scala-compiler" % "2.11.8",
//  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
  "com.typesafe.akka" % "akka-stream-experimental_2.11" % "1.0-M2",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M2",
//"com.typesafe.akka" % "akka-http-experimental_2.11" % "1.0-M2",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
//  "com.typesafe.akka" % "akka-remote_2.11" % "2.4.2",
  "com.typesafe.akka" % "akka-cluster_2.11" % "2.4.2",
//  "com.typesafe.akka" % "akka-cluster-tools_2.11" % "2.4.2",
//  "com.typesafe.akka" % "akka-cluster-sharding_2.11" % "2.4.2",
  "com.typesafe.akka" % "akka-persistence_2.11" % "2.4.2",
  "com.typesafe.akka" % "akka-contrib_2.11" % "2.4.2",
//  "org.iq80.leveldb" % "leveldb" % "0.7",
//  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
//  "com.github.dnvriend" %% "akka-persistence-inmemory" % "1.2.11",
  "net.sf.ehcache" % "ehcache" % "2.7.4",
//  "javax.transaction" % "jta" % "1.1",
//  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.0" % "test",
//  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "com.typesafe.play" % "play-json_2.11" % "2.4.0-M2",
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)

//libraryDependencies += "org.specs2" % "specs2_2.9.1" % "1.8"
//libraryDependencies += "org.scalafx" % "scalafx_2.11" % "8.0.60-R9"
//libraryDependencies += "org.scalamock" % "scalamock-scalatest-support_2.11" % "3.2.2"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.19"
// libraryDependencies += "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M2"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"
