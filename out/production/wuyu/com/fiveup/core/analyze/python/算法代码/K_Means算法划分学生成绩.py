import random
import numpy as np
import csv
import sys
import 数据库连接 as sj_sql
import pandas as pd  # 导入pandas包
import matplotlib.pyplot as plt
from pyecharts import options as opts
from pyecharts.charts import Scatter
from pyecharts.commons.utils import JsCode

WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']
grades = ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级']


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


def Read_file(file):
    with open(file, "r", encoding="utf-8") as f:
        reader = csv.reader(f)
        column_list = [row for row in reader][1:]
        # print(column_list)

        score_grade = {}
        for i in range(len(grades)):
            score_grade[grades[i]] = []

        score_list_sum = []  # k-means算法所需向量
        for row in column_list:
            row_int = list(map(float, row[4:]))
            # print(row_int)
            score_list_sum.append(row_int)
            score_grade[row[3]].append(row_int)
    # print(score_list)
    return score_grade, score_list_sum


# K-Means算法
# score_list成绩列表、M簇类、Distance距离算法
def K_Means(score_list, M, Distance, p_M):
    Center_list = []  # 质心点列表
    Cluster_list = []  # 簇列表, Cluster_list[[[],[]],[],[]]
    # 初始化质心点
    for i in range(M):
        r = random.randint(0, len(score_list) - 1)
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
            sum_np = [0, 0, 0, 0, 0]
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
            Cluster_list[min_l.index(min(min_l))].append(sl)  # 将max_l中最大的放入Cluster_list所对应的簇中

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
    x_list = []  # ti
    y_list = []  # zhi
    for clu in Cluster_list:
        i_np = np.array(clu)
        c_x = i_np[:, 2].tolist()  # i_np[:, 2] == ti
        c_y = i_np[:, 0].tolist()  # i_np[:, 0] == de
        x_list.append(c_x)
        y_list.append(c_y)

    c = (
        Scatter()
            .add_xaxis(x_list[0])
            .add_yaxis(
            'A',
            y_list[0],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .add_xaxis(x_list[1])
            .add_yaxis(
            'B',
            y_list[1],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .add_xaxis(x_list[2])
            .add_yaxis(
            'C',
            y_list[2],
            label_opts=opts.LabelOpts(is_show=False)
        )
            .set_global_opts(
            title_opts=opts.TitleOpts(title='散点图'),
            xaxis_opts=opts.AxisOpts(name='ti', type_='value', min_=0),  # x轴从20开始，原点不为0
            yaxis_opts=opts.AxisOpts(name='zhi', type_='value', min_=0),  # y轴起始点的值
            legend_opts=opts.LegendOpts(is_show=True)
        )
    ).render("weichatscatter.html")  # 散点图网页


"""

K-Means不及格的定义，按总体不及格成绩的平均值为各科成绩不及格成绩
"""


def Failure_rate(Cluster_list, score_list):
    # print(len(score_list))
    fai_score = np.average(Cluster_list[3])
    # print(fai_score)
    stu_sum = 0
    for cl in Cluster_list:
        stu_sum += len(cl)
    # print(stu_sum)
    fai_sum = (len(Cluster_list[3]) / stu_sum) * 100

    fai_subject_dict = {}

    for sl in score_list:
        for wy in WuYu:
            if sl[WuYu.index(wy)] <= fai_score:
                if wy not in fai_subject_dict:
                    fai_subject_dict[wy] = 1
                else:
                    fai_subject_dict[wy] += 1
            else:
                if wy not in fai_subject_dict:
                    fai_subject_dict[wy] = 0
                else:
                    fai_subject_dict[wy] += 0

    fai_sub_rate = []
    for wy in WuYu:
        fai_sub_rate.append((fai_subject_dict[wy] / stu_sum) * 100)

    fai_sub_rate.append(fai_sum)
    print(fai_sum)
    print(fai_sub_rate)
    return fai_sub_rate


# 保存簇的最大最小值到数据库
def Save_SQL_Center(Center_dict):
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_center_data;'
    cur.execute(del_sql)

    # 将数据写入数据库，保留2位小数
    if Center_dict:
        for c_key in Center_dict:
            if Center_dict[c_key]:
                c_id = 1
                for c in Center_dict[c_key]:
                    into = "INSERT INTO ana_center_data(ana_center_data.grade, ana_center_data.Cluster, " \
                           "ana_center_data.De, ana_center_data.Zhi, ana_center_data.Ti, ana_center_data.Mei, " \
                           "ana_center_data.Lao) VALUES ('%s', '%0.2f','%0.2f','%0.2f','%0.2f','%0.2f','%0.2f'); " \
                           % (c_key, c_id, c[0], c[1], c[2], c[3], c[4])
                    cur.execute(into)
                    c_id += 1
    conn.commit()
    conn.close()


def Save_SQL_Number(Cluster_num):
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_cluster_number;'
    cur.execute(del_sql)

    # 将数据写入数据库，保留2位小数
    if Cluster_num:
        for c_key in Cluster_num:
            if Cluster_num[c_key]:
                c_id = 1
                for c in Cluster_num[c_key]:
                    into = "INSERT INTO ana_cluster_number(ana_cluster_number.grade, ana_cluster_number.Cluster, " \
                           "ana_cluster_number.Number) VALUES ('%s', '%d','%d'); " % (c_key, c_id, c)
                    cur.execute(into)
                    c_id += 1
    conn.commit()
    conn.close()


def Save_Failure(fai_rate):
    print(WuYu)
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_wuyu_flunk;'
    cur.execute(del_sql)

    # 将数据写入数据库，保留2位小数
    if fai_rate:
        for f_key in fai_rate:
            if fai_rate[f_key]:
                into = "INSERT INTO ana_wuyu_flunk(ana_wuyu_flunk.grade, ana_wuyu_flunk.de_rate, " \
                       "ana_wuyu_flunk.zhi_rate, ana_wuyu_flunk.ti_rate, ana_wuyu_flunk.mei_rate, " \
                       "ana_wuyu_flunk.lao_rate) VALUES ('%s','%0.2f', '%0.2f', '%0.2f', '%0.2f', '%0.2f'); " \
                       % (f_key, fai_rate[f_key][0], fai_rate[f_key][1], fai_rate[f_key][2], fai_rate[f_key][3], fai_rate[f_key][4])
                cur.execute(into)

    conn.commit()
    conn.close()


if __name__ == '__main__':
    m = 4  # 聚类数
    Distance = sys.argv[1]
    # 闵可夫斯基距离 p，在夹角余弦相似度时为0
    p_M = int(sys.argv[2])
    score_grade, score_list_sum = Read_file("src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\KMeans算法数据.csv")
    Center_dict = {}
    Cluster_num = {}
    fai_rate = {}
    for ky in score_grade:
        Clunum_list = []
        Cluster_list, Center_list = K_Means(score_grade[ky], m, Distance, p_M)
        fai_sub_rate = Failure_rate(Cluster_list, score_grade[ky])

        print(ky, Center_list)
        Center_dict[ky] = Center_list

        for clu in Cluster_list:
            Clunum_list.append(len(clu))
        Cluster_num[ky] = Clunum_list

        fai_rate[ky] = fai_sub_rate

    Save_SQL_Center(Center_dict)
    Save_SQL_Number(Cluster_num)
    Save_Failure(fai_rate)
