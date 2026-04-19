package com.denkh.user.service;


import com.denkh.common.criteria.BaseSearchCriteria;
import com.denkh.common.dto.PageableRequestVO;
import com.denkh.common.dto.PageableResponseVO;
import com.denkh.user.dto.request.UserFilterRequest;
import com.denkh.user.entity.User;

public interface UserSearchService {
    PageableResponseVO<User> searchUsers(UserFilterRequest filterRequest);

    PageableResponseVO<User> searchUsersWithCriteria(BaseSearchCriteria searchCriteria, PageableRequestVO pageable);
}
