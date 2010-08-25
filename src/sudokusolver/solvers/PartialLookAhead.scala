package sudokusolver.solvers

import sudokusolver.utilities

class PartialLookAhead extends PropagationAlgorithm {	
	override def prop(item: utilities.Couple) = {
	  var failure = false
	  var k = item.next
	  while (k.isValid && !failure) {
	 	  revise(item, k)
	 	  if (this.domains.get(k).isEmpty) failure = true
	 	  k = k.next
	  }
	  if (!failure) {
	 	  failure = darc(item.next, failure)
	  }
	  failure
	}
	
	def darc(item : utilities.Couple, failure : Boolean) = {
		var j = new utilities.Couple(8, 8)
		var i = new utilities.Couple(0, 0)
		while (!j.equals(new utilities.Couple(0, 1))) {
			while (!i.equals(new utilities.Couple(8,7))) {
				// For every element of i_Domain
				// if EVERY element of j_Domain is not compatible
				// so i_element must be removed from i_Domain
				var iElements = this.domains.get(i).getValues
				var iElement = iElements.head
				iElements = iElements.tail
				while (iElement != Nil) {
					var jElements = this.domains.get(j).getValues
					var jElement = jElements.head
					jElements = jElements.tail
					var allJsAreIncompatible = true
					while (jElement != Nil && allJsAreIncompatible) {
						// TODO
						//if (checkConsistency(iElement, jElement)) allJsAreIncompatible = false
						jElement = jElements.head
						if (jElements != Nil) jElements = jElements.tail
					}
					if (allJsAreIncompatible) this.domains.get(i).deleteValue(iElement)
					iElement = iElements.head
					if (iElements != Nil) iElements = iElements.tail
				}
				i = i.next
			}
			j = j.previous
		}
		failure
	}
}
