package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyb.common.util.ExcelImportUtil;
import com.lyb.edu.entity.Subject;
import com.lyb.edu.mapper.SubjectMapper;
import com.lyb.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyb.edu.vo.SubjectSecondLevelVo;
import com.lyb.edu.vo.SubjectVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liuyoubin
 * @since 2019-11-08
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * 读取Excel文件记录并导入数据库
     * @param file Excel文件
     * @return 导入出错提示
     * @throws Exception 异常
     */
    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) throws Exception {

        //错误消息列表
        List<String> errorMsg = new ArrayList<>();

        //创建Excel工具类对象
        ExcelImportUtil excelImportUtil = new ExcelImportUtil(file.getInputStream());
        //获取工作表
        HSSFSheet sheet = excelImportUtil.getSheet();

        //获取工作表数据行数量
        int rowCount = sheet.getPhysicalNumberOfRows();
        if(rowCount<=1){
            errorMsg.add("请填充数据");
            return errorMsg;
        }

        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);

            if(rowData!=null){//行不为空
                //获取一级分类
                String levelOneValue = "";
                Cell levelOneCell = rowData.getCell(0);
                if(levelOneCell!=null){
                    //获取单元格数据
                    levelOneValue = excelImportUtil.getCellValue(levelOneCell).trim();
                    if (StringUtils.isEmpty(levelOneValue)){
                        errorMsg.add("第"+rowNum+"行一级分类为空");
                        continue;
                    }
                }

                //判断一级分类是否重复
                Subject subject = getSubjectByTitle(levelOneValue);
                String parentId;
                if(subject==null){
                    //将一级分类存入数据库
                    Subject subjectLevelOne = new Subject().setTitle(levelOneValue).setSort(rowNum);
                    baseMapper.insert(subjectLevelOne);
                    parentId = subjectLevelOne.getId();
                }else{
                    parentId = subject.getId();
                }


                //获取二级分类
                String levelTwoValue = "";
                Cell levelTwoCell = rowData.getCell(1);
                if(levelTwoCell!=null){
                    //获取单元格数据
                    levelTwoValue = excelImportUtil.getCellValue(levelTwoCell).trim();
                    if(StringUtils.isEmpty(levelTwoValue)){
                        errorMsg.add("第"+rowNum+"行二级分类为空");
                        continue;
                    }
                }

                //判断二级分类是否重复
                if(getSubjectByTitle(levelTwoValue,parentId)==null){
                    //将二级分类存入数据库
                    Subject subjectLevelTwo = new Subject().setParentId(parentId).setTitle(levelTwoValue).setSort(rowNum);
                    baseMapper.insert(subjectLevelTwo);
                }
            }
        }

        return errorMsg;
    }

    /**
     * 获取嵌套的课程列表
     * @return 嵌套的课程列表
     */
    @Override
    public List<SubjectVo> getNestedSubjectList() {
        //最终数据列表
        List<SubjectVo>  subjectVoList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort","id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper.orderByAsc("sort","id");
        List<Subject> SecondLevelSubjects = baseMapper.selectList(queryWrapper2);

        //构建一级分类的Vo数据
        for (Subject subject : subjects) {
            //创建一级分类Vo对象
            SubjectVo subjectVo = new SubjectVo();
            BeanUtils.copyProperties(subject, subjectVo);
            subjectVoList.add(subjectVo);

            //构建二级分类的Vo数据
            ArrayList<SubjectSecondLevelVo> secondLevelVos = new ArrayList<>();
            for (Subject SecondLevelSubject : SecondLevelSubjects) {
                //判断二级分类是否匹配一级分类
                if (subject.getId().equals(SecondLevelSubject.getParentId())) {
                    //创建二级分类Vo对象
                    SubjectSecondLevelVo subjectSecondLevelVo = new SubjectSecondLevelVo();
                    BeanUtils.copyProperties(SecondLevelSubject, subjectSecondLevelVo);
                    secondLevelVos.add(subjectSecondLevelVo);
                }
            }
            subjectVo.setChildren(secondLevelVos);
        }

        return subjectVoList;
    }

    /**
     * 根据title查找一级Subject
     */
    private Subject getSubjectByTitle(String title){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据title以及parent_id查找二级Subject
     */
    private Subject getSubjectByTitle(String title,String parent_id){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("parent_id", parent_id);
        return baseMapper.selectOne(queryWrapper);
    }
}
