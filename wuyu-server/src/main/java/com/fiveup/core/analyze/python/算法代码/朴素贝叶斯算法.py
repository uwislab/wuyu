"""
    成绩划分规则:
    if float(c[w]) >= 70:
        c[w] = '优秀'
    elif 64 <= float(c[w]) < 70:
        c[w] = '良好'
    elif 57 <= float(c[w]) < 64:
        c[w] = '一般'
    else:
        c[w] = '不及格'
"""

import csv
import json
import sys
import pathlib

# 表现分级
Per = ['优秀', '良好', '一般', '不及格']
# 五育：de: 德，zhi：智，ti：体，mei：美，lao：劳
WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']

"""
    P(sum) P_sum = {'优秀': 542, '良好': 526, '一般': 610, '不及格': 1090}
"""
P_sum = {}

"""
    P_per = [P_A, P_B, P_C, P_D] P_A为sum是优秀的列表
"""
P_per = []  # P_per = [[[],[],[]],[]]
"""
    P_per_num = [{优秀}, {良好}, {一般}, {不及格}] P_per_num[0]字典里存sum是优秀的德智体美劳 数量, eg: 'de优秀': 538
"""
P_per_num = []


def NaiveBayes_Data(file, grade_name):
    with open(file, "r", encoding="utf-8") as f:
        reader = csv.reader(f)
        # [1:]去csv表头
        column_list = [row for row in reader][1:]
        # 标签集 [de(德), zhi(智), ti(体), mei(美), lao(劳), sum(总体)]
        tag_list = []
        if grade_name == 'Sum':
            for row in column_list:
                # 读五育成绩标签
                tag_list.append(row[4:])
        else:
            for row in column_list:
                if row[3] == grade_name:
                    # 读五育成绩标签
                    tag_list.append(row[4:])

    # print(tag_list)
    list_all = len(tag_list)  # 成绩总数
    # print(list_all)

    for t in tag_list:
        sum_tag = t[5]  # sum
        if sum_tag in P_sum:
            P_sum[sum_tag] += 1
        else:
            P_sum[sum_tag] = 1
    # print(P_sum)

    for p in Per:
        p_list = []
        for t in tag_list:
            if p == t[5]:
                p_list.append(t)
        P_per.append(p_list)

    for P in P_per:  # P = [[],[]]
        p_dict = {}
        for p in P:  # p =[]
            for j in range(len(WuYu)):
                name = WuYu[j] + p[j]
                # print(name)
                if name in p_dict:
                    p_dict[name] += 1
                else:
                    p_dict[name] = 1
        P_per_num.append(p_dict)
    # print(P_per_num)
    return P_sum, P_per_num, list_all


def NaiveBayes_Compute(test, list_all, file_sum, file_per):
    # print(self.list_all)
    # json文件读取+转字典
    test = list(map(float, test.split(", ")))

    with open(file_sum, 'r', encoding='UTF-8') as f:
        P_sum = json.load(f)
        # print(self.P_sum)
    with open(file_per, 'r', encoding='UTF-8') as f:
        P_per_num = json.load(f)
        # print(self.P_per_num)
    test_tag = []
    for i in range(len(test)):
        if test[i] >= 70:
            t = WuYu[i] + '优秀'
        elif 64 <= test[i] < 70:
            t = WuYu[i] + '良好'
        elif 57 <= test[i] < 64:
            t = WuYu[i] + '一般'
        else:
            t = WuYu[i] + '不及格'
        test_tag.append(t)
    # print(test_tag)
    results = []
    for i in range(len(Per)):  # ppn = {}
        psum = P_sum[Per[i]] / list_all
        # print(psum)
        p_2 = 1
        for tt in test_tag:
            if tt in P_per_num[i]:
                p_2 *= P_per_num[i][tt] / P_sum[Per[i]]
            else:
                p_2 *= 1 / (P_sum[Per[i]] + 1)
        # print(p_2)
        p_r = psum * p_2
        results.append(p_r)
    result_tag = Per[results.index(max(results))]

    # print(result_tag)
    return result_tag


def Save_data(P_sum, P_per_num, file_sum, file_per):
    with open(file_sum, 'w', encoding='UTF-8') as f:
        # 设置不转换成ascii  json字符串首缩进
        f.write(json.dumps(P_sum, ensure_ascii=False, indent=2))

    with open(file_per, 'w', encoding='UTF-8') as f:
        # 设置不转换成ascii  json字符串首缩进
        f.write(json.dumps(P_per_num, ensure_ascii=False, indent=2))


if __name__ == '__main__':
    """
        test = []  # 一般 1680
        test = '76.72413793103449, 48.201438848920866, 71.23287671232876, 27.184466019417474, 61.458333333333336'
        test值从cmd命令行上传，初始为src类型
    """
    test = sys.argv[1]
    grade = sys.argv[2]
    file_data = "src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\朴素贝叶斯算法数据.csv"
    file_sum = "src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\朴素贝叶斯_sum.json"
    file_per = "src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\朴素贝叶斯_per_num.json"

    P_sum, P_per_num, list_all = NaiveBayes_Data(file_data, grade)
    Save_data(P_sum, P_per_num, file_sum, file_per)

    tag = NaiveBayes_Compute(test, list_all, file_sum, file_per)
    print(tag)
