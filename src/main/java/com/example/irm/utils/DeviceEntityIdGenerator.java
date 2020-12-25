package com.example.irm.utils;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;

public class DeviceEntityIdGenerator implements IdentifierGenerator
{
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj)
			throws HibernateException
	{
		return "dev-" + java.util.UUID.randomUUID().toString();
	}
}