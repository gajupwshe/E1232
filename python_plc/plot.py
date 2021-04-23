# importing the required module.
import matplotlib.pyplot as plt
import mysql.connector
import sys, getopt

db_host = sys.argv[1]
db_user = sys.argv[2]
db_password = sys.argv[3]
db_name = sys.argv[4]
test_no = sys.argv[5]

conn = mysql.connector.connect(host = db_host, user = db_user, password = db_password, database = db_name)
#print "connected"
cursor = conn.cursor()

query0 = "SELECT test_type FROM `test_result` WHERE  `test_no` = "+test_no
cursor.execute(query0)
#conn.commit()
testType = []
for (test_type) in cursor:
	testType = test_type
print testType[0]
if(testType[0] != "DOUBLE BLOCK BLEED TEST"):
	query1 = "SELECT hydro_pressure_a_side,date_time FROM `test_result_by_type` WHERE  `test_no` = "+test_no
	cursor.execute(query1)
	x = []
	y = []
	for (hydro_pressure_a_side, date_time) in cursor:
		  f = float(hydro_pressure_a_side)
		  x.append(f)
		  y.append(date_time)
	plt.plot(y, x, color='black', linewidth = 0.5,marker='o', markerfacecolor='black', markersize=5,label = "Pressure vs time")
	mng = plt.get_current_fig_manager()
	mng.resize(1700,500)
	plt.title(test_no+"_"+testType[0])
	plt.legend()
	print "OTHER"
	plt.show()
else:
	query1 = "SELECT hydro_pressure_a_side,hydro_pressure_b_side,date_time FROM `test_result_by_type` WHERE  `test_no` = "+test_no
	cursor.execute(query1)
	x1 = []
	x2 = []
	y = []
	for (hydro_pressure_a_side,hydro_pressure_b_side, date_time) in cursor:
		  fa = float(hydro_pressure_a_side)
		  fb = float(hydro_pressure_b_side)
		  x1.append(fa)
		  x2.append(fb)
		  y.append(date_time)
	plt.plot(y, x1, color='black', linewidth = 0.5,marker='o', markerfacecolor='black', markersize=5,label = "Pressure(A side) vs time")
	plt.plot(y, x2, color='blue', linewidth = 0.5,marker='o', markerfacecolor='blue', markersize=5,label = "Pressure(B side) vs time")
	mng = plt.get_current_fig_manager()
	mng.resize(1700,500)
	plt.title(test_no+"_"+testType[0])
	plt.legend()
	print "DOUBLE BLOCK"
	plt.show()
cursor.close() 
conn.close()
c.close()
