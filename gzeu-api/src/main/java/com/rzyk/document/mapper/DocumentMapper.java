package com.rzyk.document.mapper;

import com.rzyk.document.domain.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 材料Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-18
 */
public interface DocumentMapper 
{
    /**
     * 查询材料
     * 
     * @param id 材料主键
     * @return 材料
     */
    public Document selectDocumentById(Long id);

    /**
     * 查询材料列表
     * 
     * @param document 材料
     * @return 材料集合
     */
    public List<Document> selectDocumentList(Document document);

    /**
     * 新增材料
     * 
     * @param document 材料
     * @return 结果
     */
    public int insertDocument(Document document);

    /**
     * 修改材料
     * 
     * @param document 材料
     * @return 结果
     */
    public int updateDocument(Document document);

    /**
     * 删除材料
     * 
     * @param id 材料主键
     * @return 结果
     */
    public int deleteDocumentById(Long id);

    /**
     * 批量删除材料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDocumentByIds(Long[] ids);

    List<Document> selectDocumentByIds( String[] ids);
}
