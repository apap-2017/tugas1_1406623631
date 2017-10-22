package com.example.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	@NotNull
	private BigInteger id;
	
	@NotNull
	private BigInteger id_kota;
	
	@NotNull
	@Size(min=1, max=7)
	private String kode_kecamatan;
	
	@NotNull
	@Size(min=1, max=255)
	private String nama_kecamatan;
}
