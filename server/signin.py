import dboper

def sign_in(user,password):
    rows = dboper.get_rows(dboper.connect_db(),"users")
    for entry in rows:
        if user == entry[1] and password == entry[2]:
            return str(entry[0])
    return "no entry found"

#print sign_in("Appy","password")




