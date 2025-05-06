import 数据库连接 as sj_sql
import csv
import pandas as pd


def SQLDATA_CSV(filename):
    conn = sj_sql.Conn()
    cur = conn.cursor()

    into = "SELECT * FROM ana_wuyu_score"
    cur.execute(into.encode('utf-8'))
    data = cur.fetchall()

    conn.commit()
    conn.close()

    with open(filename, mode='w', encoding='utf-8') as f:
        write = csv.writer(f, dialect='excel')
        for item in data:
            write.writerow(item)

    df = pd.read_csv(filename, header=None, names=["id", "stu_num", "name", "grade", "de", "zhi", "ti", "mei", "lao"])
    df.to_csv(filename, index=False)


if __name__ == '__main__':
    filename = '../数据集/五育成绩表.csv'  # 文件名和路径
    SQLDATA_CSV(filename)
