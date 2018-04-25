package com.project.controller;

import com.project.service.ExamService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class ExamController {

    @Resource
    private ExamService examService;

    /**
     * 获取所有考试试题
     * @param type
     * @return
     */
    @RequestMapping(value="/exams/{type}",method= RequestMethod.GET)
    public Object getExams(@PathVariable String type) {
        return examService.getExams(type);
    }

    /**
     * 创建新的试题
     * @param req
     * @return
     */
    @RequestMapping(value="/exams",method= RequestMethod.POST)
    public Object createExam(HttpServletRequest req){
        return examService.createExam(req);
    }

    /**
     * 删除一个试题
     * @param examId
     * @param type
     * @return
     */
    @RequestMapping(value="/exams/{type}/{examId}",method= RequestMethod.DELETE)
    public Object deleteExam(@PathVariable String type,@PathVariable int examId){
        return examService.deleteExam(type,examId);
    }

    /**
     * 查询单个试题
     * @param examId
     * @param type
     * @return
     */
    @RequestMapping(value="/exams/{type}/{examId}",method= RequestMethod.GET)
    public Object getExam(@PathVariable String type,@PathVariable int examId){
        return examService.getExam(type,examId);
    }

    /**
     * 修改单个试题
     * @param req
     * @param examId
     * @return
     */
    @RequestMapping(value="/exams/{examId}",method= RequestMethod.POST)
    public Object UpdateExam(HttpServletRequest req,@PathVariable int examId){
        return examService.UpdateExam(req, examId);
    }

    /**
     * 生成考试试卷
     * @return
     */
    @RequestMapping(value="/exam/paper",method= RequestMethod.POST)
    public Object createExamPaper(HttpServletRequest req) {
        return examService.createExamPaper(req);
    }

    /**
     * 考生获取考试试卷进行答题
     * @param paperId
     * @return
     */
    @RequestMapping(value="/exam/paper/{paperId}",method= RequestMethod.GET)
    public Object getExamPaper(HttpServletRequest req,@PathVariable int paperId) {
        return examService.getExamPaper(req,paperId);
    }

    /**
     * 提交考试试卷
     * @return
     */
    @RequestMapping(value="/exam/submit",method= RequestMethod.POST)
    public Object submitExamPaper(HttpServletRequest req) {
        return examService.submitExamPaper(req);
    }

}
