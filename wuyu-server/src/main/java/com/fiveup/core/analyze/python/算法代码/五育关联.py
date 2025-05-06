import csv
import sys
import 数据库连接 as sj_sql
import pandas as pd  # 导入pandas包


def Read_file(file, grade_name):
    re_col = []
    with open(file, "r", encoding="utf-8") as f:
        data = csv.DictReader(f)
        column = [row for row in data]
        if grade_name != 'Sum':
            for c in column:
                if c['grade'] == grade_name:
                    re_col.append(c)
        else:
            re_col = column
    # print(column)
    return re_col


def Wuyu_relation(column, min_sup, cen_name):
    I_all = []
    D1 = {}

    WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']
    for w in WuYu:
        D1[w] = 0
    for c in column:
        if c['sum'] == cen_name:
            I_tmp = []
            for w in WuYu:
                if float(c[w]) >= 80:
                    D1[w] += 1
                    I_tmp.append(w)
            I_all.append(I_tmp)
    # print(I_all)
    for d in list(D1.keys()):
        if D1[d] < min_sup:
            del D1[d]
    # print(D1)

    D_all = [D1]
    n = 0
    while len(list(D_all[n].keys())) != 0:

        D2 = {}
        D_list = list(D_all[n].keys())

        for i in range(0, len(D_list) - 1):
            for j in range(i + 1, len(D_list)):
                t = D_list[i].split(' ') + D_list[j].split(' ')
                t_set = {}.fromkeys(t).keys()
                # print(t_set)
                t_str = " ".join(t_set)
                D2[str(t_str)] = 0

        for d in list(D2.keys()):
            for iall in I_all:
                t_tmp = iall + d.split(' ')
                tmp_set = {}.fromkeys(t_tmp).keys()
                # print(tmp_set)
                if len(iall) == len(tmp_set):
                    D2[d] += 1
        for d in list(D2.keys()):
            if D2[d] < min_sup:
                del D2[d]
        # print(D2)
        D_all.append(D2)
        n += 1

    if list(D_all[len(D_all) - 2].keys()):
        D_end = list(D_all[len(D_all) - 2].keys())[0].split(" ")
    else:
        D_end = []

    return D_end


if __name__ == '__main__':
    # 表现分级
    # python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\算法代码\\五育关联.py 三年级 良好
    # Per = ['优秀', '良好', '一般', '不及格']
    # grades = ['一年级', '二年级', '三年级', '四年级', '五年级', '六年级', 'Sum']
    grade_name = sys.argv[1]
    Per = sys.argv[2]
    # 文件名，最小关联数
    column = Read_file("src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\Apriori算法数据.csv", grade_name)
    # print("re", column)
    result = Wuyu_relation(column, 2, Per)
    print(result)

    conn = sj_sql.Conn()
    cur = conn.cursor()

    # 初始化数据表，清除数据
    del_sql = 'truncate table ana_subject_relation;'
    cur.execute(del_sql)

    # 将数据写入数据库
    if result:
        for r in result:
            into = "INSERT INTO ana_subject_relation(ana_subject_relation.`subject`) VALUES ('" + r + "');"
            cur.execute(into)

    conn.commit()
    conn.close()
