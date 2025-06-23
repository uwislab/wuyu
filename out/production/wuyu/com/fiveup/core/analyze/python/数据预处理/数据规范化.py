"""
输入：csv文件 (格式为 xxx._预处理完成.csv), csv文件 (格式为 xxx._规范化完成.csv), 最大最小规范化区间 (最小，最大 )
输出：csv文件 (格式为 xxx._规范化完成.csv)
作用：最大最小规范化
"""
import csv
import pandas as pd  # 导入pandas包


def Data_Normalization(file_read, file_write, new_min_score, new_max_score):

    WuYu = ['de', 'zhi', 'ti', 'mei', 'lao']
    WY_score = []
    WY_mm_score = []
    CSV_data_other = []

    with open(file_read, "r", encoding="utf-8") as f:
        reader = csv.reader(f)
        column_list = [row for row in reader]
        CSV_header = column_list[0]
    # print(CSV_header)

    with open(file_read, "r", encoding="utf-8") as f:
        data = csv.DictReader(f)
        column_dict = [row for row in data]
    # print(column_dict)

    max_score = 0
    min_score = 100

    # 除成绩外的信息
    for i in range(0, len(CSV_header) - 5):
        with open(file_read, encoding="utf-8") as f:
            data = csv.DictReader(f)
            CSV_data_other.append([row[CSV_header[i]] for row in data])
    # print(CSV_data_other)

    for w in WuYu:
        with open(file_read, "r", encoding="utf-8") as f:
            data = csv.DictReader(f)
            score_w = [row[w] for row in data]
            score_w = list(map(float, score_w))
            # print(score_w)
            WY_score.append(score_w)

            WY_mm_score.append(min(score_w))
            WY_mm_score.append(max(score_w))

    # print(new_min_score, new_max_score)
    # print(WY_score)
    # print(WY_mm_score)
    data_change = pd.read_csv(file_read, encoding='utf-8')
    for i in range(len(WuYu)):
        low = WY_mm_score[i * 2]
        high = WY_mm_score[i * 2 + 1]
        # print(low, high)
        for j in range(len(WY_score[i])):
            WY_score[i][j] = ((WY_score[i][j] - low) / (high - low)) * (new_max_score - new_min_score) + new_min_score
        data_change[WuYu[i]] = WY_score[i]
    # print(WY_score)
    data_change.to_csv(file_write, index=False, encoding='utf-8')


if __name__ == '__main__':
    print("开始了！==============")
    file_r = '../数据集/五育成绩表_预处理完成_平均值.csv'
    file_w = '..' + file_r.strip('_预处理完成.csv') + '_规范化完成.csv'
    Data_Normalization(file_r, file_w, 0, 100)
    print("规范化结束！==============")
