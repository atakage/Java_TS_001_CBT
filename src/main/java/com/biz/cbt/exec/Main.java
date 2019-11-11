package com.biz.cbt.exec;

import com.biz.cbt.service.CbtService;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		CbtService cs = new CbtService();
		
		cs.cbtMenu();
	}

}


/*
		과제 진행을 하며 아쉬웠던 점
		-과제 주제를 받고 어떻게 구현할 것인가에 대한 생각은 하지 않고 무작정 코드를 입력해 나갔고 80%까지는 계획대로 돼가는 것처럼 보였으나
			나머지 20% 구현을 앞두고 풀기 힘든 문제들에 자꾸 부딪힘
			이런 문제들이 곧 설계에 대한 고민 없이 막무가내로 코딩헸던 대가라는 것을 깨닫게 되었으나 이미 때는 늦음
			갈아엎으려고 하니 이제까지 했던 게 아깝고 막막해서 아쉬운 대로 마무리하였음 
			
			
		-코드를 작성하기 전 충분히 고민하는 자세가 필요

*/