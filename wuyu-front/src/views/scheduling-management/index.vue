<template>
  <div class="course-scheduling-container">
    <!-- 顶部工具栏 -->
    <div class="toolbar-section">
      <div class="filter-group">
        <el-select
          v-model="filter.grade"
          placeholder="年级"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        >
          <el-option v-for="grade in grades" :key="grade" :label="grade" :value="grade" />
        </el-select>
        <el-select
          v-model="filter.class"
          placeholder="班级"
          clearable
          class="filter-item"
          @change="handleFilterChange"
        >
          <el-option v-for="cls in classes" :key="cls" :label="cls" :value="cls" />
        </el-select>
        <el-input
          v-model="filter.courseName"
          placeholder="课程名称"
          clearable
          class="filter-item"
          suffix-icon="el-icon-search"
          @clear="handleFilterChange"
        />
      </div>

      <div class="operation-group">
        <el-button type="primary" icon="el-icon-plus" @click="handleAddCourse">新增</el-button>
        <el-button type="danger" icon="el-icon-delete" disabled>删除</el-button>
        <el-dropdown trigger="click" class="more-actions">
          <el-button type="info">
            更多操作<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="downloadTemplate">
                <i class="el-icon-download"></i>下载模板
              </el-dropdown-item>
              <el-dropdown-item @click="handleExport">
                <i class="el-icon-upload2"></i>导出数据
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 主体内容 -->
    <div class="main-content">
      <!-- 左侧课程树 -->
      <el-card class="course-tree" shadow="never">
        <template #header>
          <div class="card-header">
            <span>课程排课列表</span>
            <div class="card-actions">
              <el-switch
                v-model="autoCopyEnabled"
                active-text="自动复制"
                inactive-text="关闭"
                active-color="#409EFF"
                @change="handleAutoCopySwitch"
              />
              <el-popconfirm
                title="确认复制上学期排课？"
                confirm-button-text="确认"
                cancel-button-text="取消"
                @confirm="handleAutoCopy"
              >
                <template #reference>
                  <el-button type="text" icon="el-icon-copy-document" circle></el-button>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </template>

        <el-tree
          :data="filteredCourseTree"
          :props="treeProps"
          node-key="id"
          :expand-on-click-node="false"
          default-expand-all
          class="custom-tree"
        >
          <template #default="{ node, data }">
            <div class="tree-node">
              <div class="node-info">
                <span class="node-label">{{ node.label }}</span>
                <span v-if="data.type === 'course'" class="course-meta">
                  <el-tag size="mini">{{ data.period }}课时</el-tag>
                  <el-tag size="mini" type="success">{{ data.credit }}学分</el-tag>
                </span>
              </div>
              <div v-if="data.type === 'course'" class="node-actions">
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-user"
                  @click.stop="openTeacherDialog(data)"
                >
                  {{ data.teacher || '分配教师' }}
                </el-button>
                <el-dropdown trigger="click" @command="handleTreeCommand">
                  <el-button type="text" icon="el-icon-more" circle size="small"></el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item command="edit">编辑课程</el-dropdown-item>
                      <el-dropdown-item command="delete">删除课程</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </div>
          </template>
        </el-tree>
      </el-card>

      <!-- 右侧表格 -->
      <el-card class="summary-table" shadow="never">
        <template #header>
          <div class="card-header">
            <span>课程安排总览</span>
            <el-upload
              action="#"
              :on-change="handleUpload"
              :show-file-list="false"
              accept=".xlsx,.xls"
            >
              <el-button type="primary" icon="el-icon-upload" size="small">导入课表</el-button>
            </el-upload>
          </div>
        </template>

        <el-table
          :data="summaryTableData"
          v-loading="tableLoading"
          stripe
          highlight-current-row
          style="width: 100%"
        >
          <el-table-column prop="grade" label="年级" width="120" align="center" />
          <el-table-column prop="class" label="班级" width="120" align="center" />
          <el-table-column prop="course" label="课程名称" min-width="180" />
          <el-table-column label="任课教师" width="200">
            <template #default="{ row }">
              <div class="teacher-cell">
                <el-avatar :size="28" :src="getTeacherAvatar(row.teacherId)" />
                <div class="teacher-info">
                  <span class="teacher-name">{{ row.teacher || '未分配' }}</span>
                  <el-button
                    type="text"
                    size="mini"
                    icon="el-icon-edit"
                    @click="openTeacherDialog(row)"
                  ></el-button>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="period" label="课时" width="100" align="center" />
          <el-table-column prop="credit" label="学分" width="100" align="center" />
        </el-table>

        <div class="pagination-wrapper">
          <el-pagination
            :current-page="pagination.current"
            :page-size="pagination.size"
            :total="pagination.total"
            layout="total, prev, pager, next, jumper"
            background
          />
        </div>
      </el-card>
    </div>

    <!-- 教师选择对话框 -->
    <el-dialog
      title="选择任课教师"
      :visible.sync="teacherDialogVisible"
      width="60%"
      :before-close="handleDialogClose">
      <el-input
        v-model="teacherSearch"
        placeholder="输入教师姓名/拼音搜索"
        prefix-icon="el-icon-search"
        class="mb-4" />
      <el-table
        :data="filteredTeachers"
        stripe
        size="small"
        @row-click="selectTeacher">
        <el-table-column prop="name" label="教师姓名" width="120" />
        <el-table-column prop="department" label="所属院系" width="200" />
        <el-table-column prop="title" label="职称" width="100" />
        <el-table-column prop="pinyin" label="拼音" width="160" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="selectTeacher(row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="teacherPagination.current"
        :page-size="teacherPagination.size"
        :total="teachers.length"
        layout="prev, pager, next"
        class="mt-4" />
    </el-dialog>

    <!-- 导入结果提示 -->
    <el-alert
      v-if="importResult"
      :title="importResult.message"
      :type="importResult.type"
      :closable="false"
      show-icon
      class="import-result" />
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue';
import { Message,Loading  } from 'element-ui';

// 模拟数据
const grades = ['一年级', '二年级', '三年级', '四年级'];
const classes = ['1班', '2班', '3班', '4班'];
const teachers = ref([
  { id: 1, name: '张建国', pinyin: 'zhangjianguo', department: '计算机学院', title: '教授', avatar: 'https://picsum.photos/30/30?1' },
  { id: 2, name: '李淑芬', pinyin: 'lishufen', department: '外语学院', title: '副教授', avatar: 'https://picsum.photos/30/30?2' },
  { id: 3, name: '王海涛', pinyin: 'wanghaitao', department: '数学系', title: '讲师', avatar: 'https://picsum.photos/30/30?3' },
  { id: 4, name: '赵晓燕', pinyin: 'zhaoxiaoyan', department: '物理学院', title: '助教', avatar: 'https://picsum.photos/30/30?4' },
]);

// 课程树数据
const courseTree = ref([
  { id: 1, type: 'grade', label: '一年级', children: [
    { id: 11, type: 'class', label: '1班', children: [
      { id: 111, type: 'course', label: '高等数学', teacherId: 3, teacher: '王海涛', period: 64, credit: 4 },
      { id: 112, type: 'course', label: '大学英语', teacherId: 2, teacher: '李淑芬', period: 48, credit: 3 },
    ]},
    { id: 12, type: 'class', label: '2班', children: [
      { id: 121, type: 'course', label: '大学物理', teacherId: 4, teacher: '赵晓燕', period: 64, credit: 4 },
      { id: 122, type: 'course', label: '程序设计', teacherId: 1, teacher: '张建国', period: 80, credit: 5 },
    ]}
  ]},
  { id: 2, type: 'grade', label: '二年级', children: [
    { id: 21, type: 'class', label: '1班', children: [
      { id: 211, type: 'course', label: '数据结构', teacherId: 1, teacher: '张建国', period: 64, credit: 4 },
      { id: 212, type: 'course', label: '操作系统', teacherId: 1, teacher: '张建国', period: 64, credit: 4 },
    ]}
  ]}
]);

const filter = reactive({ grade: '', class: '', courseName: '' });
const autoCopyEnabled = ref(true);
const teacherDialogVisible = ref(false);
const teacherSearch = ref('');
const currentCourse = ref(null);
const importResult = ref(null);
const pagination = reactive({ current: 1, size: 10, total: 20 });
const teacherPagination = reactive({ current: 1, size: 10 });

const filteredCourseTree = computed(() => {
  if (!filter.grade && !filter.class && !filter.courseName) return courseTree.value;

  return courseTree.value.map(gradeNode => {
    if (filter.grade && gradeNode.label !== filter.grade) return null;
    return {
      ...gradeNode,
      children: gradeNode.children.map(classNode => {
        if (filter.class && classNode.label !== filter.class) return null;
        return {
          ...classNode,
          children: classNode.children.filter(course =>
            course.label.includes(filter.courseName)
          )
        };
      }).filter(Boolean)
    };
  }).filter(Boolean);
});

const filteredTeachers = computed(() => {
  const key = teacherSearch.value.toLowerCase();
  return teachers.value.filter(t =>
    t.name.includes(key) ||
    t.pinyin.includes(key) ||
    t.department.includes(key)
  );
});

const summaryTableData = computed(() => {
  const data = [];
  courseTree.value.forEach(grade => {
    grade.children.forEach(cls => {
      cls.children.forEach(course => {
        data.push({
          grade: grade.label,
          class: cls.label,
          course: course.label,
          teacher: course.teacher,
          teacherId: course.teacherId,
          period: course.period,
          credit: course.credit
        });
      });
    });
  });
  return data.slice((pagination.current - 1) * pagination.size, pagination.current * pagination.size);
});

const treeProps = ref({
  children: 'children',
  label: 'label'
});

const handleFilterChange = () => {
  pagination.current = 1;
};

const openTeacherDialog = (course) => {
  currentCourse.value = course;
  teacherDialogVisible.value = true;
  teacherSearch.value = '';
};

const selectTeacher = (teacher) => {
  if (!currentCourse.value) return;

  // 更新课程树中的教师信息
  const updateNode = (nodes) => {
    nodes.forEach(node => {
      if (node.id === currentCourse.value.id && node.type === 'course') {
        node.teacher = teacher.name;
        node.teacherId = teacher.id;
      }
      if (node.children) updateNode(node.children);
    });
  };
  updateNode(courseTree.value);

  teacherDialogVisible.value = false;
  Message.success(`已为课程${currentCourse.value.label}设置教师：${teacher.name}`);
};
// 自动fuzhi
const handleAutoCopy = () => {
  Loading.service({ text: '复制中...' });
  setTimeout(() => {
    Message.success('上学期排课已复制到本学期');
    Loading.service().close();
  }, 1000);
};

const handleUpload = (file) => {
  // 模拟文件上传
  Loading.service({ text: '导入中...' });
  setTimeout(() => {
    Loading.service().close();
    importResult.value = {
      type: 'success',
      message: '成功导入15条记录，失败2条'
    };
    console.log('导入错误：第3行班级不存在；第7行教师未注册');
  }, 1500);
};

const handleExport = () => {
  Loading.service({ text: '导出中...' });
  setTimeout(() => {
    Loading.service().close();
    Message.success('数据已成功导出为Excel');
  }, 1000);
};

const getTeacherAvatar = (id) => {
  return teachers.value.find(t => t.id === id)?.avatar || 'https://picsum.photos/30/30';
};

watch(teacherDialogVisible, (val) => {
  if (!val) currentCourse.value = null;
});
</script>

<style lang="scss" scoped>
.course-scheduling-container {
  padding: 24px;
  background: #f5f7fa;
  min-height: 100vh;
}

.toolbar-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 16px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);

  .filter-group {
    display: flex;
    gap: 12px;

    .filter-item {
      width: 160px;
    }
  }

  .operation-group {
    display: flex;
    gap: 12px;
  }
}

.main-content {
  display: grid;
  grid-template-columns: 320px 1fr;
  gap: 20px;
}

.course-tree {
  :deep(.el-card__header) {
    padding: 12px 20px;
    background: #f8fafc;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
    color: #303133;
  }

  .custom-tree {
    :deep(.el-tree-node__content) {
      height: 40px;
    }
  }
}

.tree-node {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding-right: 8px;

  .node-info {
    display: flex;
    flex-direction: column;

    .node-label {
      font-size: 14px;
      color: #303133;
    }

    .course-meta {
      margin-top: 4px;

      .el-tag {
        margin-right: 6px;
        height: 22px;
        line-height: 20px;
      }
    }
  }

  .node-actions {
    display: flex;
    align-items: center;
    gap: 8px;
  }
}

.summary-table {
  :deep(.el-card__header) {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 20px;
    background: #f8fafc;
  }

  .teacher-cell {
    display: flex;
    align-items: center;
    gap: 12px;

    .teacher-info {
      display: flex;
      align-items: center;
      gap: 8px;

      .teacher-name {
        max-width: 120px;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
