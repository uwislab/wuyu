package com.fiveup.core.commentgeneration.service.Impl;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.commentgeneration.mapper.CorpusMapper;
import com.fiveup.core.commentgeneration.service.CorpusService;
import com.fiveup.core.commentgeneration.service.SubjectService;
import com.fiveup.core.commentgeneration.vo.CorpusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 语料及评论业务逻辑实现
 */
@Service
public class CorpusServiceImpl implements CorpusService {

    @Autowired
    private CorpusMapper corpusMapper;
    @Autowired
    private SubjectService subjectService;

    @Override
    public void add(Corpus corpus) {
        corpusMapper.insert(corpus);
    }

    @Override
    public void deleteById(Integer id) {
        corpusMapper.deleteById(id);
    }

    @Override
    public void updateById(Corpus corpus) {
        corpusMapper.updateById(corpus);
    }

    @Override
    public List<Corpus> getList() {
        List<Corpus> corpuses = corpusMapper.selectList();
        return corpuses;
    }

    @Override
    public List<CorpusVO> getAll() {
//        List<Corpus> corpus = corpusMapper.selectList();
        List<CorpusVO> corpusVOS = corpusMapper.selectAll();
////        String[] SubjectName = {"泛用","德育","智育","体育","美育","劳育"};
//        for(Corpus i :corpus){
//            CorpusVO corpusVO = new CorpusVO();
//            Subject subject = subjectService.getById(i.getSubjectId());
//            corpusVO.setSubject(subject);
//            corpusVO.setId(i.getId());
//            corpusVO.setScore(i.getScore());
//            corpusVO.setComment(i.getComment());
//            corpusVO.setTid(i.getTid());
//            corpusVOS.add(corpusVO);
//        }
        return corpusVOS;
    }

    @Override
    public CorpusVO getById(Integer id) {
        CorpusVO corpusVO = corpusMapper.selectById(id);
        return corpusVO;
    }

    @Override
    public Integer deleteAll(Integer[] ids) {
        return corpusMapper.deleteAll(ids);
    }


    @Override
    public List<Corpus> getCorpusByPage(Integer pageNum, Integer pageSize) {

        int offset=(pageNum-1)*pageSize;
        List<Corpus> corpuses=corpusMapper.findAll1(offset,pageSize);

        return corpuses;
    }

    @Override
    public List<CorpusVO> search(Integer subjectId, String comment) {
        return corpusMapper.search(subjectId,comment);
    }


}
