"""
输入：csv文件
输出：csv文件 (格式为 xxx._预处理完.csv)
功能：
    1. 格式规范化
    2. 用平均值填补空值
"""
import csv
import numpy as np


class DATA_Pretreatment:
    def __init__(self):
        # 五育：de: 德，zhi：智，ti：体，mei：美，lao：劳
        self.WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']

    def Pretreatment_avg(self, filename_r, filename_w):
        with open(filename_r, "r", encoding="utf-8") as f:
            data = csv.DictReader(f)
            column = [row for row in data]
            # print(column)

            flag = 0
            while flag == 0:
                for c in column:
                    if c['stu_num'] == '' or c['name'] == '':
                        column.remove(c)
                        flag = 0
                    else:
                        flag = 1

        for c in column:
            for w in self.WuYu:
                if c[w] == '':
                    with open(filename_r, "r", encoding="utf-8") as f:
                        data = csv.DictReader(f)
                        score_w = [row[w] for row in data]
                    while '' in score_w:
                        score_w.remove('')
                    # print(score_w)
                    c[w] = str(np.mean([float(n) for n in score_w]))

        # print(column)

        with open(filename_w, 'w', encoding='UTF-8', newline='') as f:
            headers = list(column[0].keys())
            writer = csv.DictWriter(f, headers)
            writer.writeheader()
            for row in column:
                writer.writerow(row)


if __name__ == '__main__':
    print("开始了！==============")
    file_r = '../数据集/五育成绩表.csv'
    file_w = '..' + file_r.strip('.csv') + '_预处理完成_平均值.csv'
    DP = DATA_Pretreatment()
    DP.Pretreatment_avg(file_r, file_w)
    print("预处理结束！==============")
