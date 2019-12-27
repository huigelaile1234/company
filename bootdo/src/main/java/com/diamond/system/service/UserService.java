package com.diamond.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.diamond.system.entity.User;
import com.diamond.system.vo.UserVO;
import org.springframework.stereotype.Service;

import com.diamond.common.entity.Tree;
import com.diamond.system.entity.Dept;

@Service
public interface UserService {
    User get(Long id);

    List<User> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(User user);

    int update(User user);

    int remove(Long userId);

    int batchremove(Long[] userIds);

    boolean exit(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    int resetPwd(UserVO userVO, User user) throws Exception;

    int adminResetPwd(UserVO userVO) throws Exception;

    Tree<Dept> getTree();

    /**
     * 更新个人信息
     *
     * @param user
     * @return
     */
    int updatePersonal(User user);

}
