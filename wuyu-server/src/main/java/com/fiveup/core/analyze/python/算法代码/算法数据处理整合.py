import K_Means算法划分学生成绩 as km
import sys
import 五育关联 as rc
import 朴素贝叶斯算法 as nb
import 数据库连接 as sj_sql


def Sum_Algorithm():
    ftop = 'src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\'
    # K-Means
    m = 4  # 聚类数
    Distance = sys.argv[1]
    # 闵可夫斯基距离 p，在夹角余弦相似度时为0
    p_M = int(sys.argv[2])
    score_grade, score_list_sum = km.Read_file(ftop + "KMeans算法数据.csv")
    score_grade["Sum"] = score_list_sum
    # print(len(score_grade["Sum"]))

    Center_dict = {}
    Cluster_num = {}
    fai_rate = {}
    for ky in score_grade:
        Clunum_list = []
        Cluster_list, Center_list = km.K_Means(score_grade[ky], m, Distance, p_M)
        fai_sub_rate = km.Failure_rate(Cluster_list, score_grade[ky])

        print(ky, Center_list)
        Center_dict[ky] = Center_list

        for clu in Cluster_list:
            Clunum_list.append(len(clu))
        Cluster_num[ky] = Clunum_list

        fai_rate[ky] = fai_sub_rate

    km.Save_SQL_Center(Center_dict)
    km.Save_SQL_Number(Cluster_num)
    km.Save_Failure(fai_rate)


if __name__ == '__main__':
    Sum_Algorithm()
    print("完成！")
