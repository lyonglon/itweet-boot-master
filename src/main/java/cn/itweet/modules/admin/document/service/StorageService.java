package cn.itweet.modules.admin.document.service;

import cn.itweet.common.exception.SystemException;
import cn.itweet.modules.admin.document.entiry.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by whoami on 22/04/2017.
 */
public interface StorageService {
    /**
     * 文件默认栏目: 文章栏目
     * @param file
     * @param path
     * @return
     * @throws SystemException
     */
    String store(MultipartFile file, String path) throws SystemException;

    /**
     * 指定存储图片的栏目
     * @param file
     * @param path
     * @param columnd
     * @return
     * @throws SystemException
     */
    String store(MultipartFile file, String path,String columnd) throws SystemException;

    Page<Document> list(Integer page);

    Page<Document> selectByColumnd(Integer page, String columnd);

    Document loadById(Integer id);

    String loadRuleFilenameById(Integer id);

    void deleteById(Integer id, String filePath);

    void deleteByRuleFilename(String ruleFilename, String filePath);

    void deleteAll(String filePath);

    /**
     * 通过栏目获取最新的TopN的文件集合
     * @param columd
     * @param topN
     * @return
     */
    List<Document> getNewDocumentTopN(String columd, Integer topN);
}
