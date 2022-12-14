package math.polinomial

class Newton (val coeffMap : MutableMap<Double, Double>) : Polynomial() {

    private val nodeX : MutableList<Double> = arrayListOf()
    private val nodeY : MutableList<Double> = arrayListOf()

    public val xList: MutableList<Double>
        get() = nodeX

    public val yList: MutableList<Double>
        get() = nodeY

    constructor(): this(mutableMapOf(0.0 to 0.0)){
        _coeff.clear()
        nodeX.clear()
        nodeY.clear()
    }

    init{
        nodeX.addAll(coeffMap.keys)
        nodeY.addAll(coeffMap.values)
        var timePoly = Polynomial(1.0)
        val newtonP = timePoly * nodeY.first()
        for (i in 1 .. coeffMap.keys.size - 1)
        {
            timePoly *= Polynomial(-nodeX[i-1], 1.0)
            newtonP += timePoly * getDivDiff(i)
        }
        _coeff.clear()
        _coeff.addAll(newtonP.coeff);
    }

    private fun getDivDiff(k : Int) : Double
    {
        var result = 0.0
        for (j in 0 .. k)
        {
            var tempResult = 1.0
            for (i in 0 .. k)
            {
                if (i != j)
                    tempResult *= 1.0 / (nodeX[j] - nodeX[i])
            }
            result += nodeY[j] * tempResult
        }
        return result
    }

    public fun addNode(x: Double, y: Double) : Unit
    {
        var res = Polynomial(1.0)
        nodeX.forEach{ v ->
            res *= Polynomial(-v, 1.0)
        }
        nodeX.add(x)
        nodeY.add(y)
        res.timesAssign(getDivDiff())
        res.plusAssign( this)
        _coeff.clear()
        _coeff.addAll(0, res.coeff)
    }

    public fun deleteNode(i: Int) : Unit
    {
        nodeX.removeAt(i)
        nodeY.removeAt(i)
        if (nodeX.size == 0)
        {
            _coeff.clear()
            return
        }
        val map = mutableMapOf<Double,Double>()
        nodeX.forEachIndexed{ j, v -> map.put(nodeX[j], nodeY[j]) }
        val res = Newton(map)
        _coeff.clear()
        _coeff.addAll(0, res.coeff)
    }

    public fun clearPolynomial() : Unit
    {
        nodeX.clear()
        nodeY.clear()
        _coeff.clear()
    }

    private fun getDivDiff() : Double
    {
        var result = 0.0
        val sz = nodeX.size - 1
        for (j in 0 .. sz)
        {
            var tempResult = 1.0
            for (i in 0 .. sz)
            {
                if (i != j)
                    tempResult *= 1.0 / (nodeX[j] - nodeX[i])
            }
            result += nodeY[j] * tempResult
        }
        return result
    }
}