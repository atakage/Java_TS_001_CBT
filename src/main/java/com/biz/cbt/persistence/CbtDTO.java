package com.biz.cbt.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class CbtDTO {

	private String cb_code;// varchar(5) PK 
	private String cb_q;// varchar(125) 
	private String cb_af;// varchar(125) 
	private String cb_a1;// varchar(125) 
	private String cb_a2;// varchar(125) 
	private String cb_a3;// varchar(1
}
