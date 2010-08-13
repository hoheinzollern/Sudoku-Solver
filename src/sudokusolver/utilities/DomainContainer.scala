package sudokusolver.utilities

class DomainContainer(private var domains : Array[Array[Domain]] = new Array[Array[Domain]](9,9), private var set : Boolean = false) {
	
	private var modifyList : List[Operation] = Nil
	
	if (this.set == false) {
		for (i<-0 to 8) {
			for (j<-0 to 8) {
				this.domains(i)(j) = new Domain("(" + i + "," + j + ")")
			}
		}
		this.set = true
	}
	
	def get(x : Int, y : Int) = {
		this.domains(x)(y)
	}
	
	def set(domain : Domain, x : Int, y : Int) {
		this.domains(x)(y) = domain
	}
	
	override def clone() = {
		new DomainContainer(domains.clone, true)
	}
	
	def addOperation(operation : Operation) {
		this.modifyList = operation :: this.modifyList
	}
	
	def back() = {
		if (this.modifyList != Nil) {
			println("Vorrei fare back, la lista è ")
			var i = this.modifyList
			while (i!=Nil) {
				var xD = i.head.getXDomain
				var yD = i.head.getYDomain
				var value = i.head.getValueDomain
				println(xD + "," + yD + " -- " + value)
				i = i.tail
			}
			var x = this.modifyList.head.getXDomain
			var y = this.modifyList.head.getYDomain
			while ((this.modifyList != Nil) && (this.modifyList.head.getXDomain == x) && (this.modifyList.head.getYDomain == y)) {
				this.modifyList.head.restore(domains)
				this.modifyList = this.modifyList.tail
			}
			//domains(x)(y) = new Domain("(" + x + "," + y + ")")
		}
		println("Dovrei aver ripristinato tutto il dominio...")
		clone
	}
}