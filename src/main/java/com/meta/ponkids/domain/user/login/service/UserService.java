package com.meta.ponkids.domain.user.login.service;


import com.meta.ponkids.domain.user.login.dto.UserSaveReqDto;
import com.meta.ponkids.domain.user.login.entity.User;
import com.meta.ponkids.domain.user.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 패스워드 인코딩

    public UserSaveReqDto save( UserSaveReqDto userSaveReqDto) {
        
//        LocalDateTime date = LocalDateTime.now();
        
        // dto to entity 작업 (필수)
        User user = User.builder()
                .userId(userSaveReqDto.getUserId())
                .password(passwordEncoder.encode(userSaveReqDto.getPassword()))
                .userNm(userSaveReqDto.getUserNm())
                .gender(userSaveReqDto.getGender())
                .brdtDate(userSaveReqDto.getBrdtDate())
                .telNo(userSaveReqDto.getTelNo())
                .resideArea(userSaveReqDto.getResideArea())
                .zip(userSaveReqDto.getZip())
                .rdnmAdr(userSaveReqDto.getRdnmAdr())
                .detailAdr(userSaveReqDto.getDetailAdr())
                .mngrYn(userSaveReqDto.getMngrYn())
                .mngrConfmYn(userSaveReqDto.getMngrConfmYn())
                .confmerId(userSaveReqDto.getConfmerId())
                .confmerIp(userSaveReqDto.getConfmerIp())
                .confmDt(LocalDateTime.now())
                .cntnSns(userSaveReqDto.getCntnSns())
                .registerIp(userSaveReqDto.getRegisterIp())
                .delYn( "N" )
                .build();

        // save
        userRepository.save(user);

        return userSaveReqDto;
    }
}