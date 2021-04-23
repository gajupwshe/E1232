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
db_sp_one = 'insert_alarm_sp'
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
 
    pressure_transmitter1=ReadMemory(plc,14,5,S7WLBit)
    #pressure_transmitter1="NA"
    pressure_transmitter2=ReadMemory(plc,14,6,S7WLBit)
    hydraulic_motor=ReadMemory(plc,44,0,S7WLWord)
    pre_filling_motor=ReadMemory(plc,46,0,S7WLWord)
    valve_drain=ReadMemory(plc,48,0,S7WLWord)
    oil_level = "NA"
    air_inlet = "NA"
    temp = "NA"
    h5 ="NA"
    h2 = "NA"
    m3 ="NA"
    m4 ="NA"
    m5 = "NA"
    machine_mode = ReadMemory(plc,14,7,S7WLBit)
    offline_online = "NA"

args = (pressure_transmitter1,pressure_transmitter2,hydraulic_motor,pre_filling_motor,valve_drain,oil_level,air_inlet,temp,h5,h2,m3,m4,m5,machine_mode,offline_online)
#print args
a.callproc(db_sp_one, args)

print args
#db_sp_two='truncate_alarm_sp'
#a.callproc(db_sp_two, args)
conn.commit() 
a.close() 

