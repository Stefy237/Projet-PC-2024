**Tableau garde action**

| Operation           |  Pre-action     |  Garde     | Post-action   |
|---------------------|-----------------|------------|---------------|
| put (Message m)     |       /         | nfull < bufSz  | nfull++       |
| Message get()       |      /          | nfull <=0  | nfull--       |