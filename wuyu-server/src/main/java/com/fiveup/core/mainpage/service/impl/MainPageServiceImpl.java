package com.fiveup.core.mainpage.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.mainpage.domain.vo.WebUserVo;
import com.fiveup.core.mainpage.mapper.MainPageMapper;
import com.fiveup.core.mainpage.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MainPageServiceImpl implements MainPageService {

    @Autowired
    private MainPageMapper mainPageMapper;

    @Override
    public WebUserVo getWebUserById(String id) {
        WebUser webUser = mainPageMapper.selectById(id);
        if (webUser != null) {
            String identityInfo = mainPageMapper.getIdentityInfo(webUser.getIdentity());
            String schoolInfo = mainPageMapper.getSchoolInfo(webUser.getSchoolId());
            WebUserVo webUserVo = new WebUserVo();
            webUserVo.setRealName(webUser.getRealName());
            webUserVo.setPhone(webUser.getPhone());
            webUserVo.setGender(webUser.getGender());
            webUserVo.setSchool(schoolInfo);
            webUserVo.setIdentity(identityInfo);
            webUserVo.setPosition(webUser.getPosition());
            webUserVo.setTitle(webUser.getTitle());
            webUserVo.setBirthPlace(webUser.getBirthPlace());
            webUserVo.setPoliticalAppearance(webUser.getPoliticalAppearance());
            return webUserVo;
        }
        return null;
    }

    @Override
    public int updateWebUserById(int id, WebUser webUser) {
        LambdaUpdateWrapper<WebUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WebUser::getId, id)
                .set(webUser.getPassword() != null, WebUser::getPassword, webUser.getPassword())
                .set(webUser.getRealName() != null, WebUser::getRealName, webUser.getRealName())
                .set(webUser.getPhone() != null, WebUser::getPhone, webUser.getPhone())
                .set(webUser.getGender() != null, WebUser::getGender, webUser.getGender())
                .set(webUser.getPosition() != null, WebUser::getPosition, webUser.getPosition())
                .set(webUser.getTitle() != null, WebUser::getTitle, webUser.getTitle())
                .set(webUser.getBirthPlace() != null, WebUser::getBirthPlace, webUser.getBirthPlace())
                .set(webUser.getPoliticalAppearance() != null,
                        WebUser::getPoliticalAppearance, webUser.getPoliticalAppearance());
        if (StringUtils.hasText(wrapper.getSqlSet())) {
            return mainPageMapper.update(null, wrapper);
        }
        return 1;
    }
}
