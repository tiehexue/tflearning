package com.tiehexue.opencv.util

import org.bytedeco.javacpp.opencv_core.{Mat, add, not}

trait MatWrapper {
  implicit class Mat4Easy(mat: Mat) {

    import java.awt.image.{BufferedImage, DataBufferByte}

    def image = {

      var typ = BufferedImage.TYPE_BYTE_GRAY
      if (mat.channels > 1) typ = BufferedImage.TYPE_3BYTE_BGR
      val bufferSize = mat.channels * mat.cols * mat.rows
      val b = new Array[Byte](bufferSize)
      mat.data.get(b) // get all the pixels

      val image = new BufferedImage(mat.cols, mat.rows, typ)
      val targetPixels = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
      System.arraycopy(b, 0, targetPixels, 0, b.length)

      image
    }

    def show() = {
      ImageShower(image).show()
    }

    def +(other: Mat) = {
      add(mat, other).asMat
    }

    def unary_~ = {
      not(mat).asMat
    }
  }
}
