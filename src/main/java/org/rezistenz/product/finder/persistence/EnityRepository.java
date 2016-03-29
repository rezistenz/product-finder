package org.rezistenz.product.finder.persistence;


public interface EnityRepository<ENITY_TYPE, PK_TYPE> {
	ENITY_TYPE add(ENITY_TYPE entity);
	ENITY_TYPE udpate(ENITY_TYPE entity);
	ENITY_TYPE findByPK(PK_TYPE primaryKey);
}
