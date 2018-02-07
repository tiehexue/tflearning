package com.tiehexue

import org.tensorflow.Graph
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.TensorFlow

import org.bytedeco.javacpp.opencv_core.CV_8UC1
import org.bytedeco.javacpp.opencv_core.Mat

object Hello extends App {

  def tensor = {
    val g = new Graph

    val value = "Hello from " + TensorFlow.version
    // Construct the computation graph with a single operation, a constant
    val t = Tensor.create(value.getBytes("UTF-8"))
    // The Java API doesn't yet include convenience functions for adding operations.
    g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType).setAttr("value", t).build

    t.close()

    val s = new Session(g)
    val output = s.runner.fetch("MyConst").run.get(0)

    System.out.println(new String(output.bytesValue, "UTF-8"))

    s.close()
    output.close()
    g.close()
  }

  val mat = Mat.eye(3, 3, CV_8UC1)
  System.out.println("mat = " + mat.toString)

  Console.println("Hello World: " + (args mkString ", "))

}
