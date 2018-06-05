# Prerequisites

The first step towards generating the peer-review social network is to obtain the necessary data from the mysql datadump. 
1. Navigate to the directory /chromium_new/Peer-Review-Analysis/data/Obtaindata
1. In a terminal window run the sql scripy `result.sql` : ```mysql -u <user> -p<password> <dbname> < result.sql``` or if in mysql shell you can type: ```use db_name;``` then  ```source /path/to/result.sql;```
1. Similarly run ```mysql -u <user> -p<password> <dbname> < personreview.sql``` or if in mysql shell you can type: ```use db_name;``` then  ```source /path/to/personreview.sql;``` 

On running the two scripts, the dataframes result.csv and personreview.csv will be obtained. 

Personreview.csv contains details on the indivdual comments made on easch issue. The column obtained will be:
1. issue
1. sender
1. date 
1. approval

result.csv contains details on the indivdual comments made on easch issue. The column obtained will be:
1. A: number of comments on the review, 
1. B: number of unique person(s) who have posted at least one comment on the review, 
1. C: number of approvals on the review, 
1. D: date of creation of the review, 
1. E: date of modification of the review, 
1. F: number of days between the date of creation and modification, 
1. G: number of reviews owned by the person who owns the review.

## R libraries

## Required python libraries

### Install pandas to handle the dataset

**Conda Install**

```conda install -c anaconda pandas```

**Pip Install**

```pip3 install pandas```


### Install igraph for graph generation

**Conda Install**

```conda install -c conda-forge python-igraph```

**Pip Install**

```pip3 install igraph```

Follow the instructions in the python notebook `network generation.ipynb` to generate the network graph

