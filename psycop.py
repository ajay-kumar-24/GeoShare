import psycopg2
conn = psycopg2.connect(dbname="postgis_test")
cur = conn.cursor()
cur.execute("SELECT * FROM test;")
print cur.fetch()
conn.commit()
cur.close()
conn.close()


