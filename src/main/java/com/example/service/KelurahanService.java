package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KelurahanModel;

public interface KelurahanService {
		
	KelurahanModel selectKelurahanById (BigInteger id);
	
	List<KelurahanModel> selectAllKelurahan ();
	
	List<KelurahanModel> selectAllKelurahanByKecamatanId (BigInteger id_kecamatan);
	
	
}
