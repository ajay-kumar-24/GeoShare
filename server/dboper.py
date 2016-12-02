import psycopg2

def connect_db():
    conn = psycopg2.connect(dbname="postgis_test")
    return conn


def get_rows(conn,tablename):
    cur = conn.cursor()
    cur.execute("SELECT * FROM "+tablename+" ;")
    rows = cur.fetchall()
    conn.commit()
    conn.close()
    cur.close()
    return rows

def add_rows(conn,post_id,user_id,post,lat,lon):
    cur = conn.cursor()
   # latlon = "ST_GeomFromText('POINT("+lat+" "+lon+")', 4326)"
    coordinates = "POINT(%s %s)" % (lon, lat)

    #point = ppygis.ST_MakePoint(lat,lon)
    #statement = "INSERT INTO posts (post_id, user_id,post,geom) VALUES (%s, %s, %s, %s)",(post_id,user_id,post,ppygis.ST_GeomFromText(ppygis.ST_MakePoint(point),4326))
    #print statement
    cur.execute("INSERT INTO posts (post_id, user_id,post,geom) VALUES (%s, %s, %s, ST_GeomFromText(%s,4326))",(post_id,user_id,post,coordinates))
    conn.commit()
    conn.close()
    cur.close()

def get_nearest_neighbor(conn,lat,lon):
    cur = conn.cursor()
    coordinates = "POINT(%s %s)" % (lon,lat)

    cur.execute("SELECT ST_X(geom), ST_Y(geom), post,user_id FROM posts WHERE ST_Distance_Sphere(geom, ST_MakePoint(%s,%s)) <= 2 * 1609.34 LIMIT 10",(lon,lat))
    rows = cur.fetchall()
    #print rows
    conn.close()
    cur.close()
    return rows

def get_user_name(conn,user_id):
    cur = conn.cursor()
    cur.execute("SELECT USERNAME FROM USERS WHERE ID ="+str(user_id))
    rows = cur.fetchall()
    #print rows
    conn.close()
    if len(rows)>0:
        return rows[0][0]
    else:
        return ""

#print get_user_name(connect_db(),3)
#print get_nearest_neighbor(connect_db(),34.0220826,-118.2839521)
