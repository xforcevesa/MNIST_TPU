// See README.md for license details.

package neuralnet

import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.scalatest.ChiselSim
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import scala.io.Source
import mnist.NeuralNet

/**
  * To run:
  * sbt 'testOnly neuralnet.NeuralNetSpec'
  */
class NeuralNetSpec extends AnyFreeSpec with Matchers with ChiselSim {

  "Simple Neural Network should classify MNIST digits" in {
    simulate(new NeuralNet(784, 128, 64, 10)) { dut =>
      // Provide a toy input vector (flattened image)
      val input = Array.fill(784)(0.S)
      dut.io.in.poke(VecInit(input))
      dut.clock.step(1)
      val output = dut.io.out.map(_.litValue.toInt)
      println(s"Logits: ${output.mkString(", ")}")
      // Optionally assert based on expected class
    }
  }
}
