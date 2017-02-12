import dboper

def get_friend_id(user):
    conn = dboper.connect_db()
    cur = conn.cursor()
    cur.execute("SELECT friends FROM friends WHERE id=%s;"%(user))
    rows = cur.fetchall()
    conn.commit()
    conn.close()
    cur.close()
    return rows

def add_friends():
 	conn = dboper.connect_db()
 	cur = conn.cursor()
 	cur.execute("SELECT * FROM friends;")
 	rows = cur.fetchall()
 	conn.commit()
 	conn.close()
 	cur.close()
 	return rows

def get_name(user):
	conn = dboper.connect_db()
	cur = conn.cursor()
	cur.execute("SELECT username FROM users WHERE id=%s;"%(user))
	rows = cur.fetchall()
	conn.commit()
	conn.close()
	cur.close()
	return rows








