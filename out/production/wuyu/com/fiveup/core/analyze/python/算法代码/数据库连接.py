import MySQLdb


def Conn():
    print('连接到mysql服务器...')
    conn = MySQLdb.Connect(host='82.157.231.88',
                           port=3306,
                           user='root',
                           passwd='123456',
                           db='fiveup',
                           charset='utf8')
    print('连接上了!')
    return conn