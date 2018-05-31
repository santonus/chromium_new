import pandas as pd
import datetime as datetime

review = pd.read_csv("/home/poulami/Dropbox/Poulami-project/to-share/data/result.csv")   
review['created'] = review['created'].apply(pd.to_datetime, errors='coerce')      #typecasts the column date to type datetime
review = review.sort_values(by=['created'])
review = review.set_index(['created'])                                         #Required for the next step.  Sets date as an index

print("imported")
def time (start, end, index):
    global review
    s = datetime.datetime.strptime(str(start), '%Y-%m-%d %H:%M:%S.%f')
    e = datetime.datetime.strptime(str(end) , '%Y-%m-%d %H:%M:%S.%f')
    df = review.loc[s:e]                                                    #Takes only the comments made between dates s and e
    df = df.reset_index(drop=True)    
    return df.shape[0]