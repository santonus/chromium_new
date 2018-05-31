import pandas as pd
import numpy as np
import datetime as datetime
from igraph import*
#df = pd.read_csv('personreview_modified.csv')  #Enter path of the file personreview.csv
#person = pd.read_csv('person.csv')
#review = pd.read_csv('df = pd.read_csv("data/result.csv")')
def generatenetwork (start, end, index):
    
    
    df1 = df
    df1['date'] = df['date'].apply(pd.to_datetime, errors='coerce')      #typecasts the column date to type datetime
    #print(df1.dtypes)
    df1=df1.sort_values(by='date')                                        #Sorts the dataframe in ascending order of dates
    #print(df1)
    #start = input("Enter start date (yyyymmdd):")
    #end = input("Enter end date (yyyymmdd):")
    s = datetime.datetime.strptime(str(start), '%Y-%m-%d %H:%M:%S.%f')
    e = datetime.datetime.strptime(str(end) , '%Y-%m-%d %H:%M:%S.%f')
    df1 = df1.set_index(['date'])                                         #Required for the next step.  Sets date as an index
    df1 = df1.loc[s:e]                                                    #Takes only the comments made between dates s and e
    df1 = df1.reset_index(drop=True)                                      #Drops the index column 'date' as it is not needed anymore  
    df1 = df1.drop_duplicates(subset=['sender', 'issue'])                 #Removes duplicate columns
    print('top')
    print(df1.shape[0])
    
    ##################################################################################
    #    Compares the data frames w.r.t the set keys and extracts selected rows      #
    ##################################################################################

    person = df1.drop_duplicates(['sender'])
    review = df1.drop_duplicates(['issue'])
    keys = ['sender']
    i1 = df1.set_index(keys).index
    i2 = person.set_index(keys).index
    df1 = df1[i1.isin(i2)]
    keys = ['issue']
    i1 = df1.set_index(keys).index
    i2 = review.set_index(keys).index
    df1 = df1[i1.isin(i2)]
    #print(df1)
    ##################################################################################
    #                          Initialize the graph                                  #
    ##################################################################################
    g = Graph.Full(0)
    print(person.shape[0])
    for i in (person['sender']):
        g.add_vertices([i])
        #print(g)
    g.es["weight"]=0
    ##################################################################################
    #                    Creating edges and assigning edge weights                   #
    ##################################################################################
    df1 = df1[df1.duplicated(subset=['issue'] ,keep=False)]               #Keeps only the the reviews which occur twice. This is done because 
    #print('edgecre')                                                            
    sender = df1[['sender']]                                                                                                                                               #
    #print(sender)              2008-09-02 20:13:34.526552 2008-10-03 05:28:56.748409                                                                                                                                            #  CAN BE IMPROVED
    sender = sender.drop_duplicates(subset=['sender'],keep= 'first' )     #Gets unique sender names along with issues (The  dataframe sender is used for looping through)  #
    print('1')                         
    for i in (sender['sender']):   
        set1=df1[df1['sender'].isin([i])]                                 #Create first set                                                           
        sender = sender.drop(sender.index[0])                             #Deletes the first element of sender   
        for j in (sender['sender']):                                      #Creates second set               
            set2=df1[df1['sender'].isin([j])]
            newset = pd.concat([set1, set2])                              #set union
            #print(newset)
            newset = newset[newset.duplicated(subset=['issue'] ,keep='first')]  #Set intersection
            weight = (newset.shape[0])
            if(weight != 0):
                g[i,j] = weight
    g.vs['id'] = g.vs['name']                                             #assigns vertex id
    g.write_pajek(("T" + str(index+1) + "-nw.net"))                                             #writes to pajek file
generatenetwork('2008-09-02 20:13:34.526552', '2008-10-03 05:28:56.748409',1 )
    #print(df1)

