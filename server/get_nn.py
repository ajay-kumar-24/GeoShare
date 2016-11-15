import dboper
def get_nn_func(user_id,lat,lon):
    res_map = {}
    res = []
    rows = dboper.get_nearest_neighbor(dboper.connect_db(),user_id,lat,lon)
    #print rows
    for val in rows:
        temp = {}
        temp['lat'] = val[1]
        temp['lon'] = val[0]
        temp['post'] = val[2]
        res.append(temp)
    res_map['res'] = res
    return res
#print get_nn(1,34.02222,-118.2840434)