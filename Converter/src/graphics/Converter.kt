package graphics

import java.util.DoubleSummaryStatistics
import kotlin.math.abs
import kotlin.math.max

class Converter(
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double,
    width: Int,
    height: Int,
) {
    var height: Int = 1
        get() = field
        set(value) {
            field = max(1, value)
        }

    var width: Int = 1
        get() = field
        set(value) {
            field = max(1, value)
        }

    private var yMin: Double = 0.0
    private var yMax: Double = 0.0

    private var xMin: Double = 0.0
    private var xMax: Double = 0.0

    var yEdges: Pair<Double, Double>
        get() = Pair(yMin, yMax)
        set(value) {
            if (value.first < value.second) {
                yMin = value.first
                yMax = value.second
            } else {
                yMin = value.second
                yMax = value.first
                if (abs(yMin - yMax) < 0.1){
                    yMin -= 0.1
                    yMax += 0.1
                }
            }
        }

    var xEdges: Pair<Double, Double>
        get() = Pair(xMin, xMax)
        set(value) {
            if (value.first < value.second) {
                xMin = value.first
                xMax = value.second
            } else {
                xMin = value.second
                xMax = value.first
                if (abs(xMin - xMax) < 0.1){
                    xMin -= 0.1
                    xMax += 0.1
                }
            }
        }

    val yDen
        get() = height / (yMax - yMin)

    val xDen
        get() = width / (xMax - xMin)

    init {
        this.height = height
        this.width = width
        this.xMin = xMin
        this.xMax = xMax
        this.yMin = yMin
        this.yMax = yMax
        yEdges = Pair(yMin, yMax)
        xEdges = Pair(xMin, xMax)
    }

    public fun yCrtToScr(y: Double): Int{
        var res = ((yMax - y) * yDen)
        if (res < -height) res = (-1.0 * height)
        if (res > 2 * height) res = (2.0 * height)
        return res.toInt()
    }

    public fun xCrtToScr(x: Double): Int {
        var res = ((x - xMin) * xDen)
        if (res < -width) res = (-1.0 * width)
        if (res > 2 * width) res = (2.0 * width)
        return res.toInt()
    }

    //public fun yScrToCrt(y: Int): Double
    //    = -y * 1.0 / yDen + yMax
    public fun yScrToCrt(y: Int): Double{
        var res = y
        if (res < - height) res = -height
        if (res > 2 * height) res = 2 * height
        return yMax - y / yDen
    }

    //public fun xScrToCrt(x: Int): Double
    //        = x * 1.0/ xDen + xMin
    public fun xScrToCrt(x: Int): Double{
        var res = x
        if (res < - width) res = -width
        if (res > 2 * width) res = 2 * width
        return x / xDen + xMin
    }
}