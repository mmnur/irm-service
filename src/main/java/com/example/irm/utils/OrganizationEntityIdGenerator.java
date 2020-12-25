package com.example.irm.utils;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;

public class OrganizationEntityIdGenerator implements IdentifierGenerator
{
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj)
			throws HibernateException
	{
		return "org-" + java.util.UUID.randomUUID().toString();
	}
}