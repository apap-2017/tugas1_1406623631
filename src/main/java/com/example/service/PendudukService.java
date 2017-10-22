package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.PendudukModel;

public interface PendudukService {

	PendudukModel selectPenduduk (String nik);
	
	List<PendudukModel> selectPendudukFromKeluarga (BigInteger id_keluarga);
	
	BigInteger countAllPenduduk ();
	
	int countCurrentPendudukOnQuery(String query);
	
	void addPenduduk (PendudukModel penduduk);
	
	void updatePenduduk (PendudukModel penduduk);
	
	void setPendudukWafat (BigInteger id);
	
	
}
