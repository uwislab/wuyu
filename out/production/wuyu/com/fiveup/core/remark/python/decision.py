import math
import numpy as np
import csv
import sys
import pandas as pd
from numpy import *
'''创建实例数据集'''


def createDataSet1():  # 创造示例数据
    # data = pd.read_csv(r"C:\Users\potat\Desktop\pythonProject\b.csv")
    data = pd.read_csv(r"src\main\java\com\fiveup\core\remark\python\b.csv")
    data = np.array(data)
    data = data.tolist()
    dataSet = []
    for i in data:
        dataSet.append(i)
    # print(dataSet)123
    labels = ['A', 'B', 'C', 'D', 'E']  # 五个特征
    return dataSet, labels


'''计算熵'''


def calcEntropy(dataSet):
    numFeatures = len(dataSet[0])
    # print('属性或特征个数 =', numFeatures)
    numDataSet = len(dataSet)
    # print('数据集长度 =', numDataset)
    typeCount = {}
    # pi = ni / n; ni:第i类样本数；n:总样本数
    # 遍历每行，统计不同类别出现次数
    for row in dataSet:
        if row[-1] not in typeCount.keys():
            typeCount[row[-1]] = 0  # row[-1]为类别，若类别还没统计过，加入字典，出现次数为0,后面+1
        typeCount[row[-1]] += 1  # 若类别统计过，出现次数+1
    # print('每类样本出现个数', typeCount)123

    entropy = 0.0  # 熵
    for num in typeCount.values():
        p = num / numDataSet
        entropy -= p * math.log(p, 2)
    # print('entropy =', entropy)123
    return entropy


'''选择最优的分类特征'''


def chooseBestFeatureToSplit(dataSet):
    numFeatures = len(dataSet[0]) - 1  # 不算最后一列,那是类别：0，1，2
    baseEntropy = calcEntropy(dataSet)  # 原始的信息熵
    bestInfoGain = 0  # 信息增益
    bestFeatIndex = -1  # 最优特征下标
    for i in range(numFeatures):
        featList = [row[i] for row in dataSet]  # 第i列，特征组成的列表
        uniqueFeatValues = set(featList)  # 用集合去重，得到特征值，如{'a', 'b'}

        newEntropy = 0
        for value in uniqueFeatValues:  # 用特征值中的每一个 划分数据集
            subDataSet = splitDataSet(dataSet, i, value)
            # print('划分后的子集 :', subDataSet)123
            weight = len(subDataSet) / float(len(dataSet))  # 权重，子集个数/ 全集个数
            newEntropy += weight * calcEntropy(subDataSet)  # 按某个特征分类后的熵 = 累加 子集熵*weight
        # print('划分后的信息熵 :', newEntropy)123
        infoGain = baseEntropy - newEntropy  # 信息增益， 越大越好
        if infoGain > bestInfoGain:
            bestInfoGain = infoGain
            bestFeatIndex = i
    return bestFeatIndex


'''
划分数据集
axis : 最优特征BestFeature(BF)所在下标
value : BF能取得值
'''


def splitDataSet(dataSet, axis, value):  # 按某个特征分类后的数据
    retDataSet = []
    for row in dataSet:
        if row[axis] == value:
            reducedFeatvec = row[:axis]  # 取出分裂特征前的数据集
            reducedFeatvec.extend(row[axis + 1:])  # 取出分裂特征后的数据集,合并两部分数据集
            retDataSet.append(reducedFeatvec)  # 本行取得的去除value的列表 加入总列表
    return retDataSet


'''统计，多者胜出'''


def majorityCnt(typeList):  # 按分类后类别数量排序，比如：最后分类为两 1 一 2，则判定为1；
    typeCount = {}
    for t in typeList:
        if t not in typeCount.keys():
            typeCount[t] = 0
        typeCount[t] += 1
    # print('typeCount =', typeCount)123
    sortedTypeCount = sorted(typeCount.items(), key=lambda x: x[1], reverse=True)  # 从大到小排列，结果如[('1', 2), ('2', 1)]
    # print('少数服从多数，多数为 :', sortedTypeCount[0][0])123
    return sortedTypeCount[0][0]


'''递归建树'''


# def createTree(dataSet, labels):
#     typeList = [row[-1] for row in dataSet]  # 类别：1，2或3
#     if typeList.count(typeList[0]) == len(typeList):  # 若只有一个类，直接返回
#         return typeList[0]
#     if len(dataSet[0]) == 1:  # 若最后只剩下一个类别属性
#         return majorityCnt(typeList)
#     bestFeatIndex = chooseBestFeatureToSplit(dataSet)  # 最优特征下标和对应特征
#     bestFeat = labels[bestFeatIndex]
#     # print('bestFeatureIndex =', bestFeatIndex)123
#     # print('***********最优特征值 =', bestFeat, end='***********\n')123
#
#     myTree = {bestFeat: {}}  # 分类结果以字典形式保存
#     del (labels[bestFeatIndex])
#
#     uniqueVals = set()  # 最优特征能取的值，用set保证无重复
#     {uniqueVals.add(row[bestFeatIndex]) for row in dataSet}
#     # print(f'{bestFeat} 能取的值 :', uniqueVals)123
#     for value in uniqueVals:
#         subLabels = labels  # labels里已经删去了最优特征，用subLabels为了区分更明显
#         myTree[bestFeat][value] = createTree(splitDataSet(dataSet, bestFeatIndex, value), subLabels)
#     return myTree

def createTree(dataSet, labels):
    typeList = [row[-1] for row in dataSet]  # 类别：1，2或3
    if typeList.count(typeList[0]) == len(typeList):  # 若只有一个类，直接返回
        return typeList[0]
    if len(dataSet[0]) == 1:  # 若最后只剩下一个类别属性
        return majorityCnt(typeList)
    bestFeatIndex = chooseBestFeatureToSplit(dataSet)  # 最优特征下标和对应特征
    bestFeat = labels[bestFeatIndex]
    # print('bestFeatureIndex =', bestFeatIndex)123
    # print('***********最优特征值 =', bestFeat, end='***********\n')123

    myTree = {bestFeat: {}}  # 分类结果以字典形式保存
    subLabels = labels[:]
    del (subLabels[bestFeatIndex])

    uniqueVals = set()  # 最优特征能取的值，用set保证无重复
    {uniqueVals.add(row[bestFeatIndex]) for row in dataSet}
    # print(f'{bestFeat} 能取的值 :', uniqueVals)123
    for value in uniqueVals:
        subLabels = labels[:]  # labels里已经删去了最优特征，用subLabels为了区分更明显
        myTree[bestFeat][value] = createTree(splitDataSet(dataSet, bestFeatIndex, value), subLabels)
    return myTree

if __name__ == '__main__':
    dataset, labels = createDataSet1()
    res = createTree(dataset, labels)  # 输出决策树模型结果
    dic = {}
    labels = ['A', 'B', 'C', 'D', 'E']
    # for i in range(5):
    #     dic[labels[i]] = int(input('输入第' + str(i+1) + '个数，每个数以回车结束'))
    # dic['A'] = 2
    # dic['B'] = 1
    # dic['C'] = 2
    # dic['D'] = 2
    # dic['E'] = 2

    dic['A'] = int(sys.argv[1])
    dic['B'] = int(sys.argv[2])
    dic['C'] = int(sys.argv[3])
    dic['D'] = int(sys.argv[4])
    dic['E'] = int(sys.argv[5])

    # print(dic)
    # print(dic.items())123
    grade = [0,1,2]
    # print(res)123
    flag = 0
    while True:
        for x in dic.items():
            if x[0] in res:
                res = res[x[0]][x[1]]
                # print(res)123
                if res in grade:
                    flag = 1
                    break
        if flag == 1:
            break
    print(res)
# if __name__ == '__main__':
#     dataset, labels = createDataSet1()
#     res = createTree(dataset, labels)  # 输出决策树模型结果
#     dic = {}
#     labels = ['A', 'B', 'C', 'D', 'E']
#     for i in range(5):
#         dic[labels[i]] = int(input('输入第' + str(i+1) + '个数，每个数以回车结束'))
#     print(dic.items())
#     grade = [0,1,2]
#     print(res)
#     flag = 0
#     while True:
#         for x in dic.items():
#             if x[0] in res:
#                 res = res[x[0]][x[1]]
#                 print(res)
#                 if res in grade:
#                     flag = 1
#                     break
#         if flag == 1:
#             break
#     print('最后判断等级为：',res)


