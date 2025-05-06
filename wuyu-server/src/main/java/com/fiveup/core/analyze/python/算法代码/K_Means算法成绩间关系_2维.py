import random
import numpy as np
import csv
import sys
import json
import 数据库连接 as sj_sql
import pandas as pd  # 导入pandas包
import matplotlib.pyplot as plt
from pyecharts import options as opts
from pyecharts.charts import Scatter
from pyecharts.commons.utils import JsCode

WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']


# 闵可夫斯基距离
def Minkowski(a, b, p):
    a_np = np.array(a)
    b_np = np.array(b)
    if sum(a_np - b_np) != 0:
        D = (sum(abs(a_np - b_np) ** p)) ** (1 / p)
    else:
        D = 0
    return D


# 夹角余弦相似度
def Cosine(a, b):
    a_np = np.array(a)
    b_np = np.array(b)
    c = sum(a_np * b_np)
    d = (sum(a_np ** 2) ** 0.5) * (sum(b_np ** 2) ** 0.5)
    if d != 0:
        return c / d
    else:
        return 0


def Read_file(file, X, Y, grade_name):
    with open(file, "r", encoding="utf-8") as f:
        reader = csv.reader(f)
        column_list = [row for row in reader][1:]
        score_list = []  # k-means算法所需向量
        if grade_name == 'Sum':
            for row in column_list:
                row_int = list(map(float, row[4:]))
                row_data = [row_int[WuYu.index(X)], row_int[WuYu.index(Y)]]
                # print(row_int)
                score_list.append(row_data)
        else:
            for row in column_list:
                if row[3] == grade_name:
                    row_int = list(map(float, row[4:]))
                    row_data = [row_int[WuYu.index(X)], row_int[WuYu.index(Y)]]
                    # print(row_int)
                    score_list.append(row_data)
    # print(score_list)
    return score_list


# K-Means算法
def K_Means(score_list, M, Distance, p_M):
    Center_list = []  # 质心点列表
    Cluster_list = []  # 簇列表, Cluster_list[[[],[]],[],[]]
    # 初始化质心点
    for i in range(M):
        r = random.randint(0, len(score_list)-1)
        Center_list.append(score_list[r])
        Cluster_list.append([score_list[r]])
    # print(Center_list)
    # print(Cluster_list)
    flag = 1
    while flag == 1:
        """    
            sl 代表score_list中的元素
            eg: sl = [80.18996723357778, 56.23785741726579, 71.88350809236167, 40.46393210749646, 53.20430871212122]
        """
        for sl in score_list:
            max_l = []  # 存每个向量与M个质心的计算结果
            # c 代表Center_list中的元素，即质心
            for c in Center_list:
                max_l.append(Cosine(c, sl))
            Cluster_list[max_l.index(max(max_l))].append(sl)  # 将max_l中最大的放入Cluster_list所对应的簇中
        # print(Cluster_list[0])

        # 保存老质心
        Center_list_Old = Center_list

        # 重置质心点列表
        Center_list = []
        for cu in Cluster_list:
            sum_np = [0, 0]
            for i_c in range(1, len(cu)):
                i_np = np.array(cu[i_c])
                sum_np += i_np
                # print(sum_np)
            avg_l = []
            for j in sum_np:
                if (len(cu) - 1) != 0:
                    avg_l.append(j / (len(cu) - 1))
                else:
                    avg_l.append(0)
            Center_list.append(avg_l)
        Center_list_new = Center_list

        # 重置簇列表
        Cluster_list = []
        for m in range(M):
            Cluster_list.append([Center_list[m]])

        # 计算质心偏差
        old_np = np.array(Center_list_Old)
        new_np = np.array(Center_list_new)
        if np.all(abs(old_np - new_np) <= 0.000001):
            flag = 0

    # print(Center_list)
    if Distance == "夹角余弦相似度":
        for sl in score_list:
            max_l = []  # 存每个向量与M个质心的计算结果
            # c 代表Center_list中的元素，即质心
            for c in Center_list:
                max_l.append(Cosine(c, sl))
            Cluster_list[max_l.index(max(max_l))].append(sl)  # 将max_l中最大的放入Cluster_list所对应的簇中

    if Distance == "闵可夫斯基距离":
        for sl in score_list:
            min_l = []  # 存每个向量与M个质心的计算结果
            # c 代表Center_list中的元素，即质心
            for c in Center_list:
                min_l.append(Minkowski(c, sl, p_M))
            Cluster_list[min_l.index(min(min_l))].append(sl)  # 将min_l中最小的放入Cluster_list所对应的簇中

    # 按平均值从高到底排簇
    Cluster_list_sort = []
    Center_list_sort = []
    avg_cl_list = []
    for i in range(M):
        avg_cl_list.append(np.average(Cluster_list[i]))
    sort_avg = sorted(enumerate(avg_cl_list), key=lambda x: x[1], reverse=True)
    for i in range(M):
        Cluster_list_sort.append(Cluster_list[sort_avg[i][0]])
        Center_list_sort.append(Center_list[sort_avg[i][0]])
    # print(Cluster_list_sort)
    # for i in range(M):
    #     print(np.average(Center_list_sort[i]))

    # print(len(Cluster_list_sort[0]), len(Cluster_list_sort[1]), len(Cluster_list_sort[2]), len(Cluster_list_sort[3]))
    # print(np.average(Cluster_list_sort[0]), np.average(Cluster_list_sort[1]), np.average(Cluster_list_sort[2]))
    return Cluster_list_sort, Center_list_sort


# 数据可视化
def Data_Visualization(Cluster_list):
    x_list = []  # de
    y_list = []  # zhi
    for clu in Cluster_list:
        i_np = np.array(clu)
        c_x = i_np[:, 0].tolist()  # i_np[:, 2] == X
        c_y = i_np[:, 1].tolist()  # i_np[:, 0] == Y
        x_list.append(c_x)
        y_list.append(c_y)

    c = (
        Scatter()
            .add_xaxis(x_list[0])
            .add_yaxis(
            '优秀',
            y_list[0],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .add_xaxis(x_list[1])
            .add_yaxis(
            '良好',
            y_list[1],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .add_xaxis(x_list[2])
            .add_yaxis(
            '一般',
            y_list[2],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .add_xaxis(x_list[3])
            .add_yaxis(
            '不及格',
            y_list[3],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .set_global_opts(
            title_opts=opts.TitleOpts(title='散点图'),
            xaxis_opts=opts.AxisOpts(name='ti', type_='value', min_=0),  # x轴从20开始，原点不为0
            yaxis_opts=opts.AxisOpts(name='zhi', type_='value', min_=0),  # y轴起始点的值
            legend_opts=opts.LegendOpts(is_show=True)
        )
    ).render("src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\weichatscatter.html")  # 散点图网页


def Save_data(file, data):
    with open(file, 'w', encoding='UTF-8') as f:
        # 设置不转换成ascii  json字符串首缩进
        f.write(json.dumps(data, ensure_ascii=False, indent=2))


def Save_SQL(Cluster_list):
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_kmeanspoint;'
    cur.execute(del_sql)

    # 将数据写入数据库
    if Cluster_list:
        c_id = 1
        for cl in Cluster_list:
            for c in cl:
                into = "INSERT INTO ana_kmeanspoint(ana_kmeanspoint.cluster, ana_kmeanspoint.score_x, " \
                       "ana_kmeanspoint.score_y) " \
                       "VALUES ('%0.2f','%0.2f','%0.2f'); " % (
                           c_id, c[0], c[1])
                cur.execute(into)
            c_id += 1
    conn.commit()
    conn.close()


def Save_SQL_Number(Cluster_list):
    print(len(Cluster_list[0]), len(Cluster_list[1]), len(Cluster_list[2]), len(Cluster_list[3]))
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_kmeanspoint_clunumber;'
    cur.execute(del_sql)

    # 将数据写入数据库，保留2位小数
    if Cluster_list:
        c_id = 1
        for c in Cluster_list:
            into = "INSERT INTO ana_kmeanspoint_clunumber(ana_kmeanspoint_clunumber.Cluster, ana_kmeanspoint_clunumber.Number) " \
                   "VALUES ('%d','%d'); " % (c_id, len(c))
            cur.execute(into)
            c_id += 1
    conn.commit()
    conn.close()


def Sava_Cluster_JSON(Cluster_list, file_name):
    Cluster_dict = {}
    c_id = ['优秀', '良好', '一般', '不及格']
    i = 0
    for cl in Cluster_list:
        Cluster_dict[c_id[i]] = cl
        i += 1
    # print(len(Cluster_dict))
    with open(file_name, 'w', encoding='UTF-8') as f:
        # 设置不转换成ascii  json字符串首缩进
        f.write(json.dumps(Cluster_dict, ensure_ascii=False, indent=2))


if __name__ == '__main__':
    m = 4  # 聚类数
    file_name = 'src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\kmeans_2维.json'
    X = sys.argv[1]
    Y = sys.argv[2]
    grade_name = sys.argv[3]
    Distance = sys.argv[4]
    # 闵可夫斯基距离 p，在夹角余弦相似度时为0
    p_M = int(sys.argv[5])
    print(X, Y)
    score_list = Read_file("src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\KMeans算法数据.csv", X, Y,
                           grade_name)
    print(len(score_list))
    Cluster_list, Center_list = K_Means(score_list, m, Distance, p_M)

    # Data_Visualization(Cluster_list)
    # print(Cluster_list)
    # Save_data(file_name, Cluster_list)
    # Save_SQL(Cluster_list)
    Sava_Cluster_JSON(Cluster_list, file_name)
    Save_SQL_Number(Cluster_list)
