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
public class KotaModel {
	@NotNull
	private BigInteger id;

	@NotNull
	@Size(min=1, max=10)
	private String kode_kota;
	
	@NotNull
	@Size(min=1, max=255)
	private String nama_kota;
}
