<template>
  <div class="container">
    <div class="header">
      <h2>收材料要求</h2>
    </div>
    <div class="content">
      <div class="section">
        <h3>A、考查课材料</h3>
        <ul>
          <li>1、教学手册</li>
          <li>2、原始成绩（课程成绩单）</li>
          <li>3、考查分析表（系统导出）</li>
          <li>4、考查支撑资料</li>
          <li>5、教学大纲</li>
          <li>6、课程过程性评价成绩汇总表</li>
          <li>7、达成度计算表（师范专业及所有21、22级非师范专业）</li>
        </ul>
      </div>
      <div class="section">
        <h3>B、考试课材料</h3>
        <ul>
          <li>1、试卷自查表</li>
          <li>2、教学手册</li>
          <li>3、教学大纲</li>
          <li>4、试卷（机考——打印各个层次学生答卷共5份作为支撑材料，所有电子材料用u盘拷贝各系统一存档）</li>
          <li>5、课程过程性评价成绩汇总表</li>
          <li>6、试卷达成度计算表（师范专业及所有21、22级非师范专业）</li>
        </ul>
      </div>
      <div class="section">
        <h3>C、试卷装订材料清单及顺序</h3>
        <ol>
          <li>学生成绩表（课程成绩单）；</li>
          <li>考场登记表；</li>
          <li>违纪舞弊考场登记表（若无违纪情况，此表不装订）；</li>
          <li>贵州师范学院课程考核命题计划表（A、B卷各1份）；</li>
          <li>试卷分析表；</li>
          <li>试卷样卷（经审定签字的空白A、B卷各1份）；</li>
          <li>答案及评分标准；</li>
          <li>学生考试试卷（含补考卷）</li>
        </ol>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
  }
}
</script>
<style>
.container {
  width: 100%;
  max-width: 640px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.content {
  padding: 20px;
  border: 1px solid #999;
}

.section {
  margin-bottom: 20px;
}

h3 {
  font-weight: bold;
  margin-bottom: 10px;
}

ul, ol {
  padding-left: 20px;
}

li {
  margin-bottom: 5px;
}

</style>