package com.tiehexue.tensor

import org.tensorflow.types.UInt8
import org.tensorflow.{DataType, Graph, Output, Tensor}

case class GraphBuilder(g: Graph) {

  // Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
  //            return g.opBuilder("DecodeJpeg", "DecodeJpeg")
  //                    .addInput(contents)
  //                    .setAttr("channels", channels)
  //                    .build()
  //                    .<UInt8>output(0);
  //        }

  def decodeJpeg(contents: Output[String], channels: Long) = {
    g.opBuilder("DecodeJpeg", "DecodeJpeg")
      .addInput(contents).setAttr("channels", channels).build.output[UInt8](0)
  }

  def cov2d(contents: Output[String]) = {
    g.opBuilder("Conv2D", "Conv2D").addInput(contents)
  }

  def cast[T, U](value: Output[T], typ: DataType) = {
    g.opBuilder("Cast", "Cast").addInput(value).setAttr("DstT", typ)
      .build.output[U](0)
  }

  def constant(name: String, value: Array[Byte]) = {
    val t = Tensor.create(value, classOf[String])
    g.opBuilder("Const", name).setAttr("dtype", DataType.fromClass(classOf[String]))
      .setAttr("value", t).build.output[String](0)
  }
}
