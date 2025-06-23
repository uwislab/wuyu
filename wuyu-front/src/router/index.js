import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";
import Layout2 from "@/views/monitor/Layout/Layout";
import { getToken } from "@/utils/auth";
/**
 * 固定路由
 * 没有权限要求的基页
 * 所有角色均可以访问
 */
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: () => import("@/views/redirect/index"),
      },
    ],
  },
  {
    path: "/login",
    component: () => import("@/views/login/index"),
    hidden: true,
  },
  {
    path: "/auth-redirect",
    component: () => import("@/views/login/auth-redirect"),
    hidden: true,
  },
  {
    path: "/404",
    component: () => import("@/views/error-page/404"),
    hidden: true,
  },
  {
    path: "/401",
    component: () => import("@/views/error-page/401"),
    hidden: true,
  },
  {
    path: "/monitor",
    name: "Layout",
    component: Layout2,
    redirect: "/Home2",
    children: [
      {
        path: "/Home2",
        name: "Home2",
        component: () => import("@/views/monitor/views/Home2"),
      },

      {
        path: "/Home",
        name: "Home",
        component: () => import("@/views/monitor/views/Home"),
      },
      {
        path: "/Home4",
        name: "Home4",
        component: () => import("@/views/monitor/views/Home4"),
      },
      {
        path: "/Home5",
        name: "Home5",
        component: () => import("@/views/monitor/views/Home5"),
      },
    ],
  },
];

/**
 * 异步路由
 * 需要根据用户角色动态加载的路由
 */
export const asyncRoutes = [
  // 管理员/校长 主页
  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    meta: { title: "主页", icon: "dashboard" },
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: () => import("@/views/management/baseData/index"),
        meta: { title: "主页", icon: "dashboard", role: [0, 1, 2, 3] },
      },
      {
        path: "/Modify/index",
        component: () => import("@/layout/components/Modify/Modify password"),
        name: "修改密码",
        meta: { title: "修改密码", role: [0, 1, 2, 3] },
      },
      {
        path: "/Information/index",
        component: () =>
          import("@/layout/components/Modify/Personal information"),
        name: "个人信息",
        meta: { title: "个人信息", role: [0, 1, 2, 3] },
      },
    ],
  },
  {
    path: "/pression",
    component: Layout,
    redirect: "/pression",
    meta: { title: "权限管理", icon: "dashboard", role: [0] },
    children: [
      {
        path: "pression",
        name: "pression",
        component: () => import("@/views/pression/index"),
        meta: { title: "权限管理", icon: "dashboard", role: [0] },
      },
    ],
  },
  // {
  //   path: "/course-schedule",
  //   component: Layout,
  //   redirect: "/course-schedule/index",
  //   meta: { title: "排课管理", icon: "el-icon-date" },
  //   children: [
  //     {
  //       path: "index",
  //       name: "CourseSchedule",
  //       component: () => import("@/views/management/courseSchedule/index"),
  //       meta: { title: "排课管理", icon: "el-icon-date", role: [0,4] },
  //     },
  //   ],
  // },
  {
    path: "/teacher",
    component: Layout,
    redirect: "/teacher",
    meta: { title: "教师管理", icon: "dashboard", role: [0] },
    children: [
      {
        path: "teacher",
        name: "teacher",
        component: () => import("@/views/teacher/teacher"),
        meta: { title: "教师管理", icon: "dashboard", role: [0] },
      },
    ],
  },
  //用户管理
  {
    path: "/user",
    component: Layout,
    redirect: "/user",
    meta: { title: "用户管理", icon: "dashboard", role: [0] },
    children: [
      {
        path: "user",
        name: "user",
        component: () => import("@/views/user_management/user_manage.vue"),
        meta: { title: "用户管理", icon: "dashboard", role: [0] },
      },
    ],
  },
  {
    path: "/teacher_workspace",
    name: "TeacherWorkspace",
    component: () => import("@/views/teacher_workspace/teacherWork"),
    redirect: "/teacher_workspace/teacherWork",
    children: [
      {
        path: "/teacher_workspace/teacherWork",
        name: "TeacherWork",
        component: () => import("@/views/teacher_workspace/teacherWork")
      },
    ],
  },

  {
    path: "/teacher_workspace/teacherInfo",
    name: "TeacherInfo",
    component: () => import("@/views/teacher_workspace/teacherInfo"),
  },

  {
    path: "/teacher_workspace/classInfo",
    name: "班级信息",
    component: () => import("@/views/teacher_workspace/classManagement/index"),
  },

  {
    path: "/teacher_workspace/index",
    name: "工作事项",
    component: () => import("@/views/teacher_workspace/index"),
  },

  {
    path: "/teacher_workspace/lessonInfo",
    name: "任课信息",
    component: () => import("@/views/teacher_workspace/lessonInfo"),
  },

  {
    path: "/teacher_workspace/studentInfo",
    name: "学生信息",
    component: () => import("@/views/teacher_workspace/student/index"),
  },

  {
    path: "/teacher_workspace/teacherInfo",
    name: "教师信息",
    component: () => import("@/views/teacher_workspace/teacher/index"),
  },

  {
    path: "/teacher_workspace/sportScore",
    name: "体育成绩",
    component: () => import("@/views/teacher_workspace/sportScore/index"),
  },

  {
    path: "/teacher_workspace/deploy",
    name: "基础数据运维",
    component: () => import("@/views/teacher_workspace/deploy/index"),
  },
  {
    path: "/teacher_workspace/demonstrate/xxzl",
    name: "学校总览",
    component: () => import("@/views/teacher_workspace/demonstrate/xxzl"),
  },
  {
    path: "/teacher_workspace/demonstrate/wydc",
    name: "五育达成",
    component: () => import("@/views/teacher_workspace/demonstrate/wydc"),
  },
  {
    path: "/teacher_workspace/demonstrate/njqk",
    name: "年级情况",
    component: () => import("@/views/teacher_workspace/demonstrate/njqk"),
  },
  {
    path: "/teacher_workspace/demonstrate/njcz",
    name: "年级成长",
    component: () => import("@/views/teacher_workspace/demonstrate/njcz"),
  },
  {
    path: "/teacher_workspace/demonstrate/yxbz",
    name: "优秀表彰",
    component: () => import("@/views/teacher_workspace/demonstrate/yxbz"),
  },
  {
    path: "/teacher_workspace/demonstrate/bigDataIndx",
    name: "中控大屏",
    component: () =>
      import("@/views/teacher_workspace/demonstrate/bigDataIndex"),
  },
  {
    path: "/teacher_workspace/diagnose/FiveupChart",
    component: () => import("@/views/teacher_workspace/diagnose/FiveupChart"),
    name: "老师界面总体预览",
  },
  {
    path: "/teacher_workspace/diagnose/GradeFuReport",
    component: () => import("@/views/teacher_workspace/diagnose/GradeFuReport"),
    name: "老师界面年级成绩构成",
    hidden: true,
  },
  {
    path: "/teacher_workspace/diagnose/ClassFuReport",
    component: () => import("@/views/teacher_workspace/diagnose/ClassFuReport"),
    name: "老师界面班级成绩详情",
  },
  {
    path: "/teacher_workspace/diagnose/Di-results",
    component: () => import("@/views/teacher_workspace/diagnose/Di-results"),
    name: "老师界面总体诊断",
  },
  {
    path: "/teacher_workspace/diagnose/FiveupManagement",
    component: () =>
      import("@/views/teacher_workspace/diagnose/FiveupManagement"),
    name: "老师界面五育成绩管理",
  },
  {
    path: "/teacher_workspace/diagnose/Kanalysis",
    component: () => import("@/views/teacher_workspace/diagnose/Kanalysis"),
    name: "老师界面智育诊断",
  },
  {
    path: "/teacher_workspace/diagnose/SportsD",
    component: () => import("@/views/teacher_workspace/diagnose/SportsD"),
    name: "老师界面体育诊断",
  },
  {
    path: "/teacher_workspace/diagnose/Stugoal",
    component: () => import("@/views/teacher_workspace/diagnose/Stugoal"),
    name: "老师界面学生计划评价",
  },
  {
    path: "/teacher_workspace/passwordChange",
    name: "密码修改",
    component: () => import("@/views/teacher_workspace/passwordChange"),
  },

  //工程实践 古
  {
    path: "/teacher_workspace/index",
    name: "index",
    component: () => import("@/views/teacher_workspace/index"),
  },

  //问卷列表
  {
    path: "/questionnaire",
    component: Layout,
    redirect: "/questionnaire",
    children: [
      {
        path: "questionnaire",
        name: "问卷列表",
        component: () => import("@/views/questionnaire/paper/index"),
        meta: { title: "问卷列表", icon: "tab", role: [0, 1] },
      },

      {
        path: "preview",
        name: "预览",
        component: () => import("@/views/questionnaire/preview"),
        meta: { title: "预览", icon: "tab" },
        hidden: true,
      },

      {
        path: "question",
        component: () => import("@/views/questionnaire/question/index"),
        name: "修改题型",
        meta: { title: "修改题型", icon: "tab", affix: false },
        hidden: true,
      },
      {
        path: "count",
        component: () => import("@/views/questionnaire/count/index"),
        name: "统计",
        meta: { title: "统计", icon: "tab", affix: false },
        hidden: true,
      },
      {
        path: "/answersucess",
        component: () => import("@/views/questionnaire/answer/success"),
        name: "答题成功",
        meta: { title: "答题成功", icon: "tab", affix: false },
        hidden: true,
      },
      {
        path: "/answer",
        component: () => import("@/views/questionnaire/answer/index"),
        name: "答题",
        meta: { title: "答题", icon: "tab", affix: false },
        hidden: true,
      },
    ],
  },

  {
    path: "/goal",
    component: Layout,
    redirect: "/goal",
    meta: { title: "评价量表", icon: "el-icon-document-copy" },
    children: [
      // 主板块路由
      {
        path: "index",
        name: "评价量表管理",
        component: () => import("@/views/cultivation/goal"),
        meta: { title: "评价量表管理", role: [0, 1] },
      },
      {
        path: "assessmentProcess",
        name: "考核环节",
        component: () => import("@/views/cultivation/goal/assessmentProcess"),
        meta: { title: "考核环节", role: [0, 1] },
      },
      {
        path: "createDimension",
        name: "编辑量表",
        component: () => import("@/views/cultivation/goal/createDimension"),
        meta: { title: "编辑量表", role: [0, 1] },
      },
      {
        path: "implementation",
        name: "执行情况",
        component: () => import("@/views/cultivation/goal/implementation"),
        meta: { title: "执行情况", role: [0, 1] },
      },
      {
        path: "getRadarChart",
        name: "第一指标雷达图",
        component: () => import("@/views/cultivation/goal/radarChart"),
        meta: { title: "第一指标雷达图", role: [0, 1] },
      },
      // 子模块路由
      {
        path: "goalDetails12",
        name: "查看执行",
        component: () => import("@/views/cultivation/goal/goalDetails12"),
        meta: { title: "查看执行", role: [0,4] },
        hidden: true,
      },
      {
        path: "goalDetails",
        name: "目标详情",
        component: () => import("@/views/cultivation/goal/goalDetails"),
        meta: { title: "目标详情", role: [0,4] },
        hidden: true,
      },
      {
        path: "examineDetails",
        name: "考核内容",
        component: () => import("@/views/cultivation/goal/examineDetails"),
        meta: { title: "考核内容", role: [0,4] },
        hidden: true,
      },
      {
        path: "goalMindMap",
        name: "目标脑图",
        component: () => import("@/views/cultivation/goal/goalMindMap"),
        meta: { title: "目标脑图", role: [0,4] },
        hidden: true,
      },
      {
        path: "aspectManager",
        component: () => import("@/views/cultivation/goal/aspectManager"),
        name: "增加评价维度",
        meta: { title: "增加评价维度", role: [0,4] },
        hidden: true,
      },
      {
        path: "activityManager",
        name: "增加评价内容",
        component: () => import("@/views/cultivation/goal/activityManager"),
        meta: { title: "增加评价内容", role: [0,4] },
        hidden: true,
      },
    ],
  },

  {
    path: "/activityModule",
    component: Layout,
    redirect: "/activityModule/activityDisplay",
    meta: { title: "评价过程", icon: "el-icon-notebook-1", role: [0, 1] },


    children: [
      {
        path: "activityDisplay",
        name: "活动展示",
        component: () => import("@/views/activityModule/activityDisplay/index"),
        meta: {
          title: "活动展示",
          icon: "el-icon-data-analysis",
          role: [0, 1],
        },
      },
      {
        path: "addActivity",
        name: "创建活动",
        component: () => import("@/views/activityModule/addActivity/index"),
        meta: {
          title: "创建活动",
          icon: "el-icon-document-add",
          role: [0, 1],
        },
      },
      {
        path: "activityList",
        name: "活动评分",
        component: () => import("@/views/activityModule/activityList/index"),
        meta: { title: "活动评分", icon: "el-icon-files", role: [0, 1] },
      },
      {
        path: "activityDetail",
        name: "活动详情",
        component: () => import("@/views/activityModule/activityList/activityDetail.vue"),
        meta: { title: "活动详情", icon: "el-icon-files", role: [0, 1] },
        hidden: true,
      },
      {
        path: "activityScore",
        name: "活动打分",
        component: () => import("@/views/activityModule/activityList/activityScore.vue"),
        meta: { title: "活动打分", icon: "el-icon-files", role: [0, 1] },
        hidden: true,
      },
      {
        path: "editActivity",
        name: "活动情况",
        component: () => import("@/views/activityModule/editActivity/index"),
        meta: {
          title: "活动情况",
          icon: "el-icon-edit-outline",
          role: [0, 1],
        },
      },
    ],
  },
  {
    path: "/management",
    component: Layout,
    redirect: "/management/baseData",
    meta: { title: "基础数据管理", icon: "dashboard", role: [0, 1,2] },
    children: [
      {
        path: "baseData",
        name: "BaseData",
        component: () => import("@/views/management/baseData/index"),
        meta: { title: "基础数据", icon: "dashboard", role: [0, 1] },
      },
      {
        path: "classManagement",
        name: "ClassManagement",
        component: () => import("@/views/management/classManagement/index"),
        meta: { title: "班级信息", icon: "el-icon-s-order", role: [0, 1, 2] },
      },
      {
        path: "student",
        name: "StudentManagement",
        component: () => import("@/views/management/student/index"),
        meta: { title: "学生信息", icon: "el-icon-s-data", role: [0, 1, 2] },
      },
      {
        path: "teacher",
        name: "TeacherManagement",
        component: () => import("@/views/management/teacher/index"),
        meta: { title: "教师信息", icon: "el-icon-reading", role: [0, 1, 2] },
      },
      {
        path: "sportScore",
        name: "SportScoreManagement",
        component: () => import("@/views/management/sportScore/index"),
        meta: { title: "体育成绩", icon: "el-icon-s-order", role: [0, 1, 2] },
      },
      {
        path: "deploy",
        name: "DeployManagement",
        component: () => import("@/views/management/deploy/index"),
        meta: { title: "基础数据运维", icon: "el-icon-s-operation", role: [0, 1] },
      },
    ],
  },

  // {
  //   path: "/meeting",
  //   component: Layout,
  //   meta: {title: "会议纪要", icon: "el-icon-chat-square"},
  //   children: [
  //     {
  //       path: "index",
  //       component: () => import("@/views/cultivation/meeting/index"),
  //       name: "会议主页",
  //       meta: {title: "会议主页"}
  //     },
  //     {
  //       path: "myMeeting",
  //       component: () => import("@/views/cultivation/meeting/myMeeting"),
  //       name: "我的会议",
  //       meta: {title: "我的会议"}
  //     },
  //     {
  //       path: "meetingDetails",
  //       component: () => import("@/views/cultivation/meeting/meetingDetails"),
  //       name: "会议详情",
  //       meta: {title: "会议详情"},
  //       hidden: true
  //     },
  //     {
  //       path: "dataStatistics",
  //       component: () => import("@/views/cultivation/meeting/dataStatistics"),
  //       name: "数据统计",
  //       meta: {title: "数据统计"},
  //       hidden: true
  //     }
  //   ]
  // },
  // {
  //   path: "/guidance",
  //   component: Layout,
  //   redirect: "/guidance/index",
  //   meta: {title: "指导文库", icon: "el-icon-document"},
  //   children: [
  //     {
  //       path: "index",
  //       component: () => import("@/views/cultivation/guidance/index"),
  //       name: "文库主页",
  //       meta: {title: "文库主页"}
  //     },
  //     {
  //       path: "myGuidance",
  //       component: () => import("@/views/cultivation/guidance/myGuidance"),
  //       name: "我的文库",
  //       meta: {title: "我的文库"}
  //     },
  //     {
  //       path: "dataStatistics",
  //       component: () => import("@/views/cultivation/guidance/dataStatistics"),
  //       name: "数据统计",
  //       meta: {title: "数据统计"},
  //       hidden: true
  //     }
  //   ]
  // },

  {
    path: "/course_score",
    component: Layout,
    redirect: "/course_score",
    meta: { title: "学生成绩", icon: "el-icon-document-copy" },
    children: [
      {
        path: "table",
        component: () => import("@/views/course_score/table"),
        name: "数据展示",
        meta: { title: "数据展示", role: [0, 1, 2, 3] },
      },
      {
        path: "view",
        component: () => import("@/views/course_score/view"),
        name: "成绩录入",
        meta: { title: "成绩录入", role: [0, 1, 2, 3] },
      },
      {
        path: "scoreIndex",
        component: () => import("@/views/achievementAnalysis/IndexScore.vue"),
        name: "指标成绩录入",
        meta: { title: "指标成绩录入", role: [0, 1] },
      },
    ],
  },
  // {
  //   path: "/analyze",
  //   component: Layout,
  //   redirect: "/analyze/Usercharts",
  //   meta: { title: "学生五育分析", icon: "el-icon-data-analysis" },
  //   children: [
  //     {
  //       path: "DataProcessing",
  //       component: () => import("@/views/analyze/DataProcessing"),
  //       name: "DataProcessing",
  //       meta: { title: "数据更新" }
  //     },
  //     {
  //       path: "Userecharts",
  //       component: () => import("@/views/analyze/Usercharts"),
  //       name: "Userecharts",
  //       meta: { title: "数据分析1" }
  //     },
  //     {
  //       path: "Echarts_Relation",
  //       component: () => import("@/views/analyze/Echarts_Relation"),
  //       name: "Echarts_Relation",
  //       meta: { title: "数据分析2" }
  //     },
  //     {
  //       path: "KMeans_Point",
  //       component: () => import("@/views/analyze/KMeans_Point"),
  //       name: "KMeans_Point",
  //       meta: { title: "K-Means画图" }
  //     },
  //     {
  //       path: "WebInput",
  //       component: () => import("@/views/analyze/WebInput"),
  //       name: "WebInput",
  //       meta: { title: "数据预测" }
  //     },
  //     {
  //       path: "ProcessSet",
  //       component: () => import("@/views/analyze/ProcessSet"),
  //       name: "ProcessSet",
  //       meta: { title: "数据预处理与更新设置" },
  //       hidden: true
  //     }
  //   ]
  // },
  /* 学习诊断*/
  {
    path: "/diagnose",
    component: Layout,
    redirect: "/diagnose/Di-results",
    meta: { title: "学生五育诊断", icon: "dashboard", role: [0, 1,2] },
    children: [
      {
        path: "FiveupChart",
        component: () => import("@/views/diagnose/FiveupChart"),
        name: "总体预览",
        meta: { title: "总体预览", role: [0, 1,2] },
      },
      {
        path: "GradeFuReport",
        component: () => import("@/views/diagnose/GradeFuReport"),
        name: "年级成绩构成",
        meta: { title: "年级成绩构成", role: [0, 1,2] },
        hidden: true,
      },
      {
        path: "ClassFuReport",
        component: () => import("@/views/diagnose/ClassFuReport"),
        name: "班级成绩详情",
        meta: { title: "班级成绩详情", role: [0, 1,2] },
        hidden: true,
      },
      {
        path: "Di-results",
        component: () => import("@/views/diagnose/Di-results"),
        name: "总体诊断",
        meta: { title: "总体诊断", role: [0, 1,2] },
      },
      {
        path: "FiveupManagement",
        component: () => import("@/views/diagnose/FiveupManagement"),
        name: "五育成绩管理",
        meta: { title: "五育成绩管理", role: [0, 1,2] },
      },
      {
        path: "Kanalysis",
        component: () => import("@/views/diagnose/Kanalysis"),
        name: "智育诊断",
        meta: { title: "智育诊断", role: [0, 1,2] },
      },
      {
        path: "SportsD",
        component: () => import("@/views/diagnose/SportsD"),
        name: "体育诊断",
        meta: { title: "体育诊断", role: [0, 1,2] },
      },
      {
        path: "Stugoal",
        component: () => import("@/views/diagnose/Stugoal"),
        name: "学生计划评价",
        meta: { title: "学生计划评价", role: [0, 1,2] },
      },
    ],
  },
  {
    path: "/remark",
    component: Layout,
    redirect: "/remark/Home",
    meta: { title: "智能评语", icon: "dashboard", role: [0, 1,2] },
    children: [
      {
        path: "Home",
        component: () => import("@/views/remark/Home"),
        name: "学生信息管理",
        meta: { title: "学生信息管理", role: [0, 1, 2] },
      },
      {
        path: "Quantify",
        component: () => import("@/views/indicator/quantify"),
        name: "量化表",
        meta: { title: "评价指标", role: [0, 1, 2] },
      },
      {
        path: "Remark",
        component: () => import("@/views/remark/Remark"),
        name: "评语查看",
        meta: { title: "评语查看", role: [0, 1, 2] },
      },
      {
        path: "Feedback",
        component: () => import("@/views/remark/Feedback"),
        name: "反馈查看",
        meta: { title: "反馈查看", role: [0, 1, 2] },
      },
      {
        path: "Sturemark",
        component: () => import("@/views/remark/Sturemark"),
        name: "查看评语(学生)",
        meta: { title: "查看评语(学生)", role: [0, 1, 2] },
      },
      {
        path: "assess",
        name: "语料库",
        component: () => import("@/views/indicator/assess"),
        meta: { title: "语料库", role: [0, 1, 2] },
        hidden: true,
      },
      {
        path: "indicator",
        name: "指标",
        component: () => import("@/views/indicator/indicator"),
        meta: { title: "指标", role: [0, 1, 2] },
        hidden: true,
      },
    ],
  },

  {
    path: "/performanceevaluation",
    component: Layout,
    redirect: "/performanceevaluation/show",
    meta: { title: "成绩评定", icon: "el-icon-data-analysis", role: [0, 1,2] },
    children: [
      {
        path: "show",
        component: () => import("@/views/performanceevaluation/show"),
        name: "学生成绩管理",
        meta: { title: "学生成绩管理", role: [0, 1, 2] },
      },
      {
        path: "scoreweight",
        component: () => import("@/views/performanceevaluation/weight"),
        name: "课程权重管理",
        meta: { title: "课程权重管理", role: [0, 1,2] },
      },
      {
        path: "personal",
        component: () => import("@/views/performanceevaluation/personal"),
        name: "个人成绩表现",
        meta: { title: "个人成绩表现", role: [0, 1, 2] },
      },
      {
        path: "analysis",
        component: () => import("@/views/performanceevaluation/analysis"),
        name: "班级成绩分析",
        meta: { title: "班级成绩分析", role: [0, 1, 2] },
      },
      {
        path: "year",
        component: () => import("@/views/achievementAnalysis/YearScore.vue"),
        name: "年级情况总览",
        meta: { title: "年级情况总览", role: [0, 1] },
      },
    ],
  },
  {
    path: "/commentgeneration",
    component: Layout,
    redirect: "/commentgeneration/class",
    meta: { title: "评语生成", icon: "el-icon-s-comment", role: [0, 1,2] },
    children: [
      {
        path: "class",
        component: () => import("@/views/commentgeneration/class"),
        name: "班级评语册",
        meta: { title: "班级评语册", role: [0, 1, 2] },
      },
      {
        path: "edit",
        component: () => import("@/views/commentgeneration/edit"),
        name: "评语编辑",
        meta: { title: "评语编辑", role: [0, 1, 2] },
      },
      {
        path: "corpus",
        component: () => import("@/views/commentgeneration/corpus"),
        name: "语料库",
        meta: { title: "语料库", role: [0, 1, 2] },
      },
    ],
  },
   // 通知册
   {
    path: '/notice',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/notice/index'),
        name: 'Notice',
        meta: { title: '通知册', icon: 'el-icon-menu', role: [0, 1, 2] }
      }
    ]
  },
  {
    path: "/demonstrate",
    component: Layout,
    redirect: "/demonstrate/xxzl",
    meta: { title: "数据展示", icon: "dashboard",role: [0, 1]},
    children: [
      {
        path: "xxzl",
        component: () => import("@/views/demonstrate/xxzl"),
        name: "学校总览",
        meta: { title: "学校总览", role: [0, 1] },
      },
      {
        path: "wydc",
        component: () => import("@/views/demonstrate/wydc"),
        name: "五育雷达图",
        meta: { title: "五育雷达图", role: [0, 1] },
      },
      {
        path: "njqk",
        component: () => import("@/views/demonstrate/njqk"),
        name: "年级情况",
        meta: { title: "年级情况", role: [0, 1] },
      },
      {
        path: "njcz",
        component: () => import("@/views/demonstrate/njcz"),
        name: "年级成长",
        meta: { title: "年级成长", role: [0, 1] },
      },
      {
        path: "yxbz",
        name: "优秀表彰",
        component: () => import("@/views/demonstrate/yxbz"),
        meta: { title: "优秀表彰", role: [0, 1] },
      },
      {
        path: "bigDataIndex",
        component: () => import("@/views/demonstrate/bigDataIndex"),
        name: "中控大屏",
        meta: { title: "中控大屏", role: [0, 1] },
      },
      {
        path: "xxzs",
        component: () => import("@/views/demonstrate/xxzs"),
        name: "详细展示",
        hidden: true,
        meta: { title: "详细展示", role: [0, 1] },
      },
      {
        path: "grade",
        component: () => import("@/views/demonstrate/grade"),
        name: "年级成绩展示",
        meta: { title: "年级成绩展示", role: [0, 1] },
      },
      {
        path: "studentGradeChange",
        component: () => import("@/views/demonstrate/studentGradeChange"),
        name: "五育成绩展示",
        meta: { title: "五育成绩展示", role: [0, 1] },
      },
      {
        path: "average-education-achievement",
        component: () => import("@/views/demonstrate/average-education-achievement"),
        name: "平均五育成绩展示",
        meta: { title: "平均五育成绩展示", role: [0, 1] },
      },
    ],
  },

  {
    path: "/wuyu-overview",
    component: Layout,
    redirect: "/wuyu-overview/index",
    meta: { title: "五育情况总览", icon: "dashboard", role: [0, 1] },
    children: [
      {
        path: "index",
        component: () => import("@/views/wuyu-overview/index"),
        name: "五育总览",
        meta: { title: "五育总览", role: [0, 1] },
      },
      {
        path: "wuyu_item_info",
        component: () => import("@/views/wuyu-overview/grade_wuyu_info"),
        name: "单一年级五育成绩总览",
        meta: { title: "单一年级五育成绩总览", role: [0, 1,2] },
        hidden: true,
      },
      {
        path: "grade_class_info",
        component: () => import("@/views/wuyu-overview/grade_class_info"),
        name: "成绩对比详情页",
        meta: { title: "成绩对比详情页", role: [0, 1,2] },
        hidden: true,
      },
      {
        path: "class_fu_score",
        component: () => import("@/views/wuyu-overview/class_fu_score"),
        name: "班级成员成绩页",
        meta: { title: "班级成员成绩页", role: [0, 1,2] },
      },
      {
        path: "student_wuyu_info",
        component: () => import("@/views/wuyu-overview/student_wuyu_info"),
        name: "学生个人详情页",
        meta: { title: "学生个人详情页", role: [0, 1,2] },
      },
    ],
  },
  //总体运行
  {
    path: "/overview",
    component: Layout,
    redirect: "/overview/index",
    meta: { title: "总体运行", icon: "dashboard", role: [0, 1] },
    children: [
      {
        path: "index",
        component: () => import("@/views/overview/index"),
        name: "总体运行",
        meta: { title: "班级信息", role: [0, 1] },
      },

      {
        path: "detail",
        component: () => import("@/views/overview/detail"),
        name: "detail",
        meta: { title: "学生详情", role: [0, 1] },
      },

      {
        path: "award",
        component: () => import("@/views/overview/award"),
        name: "总体运行",
        meta: { title: "学校获奖信息", role: [0] },
      },
    ],
  },
  //学校总体概述
  {
    path: "/school-overview",
    component: Layout,
    redirect: "/school-overview/index",
    meta: {title: "学校总体概述", icon: "dashboard", role: [0]},
    children: [
      {
        path: "index",
        component: () => import("@/views/school-overview/index"),
        name: "学校公告",
        meta: {title: "学校公告", role: [0]},
      },
      {
          path: "statistical",
          component: () => import("@/views/school-overview/statistical"),
          name: "统计",
          meta: {title: "统计", role: [0]},
        },
    ],
  },
  // 排课管理模块
   {
    path: "/SchedulingManagement",
    component: Layout,
    redirect: "schedulingManagement/index",
    meta: {title: "排课管理", icon: "edit", role: [0, 1]},
    children: [
      {
        path: "index",
        component: () => import("@/views/scheduling-management/index"),
        name: "排课管理",
        meta: {title: "排课管理", role: [0, 1]},
      },

    ],
  },

];



const createRouter = (router) =>
  new Router({
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
  });

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

router.selfaddRoutes = function (params) {
  router.matcher = new Router().matcher;
  router.addRoutes(params);
};

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!getToken();

  if (to.path === '/login') {
    // 如果目标路径是登录页面，直接放行
    next();
  } else if (!isLoggedIn) {
    // 如果未登录且目标路径不是登录页面，重定向到登录页面
    next('/login');
  } else {
    // 已登录，放行
    next();
  }
});

export default router;
