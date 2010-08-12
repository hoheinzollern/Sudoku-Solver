package sudokusolver.utilities

class Operation(private var name : String, private var value0 : Int, private var value1 : Int, private var value2 : Int) {
	def restore(domains : Array[Array[Domain]]) {
		println("Ripristino l'operazione sul dominio...")
		//println("Prima il dominio aveva elementi " + domains(value0)(value1).getValues)
		if (this.name == "remove") {
			domains(value0)(value1).addValue(value2)
			println("Ho rimesso dentro " + value2)
		}
		println("Ora il dominio di " + domains(value0)(value1).getName + " ha elementi " + domains(value0)(value1).getValues)
	}
	def getXDomain() = {
		this.value0
	}
	def getYDomain() = {
		this.value1
	}
	def getValueDomain() = {
		this.value2
	}
	def restore(scheme : Array[Array[Int]]) {
		println("Ripristino l'operazione sullo schema...")
		if (this.name == "add") {
			scheme(value0)(value1) = value2
			println("Ho rimesso 0!")
		}
	}
}