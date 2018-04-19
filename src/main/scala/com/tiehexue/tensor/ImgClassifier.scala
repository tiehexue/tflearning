package com.tiehexue.tensor

import java.nio.file.{Files, Path}

import javax.imageio.ImageIO
import java.io.File

import org.tensorflow.{DataType, Graph, Session, Tensors}

object ImgClassifier extends App {

  val root = "/Users/wy/telecom/data/lymphoma"
  val cll = s"${root}/CLL"
  val fl = s"${root}/FL"
  val mcl = s"${root}/MCL"

  val tensors = Map(
    Tensors.create(Array(1f, 0f, 0f)) -> cll,
    Tensors.create(Array(0f, 1f, 0f)) -> fl,
    Tensors.create(Array(0f, 0f, 1f)) -> mcl)
    .map(x => Files.list(new File(x._2).toPath).toArray.map(_.asInstanceOf[Path])
    .filter(_.getFileName.toString.endsWith("tif")).map{ f1 =>

    val tif = ImageIO.read(f1.toFile)
    val newName = "output/" + f1.getFileName.toString.replace(".tif", ".jpg")
    ImageIO.write(tif, "jpeg", new File(newName))

    val bytes = Files.readAllBytes(new File(newName).toPath)

    val g = new Graph
    val b = new GraphBuilder(g)
    val input = b.constant("input", bytes)
    val output = b.cast(b.decodeJpeg(input, 0), DataType.FLOAT)

    // Session s = new Session(g)) {
    //                return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
    val sess = new Session(g)
    val what = sess.runner.fetch(output.op.name).run().get(0).expect(classOf[java.lang.Float])

    println(what.toString)

    x._1 -> what
  }).flatten

  println(tensors.head._1.toString + " -> " + tensors.head._2.toString)
}
