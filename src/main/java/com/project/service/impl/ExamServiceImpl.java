package com.project.service.impl;

import com.project.dao.ExamDao;
import com.project.model.RespEntity;
import com.project.service.ExamService;
import com.project.util.CacheUtil;
import com.project.util.DingUtil;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("examService")
public class ExamServiceImpl implements ExamService {

    @Resource
    private ExamDao examDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取考试试题
     * @return
     */
    @Override
    public Object getExams(HttpServletRequest req,String type) {
        List<Map<String,Object>> list = null;
        //获取用户信息
        String token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
        String userId = jsonUtil.getStringOrElse("userid");
        if(type.equalsIgnoreCase("choice")){
            list = examDao.getChoiceExams(userId);
        }else if(type.equalsIgnoreCase("judge")){
            list = examDao.getJudgeExams(userId);
        }
        int code = 500;
        String msg = "内部发生异常";
        if(list != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(list);
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 创建新的试题
     * @param req
     * @return
     */
    @Override
    public Object createExam(HttpServletRequest req){
        int code = 500;
        String msg = "添加试题异常";
        try{
            JSONObject jsonObject = requestUtil.getBody(req);
            JsonUtil res = new JsonUtil(jsonObject);
            String title = res.getStringOrElse("title");
            String type = res.getStringOrElse("type");
            JSONObject options = res.getJSONObject("options", null);
            String answer = res.getStringOrElse("answer");
            Map<String,Object> exams = new HashMap<>();
            exams.put("title",title);
            exams.put("answer",answer);
            if(type.equalsIgnoreCase("choice")){
                exams.put("table","exams_choice");
            }else if(type.equalsIgnoreCase("judge")){
                exams.put("table","exams_judge");
            }
            if(options != null){
                exams.put("options",options.toString());
            }else{
                exams.put("options","{}");
            }
            //获取用户信息
            String token = req.getHeader("token");
            JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
            exams.put("createUser", jsonUtil.getStringOrElse("userid"));
            JSONArray array = jsonUtil.getJSONArray("department",null);
            if(array != null && array.length() > 0){
                int department = array.getInt(0);
                JsonUtil depart = new JsonUtil(DingUtil.getInstance().getApartment(department));
                int parentId = depart.getIntOrElse("parentid",-1);
                if(parentId == -1){
                    exams.put("audit", "true");
                }else {
                    exams.put("audit", "false");
                }
            }else{
                exams.put("audit","true");
            }
            int flag = examDao.createExam(exams);
            if(flag == 1){
                code = 200;
                msg = "成功添加试题";
                respEntity.setData(exams);
            }else {
                respEntity.setData(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 删除一个试题
     * @param examId
     * @return
     */
    public Object deleteExam(String type,int examId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        Map<String,Object> parameter = new HashMap<>();
        if(type.equalsIgnoreCase("choice")){
            parameter.put("table","exams_choice");
        }else if(type.equalsIgnoreCase("judge")){
            parameter.put("table","exams_judge");
        }
        parameter.put("examId",examId);
        int res = examDao.deleteExam(parameter);
        if(res == 1){
            code = 200;
            msg = "删除成功";
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }

    /**
     * 查询单个试题
     * @param examId
     * @return
     */
    public Object getExam(String type,int examId){
        int code;
        String msg;
        Map<String,Object> parameter = new HashMap<>();
        if(type.equalsIgnoreCase("choice")){
            parameter.put("table","exams_choice");
        }else if(type.equalsIgnoreCase("judge")){
            parameter.put("table","exams_judge");
        }
        parameter.put("examId",examId);
        Map<String,Object> exam = examDao.getExam(parameter);
        if(exam != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(exam);
        }else{
            code = 500;
            msg = "找不到该数据";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 修改单个试题
     * @param examId
     * @param req
     * @return
     */
    public Object UpdateExam(HttpServletRequest req, int examId){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String title = res.getStringOrElse("title");
        String type = res.getStringOrElse("type");
        JSONObject options = res.getJSONObject("options", null);
        String answer = res.getStringOrElse("answer");
        int code;
        String msg;
        Map<String,Object> exams = new HashMap<>();
        exams.put("id",examId);
        if(type.equalsIgnoreCase("choice")){
            exams.put("table", "exams_choice");
        }else if(type.equalsIgnoreCase("judge")){
            exams.put("table", "exams_judge");
        }
        exams.put("title", title);
        exams.put("answer", answer);
        if(options != null){
            exams.put("options",options.toString());
        }else{
            exams.put("options","{}");
        }
        int flag = examDao.UpdateExam(exams);
        if(flag == 1){
            code = 200;
            msg = "更新用户成功";
            respEntity.setData(exams);
        }else {
            code = 500;
            msg = "更新用户失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 生成考试试卷 choice judge
     * @return
     */
    public Object createExamPaper(HttpServletRequest req) {
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        int choice = res.getIntOrElse("choice",50);
        int judge = res.getIntOrElse("judge",50);
        int time = res.getIntOrElse("time",60);
        Double judge_score = res.getDoubbleOrElse("judge_score", 1.0);
        Double choice_score = res.getDoubbleOrElse("choice_score", 1.0);
        int code;
        String msg;
        //随机获取选择题
        Map<String,Object> choicePara = new HashMap<>();
        choicePara.put("num",choice);
        choicePara.put("table","exams_choice");
        List<Map<String,Object>> choiceList = examDao.createExamPaper(choicePara);
        //随机判断选择题
        Map<String,Object> judgePara = new HashMap<>();
        judgePara.put("num",judge);
        judgePara.put("table","exams_judge");
        //获取试卷列表
        List<Map<String,Object>> judgeList = examDao.createExamPaper(judgePara);
        if(judgeList != null && judgeList.size() > 0){
            choiceList.addAll(judgeList);
        }
        if(choiceList != null && choiceList.size() == (choice+judge)){
            code = 200;
            msg = "获取试卷成功";
            List<String> answer = new ArrayList<>();
            for(Map<String,Object> obj : choiceList){
                answer.add(obj.get("answer").toString());
            }
            respEntity.setData(choiceList);
            //想数据保存一份试卷
            Map<String,Object> paper = new HashMap<>();
            paper.put("time",time);
            paper.put("paper",choiceList.toString());
            paper.put("time",time);
            paper.put("judge_score",judge_score);
            paper.put("choice_score",choice_score);
            paper.put("answer",answer.toString());
            String token = req.getHeader("token");
            JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
            paper.put("userId",jsonUtil.getStringOrElse("userid"));
            paper.put("userName",jsonUtil.getStringOrElse("name"));
            examDao.savePaper(paper);
        }else{
            code = 200;
            msg = "获取试卷成功";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 考生获取考试试卷进行答题
     * @param paperId
     * @return
     */
    public Object getExamPaper(HttpServletRequest req,int paperId) {
        int code = 500;
        String msg = "获取数据失败";
        Map<String,Object> paper = examDao.getExamPaper(paperId);
        if(paper != null){
            //查询该用户是否已经在考试或是已经考完
            Map<String,Object> parameter = new HashMap<>();
            String token = req.getHeader("token");
            JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
            parameter.put("userId",jsonUtil.getStringOrElse("userid"));
            parameter.put("paperId",paperId);
            Map<String,Object> record = examDao.getExamRecord(parameter);
            if(record != null){
                paper.put("time",record.get("time"));
                paper.put("score",record.get("score"));
                paper.put("userAnswer",record.get("answer"));
            }else{
                paper.put("score",0);
                paper.put("userAnswer","");
            }
            code = 200;
            msg = "成功获取数据";
            respEntity.setData(paper);
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 提交考试试卷
     * @return
     */
    public Object submitExamPaper(HttpServletRequest req) {
        int code = 500;
        String msg = "内部异常异常";
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        JSONArray answer = res.getJSONArray("answer", null);
        int paperId = res.getIntOrElse("paperId", -1);
        String userId = res.getStringOrElse("userId");
        int time = res.getIntOrElse("time",-1);
        //计算得分
        Map<String,Object> paper = examDao.getExamPaper(paperId);
        double choice_score = Double.parseDouble(paper.get("choice_score").toString());
        double judge_score = Double.parseDouble(paper.get("judge_score").toString());
        JSONArray correct_answer = new JSONArray(paper.get("answer").toString());
        int choice_count=0;
        int judge_count=0;
        for(int i = 0;i<answer.length();i++){
            String temp = answer.getString(i);
            if((temp.equalsIgnoreCase("true") || temp.equalsIgnoreCase("false")) && temp.equalsIgnoreCase(correct_answer.getString(i))){
                judge_count++;
            }else if(temp.equalsIgnoreCase(correct_answer.getString(i))){
                choice_count++;
            }
        }
        double score = choice_count*choice_score + judge_count*judge_score;
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("userId",userId);
        parameter.put("paperId",paperId);
        parameter.put("time",time);
        parameter.put("answer",answer.toString());
        parameter.put("score",score);
        int flag = examDao.submitExamPaper(parameter);
        if(flag == 1){
            code = 200;
            msg = "提交成功";
            respEntity.setData(parameter);
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }
}
