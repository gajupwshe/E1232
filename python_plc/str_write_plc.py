

from pycomm.ab_comm.slc import Driver as SlcDriver
import logging
import sys, getopt

tag_name = sys.argv[1]
tag_value = sys.argv[2]

c = SlcDriver()
if c.open('192.168.0.232'):
    try:
	if(tag_value == '1'):
        	c.write_tag(tag_name, True)
	else:
		c.write_tag(tag_name, False)
    except Exception as e:
        print e
        pass
    c.close()
