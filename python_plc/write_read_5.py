

from pycomm.ab_comm.slc import Driver as SlcDriver
import logging
import sys, getopt

tag_name = sys.argv[1]
tag_value = sys.argv[2]

c = SlcDriver()
if c.open('192.168.0.232'):
    	try:
        	c.write_tag(tag_name, int(tag_value))
		print c.read_tag('T4:2.PRE')
		print c.read_tag('T4:3.PRE')
		print c.read_tag('T4:5.PRE')
		print c.read_tag('F8:100')
		print c.read_tag('F8:101')
		print c.read_tag('B3:35/0')
    	except Exception as e:
        	print e
        	pass
c.close()
