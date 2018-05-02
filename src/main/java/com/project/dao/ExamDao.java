package com.project.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface ExamDao {
    /**
     * 获取所有选择题试题
     * @return
     */
    List<Map<String,Object>> getChoiceExams(String userId);

    /**
     * 获取所有判断题试题
     * @return
     */
    List<Map<String,Object>> getJudgeExams(String userId);

    /**
     * 创建新的试题
     * @param parameter
     * @return
     */
    int createExam(Map<String,Object> parameter);

    /**
     * 删除一个试题
     * @param parameter
     * @return
     */
    int deleteExam(Map<String,Object> parameter);

    /**
     * 查询单个试题
     * @param parameter
     * @return
     */
    Map<String,Object> getExam(Map<String,Object> parameter);

    /**
     * 修改单个试题
     * @param parameter
     * @return
     */
    int UpdateExam(Map<String,Object> parameter);

    /**
     * 生成考试试卷 choice
     * @return
     */
    List<Map<String,Object>> createExamPaperFromChoice(Map<String,Object> parameter);

    /**
     * 生成考试试卷 judge
     * @return
     */
    List<Map<String,Object>> createExamPaperFromJudge(int num);

    /**
     * 保存生成的试卷
     * @param parameter
     * @return
     */
    int savePaper(Map<String,Object> parameter);

    /**
     * 考生获取考试试卷进行答题
     * @param paperId
     * @return
     */
    Map<String,Object> getExamPaper(int paperId);

    /**
     * 根据用户Id和试卷ID来查询是否有考试记录
     * @return
     */
    Map<String,Object> getExamRecord(Map<String,Object> parameter);

    /**
     * 提交考试试卷
     * @return
     */
    int submitExamPaper(Map<String,Object> parameter);

    /**
     * 修改提交的考试试卷记录
     * @return
     */
    int updateExamPaper(Map<String,Object> parameter);

    /**
     * 获取该考试学生分数的排名
     * @return
     */
    Map<String,Object> getExamPaperScoreRank(Map<String,Object> parameter);
}
