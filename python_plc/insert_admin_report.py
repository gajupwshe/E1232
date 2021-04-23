

from pycomm.ab_comm.slc import Driver as SlcDriver
import logging
import sys, getopt
import mysql.connector

db_host = sys.argv[1]
db_user = sys.argv[2]
db_password = sys.argv[3]
db_name = sys.argv[4]
db_sp_one = sys.argv[5]
db_sp_two = sys.argv[6]


conn = mysql.connector.connect(host = db_host, user = db_user, password = db_password, database = db_name)
print "connected"
a = conn.cursor()
a.callproc(db_sp_one)
conn.commit()
c = SlcDriver()
if c.open('192.168.0.232'):
	machine_mode = c.read_tag('N7:0')
	offline_online = c.read_tag('B3:6/11')

args = (machine_mode,offline_online)

print args

a.callproc(db_sp_two, args)
conn.commit() 
a.close() 
conn.close()
c.close()

