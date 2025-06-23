package com.fiveup.core.classManage.controller;

import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.model.dto.ClassResp;
import com.fiveup.core.classManage.model.request.ClassPageReq;
import com.fiveup.core.classManage.model.request.ClassReq;
import com.fiveup.core.classManage.model.response.ClassPageResp;
import com.fiveup.core.classManage.model.*;
import com.fiveup.core.classManage.service.ClassManageService;
import com.fiveup.core.common.controller.BaseController;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageInfo;
import com.fiveup.core.management.service.CommonManagementService;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/class")
public class ClassManageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ClassManageController.class);

    @Resource
    private ClassManageService classManageService;

    //    @GetMapping("/getClassByGradeId")
//    public CommonResponse getClassByGradeId(@RequestParam int gradeId) {
//        List<Integer> classList;
//
//        classList = classManageService.getAllClassByGrade(gradeId);
//        System.out.println("!!!!测试"+classList);
//        return CommonResponse.ok(classList);
//    }
    @GetMapping("/getClassByGradeId")
    public CommonResponse<List<Integer>> getClassByGradeId(@RequestParam int gradeId) {
        List<Integer> classList;
        try {
            classList = classManageService.getAllClassByGrade(gradeId);
            logger.info("！！Successfully retrieved classes by grade ID: {}", classList);
            return CommonResponse.ok(classList);
        } catch (Exception e) {
            logger.error("错啦！！Error getting classes by grade ID: {}", gradeId, e);
            return CommonResponse.fail(404, "Failed to get classes by grade ID");
        }
    }


//
//    @GetMapping(value="/getAllClassInfo")
//    public CommonResponse getAllClassInfo() {
//        List<ClassAndMonitor> classAndMonitor;
//        classAndMonitor = classManageService.getAllClassInfo();
//        return CommonResponse.ok(classAndMonitor);
//    }
//
//    @PostMapping(value="/addClass")
//    public CommonResponse addClass(@RequestBody ClassInfo classInfo) {
//        int code = classManageService.addClass(classInfo);
//        return CommonResponse.ok(code);
//    }
//
//    @PostMapping(value="/addTeacherToClass")
//    public CommonResponse addTeacherToClass(@RequestBody Map<String, List<CTCorrelation>> correlationList) {
//        List<CTCorrelation> correlations = correlationList.get("correlationList");
//        for(CTCorrelation ctCorrelation: correlations) {
//            classManageService.addTeacherToClass(ctCorrelation);
//        }
//        return CommonResponse.ok();
//    }
//
//    @GetMapping(value="/getStudentsByClass")
//    public CommonResponse getStudentsByClass(int gradeId, int classId) {
//        List<StudentInfo> studentList;
//        studentList = classManageService.getStudentsByClass(gradeId, classId);
//        return CommonResponse.ok(studentList);
//    }

    /**
     * 添加班级
     *
     * @param classReq
     * @return CommonResponse
     * @author 王德荣
     * @date 2024/11/25
     */
    @PostMapping(value = "/addClass")
    public CommonResponse addClass(@RequestBody ClassReq classReq) {
        logger.info(classReq.toString());
        if (classReq == null) {
            return CommonResponse.fail(-1, "参数为空");
        }
        if (classReq.getGradeId() < 1 || classReq.getGradeId() > 6) {
            return CommonResponse.fail(-1, "年级参数异常，只能为1~6");
        }
        // 数据库中class字段为tinyint，取值范围为-128~127
        if (classReq.getClassId() < 1 || classReq.getClassId() > 127) {
            return CommonResponse.fail(-1, "班级参数异常，最大为127");
        }
        if (classReq.getTeacherName() == null || classReq.getTeacherName().isEmpty()) {
            return CommonResponse.fail(-1, "班主任参数异常");
        }
        ClassResp classResp = classManageService.addClass(classReq);
        if (classResp.getCode() == 1) {
            return CommonResponse.ok(null, classResp.getMessage());
        }
        return CommonResponse.fail(-1, classResp.getMessage());
    }


    //查询班级列表
    @PostMapping("/getClassListByPage")
    public CommonResponse<PageInfo<ClassPageResp>> getClassListByPage(@RequestBody ClassPageReq classPageReq, HttpServletRequest request) {
        // 从请求对象中获取页码和每页显示条目数量
        Integer pageNum = classPageReq.getPageNum();
        Integer pageSize = classPageReq.getPageSize();

        // 初始化年级和班长ID为默认值-1
        int grade = -1;
        int monitorId = -1;
        List<Integer> classIds = new ArrayList<>();
        if (this.getCurrentUserIdentity(request) == 2) {
            classIds = this.getClassId(request);
            if (classIds.isEmpty()){
                classIds.add(-1);
            }
        }
        // 如果请求中提供了年级信息，则解析并调整（假设这里的+1是根据业务需求进行的特定处理）
        if (classPageReq.getGrade() != null && !classPageReq.getGrade().isEmpty()) {
            grade = Integer.parseInt(classPageReq.getGrade()) + 1;
        }

        // 如果请求中提供了真实姓名，则通过服务查询对应的班长ID
        if (classPageReq.getRealName() != null && !classPageReq.getRealName().isEmpty()) {
            monitorId = classManageService.getMonitorIdByName(classPageReq.getRealName());
        }

        // 检查页码参数的有效性
        if (pageNum <= 0 || pageSize < 0) {
            // 如果页码或每页条数无效，返回错误响应
            return CommonResponse.fail(1001, "页码参数校验异常");
        }

        // 调用服务层方法获取班级列表
        PageInfo<ClassPageResp> classPageRespPageInfo = classManageService.getClassListByPage(
                classIds,
                grade,
                monitorId,
                pageNum,
                pageSize);

        // 返回成功响应，附带班级列表信息
        return CommonResponse.ok(classPageRespPageInfo);
    }

    //查询班主任列表
    @GetMapping("/getMonitorList")
    public CommonResponse getMonitorList() {
        List<Teacher> teacherList = classManageService.getMonitorList();
        List<String> teacherNameList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherNameList.add(teacher.getTeacherName());
        }
        return CommonResponse.ok(teacherNameList);
    }

    //查询所有教师任表
    @GetMapping("/getAllTeacherList")
    public CommonResponse getAllTeacherList() {
        List<Teacher> teacherList = classManageService.getAllTeacherList();
        List<String> teacherNameList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            teacherNameList.add(teacher.getTeacherName());
        }
        return CommonResponse.ok(teacherNameList);
    }


    /**
     * 根据班级ID删除班级信息。
     *
     * @param id 班级的唯一标识符
     * @return 删除操作的结果响应
     */
    @DeleteMapping("/deleteClass/{id}")
    public CommonResponse deleteClass(@PathVariable int id) {
        // 打印班级ID，便于调试
        System.out.println(id);

        // 调用服务层的方法尝试删除指定ID的班级
        boolean success = classManageService.deleteClass(id);

        // 打印删除结果，便于调试
        System.out.println("ClassManageController(142): add = " + success);

        // 判断删除是否成功
        if (success) {
            // 如果删除成功，返回成功的通用响应
            return CommonResponse.ok();
        } else {
            // 如果删除失败，返回包含错误信息的通用响应
            return CommonResponse.fail(-1, "删除失败，该班级中存在未删除的学生信息");
        }
    }

    @GetMapping(value = "/getClassPartInfoByClassNameAndGrade")
    public CommonResponse getClassPartInfoByClassAndGrade(String grade, String className) {
        String gradeName = null;
        logger.info("*************************************************");
        System.out.println("grade = " + grade);
        System.out.println("className = " + className);
        switch (grade) {
            case "1年级":
                gradeName = "1";
                break;
            case "2年级":
                gradeName = "2";
                break;
            case "3年级":
                gradeName = "3";
                break;
            case "4年级":
                gradeName = "4";
                break;
            case "5年级":
                gradeName = "5";
                break;
            case "6年级":
                gradeName = "6";
                break;
            default:
        }
        ClassPartInfo classPartInfo;
        System.out.println("-----------");
        System.out.println("gradeName = " + gradeName);
        System.out.println("className = " + className);
        classPartInfo = classManageService.getClassPartInfoByClassId(gradeName, className);
        System.out.println("classPartInfo = " + classPartInfo);
        return CommonResponse.ok(classPartInfo);
    }

    @GetMapping(value = "/getStudentByPage")
    public CommonResponse getStudentByPage(int classId, int page, int pageSize) {
        List<StudentInfo> studentList;
        studentList = classManageService.getStudentByPage(classId, page, pageSize);
        return CommonResponse.ok(studentList);
    }

    @GetMapping(value = "/deleteStudent")
    public CommonResponse deleteStudent(int studentId) {
        classManageService.deletestudent(studentId);
        return CommonResponse.ok();

    }

    @PutMapping(value = "/updateClassByClassInformationId")
    public CommonResponse updateClassByInformationId(int classInformationId, String classIntroduction) {
        int res;
        res = classManageService.updateClassByInformationId(classInformationId, classIntroduction);
        return CommonResponse.ok(res);
    }

    @PostMapping("/upload")
    public CommonResponse uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            // 文件为空，进行处理，比如返回错误信息
            return CommonResponse.fail(500, "文件为空");
        }

        try {
            logger.info("Uploading Excel file");
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // 假设要读取第一个工作表
            List<GradeInfo> gradeList = new ArrayList<>();
            int numRows = sheet.getPhysicalNumberOfRows();
            System.out.println("numRows:" + numRows);
            for (int i = 1; i < numRows; i++) {
                Row row = sheet.getRow(i);
                // 假设字段的顺序是 "年级", "班级", "教师"
                String grade = getStringCellValue(row.getCell(0));
                String className = getStringCellValue(row.getCell(1));
                String headTeacherNAme = getStringCellValue(row.getCell(2));

                GradeInfo gradeInfo = new GradeInfo();
                gradeInfo.setGrade(grade);
                gradeInfo.setClassName(className);
                gradeInfo.setTeacherName(headTeacherNAme);
                gradeList.add(gradeInfo);
            }
            logger.info(gradeList.toString());
            workbook.close();
            int res = classManageService.addGrade(gradeList);
            if (res == -1) {
                return CommonResponse.fail(500, "教师不存在");
            }
            if (res == -2) {
                return CommonResponse.fail(500, "年级存在空值");
            }
            if (res == -3) {
                return CommonResponse.fail(500, "班级存在空值");
            }
            return CommonResponse.ok(gradeList);
        } catch (IOException e) {
            e.printStackTrace();
            // 处理上传失败后的逻辑
            return CommonResponse.fail(500, "上传失败");
        }

    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    /**
     * 处理文件下载请求，将指定的数据作为Excel文件发送给客户端。
     *
     * @param response HTTP响应对象，用于设置响应头和输出数据
     * @throws IOException 如果在处理I/O操作时发生错误
     */
    public void downloadFile(HttpServletResponse response) throws IOException {
        // 获取要下载的文件数据
        byte[] data = getFileData();

        // 设置响应的内容类型为二进制流，表示这是一个二进制文件
        response.setContentType("application/octet-stream");

        // 设置Content-Disposition响应头，告诉浏览器这是一个附件，并指定文件名
        response.setHeader("Content-Disposition", "attachment; filename=classInput.xlsx");

        // 获取响应的输出流，用于向客户端发送数据
        ServletOutputStream outputStream = response.getOutputStream();

        // 将文件数据写入输出流
        outputStream.write(data);

        // 刷新输出流，确保所有数据都被发送到客户端
        outputStream.flush();

        // 关闭输出流（可选，通常由容器自动管理）
        // outputStream.close();
        outputStream.flush();

        // 刷新输出流，确保所有数据都被发送到客户端
        outputStream.flush();

        // 关闭输出流（可选，通常由容器自动管理）
        // outputStream.close();

        // 刷新输出流，确保所有数据都被发送到客户端
        outputStream.flush();

        // 关闭输出流（可选，通常由容器自动管理）
        // outputStream.close();
    }

    private byte[] getFileData() throws IOException {
        String filePath = "classInput.xlsx";
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

}
    