package mnist

import chisel3._

object Weights {
  val params = ujson.read(os.read(os.pwd / "params.json"))

  def toVec2D(key: String): Vec[Vec[SInt]] = {
    val raw = params(key).arr.map(_.arr.map(_.num.toInt))
    VecInit(raw.map(r => VecInit(r.map(_.S).toSeq)).toSeq)
  }

  def toVec1D(key: String): Vec[SInt] = {
    VecInit(params(key).arr.map(_.num.toInt.S).toSeq)
  }

  val l1w = toVec2D("layers.1.weight")
  val l3w = toVec2D("layers.3.weight")
  val l5w = toVec2D("layers.5.weight")
  val l1b = toVec1D("layers.1.bias")
  val l3b = toVec1D("layers.3.bias")
  val l5b = toVec1D("layers.5.bias")
}


class NeuralNet(val inSize: Int, val h1: Int, val h2: Int, val outSize: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(Vec(inSize, SInt(32.W)))
    val out = Output(Vec(outSize, SInt(32.W)))
  })

  def linear(in: Vec[SInt], weight: Vec[Vec[SInt]], bias: Vec[SInt]): Vec[SInt] = {
    VecInit(weight.zip(bias).map { case (wCol, b) =>
      in.zip(wCol).map { case (i, w) => i * w }.reduce(_ + _) + b
    })
  }

  def relu(x: Vec[SInt]): Vec[SInt] = VecInit(x.map(v => Mux(v < 0.S, 0.S, v)))

  // layer 1
  val l1 = linear(io.in, Weights.l1w, Weights.l1b)
  val a1 = relu(l1)

  // layer 2
  val l2 = linear(a1, Weights.l3w, Weights.l3b)
  val a2 = relu(l2)

  // output
  val out = linear(a2, Weights.l5w, Weights.l5b)

  io.out := out
}

