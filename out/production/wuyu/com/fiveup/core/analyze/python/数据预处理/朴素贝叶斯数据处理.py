"""
前20%为优秀，40%为良好，70%为一般，30%为不及格
五育成绩表_预处理完成_赋0_规范化完成.csv

求总分
按总分排序
"""
import csv

import numpy as np


class DATA_Pretreatment:
    def __init__(self):
        # 五育：de: 德，zhi：智，ti：体，mei：美，lao：劳
        self.WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']

    def Sort_wuyu(self, filename_r, filename_w):
        with open(filename_r, "r", encoding="utf-8") as f:
            data = csv.DictReader(f)
            column = [row for row in data]

        flag = 0
        while flag == 0:
            for c in column:
                if c['id'] == '' or c['name'] == '':
                    column.remove(c)
                    flag = 0
                else:
                    flag = 1

        column_sort_tmp = []
        column_sort_end = []
        for c in column:
            sum_score = 0
            for w in self.WuYu:
                if c[w] != '':
                    sum_score += float(c[w])
                else:
                    sum_score += 0
            c['sum'] = sum_score
            column_sort_tmp.append(list(c.items()))
        column_sort = sorted(column_sort_tmp, key=(lambda x: x[9]), reverse=True)
        # print(column_sort)
        for cs in column_sort:
            column_sort_end.append(dict(cs))

        with open(filename_w, 'w', encoding='UTF-8', newline='') as f:
            headers = list(column_sort_end[0].keys())
            writer = csv.DictWriter(f, headers)
            writer.writeheader()
            for row in column_sort_end:
                writer.writerow(row)

    def Deal_NaiveBayes(self, filename_r, filename_w):
        with open(filename_r, "r", encoding="utf-8") as f:
            data = csv.DictReader(f)
            column = [row for row in data]

        # print(column)
        for c in column:
            if float(c['sum']) >= 350:
                c['sum'] = '优秀'
            elif 320 <= float(c['sum']) < 350:
                c['sum'] = '良好'
            elif 285 <= float(c['sum']) < 320:
                c['sum'] = '一般'
            else:
                c['sum'] = '不及格'

            for w in self.WuYu:
                if float(c[w]) >= 70:
                    c[w] = '优秀'
                elif 64 <= float(c[w]) < 70:
                    c[w] = '良好'
                elif 57 <= float(c[w]) < 64:
                    c[w] = '一般'
                else:
                    c[w] = '不及格'

        # print(column)

        with open(filename_w, 'w', encoding='UTF-8', newline='') as f:
            headers = list(column[0].keys())
            writer = csv.DictWriter(f, headers)
            writer.writeheader()
            for row in column:
                writer.writerow(row)


if __name__ == '__main__':
    print("开始了！==============")
    file_r = 'src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\五育成绩表_预处理完成_赋0_规范化完成.csv'
    file_wr = '..' + file_r.strip('.csv') + '_排序.csv'
    file_w = '..' + file_r.strip('.csv') + '_贝叶斯.csv'
    DP = DATA_Pretreatment()
    DP.Sort_wuyu(file_r, file_wr)
    DP.Deal_NaiveBayes(file_wr, file_w)
    print("排序处理结束！==============")
