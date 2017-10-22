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
public class KeluargaModel {
	@NotNull
	private BigInteger id;
	
	@NotNull
	@Size(min=16, max=16)
	private String nomor_kk;
	
	@NotNull
	@Size(min=1, max=256)
	private String alamat;
	
	@NotNull
	@Size(min=3, max=3)
	private String rt;
	
	@NotNull
	@Size(min=3, max=3)
	private String rw;
	
	@NotNull
	private BigInteger id_kelurahan;
	
	@NotNull
	private int is_tidak_berlaku;
}
