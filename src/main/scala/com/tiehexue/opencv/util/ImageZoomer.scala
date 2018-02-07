package com.tiehexue.opencv.util

import java.awt.event.{MouseWheelEvent, MouseWheelListener}
import java.awt.image.BufferedImage
import java.awt.{BorderLayout, Image}
import javax.swing.{JFrame, JScrollPane, _}

case class ImageZoomer(img: BufferedImage) {

  var zoom = 1.0

  val frame = new JFrame()
  frame.setTitle("Image zoomer")
  frame.setBounds(50, 50, 1000, 800)
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val scrollPane = new JScrollPane
  frame.getContentPane.add(scrollPane, BorderLayout.CENTER)

  val panel = new JPanel()
  panel.setLayout(new BorderLayout())

  panel.addMouseWheelListener(new MouseWheelListener() {
    def mouseWheelMoved(e: MouseWheelEvent) = {
      val notches = e.getWheelRotation()
      zoom -= notches * 0.2
      resizeImage(zoom)
    }
  })

  def resizeImage(z: Double) = {

    val newImage = img.getScaledInstance((img.getWidth * z).toInt, (img.getHeight * z).toInt, Image.SCALE_DEFAULT)

    panel.removeAll()

    val icon = new ImageIcon(newImage)
    val label = new JLabel(icon)

    label.setIcon(icon);
    panel.add(label, BorderLayout.CENTER)
    scrollPane.setViewportView(panel)
  }

  def show() = {
    val w = img.getWidth.toDouble / 950
    val h = img.getHeight.toDouble / 700

    resizeImage(1.0 / math.max(w, h))
    frame.setVisible(true)
  }

}
