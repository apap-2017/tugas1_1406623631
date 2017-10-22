package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KeluargaModel;

public interface KeluargaService {

	KeluargaModel selectKeluarga (String nkk);
	
	KeluargaModel selectKeluargaById (BigInteger id);
	
	List<KeluargaModel> selectAllKeluargaByKelurahanId (BigInteger id_kelurahan);
	
	BigInteger countAllKeluarga ();
	
	int countCurrentKeluargaOnQuery (String query);
	
	void addKeluarga (KeluargaModel keluarga);
	
	void updateKeluarga (KeluargaModel keluarga);
	
	void setKeluargaActive (BigInteger id);
	
	void setKeluargaInactive (BigInteger id);
	
}
