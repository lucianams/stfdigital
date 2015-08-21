package br.jus.stf.shared.domain.model;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * @author Rafael.Alencar
 * @version 1.0
 * @created 14-ago-2015 18:33:46
 */
public class ClasseId implements ValueObject<ClasseId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sigla;

	public ClasseId(final String sigla){
		this.sigla = sigla;
	}

	public String sigla(){
		return sigla;
	}

	/**
	 * 
	 * @param o
	 */
	public boolean equals(final Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
	
		ClasseId other = (ClasseId) o;
	
		return sameValueAs(other);
	}

	public int hashCode(){
		return sigla.hashCode();
	}

	/**
	 * 
	 * @param other
	 */
	public boolean sameValueAs(final ClasseId other){
		return other != null && this.sigla.equals(other.sigla);
	}

	public String toString(){
		return sigla;
	}

}