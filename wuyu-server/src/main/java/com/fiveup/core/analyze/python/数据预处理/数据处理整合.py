import csv
import os

import csv数据预处理_平均值法 as data_avg
import csv数据预处理_赋0法 as data_0
import 数据处理_加标签 as data_tag
import 数据规范化 as data_normal
import 朴素贝叶斯数据处理 as deal_bayes
import 数据库数据转csv as csv_data
from shutil import copyfile
import 数据库连接 as sj_sql

ftop = "src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\"
file_start = ftop + '五育成绩表.csv'
csv_data.SQLDATA_CSV(file_start)
print("数据库读取结束！==============")


def KMeans(set_list):
    file_r = ftop + '五育成绩表.csv'
    if set_list[0] == "平均值法":
        file_w_avg = ftop + '五育成绩表_预处理完成.csv'
        data_avg.DATA_Pretreatment().Pretreatment_avg(file_r, file_w_avg)
    elif set_list[0] == "赋0法":
        file_w_0 = ftop + '五育成绩表_预处理完成.csv'
        data_0.DATA_Pretreatment().Pretreatment_0(file_r, file_w_0)

    if set_list[1] == "是":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + 'KMeans算法数据.csv'
        data_normal.Data_Normalization(file_r_normal, file_w_normal, 0, 100)
        print("规范化结束！==============")
    elif set_list[1] == "否":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + 'KMeans算法数据.csv'
        copyfile(file_r_normal, file_w_normal)
        print("规范化结束！==============")

    os.remove(ftop + '五育成绩表_预处理完成.csv')


def Apriori(set_list):
    file_r = ftop + '五育成绩表.csv'
    if set_list[2] == "平均值法":
        file_w_avg = ftop + '五育成绩表_预处理完成.csv'
        data_avg.DATA_Pretreatment().Pretreatment_avg(file_r, file_w_avg)
    elif set_list[2] == "赋0法":
        file_w_0 = ftop + '五育成绩表_预处理完成.csv'
        data_0.DATA_Pretreatment().Pretreatment_0(file_r, file_w_0)

    if set_list[3] == "是":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + '五育成绩表_规范化完成.csv'
        data_normal.Data_Normalization(file_r_normal, file_w_normal, 0, 100)
        print("规范化结束！==============")
    elif set_list[3] == "否":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + '五育成绩表_规范化完成.csv'
        copyfile(file_r_normal, file_w_normal)
        print("规范化结束！==============")

    file_r_n = ftop + '五育成绩表_规范化完成.csv'
    file_wr_n = ftop + '五育成绩表_排序.csv'
    DP = deal_bayes.DATA_Pretreatment()
    DP.Sort_wuyu(file_r_n, file_wr_n)
    print("排序处理结束！==============")

    file_r_tag = file_wr_n
    file_w_tag = ftop + 'Apriori算法数据.csv'
    data_tag.Deal_Tag(file_r_tag, file_w_tag)
    print("加Tag处理结束！==============")

    os.remove(ftop + '五育成绩表_预处理完成.csv')
    os.remove(ftop + '五育成绩表_规范化完成.csv')
    os.remove(ftop + '五育成绩表_排序.csv')


def NaiveBayes(set_list):
    file_r = ftop + '五育成绩表.csv'
    if set_list[4] == "平均值法":
        file_w_avg = ftop + '五育成绩表_预处理完成.csv'
        data_avg.DATA_Pretreatment().Pretreatment_avg(file_r, file_w_avg)
    elif set_list[4] == "赋0法":
        file_w_0 = ftop + '五育成绩表_预处理完成.csv'
        data_0.DATA_Pretreatment().Pretreatment_0(file_r, file_w_0)

    if set_list[5] == "是":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + '五育成绩表_规范化完成.csv'
        data_normal.Data_Normalization(file_r_normal, file_w_normal, 0, 100)
        print("规范化结束！==============")
    elif set_list[5] == "否":
        file_r_normal = ftop + '五育成绩表_预处理完成.csv'
        file_w_normal = ftop + '五育成绩表_规范化完成.csv'
        copyfile(file_r_normal, file_w_normal)
        print("规范化结束！==============")

    file_r_n = ftop + '五育成绩表_规范化完成.csv'
    file_wr_n = ftop + '五育成绩表_排序.csv'
    file_w_n = ftop + '朴素贝叶斯算法数据.csv'
    DP = deal_bayes.DATA_Pretreatment()
    DP.Sort_wuyu(file_r_n, file_wr_n)
    DP.Deal_NaiveBayes(file_wr_n, file_w_n)

    os.remove(ftop + '五育成绩表_预处理完成.csv')
    os.remove(ftop + '五育成绩表_规范化完成.csv')
    os.remove(ftop + '五育成绩表_排序.csv')


def Read_SQL():
    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    sel_sql = 'SELECT ana_updata_set.Fill_kmeans, ana_updata_set.Normal_kmeans, ana_updata_set.Fill_apriori, ' \
              'ana_updata_set.Normal_apriori, ana_updata_set.Fill_bayes, ana_updata_set.Normal_bayes FROM ' \
              'ana_updata_set '
    cur.execute(sel_sql)
    results = list(cur.fetchall())
    conn.close()
    return results[0]


if __name__ == '__main__':
    # Kmeans \ Aporiri \ 朴素贝叶斯
    set_list = Read_SQL()
    KMeans(set_list)
    Apriori(set_list)
    NaiveBayes(set_list)
