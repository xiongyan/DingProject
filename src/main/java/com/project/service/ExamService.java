package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface ExamService {
    /**
     * 获取考试试题
     * @return
     */
    Object getExams(String type);

    /**
     * 创建新的试题
     * @param req
     * @return
     */
    Object createExam(HttpServletRequest req);

    /**
     * 删除一个试题
     * @param examId
     * @return
     */
    Object deleteExam(String type,int examId);

    /**
     * 查询单个试题
     * @param userId
     * @return
     */
    Object getExam(String type,int userId);

    /**
     * 修改单个试题
     * @param examId
     * @param req
     * @return
     */
    Object UpdateExam(HttpServletRequest req, int examId);

    /**
     * 生成考试试卷
     * @return
     */
    Object createExamPaper(HttpServletRequest req);

    /**
     * 考生获取考试试卷进行答题
     * @param paperId
     * @return
     */
    Object getExamPaper(HttpServletRequest req,int paperId);

    /**
     * 提交考试试卷
     * @return
     */
    Object submitExamPaper(HttpServletRequest req);

}

