package com.diamond.common.service.impl;

import com.diamond.common.exception.BusinessException;
import com.diamond.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.diamond.common.dao.DictDao;
import com.diamond.common.entity.Dict;
import com.diamond.common.service.DictService;

@Slf4j
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public Dict get(Long id) {
        return dictDao.get(id);
    }

    @Override
    public List<Dict> list(Map<String, Object> map) {
        return dictDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return dictDao.count(map);
    }

    @Override
    public int save(Dict dict) {
        return dictDao.save(dict);
    }

    @Override
    public int update(Dict dict) {
        return dictDao.update(dict);
    }

    @Override
    public int remove(Long id) {
        return dictDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return dictDao.batchRemove(ids);
    }

    @Override
    public List<Dict> batchQuery(Long[] ids) {
        return dictDao.batchQuery(ids);
    }

    @Override
    public String batchInsert(List<Dict> list, boolean isUpdateSupport, Long userId) {
        if (CollectionUtils.isEmpty(list) || list.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Dict dictDO : list) {
            try {
                Dict dict = get(dictDO.getId());
                if (null == dict) {
                    dictDao.save(dict);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号ID ").append(userId).append(" 导入成功");
                } else if (isUpdateSupport) {
                    dict.setUpdateBy(userId);
                    dictDao.update(dict);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号ID " + userId + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号ID " + userId + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号ID " + userId + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<Dict> listType() {
        return dictDao.listType();
    }

    @Override
    public String getName(String type, String value) {
        Map<String, Object> param = new HashMap<String, Object>(16);
        param.put("type", type);
        param.put("value", value);
        String rString = dictDao.list(param).get(0).getName();
        return rString;
    }

    @Override
    public List<Dict> getHobbyList(User user) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "hobby");
        List<Dict> hobbyList = dictDao.list(param);

        if (StringUtils.isNotEmpty(user.getHobby())) {
            String userHobbys[] = user.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (Dict hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<Dict> getSexList() {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", "sex");
        return dictDao.list(param);
    }

    @Override
    public List<Dict> listByType(String type) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        return dictDao.list(param);
    }

}
