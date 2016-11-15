import dboper
def add_posts(user_id,post,lat,lon):
    rows = dboper.get_rows(dboper.connect_db(), "posts")
    dboper.add_rows(dboper.connect_db(),len(rows)+1,user_id,post,lat,lon)
    return "status ok"
#add_posts(2,"hgqy","fawt",'gfhd')