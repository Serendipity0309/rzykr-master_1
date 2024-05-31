package com.rzyk.document.service.impl;

import java.util.List;

import com.rzyk.document.domain.Document;
import com.rzyk.document.mapper.DocumentMapper;
import com.rzyk.document.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 材料Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-18
 */
@Service
public class DocumentServiceImpl implements IDocumentService
{
    @Autowired
    private DocumentMapper documentMapper;

    /**
     * 查询材料
     * 
     * @param id 材料主键
     * @return 材料
     */
    @Override
    public Document selectDocumentById(Long id)
    {
        return documentMapper.selectDocumentById(id);
    }

    /**
     * 查询材料列表
     * 
     * @param document 材料
     * @return 材料
     */
    @Override
    public List<Document> selectDocumentList(Document document)
    {
        return documentMapper.selectDocumentList(document);
    }

    /**
     * 新增材料
     * 
     * @param document 材料
     * @return 结果
     */
    @Override
    public int insertDocument(Document document)
    {
        return documentMapper.insertDocument(document);
    }

    /**
     * 修改材料
     * 
     * @param document 材料
     * @return 结果
     */
    @Override
    public int updateDocument(Document document)
    {
        return documentMapper.updateDocument(document);
    }

    /**
     * 批量删除材料
     * 
     * @param ids 需要删除的材料主键
     * @return 结果
     */
    @Override
    public int deleteDocumentByIds(Long[] ids)
    {
        return documentMapper.deleteDocumentByIds(ids);
    }

    /**
     * 删除材料信息
     * 
     * @param id 材料主键
     * @return 结果
     */
    @Override
    public int deleteDocumentById(Long id)
    {
        return documentMapper.deleteDocumentById(id);
    }

    @Override
    public List<Document> selectDocumentByIds(String[] id_split) {
        return documentMapper.selectDocumentByIds(id_split);
    }
}
