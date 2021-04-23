import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import logging
import sys, getopt
import mysql.connector

#db_host = sys.argv[1]
#db_user = sys.argv[2]
#db_password = sys.argv[3]
#db_name = sys.argv[4]
db_sp_one = 'insert_test_tags_sp'
#test_no = sys.argv[6]

conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1024')

#print "connected"
a = conn.cursor()

conn.commit()
def ReadMemory(plc,byte,bit,datatype):
    result = plc.read_area(areas['MK'],0,byte,datatype)
    if datatype==S7WLBit:
        return get_bool(result,0,bit)
    elif datatype==S7WLByte or datatype==S7WLWord:
        return get_int(result,0)
    elif datatype==S7WLReal:
        return get_real(result,0)
    elif datatype==S7WLDWord:
        return get_dword(result,0)
    else:
        return None



if __name__=="__main__":
    plc = c.Client()
    #ip address of PLC
    plc.connect('192.168.20.111',0,1)
 
    hydro_Actual_pressure = ReadMemory(plc,40,0,S7WLReal)
    overall_time = ReadMemory(plc,32,0,S7WLDWord)
    holding_time = ReadMemory(plc,24,0,S7WLDWord)
    bubble="NA"
    alert = ReadMemory(plc,14,1,S7WLBit)
    

args = (holding_time,overall_time,hydro_Actual_pressure,bubble,alert) 
 
#print args
a.callproc(db_sp_one, args)

conn.commit() 
a.close() 
conn.close()

