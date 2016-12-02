import dboper
def get_nn_func(lat,lon):
    res_map = {}
    res = []
    rows = dboper.get_nearest_neighbor(dboper.connect_db(),lat,lon)
    print rows
    for val in rows:
        temp = {}
        temp['lat'] = val[1]
        temp['lon'] = val[0]
        temp['post'] = val[2]
        temp['user'] = dboper.get_user_name(dboper.connect_db(),val[3])

        res.append(temp)
    res_map['res'] = res
    return res
#print get_nn_func(1,34.0220826,-118.2839521)