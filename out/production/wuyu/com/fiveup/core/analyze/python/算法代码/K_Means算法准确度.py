import K_Means算法划分学生成绩 as K_Means
import csv
from sklearn.model_selection import train_test_split

with open("../数据集/五育成绩表_预处理完成_赋0_规范化完成_排序_Tag.csv", "r", encoding="utf-8") as f:
    reader = csv.reader(f)
    column_list = [row for row in reader][1:]
    # 五育成绩集
    score_list = []
    # 标签集
    tag_list = []
    for row in column_list:
        # 读五育成绩
        row_int = list(map(float, row[2:7]))

        tag_list.append(row[7])
        score_list.append(row_int)

# print(score_list)
# print(tag_list)

# 划分数据集
x_train, x_test, y_train, y_test = train_test_split(score_list, tag_list, test_size=0.25, random_state=4)
# print(x_test)
# print(y_test)

"""
    K_Means([[de,zhi,ti,mei,lao],[],[]]，簇数)
    输入：[[de,zhi,ti,mei,lao],[],[]]，簇数
    返回：簇列表，质心点
"""
P = ['优秀', '良好', '一般', '不及格']
Cluster_list_sort, Center_list = K_Means.K_Means(x_train, 4)
# print(Center_list)

Result_list = []
for xt in x_test:
    max_l = []  # 存每个向量与M个质心的计算结果
    # c 代表Center_list中的元素，即质心
    for c in Center_list:
        max_l.append(K_Means.Cosine(c, xt))

    i = max_l.index(max(max_l))
    Result_list.append(P[i])  # 将max_l中最大的放入Cluster_list所对应的簇中

T = 0
# print(Result_list)
for y in range(len(y_test)):
    if y_test[y] == Result_list[y]:
        T += 1
Accuracy = T / len(Result_list)
print(Accuracy)
