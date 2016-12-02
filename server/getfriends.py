import dboper

def get_friend_id(user):
    conn = dboper.connect_db()
    cur = conn.cursor()
    print 'HI'
    cur.execute("SELECT friends FROM friends WHERE id=%s;"%(user))
    print 'HELLO'
    rows = cur.fetchall()
    print rows
    conn.commit()
    conn.close()
    cur.close()
    return rows

def add_friends():
	print 'enter'
 	conn = dboper.connect_db()
 	cur = conn.cursor()
 	cur.execute("SELECT * FROM friends;")
 	rows = cur.fetchall()
 	print 'hi'
 	conn.commit()
 	conn.close()
 	cur.close()
 	return rows

def get_name(user):
	conn = dboper.connect_db()
	cur = conn.cursor()
	print 'name'
	cur.execute("SELECT username FROM users WHERE id=%s;"%(user))
	rows = cur.fetchall()
	print rows
	conn.commit()
	conn.close()
	cur.close()
	return rows








