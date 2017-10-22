package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KecamatanModel;

public interface KecamatanService {

	KecamatanModel selectKecamatanById (BigInteger id);
	
	List<KecamatanModel> selectAllKecamatanByKotaId (BigInteger id_kota);
	
	
}
