

from pycomm.ab_comm.slc import Driver as SlcDriver
import logging
import sys, getopt


c = SlcDriver()
if c.open('192.168.0.232'):
	print c.read_tag('N7:0')
	print c.read_tag('B3:6/11')
	c.close()

