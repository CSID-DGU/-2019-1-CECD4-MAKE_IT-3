
# coding: utf-8

# In[37]:


import pandas as pd
import tensorflow as tf
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import confusion_matrix
from sklearn.preprocessing import StandardScaler

drum = pd.read_csv("test.csv",header=None)


# In[38]:


X = drum.iloc[:, 0:10].values
y=drum.iloc[:, 10].values
print(x[:37, :])


# In[39]:


X_train, X_test, y_train, y_test = train_test_split(X,y,random_state=0)


# In[40]:


forest = RandomForestClassifier(n_estimators=100,random_state=0)
forest.fit(x_train, y_train)

print("훈련 세트 정확도: {:.3f}".format(forest.score(X_train,y_train)))
print("테스트 세트 정확도: {:.3f}".format(forest.score(X_test, y_test)))


# In[35]:


import matplotlib.pyplot as plt
import mglearn

fig, axes = plt.subplots(2, 3, figsize=(20, 10))
for i, (ax, tree) in enumerate(zip(axes.ravel(), forest.estimators_)):
    ax.set_title("tree {}".format(i))
    mglearn.plots.plot_tree_partition(x, y, tree, ax=ax)
    
mglearn.plots.plot_2d_separator(forest, x, fill=True, ax=axes[-1, -1], alpha=.4)
axes[-1, -1].set_title("랜덤 포레스트")
mglearn.discrete_scatter(x[:, 0], x[:, 1], y)


# In[42]:


import matplotlib.pyplot as plt
import mglearn
forest.fit(X, y)
importances = forest.feature_importances_
std = np.std([tree.feature_importances_ for tree in forest.estimators_],
             axis=0)
indices = np.argsort(importances)
plt.figure()
plt.title("Feature importances")
plt.barh(range(X.shape[1]), importances[indices],
       color="r", xerr=std[indices], align="center")

plt.yticks(range(X.shape[1]), indices)
plt.ylim([-1, X.shape[1]])
plt.show()

