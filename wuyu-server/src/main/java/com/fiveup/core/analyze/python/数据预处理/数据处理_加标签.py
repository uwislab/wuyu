"""
K-Means算法加标签，用于测量精确度
"""

import csv

import numpy as np


def Deal_Tag(filename_r, filename_w):
    with open(filename_r, "r", encoding="utf-8") as f:
        data = csv.DictReader(f)
        column = [row for row in data]

    # print(column)
    for c in column:
        if float(c['sum']) >= 320:
            c['sum'] = '优秀'
        elif 290 <= float(c['sum']) < 320:
            c['sum'] = '良好'
        elif 240 <= float(c['sum']) < 290:
            c['sum'] = '一般'
        else:
            c['sum'] = '不及格'

    # print(column)

    with open(filename_w, 'w', encoding='UTF-8', newline='') as f:
        headers = list(column[0].keys())
        writer = csv.DictWriter(f, headers)
        writer.writeheader()
        for row in column:
            writer.writerow(row)


if __name__ == '__main__':
    print("开始了！==============")
    file_r = '../测试/五育成绩表_预处理完成_赋0_规范化完成_排序.csv'
    file_w = '..' + file_r.strip('.csv') + '_Tag.csv'
    Deal_Tag(file_r, file_w)
    print("加Tag处理结束！==============")
