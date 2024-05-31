package com.rzyk.notice.service.impl;

import com.rzyk.common.utils.DateUtils;
import com.rzyk.notice.domain.Notice;
import com.rzyk.notice.mapper.NoticeMapper;
import com.rzyk.notice.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 通知公告Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-09
 */
@Service
public class NoticeServiceImpl implements INoticeService
{
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 查询通知公告
     * 
     * @param noticeId 通知公告主键
     * @return 通知公告
     */
    @Override
    public Notice selectNoticeByNoticeId(Integer noticeId)
    {
        return noticeMapper.selectNoticeByNoticeId(noticeId);
    }

    /**
     * 查询通知公告列表
     * 
     * @param notice 通知公告
     * @return 通知公告
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增通知公告
     * 
     * @param notice 通知公告
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice)
    {
        notice.setCreateTime(DateUtils.getNowDate());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改通知公告
     * 
     * @param notice 通知公告
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice)
    {
        notice.setUpdateTime(DateUtils.getNowDate());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 批量删除通知公告
     * 
     * @param noticeIds 需要删除的通知公告主键
     * @return 结果
     */
    @Override
    public int deleteNoticeByNoticeIds(Integer[] noticeIds)
    {
        return noticeMapper.deleteNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除通知公告信息
     * 
     * @param noticeId 通知公告主键
     * @return 结果
     */
    @Override
    public int deleteNoticeByNoticeId(Integer noticeId)
    {
        return noticeMapper.deleteNoticeByNoticeId(noticeId);
    }
}
