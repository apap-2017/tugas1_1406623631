package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KotaModel;

public interface KotaService {

	KotaModel selectKotaById (BigInteger id);
	
	List<KotaModel> selectAllKota ();
	

}
