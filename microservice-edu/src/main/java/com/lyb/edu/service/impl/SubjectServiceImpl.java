package com.lyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyb.common.util.ExcelImportUtil;
import com.lyb.edu.entity.Subject;
import com.lyb.edu.entity.Teacher;
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
     * 根据ID删除课程分类
     * @param id 课程分类ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteById(String id) {

        //根据ID查看数据库中是否存在此ID为parent_id(存在二级分类)
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //如果有则不能删除，返回false
        if(subjects.size()!=0){
            return false;
        }
        //如果没有则删除并返回true
        int i = baseMapper.deleteById(id);

        return i==1;
    }

    /**
     * 添加一级课程分类
     * @param subject 需要添加课程分类对象
     * @return 是否添加成功
     */
    @Override
    public Boolean saveLevelOneSubject(Subject subject) {

        String title = subject.getTitle();
        //标题不能为空
        if(title==null||title.equals("")){
            return false;
        }
        //判断该一级分类是否已存在
        Subject lastSubject = this.getSubjectByTitle(title);
        //如果存在，返回false
        if(lastSubject!=null){
            return false;
        }
        //如果不存在则保存到数据库并返回true
        subject.setSort(selectLastSortOfLevelOneSubject()+1);
        int i = baseMapper.insert(subject);

        return i==1;
    }

    /**
     * 添加二级课程分类
     * @param subject 需要添加课程分类对象
     * @return 是否添加成功
     */
    @Override
    public Boolean saveLevelTwoSubject(Subject subject) {
        String title = subject.getTitle();
        String parentId = subject.getParentId();
        //标题不能为空
        if(title==null||title.equals("")){
            return false;
        }
        //判断该二级分类是否已存在
        Subject lastSubject = this.getSubjectByTitle(title,parentId);
        //如果存在，返回false
        if(lastSubject!=null){
            return false;
        }
        //如果不存在则保存到数据库并返回true
        subject.setSort(selectLastSortOfLevelTwoSubject(parentId));
        int i = baseMapper.insert(subject);

        return i==1;
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

    /**
     * 查询当前一级课程分类的最新排序号
     */
    private int selectLastSortOfLevelOneSubject(){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        queryWrapper.orderByDesc("sort");
        Page<Subject> pageParam = new Page<>(1,1);
        return baseMapper.selectPage(pageParam,queryWrapper).getRecords().get(0).getSort();
    }

    /**
     * 查询指定一级分类课程下二级分类的最新排号,返回当前添加的二级分类应该使用的序号
     */
    private int selectLastSortOfLevelTwoSubject(String parent_id){
        //查找指定parent_id的二级分类数据
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parent_id);
        queryWrapper.orderByDesc("sort");
        Page<Subject> pageParam = new Page<>(1,1);
        List<Subject> records = baseMapper.selectPage(pageParam, queryWrapper).getRecords();
        //如果二级分类数据为0，则当前二级分类排序序号为对应的一级分类的排序序号
        if(records.size()==0){
            QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", parent_id);
            return baseMapper.selectOne(queryWrapper2).getSort();
        }else{//如果指定一级分类存在二级分类，则当前二级分类排序序号为最新二级分类排序序号+1
            return records.get(0).getSort()+1;
        }
    }
}
