 

import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import logging
import sys, getopt
import mysql.connector

db_host = sys.argv[1]
db_user = sys.argv[2]
db_password = sys.argv[3]
db_name = sys.argv[4]
db_sp_one = sys.argv[5]
db_sp_two = sys.argv[6]
ip=''
conn = mysql.connector.connect(host = db_host, user = db_user, password = db_password, database = db_name)

#print "connected"
a = conn.cursor()
# a.callproc(db_sp_one)
conn.commit()

a.execute('SELECT ip FROM plc_ip')

for row in a:
    ip =row[0]

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
    plc.connect(ip,0,1)
    stabilization_time_set = ReadMemory(plc,48,0,S7WLWord)
    holding_time_set = ReadMemory(plc,50,0,S7WLWord)
    drain_time_set = ReadMemory(plc,60,0,S7WLWord)
    over_all_time_time = ReadMemory(plc,66,0,S7WLWord)
    hydro_set = ReadMemory(plc,32,0,S7WLReal)
    hydraulic_set = ReadMemory(plc,36,0,S7WLReal)
    hydro_actual_a_pressure = ReadMemory(plc,20,0,S7WLReal)
    hydro_actual_b_pressure = ReadMemory(plc,24,0,S7WLReal)
    hydraulic_actual_pressure =ReadMemory(plc,28,0,S7WLReal)
    cycle_status= ReadMemory(plc,76,0,S7WLWord)
    stabilization_timer= ReadMemory(plc,48,0,S7WLWord)
    holding_timer= ReadMemory(plc,50,0,S7WLWord)
    drain_timer= ReadMemory(plc,60,0,S7WLWord)
    machine_mode =ReadMemory(plc,2,0,S7WLWord)
    offline_online = 'NA'
    pop_ups = ReadMemory(plc,18,0,S7WLWord)
    invalid = ReadMemory(plc,0,4,S7WLBit)
    result = ReadMemory(plc,78,0,S7WLWord)
    bubble = ReadMemory(plc,108,0,S7WLWord)

args = (stabilization_time_set,holding_time_set,drain_time_set,over_all_time_time,hydro_set,hydraulic_set,hydro_actual_a_pressure,hydro_actual_b_pressure,hydraulic_actual_pressure,cycle_status,stabilization_timer,holding_timer,drain_timer,machine_mode,offline_online,pop_ups,result,invalid,bubble) 
 
print args
a.callproc(db_sp_one, args)

conn.commit() 
a.close() 

