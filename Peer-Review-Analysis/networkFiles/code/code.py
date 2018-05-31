import pandas as pd
import numpy as numpy
import datetime as datetime
from igraph import *
from timeseriesAnanlysis import time
#from networkgenerator import generatenetwork
#df = pd.read_csv('personreview.csv')  #Enter path of the file personreview.csv 
review = pd.read_csv("/home/poulami/Dropbox/Poulami-project/to-share/data/result.csv")     
head = datetime.datetime.strptime('2008-09-02 20:13:34.526552', '%Y-%m-%d %H:%M:%S.%f') 
tail = datetime.datetime.strptime('2012-10-31 03:02:05.619400', '%Y-%m-%d %H:%M:%S.%f') 
print(head,'\n',tail)
step = (tail-head)/50
print(step)
sum=0
for i in range(0,50):
        start  = head+i*step
        end = head +(1+i)*step
        #print(i,start, head, end)
        #generatenetwork(head, end,i)
        x=(time(start, end,i))
        sum+=x
        print(x)
print(sum)

