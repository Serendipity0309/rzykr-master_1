package com.rzyk.superintendent.mapper;

import com.rzyk.superintendent.domain.Superintendent;

import java.util.List;

/**
 * 监考老师信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public interface SuperintendentMapper 
{
    /**
     * 查询监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 监考老师信息
     */
    public Superintendent selectSuperintendentById(String id);

    /**
     * 查询监考老师信息列表
     * 
     * @param superintendent 监考老师信息
     * @return 监考老师信息集合
     */
    public List<Superintendent> selectSuperintendentList(Superintendent superintendent);

    /**
     * 新增监考老师信息
     * 
     * @param superintendent 监考老师信息
     * @return 结果
     */
    public int insertSuperintendent(Superintendent superintendent);

    /**
     * 修改监考老师信息
     * 
     * @param superintendent 监考老师信息
     * @return 结果
     */
    public int updateSuperintendent(Superintendent superintendent);

    /**
     * 删除监考老师信息
     * 
     * @param id 监考老师信息主键
     * @return 结果
     */
    public int deleteSuperintendentById(String id);

    /**
     * 批量删除监考老师信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSuperintendentByIds(String[] ids);
}
