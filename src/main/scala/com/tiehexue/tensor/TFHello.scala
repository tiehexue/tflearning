package com.tiehexue.tensor

import com.tiehexue.opencv.util.MatWrapper
import org.tensorflow.{Graph, Session, Tensor, TensorFlow}

object TFHello extends App with MatWrapper {

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

  tensor
}
