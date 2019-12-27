package com.diamond.system.service;

import java.util.Collection;
import java.util.List;

import com.diamond.system.entity.User;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.diamond.system.entity.UserOnline;

@Service
public interface SessionService {
    List<UserOnline> list();

    List<User> listOnlineUser();

    Collection<Session> sessionList();

    boolean forceLogout(String sessionId);
}
