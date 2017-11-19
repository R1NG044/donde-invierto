package edu.utn.frba.dds.grupo5.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class TimestampPersistent extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6011752825242679352L;
	private Date lastUpdate;
	
	@PreUpdate
	public void preUpdate(){
		setLastUpdate(new Date());
	}
	
	@PrePersist
	public void prePersist(){
		setLastUpdate(new Date());
	}

	@Column(name="per_last_update",nullable=true)
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
