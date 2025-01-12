package com.OhsunDosun.mapper;

import com.OhsunDosun.dto.SearchUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchUserMapper {
    // 이름으로 사용자 ID 조회
    SearchUserDTO findUserByName(@Param("name") String name);
}
