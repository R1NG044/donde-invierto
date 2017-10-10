package edu.utn.frba.dds.grupo5.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Persistent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8163469738603516998L;
	private Long oid;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "oid", insertable=true, updatable=true, unique=true, nullable=false)
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.oid != null && obj != null && obj instanceof Persistent && this.oid.equals(((Persistent)obj).getOid() )) ||super.equals(obj);
	}


}
