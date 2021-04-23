

from pycomm.ab_comm.slc import Driver as SlcDriver
import logging
import sys, getopt
from time import sleep

tag_name = sys.argv[1]
tag_value = sys.argv[2]

c = SlcDriver()
if c.open('192.168.0.232'):
    	try:
        	c.write_tag(tag_name, int(tag_value))
		print c.read_tag('N7:2')
		print c.read_tag('N7:3')
		print c.read_tag('B3:35/0')
		print c.read_tag('F8:100')
		print c.read_tag('F8:101')
    	except Exception as e:
        	print e
        	pass
c.close()
