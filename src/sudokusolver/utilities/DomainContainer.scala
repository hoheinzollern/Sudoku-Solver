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
}