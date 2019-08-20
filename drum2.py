
# coding: utf-8

# In[1]:


import numpy as np
import pandas as pd
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import confusion_matrix
print("hello")


# In[2]:


dataset=pd.read_csv("result2.csv")
print("fihished")


# In[3]:


dataset.head()


# In[4]:



x=dataset
y=pd.read_csv("midi2.csv")x=dataset
y=pd.read_csv("midi2.csv")
print("fihished")
y.head()


# In[6]:


code2idx={'64': 1, 'unnamed': 2, '3octavee': 3, '1': 4, 'noteon': 5, 'noteoff': 6, '0': 7, '2': 8, '3': 9, '4': 10, '3octaved': 11, '60': 12, '5': 13, '6': 14, '120': 15, '7': 16, '8': 17, '9': 18, '10': 19, '11': 20, '12': 21, '13': 22, '57': 23, '58': 24, '59': 25, '61': 26, '62': 27, '63': 28, '65': 29, '66': 30, '67': 31, '68': 32}

x_out=x
y_out=y

max_idx_value=32

x=[code2idx[it]/float(max_idx_value) for it in x]
y=[code2idx[it]/float(max_idx_value) for it in y]


# In[37]:


x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.25 , random_state=21)
print("done")


# In[21]:


from sklearn.preprocessing import StandardScaler
sc=StandardScaler()
x_train=sc.fit_transform(x_train)
x_test=sc.transform(x_test)

from sklearn.decomposition import PCA
pca=PCA(n_components=2)

x_train=pca.fit_transform(x_train)
x_test=pca.transform(x_test)

print("PCA")


# In[22]:


clf=RandomForestClassifier(n_estimators=50, criterion='entropy', random_state=42)
clf.fit(x, y)


# In[8]:


print(clf.feature_importances_)


# In[9]:


print(clf.predict([[0,0,0,0,0,0,1,1,0,3]]))


# In[10]:


model=RandomForestClassifier(max_depth=10, n_estimators=100, random_state=0).fit(x,y)
print("done")


# In[11]:


a, b, c, d, e, f, g, h, i, j=input('정보를 입력하세요: ').split()


# In[12]:


print(clf.predict([[a, b, c, d, e, f, g, h, i, j]]))


# In[13]:


newdata=pd.read_csv("drumtest.csv")
print("fihished")


# In[14]:


newdata.head()


# In[15]:


pred=clf.predict(newdata)
print(pred)


# In[16]:


np.save('predicted.npy',pred)


# In[17]:


pp=np.load('predicted.npy')
print(pp)

