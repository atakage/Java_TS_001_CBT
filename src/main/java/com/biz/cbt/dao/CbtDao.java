package com.biz.cbt.dao;

import java.util.List;

import com.biz.cbt.persistence.CbtDTO;

public interface CbtDao {
	
	
	public List<CbtDTO> selectAll();
	public List<CbtDTO> selectToMaxCode();
	public int insert(CbtDTO dto);
	public CbtDTO selectByCode(String cb_code);
	public int update(CbtDTO dto);
	public int delete(String cb_code);
	public CbtDTO selectByMC(String strMC);		// 나의 답안을 넘겨 해당 문제DTO 추출 

}
