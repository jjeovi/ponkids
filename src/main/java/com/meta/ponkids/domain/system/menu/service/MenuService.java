package com.meta.ponkids.domain.system.menu.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.meta.ponkids.domain.system.menu.dto.MenuDto;
import com.meta.ponkids.domain.system.menu.dto.MenuListDto;
import com.meta.ponkids.domain.system.menu.dto.MenuModDto;
import com.meta.ponkids.domain.system.menu.dto.MenuRoleSaveDto;
import com.meta.ponkids.domain.system.menu.dto.MenuSaveDto;
import com.meta.ponkids.domain.system.menu.entity.AdminMenuHierarchy;
import com.meta.ponkids.domain.system.menu.entity.Menu;
import com.meta.ponkids.domain.system.menu.entity.MenuHierarchy;
import com.meta.ponkids.domain.system.menu.entity.MenuRole;
import com.meta.ponkids.domain.system.menu.entity.UserMenuHierarchy;
import com.meta.ponkids.domain.system.menu.repository.MenuRepository;
import com.meta.ponkids.domain.system.menu.repository.MenuRoleRepository;
import com.meta.ponkids.domain.system.role.dto.RoleListDto;
import com.meta.ponkids.global.util.ip.IpUtils;
import com.meta.ponkids.global.util.session.SessionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	@Value("${key.menu.jsonFilePath}")
	private String JSON_FILE_PATH;

	private final MenuRepository menuRepository; // repository setting
	private final MenuRoleRepository menuRoleRepository;

	@Transactional
	public MenuSaveDto save(MenuSaveDto saveDto, HttpServletRequest request) throws IOException {

		saveDto.setRegisterId(SessionUtils.getClientId()); // Id set : regist
		saveDto.setRegisterIp(IpUtils.getClientIP(request)); // Ip set : regist
		saveDto.setUpdusrId(SessionUtils.getClientId()); // Id set : update
		saveDto.setUpdusrIp(IpUtils.getClientIP(request)); // Ip set : update

		Menu newMenu = menuRepository.save(saveDto.toEntity()); // ** save -> save된 정보 newXxx 로 저장

		saveDto.setMenuSn(newMenu.getMenuSn());

		return saveDto;

	}

	public MenuSaveDto saveAndFlush(MenuSaveDto saveDto, HttpServletRequest request) throws IOException {

		saveDto.setRegisterId(SessionUtils.getClientId()); // Id set : regist
		saveDto.setRegisterIp(IpUtils.getClientIP(request)); // Ip set : regist
		saveDto.setUpdusrId(SessionUtils.getClientId()); // Id set : update
		saveDto.setUpdusrIp(IpUtils.getClientIP(request)); // Ip set : update

		Menu newMenu = menuRepository.saveAndFlush(saveDto.toEntity()); // ** save -> save된 정보 newXxx 로 저장

		saveDto.setMenuSn(newMenu.getMenuSn());

		return saveDto;

	}

	public List<MenuListDto> getList(MenuListDto listDto) {

		// 메뉴 목록은 category lv1Sn 값이 설정되어있어야 조회 가능. 그렇지 않으면 빈 List<> return
		if (listDto.getCategory() != null && listDto.getCategory().getLv1Sn() != null) {
			// (1) 전체 권한 메뉴 리스트
			// (2) 특정 권한 메뉴 리스트 를 구분한다.

			// 1. 마지막 업데이트 시간. (캐시로 저장)
			// 2. 마지막 업데이트 시간 (캐시 저장하지 않음 - 계속 DB 조회)

			// 캐시로 저장된 마지막 업데이트 시간 (1) 과 캐시로 저장하지않고 계속 조회하는 마지막 업데이트 시간 (2) 을 비교해
			// 같지 않으면, 캐시를 지우고 다시 조회

			// 1. 마지막 업데이트 시간. (캐시로 저장)
			AdminMenuHierarchy updtDtMenuCache = menuRepository
					.findLastUpdtDtAdminMenuCache(listDto.getCategory().getLv1Sn());
			// 2. 마지막 업데이트 시간 (캐시 저장하지 않음 - 계속 DB 조회)
			AdminMenuHierarchy updtDtMenuNoCache = menuRepository
					.findLastUpdtDtAdminMenuNoCache(listDto.getCategory().getLv1Sn());

			if (listDto.getCategory().getLv1Sn() == 0) {
				// (1) 전체 메뉴 list

				if (menuRenewalCheck(updtDtMenuCache, updtDtMenuNoCache)) {
					// 메뉴 의 수정이 감지 됬을 경우
//                    System.out.println("변경 감지했습니다.");

					// 캐시로 저장된 마지막 업데이트 시간을 갱신한다.
					menuRepository.findLastUpdtDtAdminMenuAgainCache(listDto.getCategory().getLv1Sn());

					return menuRepository.getAllListAgain(listDto);

				} else {
					// 변경이 없을 때 ( 캐시에 저장된 값 조회 , DB 읽지 않음)
//                    System.out.println("변경이 감지되지 않았습니다. 혹은 최초 캐시 저장시입니다.");

					return menuRepository.getAllList(listDto);
				}
			} else {
				// (2) 특정 권한의 메뉴 list

				if (menuRenewalCheck(updtDtMenuCache, updtDtMenuNoCache)) {
					// 메뉴 의 수정이 감지 됬을 경우
//                    System.out.println("변경 감지했습니다.");

					// 캐시로 저장된 마지막 업데이트 시간을 갱신한다.
					menuRepository.findLastUpdtDtAdminMenuAgainCache(listDto.getCategory().getLv1Sn());

					return menuRepository.getListAgain(listDto);
				} else {
					// 변경이 없을 때 ( 캐시에 저장된 값 조회 , DB 읽지 않음)
//                    System.out.println("변경이 감지되지 않았습니다.");
					return menuRepository.getList(listDto);
				}
			}
		} else {
			return Collections.emptyList(); // 빈 List<> 생성
		}
	}

	public List<MenuListDto> getUserMenuList(MenuListDto listDto) {

		// (1) 전체 권한 메뉴 리스트
		// (2) 특정 권한 메뉴 리스트 를 구분한다.

		// 1. 마지막 업데이트 시간. (캐시로 저장)
		// 2. 마지막 업데이트 시간 (캐시 저장하지 않음 - 계속 DB 조회)

		// 캐시로 저장된 마지막 업데이트 시간 (1) 과 캐시로 저장하지않고 계속 조회하는 마지막 업데이트 시간 (2) 을 비교해
		// 같지 않으면, 캐시를 지우고 다시 조회

		// 1. 마지막 업데이트 시간. (캐시로 저장)
		UserMenuHierarchy updtDtMenuCache = menuRepository.findLastUpdtDtUserMenuCache();
		// 2. 마지막 업데이트 시간 (캐시 저장하지 않음 - 계속 DB 조회)
		UserMenuHierarchy updtDtMenuNoCache = menuRepository.findLastUpdtDtUserMenuNoCache();

		if (menuRenewalCheck(updtDtMenuCache, updtDtMenuNoCache)) {
			// 메뉴 의 수정이 감지 됬을 경우
//                    System.out.println("변경 감지했습니다.");

			// 캐시로 저장된 마지막 업데이트 시간을 갱신한다.
			menuRepository.findLastUpdtDtUserMenuAgainCache();

			return menuRepository.getUserMenuListAgain(listDto);

		} else {
			// 변경이 없을 때 ( 캐시에 저장된 값 조회 , DB 읽지 않음)
//                    System.out.println("변경이 감지되지 않았습니다. 혹은 최초 캐시 저장시입니다.");

			return menuRepository.getUserMenuList(listDto);
		}
	}

	public MenuModDto findById(Long pk) {

		Menu menu = menuRepository.findById(pk).orElse(null);

		MenuModDto modDto = new MenuModDto();
		modDto = modDto.toDto(menu);

		return modDto;
	}

	@Transactional
	public void update(MenuModDto modDto, HttpServletRequest request) throws IOException {

		// target 조회
		Menu menu = menuRepository.findById(modDto.getMenuSn()).orElse(null);

		// target object 전환 ( entity to dto )
		MenuModDto targetDto = new MenuModDto();
		targetDto = targetDto.toDto(menu);

		// entity 에서 반영하지 않을 컬럼은 updatable = false 옵션 추가
		if (modDto.getUpperMenuSn() != null)
			targetDto.setUpperMenuSn(modDto.getUpperMenuSn()); // 부모메뉴
		if (StringUtils.hasText(modDto.getMenuNm()))
			targetDto.setMenuNm(modDto.getMenuNm()); // 메뉴이름
		if (StringUtils.hasText(modDto.getMenuCd()))
			targetDto.setMenuCd(modDto.getMenuCd()); // 메뉴코드
		if (modDto.getMenuUrl() != null)
			targetDto.setMenuUrl(modDto.getMenuUrl()); // 메뉴URL
		if (StringUtils.hasText(modDto.getParntsMenuYn()))
			targetDto.setParntsMenuYn(modDto.getParntsMenuYn()); // 부모메뉴여부
		if (modDto.getMenuSeq() != null)
			targetDto.setMenuSeq(modDto.getMenuSeq()); // 메뉴순번
		if (StringUtils.hasText(modDto.getMenuDcSetYn()))
			targetDto.setMenuDcSetYn(modDto.getMenuDcSetYn()); // 메뉴설명설정여부
		if (modDto.getMenuDc() != null)
			targetDto.setMenuDc(modDto.getMenuDc()); // 메뉴설명
		if (modDto.getMenuDetailDc() != null)
			targetDto.setMenuDetailDc(modDto.getMenuDetailDc()); // 메뉴상세설명
		if (StringUtils.hasText(modDto.getUseYn()))
			targetDto.setUseYn(modDto.getUseYn()); // 사용여부
		if (StringUtils.hasText(modDto.getNewWindowYn()))
			targetDto.setNewWindowYn(modDto.getNewWindowYn()); // 새창여부

		targetDto.setAtchFileSn(modDto.getAtchFileSn()); // 첨부파일 (첨부파일은 Null이어도 변경)

		// id,ip setting
		targetDto.setUpdusrIp(IpUtils.getClientIP(request));
		targetDto.setUpdusrId(SessionUtils.getClientId());

		// target object 전환 ( dto to entity )
		menu = targetDto.toEntity();

		// 수정사항 적용
		menuRepository.save(menu);
	}

	@Transactional
	public void menuRoleUpdate(MenuDto menuDto, HttpServletRequest request) throws IOException {

		// (1) tb_menu_role 테이블에 menu_sn 으로 해당되는 권한 모두 지운뒤
		// (2) tb_menu_role 에 modDto.roleSn 에 있는 값들을 모두 insert처리

		// (1) tb_menu_role 테이블에 menu_sn 으로 해당되는 권한 모두 지운뒤
		if (menuDto.getMenuSn() != null)
			menuRoleRepository.deleteAllByMenuSn(menuDto.getMenuSn());

		// (2) tb_menu_role 에 modDto.roleSn 에 있는 값들을 모두 insert처리
		if (menuDto.getRoleSnList() != null) {
			List<MenuRole> menuRoleList = new ArrayList<>();

			for (Long roleSn : menuDto.getRoleSnList()) {
				MenuRoleSaveDto menuRole = new MenuRoleSaveDto();

				menuRole.setRegisterId(IpUtils.getClientIP(request));
				menuRole.setRegisterIp(SessionUtils.getClientId());

				menuRole.setMenuSn(menuDto.getMenuSn());
				menuRole.setRoleSn(roleSn);

				menuRoleList.add(menuRole.toEntity());
			}

			menuRoleRepository.saveAll(menuRoleList);
		}
	}

	@Transactional
	public void menuRoleRootUpdate(MenuDto menuDto, HttpServletRequest request) throws IOException {

		// (1) tb_menu_role 에 modDto.roleSn 에 있는 값들을 모두 insert처리 . root 메뉴라서 추가만 . 제거는
		// 하지 않음

		// (2) tb_menu_role 에 modDto.roleSn 에 있는 값들을 모두 insert처리
		if (menuDto.getRoleSnList() != null) {
			List<MenuRole> menuRoleList = new ArrayList<>();

			for (Long roleSn : menuDto.getRoleSnList()) {
				MenuRoleSaveDto menuRole = new MenuRoleSaveDto();

				menuRole.setRegisterId(IpUtils.getClientIP(request));
				menuRole.setRegisterIp(SessionUtils.getClientId());

				menuRole.setMenuSn(menuDto.getMenuSn());
				menuRole.setRoleSn(roleSn);

				menuRoleList.add(menuRole.toEntity());
			}

			menuRoleRepository.saveAll(menuRoleList);
		}
	}

	@Transactional
	public void deleteAllById(Long pk) {

		// delete 처리 : 실제 delete는 아니고 update 하여 del_yn 값을 Y로 수정작업
		menuRepository.deleteById(pk); // Entity 의 @SQLDelete 를 수행
	}

	public List<RoleListDto> getPossibleRoleListAjax(MenuListDto listDto) {
		return menuRepository.getPossibleRoleListAjax(listDto);
	}

	private boolean menuRenewalCheck(MenuHierarchy updtDtMenuCache, MenuHierarchy updtDtMenuNoCache) {

		if (updtDtMenuCache != null && updtDtMenuNoCache != null) {
			if (updtDtMenuCache.getUpdtDt() != null && updtDtMenuNoCache.getUpdtDt() != null) {

//                System.out.println( "updtDtMenuCache의   최종 수정시간 = " + updtDtMenuCache.getUpdtDt() );
//                System.out.println( "updtDtMenuNoCache의 최종 수정시간 = " + updtDtMenuNoCache.getUpdtDt() );

				// 최종 수정 시간이 변경을 감지 했다면
				// 메뉴 리스트 의 캐시를 갱신해야함.
				return !updtDtMenuCache.getUpdtDt().equals(updtDtMenuNoCache.getUpdtDt());
			}
		}

		return true;
	}

	@Transactional
	// 메뉴 등록, 수정, 삭제 시
	public void menuSaveToJsonFile() throws ParseException, IOException {

		MenuListDto menuListDto = new MenuListDto();

		List<MenuListDto> menuListDtos = this.getUserMenuList(menuListDto);

		// Gson 사용
		String json = new Gson().toJson(menuListDtos);

		Gson gson = new Gson();

		// lecture 객체를 파일에 쓰기
		String rootPath = System.getProperty("user.dir");

		String compareFilePath = rootPath + JSON_FILE_PATH + "pon-menu-list.json";
		compareFilePath = compareFilePath.replaceAll("\\\\", "/");

		File file = new File(compareFilePath);

		Path existPath = Paths.get( rootPath + JSON_FILE_PATH + "pon-menu-list.json" );
		Path oldPath = Paths.get( rootPath + JSON_FILE_PATH + "pon-menu-list_old.json" );

		if (file.exists()) {
			// 파일이 존재하면 old 버전으로 복사
			Files.copy(existPath, oldPath, StandardCopyOption.REPLACE_EXISTING); // oldPath 가 존재한다 하더라도 덮어쓰기로 old 파일 생성

		}

		// 파일이 존재하지 않다면 : 새로 생성
		try (PrintWriter out = new PrintWriter(new FileWriter(rootPath + JSON_FILE_PATH + "pon-menu-list.json"))) {
			String jsonString = gson.toJson(menuListDtos);
			out.write(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
