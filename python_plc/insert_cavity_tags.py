

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
#print "connected"truncate_test_tags_sp 'insert_test_tags_sp'
a = conn.cursor()
a.callproc(db_sp_one)
conn.commit()
c = SlcDriver()
if c.open('192.168.0.232'):
	#New tags updated on 04/08/2018
	#stabilization_timer = c.read_tag('T4:2.ACC')
	#holding_timer = c.read_tag('T4:3.ACC')
	#drain_delay = c.read_tag('T4:5.ACC')
	#drain_timer = c.read_tag('T4:4.ACC')
	#over_all_time = c.read_tag('T4:15.ACC')
	hydraulic_actual_pressure = c.read_tag('F8:9')
	#hydro_actual_a_pressure = c.read_tag('F8:103')
	#hydro_actual_b_pressure = c.read_tag('F8:104')
	machine_mode = c.read_tag('N7:0')
	offline_online = c.read_tag('B3:6/11')
	#cycle_status = c.read_tag('N7:23')
	result = c.read_tag('N7:19')
	#pop_ups = c.read_tag('N7:9')
	#second_pop = c.read_tag('B3:2/2')
	#third_pop = c.read_tag('B3:2/3')
	#Actual_leakage to add

args = ('NA','NA','NA','NA','NA',hydraulic_actual_pressure,'NA','NA',machine_mode,offline_online,'NA',result,'NA') 
 
#print args
a.callproc(db_sp_two, args)

conn.commit() 
a.close() 
conn.close()
c.close()
