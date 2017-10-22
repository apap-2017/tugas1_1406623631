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
public class KelurahanModel {
	@NotNull
	private BigInteger id;
	
	@NotNull
	private BigInteger id_kecamatan;
	
	@NotNull
	@Size(min=1, max=10)
	private String kode_kelurahan;
	
	@NotNull
	@Size(min=1, max=255)
	private String nama_kelurahan;
	
	@NotNull
	@Size(min=5, max=5)
	private String kode_pos;
}
