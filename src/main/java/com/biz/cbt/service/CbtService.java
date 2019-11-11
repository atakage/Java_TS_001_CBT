package com.biz.cbt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.biz.cbt.config.DBConnection;
import com.biz.cbt.dao.CbtDao;
import com.biz.cbt.persistence.CbtDTO;

public class CbtService {

	CbtDao cbtDao = null;
	Scanner scan = null;
	int intScore = 0;
	int qNumber = 1;
	int int5Count = 0; // 5문제 해결 뒤 이력 출력용 판별 변수

	int intOF = 0;

	public CbtService() {
		// TODO Auto-generated constructor stub

		cbtDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(CbtDao.class);
		scan = new Scanner(System.in);
	}

	public void cbtMenu() {

		while (true) {

			try {

				System.out.println("===================================");
				System.out.println("1.문제입력 2.문제풀이 0.종료");
				System.out.println("====================================");
				System.out.print(">> ");
				String strC = scan.nextLine();

				int intC = Integer.valueOf(strC);

				if (intC == 0)
					break;

				if (intC == 1) {

					this.cbtMenu1();

					return;

				}

				else if (intC == 2) {

					this.cbtMenu2();

					return;
				}

			} catch (Exception e) {
				// TODO: handle exception
				
				System.out.println("커맨드 제대로 입력하시오");
			}

		}

	}

	public void cbtMenu1() {

		while (true) {

			try {

				System.out.println("===================================");
				System.out.println("1.문제등록 2.문제수정 3.문제삭제 0.종료");
				System.out.println("====================================");
				System.out.print(">> ");
				String strC = scan.nextLine();

				int intC = Integer.valueOf(strC);

				if (intC == 0)
					break;

				if (intC == 1) {

					List<CbtDTO> cbtList = cbtDao.selectToMaxCode();
					List<Integer> codeList = new ArrayList<Integer>();

					if (cbtList == null) {
						System.out.println("문제 데이터가 없음, 문제 최초 등록은 데이터베이스를 통하여 입력하시오 ");
						System.out.println("입력 시 문제 코드를 이와 같이 추가하시오: Q0001");
						continue;
					}

					for (CbtDTO dto : cbtList) { // 테이블에서 문제 코드값(숫자)을 추출해 다시 코드리스트에 저장

						String strcb_code = dto.getCb_code();

						String strcb_code2 = strcb_code.substring(1, 5);

						int intcb_code = Integer.valueOf(strcb_code2);

						codeList.add(intcb_code);

					}

					int codeMax = Collections.max(codeList); // 코드값 중 가장 큰 값을 추출해 +1

					String strcb_code = String.format("Q%04d", codeMax + 1); // 최종 신규 코드값

					System.out.println(strcb_code + ", 문제 등록 시작");
					System.out.print("문제 입력(최대 125자)>> ");
					String strcb_q = scan.nextLine();

					if (strcb_q.trim().isEmpty() || strcb_q.length() > 125) { // 공백 체크, 글자 수 체크
						System.out.println("125자 이하로 무엇이든 입력하시오");
						continue;
					}

					System.out.print("보기(1, 정답) 입력(최대 125자)>> ");
					String strcb_af = scan.nextLine();

					if (strcb_af.trim().isEmpty() || strcb_af.length() > 125) { // 공백 체크, 글자 수 체크
						System.out.println("125자 이하로 무엇이든 입력하시오");
						continue;
					}

					System.out.print("보기(2) 입력(최대 125자)>> ");
					String strcb_a1 = scan.nextLine();

					if (strcb_a1.trim().isEmpty() || strcb_a1.length() > 125) { // 공백 체크, 글자 수 체크
						System.out.println("125자 이하로 무엇이든 입력하시오");
						continue;
					}

					System.out.print("보기(3) 입력(최대 125자)>> ");
					String strcb_a2 = scan.nextLine();

					if (strcb_a2.trim().isEmpty() || strcb_a2.length() > 125) { // 공백 체크, 글자 수 체크
						System.out.println("125자 이하로 무엇이든 입력하시오");
						continue;
					}

					System.out.print("보기(4) 입력(최대 125자)>> ");
					String strcb_a3 = scan.nextLine();

					if (strcb_a3.trim().isEmpty() || strcb_a3.length() > 125) { // 공백 체크, 글자 수 체크
						System.out.println("125자 이하로 무엇이든 입력하시오");
						continue;
					}

					CbtDTO dto = CbtDTO.builder().cb_a1(strcb_a1).cb_a2(strcb_a2).cb_a3(strcb_a3).cb_af(strcb_af)
							.cb_code(strcb_code).cb_q(strcb_q).build();

					int ret = cbtDao.insert(dto);

					if (ret > 0) {
						System.out.println("문제 등록 완료");

					} else {
						System.out.println("문제 등록 실패");
					}

					continue;

				}

				else if (intC == 2) {

					System.out.println("수정할 문제코드 입력 >> ");
					String strcb_code = scan.nextLine();

					CbtDTO dto = cbtDao.selectByCode(strcb_code);

					if (dto == null) {

						System.out.println("해당 문제코드의 데이터 없음");
						continue;
					}

					this.dtoPrint(dto);

					System.out.println(dto.getCb_code() + "의 수정 시작");
					System.out.print("문제 입력(최대 125자)>> ");
					String strcb_q = scan.nextLine();

					if (strcb_q.length() > 125) {
						System.out.println("125자 이하로 입력");
						continue;
					}
					if (strcb_q.trim().isEmpty()) {
						strcb_q = dto.getCb_q();
					}

					System.out.print("보기(1, 정답) 입력(최대 125자)>> ");
					String strcb_af = scan.nextLine();

					if (strcb_af.length() > 125) {
						System.out.println("125자 이하로 입력");
						continue;
					}
					if (strcb_af.trim().isEmpty()) {
						strcb_af = dto.getCb_af();
					}

					System.out.print("보기(2) 입력(최대 125자)>> ");
					String strcb_a1 = scan.nextLine();

					if (strcb_a1.length() > 125) {
						System.out.println("125자 이하로 입력");
						continue;
					}
					if (strcb_a1.trim().isEmpty()) {
						strcb_a1 = dto.getCb_a1();
					}

					System.out.print("보기(3) 입력(최대 125자)>> ");
					String strcb_a2 = scan.nextLine();

					if (strcb_a2.length() > 125) {
						System.out.println("125자 이하로 입력");
						continue;
					}
					if (strcb_a2.trim().isEmpty()) { // 공백 체크, 글자 수 체크
						strcb_a2 = dto.getCb_a2();
					}

					System.out.print("보기(4) 입력(최대 125자)>> ");
					String strcb_a3 = scan.nextLine();

					if (strcb_a3.length() > 125) {
						System.out.println("125자 이하로 입력");
						continue;
					}
					if (strcb_a3.trim().isEmpty()) { // 공백 체크, 글자 수 체크
						strcb_a3 = dto.getCb_a3();
					}

					dto.setCb_a1(strcb_a1);
					dto.setCb_a2(strcb_a2);
					dto.setCb_a3(strcb_a3);
					dto.setCb_af(strcb_af);
					dto.setCb_q(strcb_q);

					int ret = cbtDao.update(dto);
					if (ret > 0) {
						System.out.println("업뎃 성공");
					} else {
						System.out.println("업뎃 실패");
					}

					continue;
				}

				else if (intC == 3) {

					System.out.println("삭제할 문제코드 입력 >> ");
					String strcb_code = scan.nextLine();

					CbtDTO dto = cbtDao.selectByCode(strcb_code);

					if (dto == null) {

						System.out.println("해당 문제코드의 데이터 없음");
						continue;
					}

					this.dtoPrint(dto);

					System.out.println("정말 삭제?(Y/N)");
					System.out.print(">> ");
					String strYN = scan.nextLine();

					if (strYN.equalsIgnoreCase("Y")) {

						int ret = cbtDao.delete(strcb_code);

						if (ret > 0) {
							System.out.println("삭제 완료");
						} else {
							System.out.println("삭제 실패");
						}

					} else if (strYN.equalsIgnoreCase("N")) {
						qNumber += 1;
						continue;
					}

					continue;

				}

			} catch (Exception e) {
				// TODO: handle exception
				
				System.out.println("커맨드 제대로 입력하시오");
			}

		}

	}

	public void cbtMenu2() {

		List<CbtDTO> cbtList = cbtDao.selectAll(); // 문제 리스트

		List<Integer> cbtNList = new ArrayList<Integer>();
		List<String> cbtOList = new ArrayList<String>(); // 문제 저장용 리스트
		List<String> strOFList = new ArrayList<String>(); // 답안 저장용
		List<String> strASList = new ArrayList<String>(); // 정답 저장용 리스트
		List<String> strMCList = new ArrayList<String>(); // 나의 답안 저장용 문자열 리스트

		if (cbtList == null) {
			System.out.println("문제를 등록하시오");
			return;

		}
		int intCbtListIndex = 0; // 문제리스트 인덱스

		qNumber = 1; // 문제 넘버

		int intReChance = 0; // 다시풀기용 스위치 변수

		Collections.shuffle(cbtList);

		while (true) {

			if (int5Count == 20) {
				System.out.println("★★★★★★★★문제 풀이 종료★★★★★★★★★★★★");
				this.saveOList(cbtNList, cbtOList, strOFList, strASList, strMCList);
				return;
			}

			if (int5Count % 5 == 0 && int5Count != 0) {

				this.print5(cbtNList, cbtOList, strOFList, strASList, strMCList, int5Count);

			}

			CbtDTO dto = cbtList.get(intCbtListIndex);

			HashMap<Integer, String> map = new HashMap<Integer, String>(); // 답안(번호), 답안 담을 맵

			List<Integer> intASList = new ArrayList<Integer>();

			intASList.add(1);
			intASList.add(2);
			intASList.add(3);
			intASList.add(4);

			Collections.shuffle(intASList); // 답안 섞기

			map.put(intASList.get(0), dto.getCb_af());
			map.put(intASList.get(1), dto.getCb_a1());
			map.put(intASList.get(2), dto.getCb_a2());
			map.put(intASList.get(3), dto.getCb_a3());

			int keyVal1 = 0;
			int keyVal2 = 0;
			int keyVal3 = 0;
			int keyVal4 = 0;

			for (int key : map.keySet()) {

				if (key == 1) {
					keyVal1 = key;
				}
				if (key == 2) {
					keyVal2 = key;
				}
				if (key == 3) {
					keyVal3 = key;
				}
				if (key == 4) {
					keyVal4 = key;
				}
			}

			System.out.println("================================"); // map에서 key(번호)와 value(답안)가져오기
			System.out.println("문제." + qNumber + ": " + dto.getCb_q());
			System.out.println("답안" + "(" + keyVal1 + ")" + ": " + map.get(keyVal1));
			System.out.println("답안" + "(" + keyVal2 + ")" + ": " + map.get(keyVal2));
			System.out.println("답안" + "(" + keyVal3 + ")" + ": " + map.get(keyVal3));
			System.out.println("답안" + "(" + keyVal4 + ")" + ": " + map.get(keyVal4));
			System.out.println("==============================");
			System.out.print("답 >> ");
			try {
				String strC = scan.nextLine();
				int intC = Integer.valueOf(strC);

				if (intC == intASList.get(0)) {
					System.out.println("정답!!");

					while (true) {

						System.out.println("--------------");
						System.out.println("Enter:다음문제풀기, -q:quit");
						System.out.println(">> ");
						String strC2 = scan.nextLine();

						if (strC2.equalsIgnoreCase("-q")) {

							cbtNList.add(qNumber);
							cbtOList.add(dto.getCb_q() + "        ★정답★");
							strOFList.add(map.get(keyVal1));
							strOFList.add(map.get(keyVal2));
							strOFList.add(map.get(keyVal3));
							strOFList.add(map.get(keyVal4));
							strASList.add(dto.getCb_af());
							strMCList.add(map.get(intC));

							if (intReChance == 0) {
								intScore += 5;
							}

							int5Count += 1;

							this.saveOList(cbtNList, cbtOList, strOFList, strASList, strMCList); // 내가푼 문제 dto 담은 리스트,
																									// 나의
																									// 정답오답 리스트 등등을 출력용
																									// 메서드에 전달

							return;
						} else if (strC2.trim().isEmpty()) { // 다음 문제로

							cbtNList.add(qNumber);
							cbtOList.add(dto.getCb_q() + "        ★정답★");
							strOFList.add(map.get(keyVal1));
							strOFList.add(map.get(keyVal2));
							strOFList.add(map.get(keyVal3));
							strOFList.add(map.get(keyVal4));
							strASList.add(dto.getCb_af());
							strMCList.add(map.get(intC));

							intCbtListIndex += 1;
							++qNumber;
							intReChance = 0;

							int5Count += 1;

							if (intReChance == 0) {
								intScore += 5;
							}

							break;
						} else if (!strC2.trim().isEmpty() || strC2.equalsIgnoreCase("-q")) {
							System.out.println("커맨드를 제대로 입력하시오");
							continue;
						} else if (this.isStringDouble(strC2)) {
							System.out.println("커맨드를 제대로 입력하시오");
							continue;
						}

					}

					continue;
				} else if (intC < 1 || intC > 4) {
					System.out.println(" 1~4까지만 입력 가능");
					continue;
				}

				else {
					System.out.println("틀림!!!" + " 정답은 " + intASList.get(0));

					if (intReChance == 1) {

						System.out.println("재도전 기회 없음!!");

						cbtNList.add(qNumber);
						cbtOList.add(dto.getCb_q());
						strOFList.add(map.get(keyVal1));
						strOFList.add(map.get(keyVal2));
						strOFList.add(map.get(keyVal3));
						strOFList.add(map.get(keyVal4));
						strASList.add(dto.getCb_af());
						strMCList.add(map.get(intC));

						this.saveOList(cbtNList, cbtOList, strOFList, strASList, strMCList);

						return;

					}

					while (true) {

						System.out.println("R:다시풀기, N:다음문제풀기");
						System.out.print(">> ");
						String strC2 = scan.nextLine();

						if (strC2.equalsIgnoreCase("R")) {

							cbtNList.add(qNumber);
							cbtOList.add(dto.getCb_q());
							strOFList.add(map.get(keyVal1));
							strOFList.add(map.get(keyVal2));
							strOFList.add(map.get(keyVal3));
							strOFList.add(map.get(keyVal4));
							strASList.add(dto.getCb_af());
							strMCList.add(map.get(intC));

							intReChance = 1;

							while (true) { // 다시 풀기진입

								System.out.println("================================"); // map에서 key(번호)와 value(답안)가져오기
								System.out.println("문제." + qNumber + ": " + dto.getCb_q());
								System.out.println("답안" + "(" + keyVal1 + ")" + ": " + map.get(keyVal1));
								System.out.println("답안" + "(" + keyVal2 + ")" + ": " + map.get(keyVal2));
								System.out.println("답안" + "(" + keyVal3 + ")" + ": " + map.get(keyVal3));
								System.out.println("답안" + "(" + keyVal4 + ")" + ": " + map.get(keyVal4));
								System.out.println("==============================");

								System.out.print("답 >> ");
								String strCF = scan.nextLine();
								int intCF = Integer.valueOf(strCF);

								if (intCF == intASList.get(0)) {
									System.out.println("정답!!");

									while (true) {
										System.out.println("--------------");
										System.out.println("Enter:다음문제풀기, -q:quit");
										System.out.println(">> ");
										String strC2F = scan.nextLine();

										if (strC2F.equalsIgnoreCase("-q")) {

											int5Count += 1;

											this.saveOList(cbtNList, cbtOList, strOFList, strASList, strMCList); // 내가푼
																													// 문제
																													// dto
																													// 담은
																													// 리스트,
																													// 나의
																													// 정답오답
																													// 리스트
																													// 전달

											return;
										} else if (strC2F.trim().isEmpty()) { // 다음 문제로

											intCbtListIndex += 1;
											++qNumber;
											intReChance = 0;
											int5Count += 1;

											break;
										} else if (!strC2F.trim().isEmpty() || strC2F.equalsIgnoreCase("-q")) {
											System.out.println("커맨드를 제대로 입력하시오");
											continue;
										} else if (this.isStringDouble(strC2F)) {
											System.out.println("커맨드를 제대로 입력하시오");
											continue;
										}

									}

									break;
								} else if (intC < 1 || intC > 4) {
									System.out.println(" 1~4까지만 입력 가능");
									continue;
								}

								else { // 없어도 될?
									System.out.println("틀림!!!" + " 정답은 " + intASList.get(0));

									if (intReChance == 1) {

										System.out.println("재도전 기회 없음!!");

										cbtNList.add(qNumber);
										cbtOList.add(dto.getCb_q());
										strOFList.add(map.get(keyVal1));
										strOFList.add(map.get(keyVal2));
										strOFList.add(map.get(keyVal3));
										strOFList.add(map.get(keyVal4));
										strASList.add(dto.getCb_af());
										strMCList.add(map.get(intCF));

										int5Count += 1;

										this.saveOList(cbtNList, cbtOList, strOFList, strASList, strMCList);

										return;

									}

								}

								continue;
							}
						} else if (strC2.equalsIgnoreCase("N")) {

							cbtNList.add(qNumber);
							cbtOList.add(dto.getCb_q());
							strOFList.add(map.get(keyVal1));
							strOFList.add(map.get(keyVal2));
							strOFList.add(map.get(keyVal3));
							strOFList.add(map.get(keyVal4));
							strASList.add(dto.getCb_af());
							strMCList.add(map.get(intC));

							intCbtListIndex += 1;
							qNumber += 1;
							intReChance = 0;
							int5Count += 1;
							break;

						} else if (!strC2.equalsIgnoreCase("N") || strC2.equalsIgnoreCase("R")) {
							System.out.println("커맨드를 제대로 입력하시오");
							continue;
						} else if (this.isStringDouble(strC2)) {
							System.out.println("커맨드를 제대로 입력하시오");
							continue;
						}

						break;

					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				System.out.println("커맨드를 제대로 입력하시오");
				
				continue;
			}
		}

	}

	public void saveOList(List<Integer> cbtNList, List<String> cbtOList, List<String> strOFList, List<String> strASList,
			List<String> strMCList) { // 문제풀이 전체출력용 메서드

		int intOFA = 0; // 답안 인덱스값 돌리기

		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓ 나의 문제풀이  이력 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");

		for (int i = 0; i < strMCList.size(); i++) {

			System.out.println("문제" + cbtNList.get(i) + " " + cbtOList.get(i));
			System.out.println("답안1: " + strOFList.get(intOFA));
			System.out.println("답안2: " + strOFList.get(intOFA + 1));
			System.out.println("답안3: " + strOFList.get(intOFA + 2));
			System.out.println("답안4: " + strOFList.get(intOFA + 3));

			System.out.println("---------------------------");
			System.out.println("정답: " + strASList.get(i));
			System.out.println("나의 답안: " + strMCList.get(i));

			System.out.println();
			System.out.println();

			intOFA += 4;

		}

		System.out.println("-----------------------------------------------------------");
		System.out.println("풀이한 문제:" + int5Count);
		System.out.println("점수:" + intScore);

		System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");

	}

	public void print5(List<Integer> cbtNList, List<String> cbtOList, List<String> strOFList, List<String> strASList,
			List<String> strMCList, int int5Count) { // 5문제 풀이 출력용 메서드

		int5Count -= 5; //

		System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓ 중간점검 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");

		for (int i = int5Count; i < strMCList.size(); i++) {

			System.out.println("문제" + cbtNList.get(i) + " " + cbtOList.get(i));
			System.out.println("답안1: " + strOFList.get(intOF));
			System.out.println("답안2: " + strOFList.get(intOF + 1));
			System.out.println("답안3: " + strOFList.get(intOF + 2));
			System.out.println("답안4: " + strOFList.get(intOF + 3));

			System.out.println("---------------------------");
			System.out.println("정답: " + strASList.get(i));
			System.out.println("나의 답안: " + strMCList.get(i));

			System.out.println();
			System.out.println();

			intOF += 4;

		}

		int5Count += 5;
		this.int5Count = int5Count;

		System.out.println("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");

	}

	public void dtoPrint(CbtDTO dto) {

		System.out.println("==================================");
		System.out.println("코드: " + dto.getCb_code());
		System.out.println("문제: " + dto.getCb_q());
		System.out.println("보기1(정답): " + dto.getCb_af());
		System.out.println("보기2: " + dto.getCb_a1());
		System.out.println("보기3: " + dto.getCb_a2());
		System.out.println("보기4: " + dto.getCb_a3());
		System.out.println("==================================");

	}

	public boolean isStringDouble(String strC) { // String에 정수형 데이터 들어갔는가 ㄱ검증용

		try {
			Double.parseDouble(strC);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
