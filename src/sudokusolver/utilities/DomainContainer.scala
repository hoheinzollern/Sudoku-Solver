package sudokusolver.utilities

class DomainContainer(private var domains : Array[Array[Domain]] = new Array[Array[Domain]](9,9), private var set : Boolean = false) {
	if (this.set == false) {
		for (i<-0 to 8) {
			for (j<-0 to 8) {
				this.domains(i)(j) = new Domain("(" + i + "," + j + ")")
			}
		}
		this.set = true
	}
	
	def get(x : Int, y : Int) : Domain = {
		this.domains(x)(y)
	}
	
	def get(couple : Couple) : Domain = {
		get(couple.getX, couple.getY)
	}
	
	def set(domain : Domain, x : Int, y : Int) {
		this.domains(x)(y) = domain
	}
	
	def set(domain : Domain, couple : Couple) {
		set(domain, couple.getX, couple.getY)
	}
	
	override def clone() = {
		new DomainContainer(domains.clone, true)
	}
	
	def checkEmptyDomains() = {
		var checkFailed = false
		var i = 0
		while (i < 9 && !checkFailed) {
			var j = 0
			while (j < 9 && !checkFailed) {
				if (domains(i)(j).isEmpty) checkFailed = true
				j = j+1
			}
			i = i+1
		}
		checkFailed
	}
}