package com.tiehexue.opencv.preproc

import com.tiehexue.opencv.util.MatWrapper
import com.tiehexue.util.Tesseract
import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_imgcodecs._
import org.bytedeco.javacpp.opencv_imgproc._

object PicPre extends App with MatWrapper {

  val src = imread("/Users/wy/Desktop/test.jpg")
  src.show()
  // imshow("original", mat)

  // val rsz = new Mat()
  // resize(src, rsz, new Size(800, 900))
  // rsz.show()

  val gray = new Mat()
  cvtColor(src, gray, CV_BGR2GRAY)
  gray.show()

  val bw = new Mat()
  adaptiveThreshold(~gray, bw, 255, CV_ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, -2)
  bw.show()

  val horizontal = bw.clone()
  val vertical = bw.clone()
  val scale = 15
  val horizontalSize = horizontal.cols / scale
  val hStructure = getStructuringElement(MORPH_RECT, new Size(horizontalSize, 1))
  erode(horizontal, horizontal, hStructure)
  dilate(horizontal, horizontal, hStructure)
  horizontal.show()

  val verticalSize = vertical.rows / scale / 2
  val vStructure = getStructuringElement(MORPH_RECT, new Size(1, verticalSize), new Point(-1, -1))
  erode(vertical, vertical, vStructure)
  dilate(vertical, vertical, vStructure)
  vertical.show()

  val mask = horizontal + vertical
  mask.show()

  val joints = new Mat()
  bitwise_and(horizontal, vertical, joints)
  joints.show()

  val contours = new MatVector()
  val hierarchy = new Mat()
  findContours(mask, contours, RETR_LIST, CHAIN_APPROX_NONE, new Point(0, 0))

  val contours_poly = new MatVector(contours.size)
  (0 until contours.size.toInt).foreach { i =>
    val c = contours.get(i)
    val area = contourArea(c)

    if (area > 10) {
      val poly = new Mat()
      approxPolyDP(c, poly, 3, true)
      val boundRect = boundingRect(poly)

      // find the number of joints that each table has
      val roi = new Mat(joints, boundRect)
      val joints_contours = new MatVector()
      findContours(roi, joints_contours, RETR_CCOMP, CHAIN_APPROX_SIMPLE)

      // if the number is not more than 5 then most likely it not a table
      val fileName = s"./output/table_${i}_${boundRect.x}-${boundRect.y}-${boundRect.width}-${boundRect.height}.jpg"
      if (joints_contours.size == 4) {
        imwrite(fileName, new Mat(src, boundRect))

        try {
          val result = Tesseract.getText(fileName)
          System.out.println(s"${fileName} -> ${result}")
        } catch {
          case e: Throwable =>
            System.err.println(e.getMessage)
        }
      }
    }
  }
}
